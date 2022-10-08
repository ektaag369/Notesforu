package com.example.notesforu.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.notesforu.MainActivity;
import com.example.notesforu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH=3300;
    FirebaseAuth mAuth;
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
    Animation topanim,bottomanim;
    ImageView splash_img,splash_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        mAuth=FirebaseAuth.getInstance();

        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(this,R.anim.bottom_animatio);
        splash_img=findViewById(R.id.logo);
        splash_text=findViewById(R.id.name);

//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc= GoogleSignIn.getClient(this,gso);

        splash_img.setAnimation(topanim);
        splash_text.setAnimation(bottomanim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mAuth.getCurrentUser();
//                GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(SplashScreen.this);

                if(currentUser!=null){
                    Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent=new Intent(SplashScreen.this, register.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH);



    }
}