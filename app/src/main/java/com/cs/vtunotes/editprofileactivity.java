package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class editprofileactivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView useremail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileactivity);
        useremail = findViewById(R.id.useremail);
        username = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();

        String user_name = mAuth.getCurrentUser().getDisplayName().toUpperCase().toString();
        String user_email = mAuth.getCurrentUser().getEmail().toString();
        username.setText(user_name);
        useremail.setText(user_email);


    }
}