package com.niccher.loaner.frag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niccher.loaner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Pending extends Fragment {

    Button activate;
    Dialog myDialog;
    TextView cont;

    EditText amount, cause;

    TextView ps,md,sb, in, tot;
    TextView info;
    Spinner period;

    Bundle getbun;

    public Frag_Pending() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_pending, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Loan Process");

        ps = fraghome.findViewById(R.id.pending_amount);
        md = fraghome.findViewById(R.id.pending_time);
        sb = fraghome.findViewById(R.id.pending_reason);

        tot = fraghome.findViewById(R.id.pending_total);
        in = fraghome.findViewById(R.id.pending_interest);

        info = fraghome.findViewById(R.id.activate_info);
        activate= fraghome.findViewById(R.id.pend_activate);

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivate();
            }
        });

        getbun = getArguments();

        String pesa=getbun.getString("pesa");
        String muda=getbun.getString("muda");
        String sababu=getbun.getString("sababu");
        double fina = Integer.parseInt(pesa) + (Integer.parseInt(pesa) * .1);

        ps.setText(pesa);
        md.setText(muda);
        sb.setText(sababu);
        in.setText("10%");
        tot.setText( String.valueOf(fina));

        String info_state = "1. Go to M-Pesa\n2. Lipa na M-Pesa\n3. Select Paybill\n4. Bussines Number XXXXXXXX\n5. Account Number {My Phone Number}";
        info_state+="\n6. Enter Amount 280\n7. Enter PIN and Confirm";
        info.setText(info_state);

        return fraghome;
    }

    private void showActivate() {
        AlertDialog.Builder aka2=new AlertDialog.Builder(getActivity());

        aka2.setTitle("Activate Account");

        LinearLayout linlay=new LinearLayout(getActivity());
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setPadding(10,10,10,10);

        final EditText edi=new EditText(getActivity());
        edi.setHint("Enter the M-Pesa Confirmation code");
        linlay.addView(edi);

        aka2.setView(linlay);

        aka2.setPositiveButton("Check", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String new1=edi.getText().toString().trim();
                if (!TextUtils.isEmpty(new1)){
                    //pds.show();

                }else {
                    Toast.makeText(getActivity(), "Blank Space is not Allowed please", Toast.LENGTH_SHORT).show();
                }
            }
        });
        aka2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        aka2.create().show();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
