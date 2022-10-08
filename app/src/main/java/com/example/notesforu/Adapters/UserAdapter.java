package com.example.notesforu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesforu.Fragment.ProfileFragment;
import com.example.notesforu.Models.UserModel;
import com.example.notesforu.R;
import com.example.notesforu.SearchProfile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel,UserAdapter.UserViewHolder> {

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UserModel model) {


        holder.username.setText(model.getUsername());
        holder.fullname.setText(model.getFullname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.itemView.getContext(), SearchProfile.class);
                i.putExtra("profileid",model.getId());
                i.putExtra("profilename",model.getUsername());
                i.putExtra("profileemail",model.getEmail());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(i);

            }
        });

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);

        return new UserViewHolder(view);
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView username,fullname;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username_);
            fullname=itemView.findViewById(R.id.fullname_);
        }
    }

}