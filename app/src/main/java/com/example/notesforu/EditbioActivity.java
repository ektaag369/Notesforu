package com.example.notesforu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notesforu.Fragment.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditbioActivity extends AppCompatActivity {

    EditText et_bio;
    Button btn_bio;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbio);

        et_bio=findViewById(R.id.et_bio);
        btn_bio=findViewById(R.id.btn_bio);
        mAuth=FirebaseAuth.getInstance();

        btn_bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio=et_bio.getText().toString();

                FirebaseUser firebaseUser= mAuth.getCurrentUser();
                String id=firebaseUser.getUid();

                DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Bio").child(id);

                HashMap<String,Object> haspmap=new HashMap<>();
                haspmap.put("bio",bio);
                databaseReference1.setValue(haspmap);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i =new Intent(EditbioActivity.this,ProfileFragment.class);
        startActivity(i);
    }
}