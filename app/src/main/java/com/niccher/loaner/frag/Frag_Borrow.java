package com.niccher.loaner.frag;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Borrow extends Fragment {
    
    Button apply,next;
    Dialog myDialog;
    TextView cont,p_pesa,p_muda,p_interest, p_sababu;

    EditText amount, cause,rea;
    Spinner period;

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

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Loaner/Transactions/Borrow/"+userf.getUid());

        apply = fraghome.findViewById(R.id.prod_csend);
        amount = fraghome.findViewById(R.id.prod_camount);
        cause = fraghome.findViewById(R.id.prod_ccause);
        period = fraghome.findViewById(R.id.prod_cduration);


        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.part_poppa);
        next= myDialog.findViewById(R.id.pend_activate);

        p_pesa= myDialog.findViewById(R.id.pop_amount);
        p_muda= myDialog.findViewById(R.id.pop_time);
        p_sababu= myDialog.findViewById(R.id.pop_reason);
        p_interest= myDialog.findViewById(R.id.pop_interest);

        FloatingActionButton close = myDialog.findViewById(R.id.popclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(amount.getText().toString(),cause.getText().toString(),period.getSelectedItem().toString());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frag_Pending fraest=new Frag_Pending();
                Frag_Borrow frae=new Frag_Borrow();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                fragmentTransaction.remove(frae);
                fragmentTransaction.commit();

                myDialog.dismiss();
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
            myFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).remove(frae).commit();
        }

    }

    private void Insert(String am, String res, String tm){
        pds.setMessage("Please wait");//pds.create();
        pds.show();
        Log.e("Inserting", "Inserting start");
        String uploadId = mDatabaseRef.push().getKey();
        Calendar cal= Calendar.getInstance();
        String tt = String.valueOf(cal.getTimeInMillis());
        //String gUid, gTime, gAmount, gReason, gAccepted;

        Mod_Apply posed = new Mod_Apply(uploadId,tt,am,res,"Pending",tm);
        mDatabaseRef.child(uploadId).setValue(posed);
        SystemClock.sleep(2000);
        Log.e("Inserting", "Inserting done");
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
