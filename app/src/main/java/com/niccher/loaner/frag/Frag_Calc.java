package com.niccher.loaner.frag;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Calc extends Fragment {

    Button apply,calc;
    Dialog myDialog;
    TextView cont,p_pesa,p_muda,p_interest, p_sababu;

    EditText amount, cause;
    Spinner period;

    private DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser userf;

    ProgressDialog pds;

    public Frag_Calc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_calc, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Loan Interest Calculator");

        pds=new ProgressDialog(getActivity());

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Loaner/Transactions/Borrow/"+userf.getUid());

        calc = fraghome.findViewById(R.id.prod_calc0);
        apply = fraghome.findViewById(R.id.prod_apply);

        amount = fraghome.findViewById(R.id.prod_amount);
        cause = fraghome.findViewById(R.id.prod_cause);
        period = fraghome.findViewById(R.id.prod_duration);
        cont = fraghome.findViewById(R.id.prod_rate);

        cont.setVisibility(View.GONE);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont.setVisibility(View.VISIBLE);

                try {
                    double fina = Integer.parseInt(amount.getText().toString()) + (Integer.parseInt(amount.getText().toString()) * .1);
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

            Mod_Apply posed = new Mod_Apply(uploadId,tt,pesa,sababu,"Pending",muda);
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

}
