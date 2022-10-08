package com.example.notesforu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesforu.Models.CommentModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CommentAdapter extends FirebaseRecyclerAdapter<CommentModel,CommentAdapter.CommentViewHolder> {

    public CommentAdapter(@NonNull FirebaseRecyclerOptions<CommentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull CommentModel model) {

        holder.commentemail.setText(model.getEmail());
        holder.commenttv.setText(model.getComments());

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);

        return new CommentAdapter.CommentViewHolder(view);
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView commentemail,commenttv;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            commentemail=itemView.findViewById(R.id.cmmtemail);
            commenttv=itemView.findViewById(R.id.commenttv);
        }
    }
}
