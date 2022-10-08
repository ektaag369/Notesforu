package com.example.notesforu.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesforu.Adapters.PdfAdapter;
import com.example.notesforu.Adapters.ProfileAdapter;
import com.example.notesforu.EditbioActivity;
import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.Models.ProfileModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView profileemail,profileusername,profileuserbio;
    ImageView editbio;
    RecyclerView profilerv;
    ProfileAdapter profileAdapter;

    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        profileemail=view.findViewById(R.id.profileemailid);
        profileusername=view.findViewById(R.id.profileusername);
        profileuserbio=view.findViewById(R.id.profileuserbio);
        editbio=view.findViewById(R.id.profileeditbio);
        profilerv=view.findViewById(R.id.profile_rv);
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        String id=firebaseUser.getUid();
        String email=firebaseUser.getEmail();

        DatabaseReference dref= FirebaseDatabase.getInstance().getReference("users").child(id).child("Username");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name=snapshot.getValue().toString();
                    profileusername.setText(name);
                    profileemail.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Couldn't get the data...", Toast.LENGTH_SHORT).show();

            }
        });

        DatabaseReference dref1= FirebaseDatabase.getInstance().getReference("Bio").child(id).child("bio");
        dref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String bio=snapshot.getValue().toString();
                    profileuserbio.setText(bio);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Couldn't get the bio...", Toast.LENGTH_SHORT).show();

            }
        });

        editbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), EditbioActivity.class);
                startActivity(i);

            }
        });

        profilerv.setLayoutManager(new GridLayoutManager(getContext(),2));

        FirebaseRecyclerOptions<ProfileModel> options=
                new FirebaseRecyclerOptions.Builder<ProfileModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("ProfileDocuments").child(id),ProfileModel.class)
                        .build();

        profileAdapter=new ProfileAdapter(options);
        profilerv.setAdapter(profileAdapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        profileAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        profileAdapter.stopListening();
    }
}