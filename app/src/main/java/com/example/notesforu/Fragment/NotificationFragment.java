package com.example.notesforu.Fragment;

import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesforu.Adapters.NotificationAdapter;
import com.example.notesforu.Adapters.PdfAdapter;
import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.Models.NotificationModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationFragment extends Fragment {

    RecyclerView notificationrv;
    NotificationAdapter notifyadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        notificationrv=view.findViewById(R.id.notify_rv);
        notificationrv.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<NotificationModel> options=
                new FirebaseRecyclerOptions.Builder<NotificationModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("notification").orderByChild("time"),NotificationModel.class)
                        .build();

        notifyadapter=new NotificationAdapter(options);
        notificationrv.setAdapter(notifyadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        notifyadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notifyadapter.stopListening();
    }
}