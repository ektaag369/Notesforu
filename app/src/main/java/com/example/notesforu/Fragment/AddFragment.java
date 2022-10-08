package com.example.notesforu.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesforu.Models.FileInfoModel;
import com.example.notesforu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class AddFragment extends Fragment {

    ImageView browseimg,pdfimg,cancelimg;
    EditText pdftitle,pdfabout,pdfdescription;
    Button uploadbtn;

    Uri filepath;
    FirebaseAuth mAuth;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add, container, false);

        browseimg=view.findViewById(R.id.browseimg);
        pdfimg=view.findViewById(R.id.pdfimg);
        pdfabout=view.findViewById(R.id.pdftitlerelated);
        pdfdescription=view.findViewById(R.id.pdfdescription);
        cancelimg=view.findViewById(R.id.cancelimg);
        pdftitle=view.findViewById(R.id.pdftitle);
        uploadbtn=view.findViewById(R.id.upload);

        mAuth=FirebaseAuth.getInstance();

        pdfimg.setVisibility(View.INVISIBLE);
        cancelimg.setVisibility(View.INVISIBLE);

        cancelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfimg.setVisibility(View.INVISIBLE);
                cancelimg.setVisibility(View.INVISIBLE);
                browseimg.setVisibility(View.VISIBLE);
            }
        });

       browseimg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dexter.withContext(getContext())
                       .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                       .withListener(new PermissionListener() {
                           @Override
                           public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                               Intent i=new Intent();
                               i.setType("applicatio/pdf");
                               i.setAction(Intent.ACTION_GET_CONTENT);
                               startActivityForResult(Intent.createChooser(i,"select pdf files"),101);

                           }

                           @Override
                           public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                           }

                           @Override
                           public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                           }
                       }).check();
           }
       });

       uploadbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               processUpload(filepath);

           }
       });

        return view;

    }

    private void processUpload(Uri filepath) {


        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setTitle("Uploading PDF....!!");
        pd.show();

        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        String id=firebaseUser.getUid();
        String email=firebaseUser.getEmail();

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("mydocuments");
        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("ProfileDocuments").child(id);

        StorageReference reference=storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                FileInfoModel obj =new FileInfoModel(pdftitle.getText().toString(),uri.toString(),
                                        pdfabout.getText().toString(),pdfdescription.getText().toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);

                                FileInfoModel obj1 =new FileInfoModel(pdftitle.getText().toString(),uri.toString(),
                                        pdfabout.getText().toString(),pdfdescription.getText().toString());
                                databaseReference2.child(databaseReference.push().getKey()).setValue(obj1);

                                 DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("notification");

                                 String notify=email + " Uploaded a new pdf named as: " + pdftitle.getText().toString();
                                 HashMap<String,Object> haspmap=new HashMap<>();
                                 haspmap.put("notify",notify);
                                 haspmap.put("time", LocalDateTime.now());
                                 databaseReference1.child(databaseReference1.push().getKey()).setValue(haspmap);

                                pd.dismiss();
                                Toast.makeText(getContext(), "PDF uploaded", Toast.LENGTH_SHORT).show();

                                pdfimg.setVisibility(View.INVISIBLE);
                                cancelimg.setVisibility(View.INVISIBLE);
                                browseimg.setVisibility(View.VISIBLE);
                                pdftitle.setText("");

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded:"+(int)percent+"%");
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            pdfimg.setVisibility(View.VISIBLE);
            cancelimg.setVisibility(View.VISIBLE);
            browseimg.setVisibility(View.INVISIBLE);
        }
    }
}