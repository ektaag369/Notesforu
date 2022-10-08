package com.example.notesforu.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesforu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {

    EditText forgetemail;
    Button forgotbtn;
    String email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        forgetemail=findViewById(R.id.forgotemail);
        forgotbtn=findViewById(R.id.forgotbtn);

        mAuth=FirebaseAuth.getInstance();

        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        email=forgetemail.getText().toString();
        if(email.isEmpty()){
            forgetemail.setError("Required");
        }else{
            forgetPassword();
        }
    }

    private void forgetPassword() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){
                  Toast.makeText(forgetpassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(forgetpassword.this, login.class));
                  finish();
              }  else{
                  Toast.makeText(forgetpassword.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
}