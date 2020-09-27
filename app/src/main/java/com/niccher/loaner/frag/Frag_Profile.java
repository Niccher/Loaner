package com.niccher.loaner.frag;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.niccher.loaner.R;
import com.niccher.loaner.auth.UserLogin;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Profile extends Fragment {

    ImageView userpic,coverimg,fab,testa;
    TextView uname,uphone,uemail;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref1,mDatabaseRef;
    StorageReference mStorageRef;

    ProgressDialog pds;
    String PermStor[];
    public static String Varr;

    public Frag_Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View solv= inflater.inflate(R.layout.frag_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        mAuth= FirebaseAuth.getInstance();

        userf=mAuth.getCurrentUser();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Loaner/Users").child(userf.getUid());
        dref1=FirebaseDatabase.getInstance().getReference("Loaner/Users").child(userf.getUid());
        dref1.keepSynced(true);

        userpic=solv.findViewById(R.id.com_image);
        uname=solv.findViewById(R.id.com_name);
        uphone=solv.findViewById(R.id.com_phone);
        uemail=solv.findViewById(R.id.com_email);
        coverimg=solv.findViewById(R.id.com_usercover);
        testa=solv.findViewById(R.id.com_test);

        pds=new ProgressDialog(getContext());

        fab=solv.findViewById(R.id.com_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfile();
            }
        });

        PopulateMe();

        return solv;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}else {
            startActivity(new Intent(getContext(), UserLogin.class));
            getActivity().finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}else {
            startActivity(new Intent(getContext(), UserLogin.class));
            getActivity().finish();
        }
    }

    private void showEditProfile() {
        String options[]={"Edit Email","Edit Phone","Edit Name"};
        AlertDialog.Builder aka=new AlertDialog.Builder(getContext());

        aka.setTitle("Select any Action");

        aka.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    showEmail();

                } else if (i==1){
                    //pds.setMessage("Updating Phone");
                    showPhone();

                } else if (i==2){
                    //pds.setMessage("Updating Name");
                    showName();
                }
            }
        });

        aka.create().show();
    }

    private void PopulateMe() {
        dref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gEmail,gUsername,gPhone;
                gEmail= (String) dataSnapshot.child("gEmail").getValue();
                gUsername= (String) dataSnapshot.child("gFname").getValue();
                gPhone= (String) dataSnapshot.child("gPhone").getValue();

                uname.setText(gUsername);
                uphone.setText(gPhone);
                uemail.setText(gEmail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showPhone() {

        AlertDialog.Builder aka2=new AlertDialog.Builder(getActivity());

        aka2.setTitle("Update Phone");

        LinearLayout linlay=new LinearLayout(getActivity());
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setPadding(10,10,10,10);

        final EditText edi=new EditText(getActivity());
        edi.setHint("Enter new Phone");
        linlay.addView(edi);

        aka2.setView(linlay);

        aka2.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String new1=edi.getText().toString().trim();
                if (!TextUtils.isEmpty(new1)){
                    pds.show();
                    HashMap<String , Object> hasm2=new HashMap<>();
                    hasm2.put("gPhone",new1);

                    dref1.updateChildren(hasm2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pds.dismiss();
                            PopulateMe();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds.dismiss();
                            Toast.makeText(getActivity(), "Failed to Update\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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

    private void showName() {

        AlertDialog.Builder aka2=new AlertDialog.Builder(getActivity());

        aka2.setTitle("Update Username");

        LinearLayout linlay=new LinearLayout(getActivity());
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setPadding(10,10,10,10);

        final EditText edi=new EditText(getActivity());
        edi.setHint("Enter new Username");
        linlay.addView(edi);

        aka2.setView(linlay);

        aka2.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String new1=edi.getText().toString().trim();
                if (!TextUtils.isEmpty(new1)){
                    pds.show();
                    HashMap<String , Object> hasm2=new HashMap<>();
                    hasm2.put("gUsername",new1);

                    dref1.updateChildren(hasm2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pds.dismiss();
                            PopulateMe();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds.dismiss();
                            Toast.makeText(getActivity(), "Failed to Update\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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

    private void showEmail() {

        AlertDialog.Builder aka2=new AlertDialog.Builder(getActivity());

        aka2.setTitle("Update Email");

        LinearLayout linlay=new LinearLayout(getActivity());
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setPadding(10,10,10,10);

        final EditText edi=new EditText(getActivity());
        edi.setHint("Enter new Email");
        linlay.addView(edi);

        aka2.setView(linlay);

        aka2.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String new1=edi.getText().toString().trim();
                if (!TextUtils.isEmpty(new1)){
                    pds.show();
                    HashMap<String , Object> hasm2=new HashMap<>();
                    hasm2.put("gEmail",new1);

                    dref1.updateChildren(hasm2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pds.dismiss();
                            PopulateMe();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds.dismiss();
                            Toast.makeText(getActivity(), "Failed to Update\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
}

