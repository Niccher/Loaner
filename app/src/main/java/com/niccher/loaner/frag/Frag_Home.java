package com.niccher.loaner.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.niccher.loaner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Home extends Fragment {

    CardView card_add_prod,card_view_prod,card_add_gallery,card_view_gallery,cvprof;

    public Frag_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");


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

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
