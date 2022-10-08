package com.example.notesforu.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesforu.CommentActivity;
import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.R;
import com.example.notesforu.Viewpdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PdfAdapter extends FirebaseRecyclerAdapter<FileInfoModel, PdfAdapter.PdfViewHolder> {

    FirebaseAuth mAuth;

    public PdfAdapter(@NonNull FirebaseRecyclerOptions<FileInfoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PdfViewHolder holder, int position, @NonNull FileInfoModel model) {

        holder.pdftitle.setText("Pdf name: "+model.getFilename());
        holder.pdfabout.setText("Pdf fields: "+model.getFileabout());
        holder.pdfdescription.setText("Description: "+model.getFiledescription());

        holder.pdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.pdfimg.getContext(), Viewpdf.class);
                i.putExtra("filename",model.getFilename());
                i.putExtra("fileurl",model.getFileurl());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdfimg.getContext().startActivity(i);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth=FirebaseAuth.getInstance();

                FirebaseUser firebaseUser=mAuth.getCurrentUser();
                String email=firebaseUser.getEmail();
                String id=firebaseUser.getUid();

                Intent i=new Intent(holder.comment.getContext(), CommentActivity.class);
                i.putExtra("filename",model.getFilename());
                i.putExtra("useremail",email);
                i.putExtra("userid",id);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.comment.getContext().startActivity(i);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=model.getFileurl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                v.getContext().startActivity(intent);

            }
        });
    }


    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_recycler,parent,false);

        return new PdfViewHolder(view);

    }



    class PdfViewHolder extends RecyclerView.ViewHolder{
        ImageView pdfimg,download,comment;
        TextView pdftitle,pdfabout,pdfdescription;


        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfimg=itemView.findViewById(R.id.pdfimg);
            pdftitle=itemView.findViewById(R.id.pdftext);
            pdfabout=itemView.findViewById(R.id.pdfabout);
            pdfdescription=itemView.findViewById(R.id.pdfdescription);
            download=itemView.findViewById(R.id.download);
            comment=itemView.findViewById(R.id.comments);

        }
    }



}
