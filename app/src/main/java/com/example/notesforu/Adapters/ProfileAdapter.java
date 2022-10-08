package com.example.notesforu.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesforu.Models.ProfileModel;
import com.example.notesforu.R;
import com.example.notesforu.Viewpdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProfileAdapter extends FirebaseRecyclerAdapter<ProfileModel,ProfileAdapter.ProfileViewHolder> {

    public ProfileAdapter(@NonNull FirebaseRecyclerOptions<ProfileModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProfileViewHolder holder, int position, @NonNull ProfileModel model) {
        holder.profilepdftitle.setText(model.getFilename());

        holder.profilepdfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.profilepdfimg.getContext(), Viewpdf.class);
                i.putExtra("filename",model.getFilename());
                i.putExtra("fileurl",model.getFileurl());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.profilepdfimg.getContext().startActivity(i);
            }
        });

    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_pdf,parent,false);

        return new ProfileAdapter.ProfileViewHolder(view);
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{
        ImageView profilepdfimg;
        TextView profilepdftitle;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profilepdfimg=itemView.findViewById(R.id.profilepdfimg);
            profilepdftitle=itemView.findViewById(R.id.profilepdftext);
        }
    }
}
