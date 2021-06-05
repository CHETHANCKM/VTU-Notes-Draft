package com.cs.vtunotes.physics_chemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.cs.vtunotes.login;
import com.cs.vtunotes.terms_and_conditions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class physicsandchemi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicsandchemi);

    }

    public void phy_che2015(View view) {
        Intent i = new Intent(physicsandchemi.this, phychem2015page.class);
        startActivity(i);
    }

    public void phy_che2017(View view) {
        Intent i = new Intent(physicsandchemi.this, phychem2017page.class);
        startActivity(i);
    }

    public void phy_che2018(View view) {
        Intent i = new Intent(physicsandchemi.this, phychem2018page.class);
        startActivity(i);
    }
}