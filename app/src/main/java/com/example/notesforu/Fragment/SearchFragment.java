package com.example.notesforu.Fragment;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesforu.Adapters.PdfAdapter;
import com.example.notesforu.Adapters.UserAdapter;
import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.Models.UserModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {
    SearchView searchView;
    RecyclerView recyclerView;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView=view.findViewById(R.id.search_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        FirebaseRecyclerOptions<UserModel> options=
//                new FirebaseRecyclerOptions.Builder<UserModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"),UserModel.class)
//                        .build();
//
//        adapter=new UserAdapter(options);
//        recyclerView.setAdapter(adapter);

       /////////////////////////////
        searchView=view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseRecyclerOptions<UserModel> options1 = new FirebaseRecyclerOptions.Builder<UserModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                .child("users").orderByChild("Username").startAt(query).endAt(query + "\uf8ff"), UserModel.class)
                        .build();

                if (options1!=null){
                adapter = new UserAdapter(options1);
                adapter.startListening();
                recyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity().getApplicationContext(), "something found", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "nothing found", Toast.LENGTH_SHORT).show();
                }

                return false;}

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return view;
    }


}