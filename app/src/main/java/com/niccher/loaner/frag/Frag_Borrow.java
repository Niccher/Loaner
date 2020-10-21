package com.niccher.loaner.frag;

import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.niccher.loaner.utils.Loaner.ch_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Borrow extends Fragment implements AdapterView.OnItemSelectedListener {
    
    Button apply,next;
    Dialog myDialog;
    TextView cont,p_pesa,p_muda,p_interest, p_sababu;

    EditText amount, cause,rea;
    Spinner period;

    String interest;

    NotificationManagerCompat notmanager;

    private DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser userf;

    ProgressDialog pds;

    public Frag_Borrow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_borrow, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Borrow");

        pds=new ProgressDialog(getActivity());
        notmanager = NotificationManagerCompat.from(getActivity());

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Konstants.Data_Borrow+"/" +userf.getUid());

        apply = fraghome.findViewById(R.id.prod_csend);
        amount = fraghome.findViewById(R.id.prod_camount);
        cause = fraghome.findViewById(R.id.prod_ccause);
        period = fraghome.findViewById(R.id.prod_cduration);
        p_interest= fraghome.findViewById(R.id.int_level);

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
        
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        //Toast.makeText(getActivity(), pesa+"\n"+sababu+"\n"+muda+"\n", Toast.LENGTH_SHORT).show();
        if (pesa.isEmpty() || sababu.isEmpty() ||muda.isEmpty()){
            Toast.makeText(getContext(), "Fill all the fields to proceed", Toast.LENGTH_SHORT).show();
        }else {
            Insert(pesa, sababu, muda);

            Fragment myFragment = new Frag_Pending();
            Frag_Borrow frae=new Frag_Borrow();
            pds.dismiss();
            Bundle args= new Bundle();
            args.putString("pesa",pesa);
            args.putString("sababu",sababu);
            args.putString("muda",muda);
            args.putString("kiwango",interest);
            myFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).remove(frae).commit();
        }

    }

    private void Insert(String pesa, String sababu, String muda){

        pds.setMessage("Please wait");
        pds.show();

        if (pesa.isEmpty() || sababu.isEmpty() ||muda.isEmpty()){
            Toast.makeText(getContext(), "Fill all the fields to proceed", Toast.LENGTH_SHORT).show();
        }else {
            String uploadId = mDatabaseRef.push().getKey();
            Calendar cal= Calendar.getInstance();
            String tt = String.valueOf(cal.getTimeInMillis());
            //String gUid, gTime, gAmount, gReason, gAccepted;

            Mod_Apply posed = new Mod_Apply(uploadId,tt,pesa,sababu,"Pending",muda,interest);
            mDatabaseRef.child(uploadId).setValue(posed);
            PopNotify();
        }


        Log.e("Inserting", "Inserting done");
    }

    private void PopNotify() {
        long dd = Calendar.getInstance().getTimeInMillis();
        int ids = Integer.parseInt(String.valueOf(dd).substring(8));
        //Log.e("PopNotify()", "PopNotify: as "+ids);

        Notification nott = new NotificationCompat.Builder(getActivity(), ch_ID)
                .setSmallIcon(R.drawable.ic_passed)
                .setContentText("Your loan request has been received and will be processed shortly. \n We will keep you posted.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notmanager.notify(ids,nott);
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
        p_interest.setText("Loan interest to be applied is "+interest+"%");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
