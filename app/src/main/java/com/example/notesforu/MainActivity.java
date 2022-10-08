package com.example.notesforu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.notesforu.Fragment.AddFragment;
import com.example.notesforu.Fragment.HomeFragment;
import com.example.notesforu.Fragment.NotificationFragment;
import com.example.notesforu.Fragment.ProfileFragment;
import com.example.notesforu.Fragment.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    MeowBottomNavigation meowBottomNavigation;
    Toolbar toolbar;

//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;

    FirebaseAuth mAuth;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout=findViewById(R.id.framelayout);
        meowBottomNavigation=findViewById(R.id.meowbnv);
        toolbar=findViewById(R.id.toolbar);

        mAuth=FirebaseAuth.getInstance();

//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc= GoogleSignIn.getClient(this,gso);

//        GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(this);
//        if(acct!=null){
//            String personname=acct.getDisplayName();
//            String personemail=acct.getEmail();
//
//            String userid= acct.getId();



//            reference= FirebaseDatabase.getInstance().getReference().child("usersgoogle").child(userid);
//
//            HashMap<String,Object> haspmap=new HashMap<>();
//            haspmap.put("Fullname",personname);
//            haspmap.put("Email",personemail);
//            haspmap.put("id",userid);
//
//            reference.setValue(haspmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(MainActivity.this, "info stored", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });


        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.search));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.add));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.notifications));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.person));

        meowBottomNavigation.show(1,true);
        replace(new HomeFragment());

       meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
           @Override
           public Unit invoke(MeowBottomNavigation.Model model) {
               switch(model.getId()){
                   case 1:
                       replace(new HomeFragment());
                       toolbar.setVisibility(View.VISIBLE);
                       break;

                   case 2:
                       replace(new SearchFragment());
                       toolbar.setVisibility(View.GONE);
                       break;

                   case 3:
                       replace(new AddFragment());
                       toolbar.setVisibility(View.VISIBLE);
                       break;

                   case 4:
                       replace(new NotificationFragment());
                       toolbar.setVisibility(View.VISIBLE);
                       break;

                   case 5:
                       replace(new ProfileFragment());
                       toolbar.setVisibility(View.GONE);
                       break;
               }
               return null;
           }
       });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
}