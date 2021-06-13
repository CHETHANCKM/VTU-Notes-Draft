package com.cs.vtunotes.physics_chemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs.vtunotes.R;
import com.cs.vtunotes.adapter.schemeadapter;
import com.cs.vtunotes.models.SchemeModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class scheme extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    schemeadapter schemeadapter;
    ShimmerFrameLayout shimmerlayout;
    TextView subject_name;
    ArrayList<SchemeModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheme);
        recyclerView = findViewById(R.id.recyclerview_scheme);
        shimmerlayout = findViewById(R.id.shimmerlayout);
        subject_name = findViewById(R.id.subject_name);

        Intent intent = getIntent();
        String branch = intent.getStringExtra("branch");

        subject_name.setText(branch+" - Scheme");

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