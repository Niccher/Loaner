package com.niccher.loaner.frag;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.niccher.loaner.R;
import com.niccher.loaner.mod.Mod_Apply;
import com.niccher.loaner.utils.Konstants;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Calc extends Fragment implements AdapterView.OnItemSelectedListener {

    Button apply,calc;
    TextView cont,p_pesa,p_muda,p_interest, p_sababu, p_varr;

    EditText amount, cause;
    Spinner period;

    DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser userf;

    String interest;

    ProgressDialog pds;

    public Frag_Calc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_calc, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Calculate Interest Applied");

        pds=new ProgressDialog(getActivity());

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Konstants.Data_Borrow+"/" +userf.getUid());

        calc = fraghome.findViewById(R.id.prod_calc0);
        apply = fraghome.findViewById(R.id.prod_apply);

        amount = fraghome.findViewById(R.id.prod_amount);
        cause = fraghome.findViewById(R.id.prod_cause);
        period = fraghome.findViewById(R.id.prod_duration);
        cont = fraghome.findViewById(R.id.prod_rate);
        p_varr = fraghome.findViewById(R.id.int_var);

        cont.setVisibility(View.GONE);

        ArrayAdapter<String> timeArray;
        period.setOnItemSelectedListener(this);

        timeArray = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        timeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        period.setAdapter(timeArray);

        timeArray.add("1 Week");
        timeArray.add("2 Week");
        timeArray.add("3 Week");
        timeArray.add("4 Week");
        timeArray.add("5 Week");
        timeArray.add("6 Week");

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont.setVisibility(View.VISIBLE);

                try {

                    Float full = Float.parseFloat(amount.getText().toString());
                    Float rts = Float.parseFloat(interest);

                    float fina = (float) (full + (full * rts));
                    cont.setText("Expected payback amount is "+fina);
                }catch (Exception s){
                    cont.setVisibility(View.GONE);
                    amount.setError("Type the loan you wish");
                    amount.requestFocus();
                }
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pds.setMessage("Please wait");//pds.create();
                pds.show();
                ShowDialog(amount.getText().toString(),cause.getText().toString(),period.getSelectedItem().toString());
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 5){
                    amount.setText("12000");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int iss = Integer.parseInt(String.valueOf(s));
                if (iss > 12000){
                    amount.setText("12000");
                    amount.setError("Value Can't be over 99999");
                }
            }
        });

        return fraghome;
    }

    private void ShowDialog(String pesa, String sababu, String muda) {
        if (pesa.isEmpty() || sababu.isEmpty() ||muda.isEmpty()){
            Toast.makeText(getContext(), "Fill all the fields to proceed", Toast.LENGTH_SHORT).show();
        }else {
            Log.e("Inserting", "Inserting start");
            String uploadId = mDatabaseRef.push().getKey();
            Calendar cal= Calendar.getInstance();
            String tt = String.valueOf(cal.getTimeInMillis());
            //String gUid, gTime, gAmount, gReason, gAccepted;

            Mod_Apply posed = new Mod_Apply(uploadId,tt,pesa,sababu,"Pending",muda,period.getSelectedItem().toString());
            mDatabaseRef.child(uploadId).setValue(posed);
            SystemClock.sleep(2000);
            Log.e("Inserting", "Inserting done");

            Fragment myFragment = new Frag_Pending();
            Frag_Calc frae=new Frag_Calc();
            pds.dismiss();

            Bundle args= new Bundle();
            args.putString("pesa",pesa);
            args.putString("sababu",sababu);
            args.putString("muda",muda);
            myFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();
        }
        pds.dismiss();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int timeSpinnerPosition = period.getSelectedItemPosition();
        switch (timeSpinnerPosition) {
            case 0:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "5";
                break;
            case 1:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "6";
                break;
            case 2:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "8";
                break;
            case 3:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "10";
                break;
            case 4:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "10";
                break;
            case 5:
                Log.e("setOnClickListener", "period: int as " + period.getSelectedItem());
                interest = "10";
                break;
        }
        p_varr.setText("Loan interest applied is only "+interest+"%.");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
