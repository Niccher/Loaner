package com.niccher.loaner.frag;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niccher.loaner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Loan_View extends Fragment {

    Button delete;
    TextView ps,md,sb, in, tot,when;
    TextView info;

    Bundle getbun;

    private DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;
    FirebaseUser userf;

    ProgressDialog pds;

    public Frag_Loan_View() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_loan_view, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Loan Element");

        pds=new ProgressDialog(getActivity());

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Loaner/Transactions/Borrow/"+userf.getUid());


        ps = fraghome.findViewById(R.id.pending_amount);
        md = fraghome.findViewById(R.id.pending_time);
        sb = fraghome.findViewById(R.id.pending_reason);
        tot = fraghome.findViewById(R.id.pending_total);
        in = fraghome.findViewById(R.id.pending_interest);
        when = fraghome.findViewById(R.id.pending_timeapply);

        info = fraghome.findViewById(R.id.activate_info);
        delete= fraghome.findViewById(R.id.pend_activate);

        getbun = getArguments();

        String pesa=getbun.getString("pesa");
        String muda=getbun.getString("muda");
        String sababu=getbun.getString("sababu");
        String tarehe=getbun.getString("tarehe");
        String nambari=getbun.getString("nambari");
        double fina = Integer.parseInt(pesa) + (Integer.parseInt(pesa) * .1);

        ps.setText(pesa);
        md.setText(muda);
        sb.setText(sababu);
        in.setText("10%");
        tot.setText( String.valueOf(fina));
        when.setText(tarehe);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pds.setMessage("Deleting Loan Request");
                pds.create();
                pds.show();

                mDatabaseRef.child(nambari).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        pds.dismiss();
                        Toast.makeText(getActivity(), "Loan request deleted", Toast.LENGTH_SHORT).show();
                        Frag_History fraest=new Frag_History();
                        Frag_Loan_View frae=new Frag_Loan_View();

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fraest);
                        fragmentTransaction.remove(frae);
                        fragmentTransaction.commit();
                    }
                });
                //Toast.makeText(getActivity(), "Loan request not deleted,\nTry again", Toast.LENGTH_SHORT).show();
                pds.dismiss();
            }
        });


        return fraghome;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
