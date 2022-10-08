package com.example.notesforu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesforu.Models.NotificationModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class NotificationAdapter extends FirebaseRecyclerAdapter<NotificationModel,NotificationAdapter.NotificationViewHolder> {

    public NotificationAdapter(@NonNull FirebaseRecyclerOptions<NotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull NotificationModel model) {

        holder.notification.setText(model.getNotify());

    }

//    @NonNull
//    @Override
//    public NotificationModel getItem(int position) {
//        return super.getItem(position);
//    }
    @Override
    public NotificationModel getItem(int position) {
        return super.getItem(getItemCount() - 1 - position);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item,parent,false);

        return new NotificationAdapter.NotificationViewHolder(view);
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{
        TextView notification;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notification=itemView.findViewById(R.id.notify);
        }
    }
}
