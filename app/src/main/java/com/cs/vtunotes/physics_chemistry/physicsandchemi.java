package com.cs.vtunotes.physics_chemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.cs.vtunotes.adapter.schemeadapter;
import com.cs.vtunotes.adapter.subjectAdapter;
import com.cs.vtunotes.login;
import com.cs.vtunotes.models.SchemeModel;
import com.cs.vtunotes.models.subjectnameModels;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    schemeadapter schemeadapter;
    ShimmerFrameLayout shimmerlayout;
    ArrayList<SchemeModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicsandchemi);
        recyclerView = findViewById(R.id.recyclerview_scheme);
        shimmerlayout = findViewById(R.id.shimmerlayout);

        mDatabase = FirebaseDatabase.getInstance()
                .getReference().child("scheme");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        shimmerlayout.setVisibility(View.VISIBLE);
        schemeadapter = new schemeadapter(this, list);
        schemeadapter.setHasStableIds(true);
        recyclerView.setAdapter(schemeadapter);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren())
                {

                    SchemeModel schemeModel = ds.getValue(SchemeModel.class);
                    list.add(schemeModel);
                }
                shimmerlayout.setVisibility(View.GONE);
                schemeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}