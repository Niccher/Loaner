package com.niccher.loaner.frag;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niccher.loaner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Activate extends Fragment {

    EditText editTextPhoneNumber;
    Button sendButton;
    Daraja daraja;

    String phoneNumber;

    public Frag_Activate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_activate, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Activate Account");

        editTextPhoneNumber = fraghome.findViewById(R.id.activate_phone);
        sendButton = fraghome.findViewById(R.id.activate_btn);


        //Init Daraja
        daraja = Daraja.with("Qu2pACxbNsWBKqYSgGrAr8iqexy3bwuU", "DcIvQzkAYsir6Y8E", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.e("Daraja.with", accessToken.getAccess_token());
                //Toast.makeText(getContext(), "Toke as : " + accessToken.getAccess_token(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error) {
                Log.e("onError", error);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber)) {
                    editTextPhoneNumber.setError("Please Provide a Phone Number");
                    editTextPhoneNumber.requestFocus();
                    return;
                }//old 174379 new

                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                        TransactionType.CustomerPayBillOnline,
                        "1",
                        "254727122633",
                        "174379",
                        phoneNumber,
                        "http://requestbin.net/r/1fipdwj1",
                        "Europol Kenafrica",
                        "Loaner Account Activation"
                );

                daraja.requestMPESAExpress(lnmExpress,
                        new DarajaListener<LNMResult>() {
                            @Override
                            public void onResult(@NonNull LNMResult lnmResult) {
                                //Toast.makeText(getContext(), "void onResult "+lnmResult.ResponseDescription, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "Application succesfull", Toast.LENGTH_LONG).show();
                                Log.e("daraja.requestMPESAExpr", "lnmResult.ResponseDescription "+lnmResult.ResponseDescription);
                            }

                            @Override
                            public void onError(String error) {
                                //Toast.makeText(getContext(),"onError(String error) "+error, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "Please retry again", Toast.LENGTH_LONG).show();
                                Log.e("daraja.requestMPESAExpr", "onError(String error) "+error);
                            }
                        }
                );
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
