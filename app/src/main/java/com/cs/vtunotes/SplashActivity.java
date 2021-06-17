package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.inappmessaging.model.Button;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                FirebaseUser user = mauth.getCurrentUser();

                if (user==null)
                {
                    Intent i = new Intent(SplashActivity.this, terms_and_conditions.class);
                    startActivity(i);
                }
                else
                {

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }



            }
        },1000);
    }
}