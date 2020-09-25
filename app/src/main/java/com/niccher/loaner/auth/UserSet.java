package com.niccher.loaner.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.niccher.loaner.MainActivity;
import com.niccher.loaner.R;
import com.niccher.loaner.mod.Mod_UserConfig;

import java.util.Calendar;


public class UserSet extends AppCompatActivity {

    EditText Rfname,Rlname,Rphone,Remail,Rbirth,Rid,Rdate,Rpwd;
    TextView sback;
    ProgressDialog pds;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref;
    FirebaseDatabase fdbas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);

        dref = FirebaseDatabase.getInstance().getReference("Loaner/Users");
        mAuth = FirebaseAuth.getInstance();

        sback = (TextView) findViewById(R.id.user_new_procid);

        Rfname= (EditText) findViewById(R.id.user_new_fname);
        Rlname= (EditText) findViewById(R.id.user_new_lname);
        Rphone= (EditText) findViewById(R.id.user_new_phone);
        Remail= (EditText) findViewById(R.id.user_new_email);
        Rbirth= (EditText) findViewById(R.id.user_new_dob);
        Rid= (EditText) findViewById(R.id.user_new_id);
        //Rdate= (EditText) findViewById(R.id.user_set_usr);
        Rpwd= (EditText) findViewById(R.id.user_new_pwd);

        pds=new ProgressDialog(this);

        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verrif();
            }
        });
    }


    private void Verrif() {
        final String fname = Rfname.getText().toString().trim();
        final String lname = Rlname.getText().toString().trim();
        final String phone = Rphone.getText().toString().trim();
        final String email = Remail.getText().toString().trim();
        final String birth = Rbirth.getText().toString().trim();
        final String national = Rid.getText().toString().trim();
        final String pwd = Rpwd.getText().toString().trim();


        if (fname.isEmpty()) {
            Rfname.setError("First Name is required");
            Rfname.requestFocus();
            return;
        }

        if (lname.isEmpty()) {
            Rlname.setError("Last Name is required");
            Rlname.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            Rphone.setError("Please enter a valid Phone Number");
            Rphone.requestFocus();
            return;
        }

        if (phone.length() < 8) {
            Rphone.setError("Minimum length of a Phone should be 10");
            Rphone.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Remail.setError("Please enter a valid Email Address");
            Remail.requestFocus();
            return;
        }

        if (birth.isEmpty()) {
            Rdate.setError("Date of Birth is required");
            Rdate.requestFocus();
            return;
        }

        if (national.isEmpty()) {
            Rid.setError("National ID is required");
            Rid.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            Rpwd.setError("National ID is required");
            Rpwd.requestFocus();
            return;
        }

        if (pwd.length() < 7) {
            Rpwd.setError("Minimum length of a Password should be 7");
            Rpwd.requestFocus();
            return;
        }

        pds.setMessage("Please Wait");
        pds.show();

        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pds.dismiss();
                if (task.isSuccessful()) {
                    try {
                        userf=mAuth.getCurrentUser();
                        String uid = dref.push().getKey();
                        Log.e("getException()", "-+-+-+-+-+-+-+-+-"+userf.getUid());
                        String now = String.valueOf(Calendar.getInstance().getTimeInMillis());
                        Mod_UserConfig upload = new Mod_UserConfig(uid,fname,lname,phone,email,birth,national,now,pwd);
                        dref.child(userf.getUid()).setValue(upload);

                        finish();
                        startActivity(new Intent(UserSet.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        pds.dismiss();
                    } catch (Exception e) {
                        pds.dismiss();
                        Toast.makeText(UserSet.this, "Encountered an error "+e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("getException()", "e.getMessage() "+ e.getMessage());
                    }

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Email has been registered", Toast.LENGTH_SHORT).show();
                        Log.e("task.getException()", "FirebaseAuthUserCollisionException ");

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("task.getException()", "task.getException().getMessage(): "+task.getException().getMessage() );
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(UserSet.this,UserLogin.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
