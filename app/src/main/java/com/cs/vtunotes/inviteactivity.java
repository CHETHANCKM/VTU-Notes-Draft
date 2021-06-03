package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class inviteactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviteactivity);
    }

    public void invitecode(View view)
    {
        Intent i = new Intent(inviteactivity.this, reddemcode.class);
        startActivity(i);
    }
}