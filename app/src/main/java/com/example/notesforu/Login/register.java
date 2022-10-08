package com.example.notesforu.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesforu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    EditText username,fullname,email,password,confirmpaswd;
    Button register;
    TextView switchtologin;
    FirebaseAuth mAuth;
    ProgressDialog pd;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.regemail);
        password=findViewById(R.id.psswd);
        confirmpaswd=findViewById(R.id.cndpsswd);
        register=findViewById(R.id.registerbtn);
        switchtologin=findViewById(R.id.switchtologin);

        mAuth=FirebaseAuth.getInstance();

        switchtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_username=username.getText().toString();
                String str_fullname=fullname.getText().toString();
                String str_email=email.getText().toString();
                String str_password=password.getText().toString();
                String str_confirmpassword=confirmpaswd.getText().toString();

                if(TextUtils.isEmpty(str_username)||TextUtils.isEmpty(str_fullname)||
                        TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)||
                        TextUtils.isEmpty(str_confirmpassword)){
                    Toast.makeText(register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else  if(str_password.length()<6){
                    Toast.makeText(register.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!str_password.equals(str_confirmpassword)){
                    Toast.makeText(register.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd=new ProgressDialog(register.this);
                    pd.setMessage("Please wait...");
                    pd.show();
                    mAuth.createUserWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                FirebaseUser firebaseUser= mAuth.getCurrentUser();
                                String userid= firebaseUser.getUid();
                                reference= FirebaseDatabase.getInstance().getReference().child("users").child(userid);

                                HashMap<String,Object> haspmap=new HashMap<>();
                                haspmap.put("Id",userid);
                                haspmap.put("Username",str_username.toLowerCase());
                                haspmap.put("Fullname",str_fullname);
                                haspmap.put("Email",str_email);

                                reference.setValue(haspmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            pd.dismiss();
                                            Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(register.this,login.class));
                                        }
                                    }
                                });

                            }
                            else{
                                pd.dismiss();
                                Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }


}