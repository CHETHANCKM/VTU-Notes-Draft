package com.cs.vtunotes.physics_chemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.cs.vtunotes.adapter.subjectAdapter;
import com.cs.vtunotes.models.subjectnameModels;
import com.cs.vtunotes.webactivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class subjects1 extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    Button add_subject_button;
    TextInputEditText add_subject_code, add_subject_name;

    subjectAdapter subjectAdapter;
    ShimmerFrameLayout shimmerlayout;
    ArrayList<subjectnameModels> list;
    String m_scheme_year, db_code,branch, semester_name;
    TextView subject_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects1);
        shimmerlayout = findViewById(R.id.shimmerlayout);

        Intent intent = getIntent();
        branch = intent.getStringExtra("branch");
        m_scheme_year = intent.getStringExtra("scheme_code");
        db_code = intent.getStringExtra("db_code");
        semester_name = intent.getStringExtra("semester_name");

        subject_name = findViewById(R.id.subject_name);
        subject_name.setText(branch);



        mDatabase = FirebaseDatabase.getInstance()
                .getReference("Notes")
                .child("subjects")
                .child(branch)
                .child(m_scheme_year)
                .child(db_code);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        list = new ArrayList<>();
//        loading_sub.setVisibility(View.VISIBLE);
        shimmerlayout.setVisibility(View.VISIBLE);

        subjectAdapter = new subjectAdapter(this, list);
        subjectAdapter.setHasStableIds(true);
        recyclerView.setAdapter(subjectAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren())
                {

                    subjectnameModels subjectnameModels = ds.getValue(com.cs.vtunotes.models.subjectnameModels.class);
                    list.add(subjectnameModels);
                }
                shimmerlayout.setVisibility(View.GONE);
                subjectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }





    public void addsubject(View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_subject);

                add_subject_button = bottomSheetDialog.findViewById(R.id.add_subject_button);
                add_subject_name = bottomSheetDialog.findViewById(R.id.add_subject_name);
                add_subject_code = bottomSheetDialog.findViewById(R.id.add_subject_code);
                mDatabase = FirebaseDatabase.getInstance().getReference();

                add_subject_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String subjectcode = add_subject_code.getText().toString().toUpperCase();
                        String subjectname = add_subject_name.getText().toString().toUpperCase();

                        if (subjectname.isEmpty() || subjectcode.isEmpty())
                        {
                            Toast.makeText(subjects1.this, "All feilds required", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            HashMap<Object, String> data = new HashMap<>();
                            data.put("subject_code", subjectcode);
                            data.put("subject_name", subjectname);
                            data.put("scheme", m_scheme_year);

                            DatabaseReference refs = FirebaseDatabase.getInstance()
                                    .getReference("Notes");
                            refs.child("subjects").child(branch)
                                    .child(m_scheme_year)
                                    .child(db_code)
                                    .child(subjectcode)
                                    .setValue(data)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            Toast.makeText(subjects1.this, "New subject was added successfully.", Toast.LENGTH_SHORT).show();
                                            bottomSheetDialog.dismiss();
                                        }
                                    });
                        }

                    }
                });

        bottomSheetDialog.show();
    }


}