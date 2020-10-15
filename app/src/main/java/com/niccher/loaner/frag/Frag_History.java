package com.niccher.loaner.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niccher.loaner.R;
import com.niccher.loaner.adapter.Adp_Apply;
import com.niccher.loaner.mod.Mod_Apply;
import com.niccher.loaner.utils.Konstants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_History extends Fragment {

    RecyclerView recyl;

    Adp_Apply adp_loans;
    List<Mod_Apply> mod_loans;

    FirebaseAuth mAuth;
    FirebaseUser userf;

    public Frag_History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_history, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Applied Loans");

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        recyl=fraghome.findViewById(R.id.list_fries);
        recyl.setHasFixedSize(true);

        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setReverseLayout(true);
        lim.setStackFromEnd(true);
        recyl.setLayoutManager(lim);
        mod_loans=new ArrayList<>();

        GetInit();

        return fraghome;
    }

    private void GetInit(){
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference(Konstants.Data_Borrow +userf.getUid());
        dref.keepSynced(true);
        Log.e("DataSnapshot", "User as : "+Konstants.Data_Borrow +userf.getUid());

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mod_loans.clear();

                for (DataSnapshot ds1: dataSnapshot.getChildren()){
                    Mod_Apply ug=ds1.getValue(Mod_Apply.class);

                    mod_loans.add(ug);

                    adp_loans =new Adp_Apply(getActivity(),mod_loans);

                    recyl.setAdapter(adp_loans);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FetchFries", "onCancelled: "+databaseError );
            }
        });
        Log.e("DataSnapshot", "onDataChange: Finished");
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
