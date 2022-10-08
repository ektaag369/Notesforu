package com.example.notesforu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesforu.Adapters.CommentAdapter;
import com.example.notesforu.Adapters.PdfAdapter;
import com.example.notesforu.Models.CommentModel;
import com.example.notesforu.Models.FileInfoModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {
    RecyclerView commentrv;
    CommentAdapter cmmtadapter;

    EditText addcmmnt;
    ImageView sendimg;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        addcmmnt=findViewById(R.id.edittextcmmnt);
        sendimg=findViewById(R.id.sendimg);

        commentrv=findViewById(R.id.comment_rv);

        String filename=getIntent().getStringExtra("filename");
        String useremail=getIntent().getStringExtra("useremail");
        String userid=getIntent().getStringExtra("userid");

        sendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=addcmmnt.getText().toString();

                dref= FirebaseDatabase.getInstance().getReference("Comments").child(filename);
                HashMap<String,Object> haspmap=new HashMap<>();
                haspmap.put("comments",comment);
                haspmap.put("email",useremail);

                dref.child(dref.push().getKey()).setValue(haspmap);

            }
        });

        commentrv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<CommentModel> options=
                new FirebaseRecyclerOptions.Builder<CommentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Comments").child(filename),CommentModel.class)
                        .build();

        cmmtadapter=new CommentAdapter(options);
        commentrv.setAdapter(cmmtadapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        cmmtadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cmmtadapter.stopListening();
    }

    @Override
    public void onBackPressed() {

        Intent i =new Intent(CommentActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}