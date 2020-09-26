package com.niccher.loaner.frag;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.niccher.loaner.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Status extends Fragment {

    Button activate;
    TextView info;

    public Frag_Status() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_status, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Account Status");

        info = fraghome.findViewById(R.id.activate_info);
        activate= fraghome.findViewById(R.id.pend_activate);

        String info_state = "1. Go to M-Pesa\n2. Lipa na M-Pesa\n3. Select Paybill\n4. Bussines Number XXXXXXXX\n5. Account Number {My Phone Number}";
        info_state+="\n6. Enter Amount 280\n7. Enter PIN and Confirm";
        info.setText(info_state);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frag_Activate fraest=new Frag_Activate();
                Frag_Status frae=new Frag_Status();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fraest);
                //fragmentTransaction.remove(null);
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
