package com.example.notesforu.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesforu.Adapters.UserAdapter;
import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.Adapters.PdfAdapter;
import com.example.notesforu.Models.UserModel;
import com.example.notesforu.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    RecyclerView pdfrecyclerView;
    PdfAdapter adapter;
    FirebaseAuth mAuth;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        pdfrecyclerView=view.findViewById(R.id.pdf_recycler_view);
        searchView=view.findViewById(R.id.search_pdf);
        pdfrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        String id=firebaseUser.getUid();

        FirebaseRecyclerOptions<FileInfoModel> options = new FirebaseRecyclerOptions.Builder<FileInfoModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("mydocuments"), FileInfoModel.class)
                .build();

        adapter=new PdfAdapter(options);
        pdfrecyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseRecyclerOptions<FileInfoModel> options1 = new FirebaseRecyclerOptions.Builder<FileInfoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("mydocuments").orderByChild("fileabout").startAt(query).endAt(query + "\uf8ff"), FileInfoModel.class)
                        .build();

                if (options1!=null){

                    adapter = new PdfAdapter(options1);
                    adapter.startListening();
                    pdfrecyclerView.setAdapter(adapter);
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

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}