package com.niccher.loaner.frag;

import android.app.Dialog;
import android.os.Bundle;
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
import com.niccher.loaner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Borrow extends Fragment {
    
    Button apply,next;
    Dialog myDialog;
    TextView cont,p_pesa,p_muda,p_interest, p_sababu;

    EditText amount, cause,rea;
    Spinner period;

    public Frag_Borrow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_borrow, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Borrow");

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
            /*p_pesa.setText(pesa );
            p_muda.setText(muda );
            p_sababu.setText(sababu );
            p_interest.setText("10" );

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();*/
            Fragment myFragment = new Frag_Pending();
            Frag_Borrow frae=new Frag_Borrow();

            Bundle args= new Bundle();
            args.putString("pesa",pesa);
            args.putString("sababu",sababu);
            args.putString("muda",muda);
            myFragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).remove(frae).commit();
        }

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
