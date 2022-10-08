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

import com.example.notesforu.MainActivity;
import com.example.notesforu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView forgotpassword,switchtoregister;
    FirebaseAuth mAuth;
    ProgressDialog pd;
    DatabaseReference refrences;
//    FloatingActionButton google;

//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.loginbtn);
        forgotpassword=findViewById(R.id.forgot);
        switchtoregister=findViewById(R.id.switchtoregister);
        mAuth=FirebaseAuth.getInstance();

//        google=findViewById(R.id.google);
//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc= GoogleSignIn.getClient(this,gso);

        switchtoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.getContext(), forgetpassword.class));
            }
        });

//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googlesignin();
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_email=email.getText().toString();
                String str_password=password.getText().toString();

                if(TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password)){
                    Toast.makeText(login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{

                    ProgressDialog pd=new ProgressDialog(login.this);
                    pd.setMessage("Please wait...");
                    pd.show();

                    mAuth.signInWithEmailAndPassword(str_email,str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(mAuth.getCurrentUser().getUid());

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        pd.dismiss();
                                        Toast.makeText(login.this, "login is done", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(login.getContext(), MainActivity.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        pd.dismiss();
                                        Toast.makeText(login.this, "login failed"+error, Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                            else{
                                pd.dismiss();
                                Toast.makeText(login.this, "login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

//    private void googlesignin() {
//        Intent signInIntent=gsc.getSignInIntent();
//        startActivityForResult(signInIntent,1000);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==1000){
//            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                startActivity(new Intent(login.getContext(),MainActivity.class));
//                finish();
//            } catch (ApiException e) {
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
}