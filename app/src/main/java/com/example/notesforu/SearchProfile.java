package com.example.notesforu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.notesforu.Adapters.ProfileAdapter;
import com.example.notesforu.Fragment.AddFragment;
import com.example.notesforu.Fragment.HomeFragment;
import com.example.notesforu.Fragment.NotificationFragment;
import com.example.notesforu.Fragment.ProfileFragment;
import com.example.notesforu.Fragment.SearchFragment;
import com.example.notesforu.Models.ProfileModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SearchProfile extends AppCompatActivity {
    TextView searchprofileemail,searchprofileusername,userbio;
    RecyclerView searchprofilerv;
    ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);

        searchprofileemail=findViewById(R.id.searchprofileemailid);
        searchprofileusername=findViewById(R.id.searchprofileusername);
        searchprofilerv=findViewById(R.id.searchprofile_rv);
        userbio=findViewById(R.id.searchuserbio);

        String Searchprofileid=getIntent().getStringExtra("profileid");
        String Searchprofilename=getIntent().getStringExtra("profilename");
        String Searchprofileemail=getIntent().getStringExtra("profileemail");

        searchprofileusername.setText(Searchprofilename);
        searchprofileemail.setText(Searchprofileemail);

        searchprofilerv.setLayoutManager(new GridLayoutManager(this,2));

        DatabaseReference dref1= FirebaseDatabase.getInstance().getReference("Bio").child(Searchprofileid).child("bio");
        dref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String bio=snapshot.getValue().toString();
                    userbio.setText(bio);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchProfile.this, "Couldn't get the bio...", Toast.LENGTH_SHORT).show();

            }
        });

        FirebaseRecyclerOptions<ProfileModel> options=
                new FirebaseRecyclerOptions.Builder<ProfileModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("ProfileDocuments").child(Searchprofileid),ProfileModel.class)
                        .build();

        profileAdapter=new ProfileAdapter(options);
        searchprofilerv.setAdapter(profileAdapter);



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