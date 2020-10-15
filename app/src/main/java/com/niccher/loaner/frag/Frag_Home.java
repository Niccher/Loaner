package com.niccher.loaner.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.niccher.loaner.utils.Konstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Home extends Fragment {

    LinearLayout card_add_prod,card_view_prod,card_add_gallery,card_view_gallery,cvprof;
    TextView welcome, declares;

    FirebaseAuth mAuth;
    FirebaseUser userf;

    public Frag_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        welcome=fraghome.findViewById(R.id.welc);
        declares=fraghome.findViewById(R.id.decl);

        LoadData();

        card_add_prod=fraghome.findViewById(R.id.card_add);
        card_view_prod=fraghome.findViewById(R.id.card_view);
        card_add_gallery=fraghome.findViewById(R.id.cardadd_gallery);
        card_view_gallery=fraghome.findViewById(R.id.cardview_gallery);
        cvprof=fraghome.findViewById(R.id.cardprof);

        card_add_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_Borrow fracar=new Frag_Borrow();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        card_view_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_Status fraest=new Frag_Status();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        card_add_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_Calc fraest=new Frag_Calc();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        card_view_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_History fraest=new Frag_History();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cvprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_Profile fraest=new Frag_Profile();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return fraghome;
    }

    private void LoadData() {
        DatabaseReference dref2= FirebaseDatabase.getInstance().getReference(Konstants.Data_Users+"/"+userf.getUid());
        dref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //pds.dismiss();
                //String gUid, gFname, gLname, gPhone, gEmail, gDateBirth, gNationalid, gDate, gPwd;
                String nm = (String) dataSnapshot.child("gFname").getValue();
                String sm = (String) dataSnapshot.child("gLname").getValue();
                String ph = (String) dataSnapshot.child("gPhone").getValue();

                welcome.setText("Hello "+nm+", Welcome back to Europol Azima SACCO, Continue borrowing and repaying to grow your loan limits, currently you can borrow loan upto KShs 12,000.00 .");
                declares.setText(sm+", Your currently loan balance is 0, any loan that you apply, will be sent to "+ph+" .");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", "DatabaseError: "+databaseError.getMessage());
            }
        });
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
