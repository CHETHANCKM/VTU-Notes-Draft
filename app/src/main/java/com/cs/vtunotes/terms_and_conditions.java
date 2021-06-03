package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class terms_and_conditions extends AppCompatActivity {

    Button accept;
    TextView tc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        accept = findViewById(R.id.accept);
        tc = findViewById(R.id.tc);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(terms_and_conditions.this, login.class);
                startActivity(i);
            }
        });

        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(terms_and_conditions.this, "Working", Toast.LENGTH_SHORT).show();
            }
        });
    }
}