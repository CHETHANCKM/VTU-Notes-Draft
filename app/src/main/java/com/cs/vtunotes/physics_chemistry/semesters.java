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
import com.cs.vtunotes.adapter.semesterAdapter;
import com.cs.vtunotes.models.semesterModel;
import com.cs.vtunotes.webactivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class semesters extends AppCompatActivity {

    TextView subject_name;
    RecyclerView recyclerView;
    ArrayList<semesterModel> list;
    semesterAdapter semesterAdapter;
    EditText url_text;
    Button update_url_button;
    String branch, scheme_year, db_code, subject_main;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters);

        subject_name = findViewById(R.id.subject_name);
        recyclerView = findViewById(R.id.semester_cycle);


        Intent intent = getIntent();
        String branch = intent.getStringExtra("branch");
        scheme_year = intent.getStringExtra("scheme_code");
        db_code = intent.getStringExtra("db_code");


        subject_main = intent.getStringExtra("subject_main");

        subject_name.setText(branch);
        mDatabase = FirebaseDatabase.getInstance()
                .getReference().child("semester").child(db_code);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        semesterAdapter = new semesterAdapter(this, list);
        semesterAdapter.setHasStableIds(true);
        recyclerView.setAdapter(semesterAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    semesterModel semesterModel = ds.getValue(semesterModel.class);
                    list.add(semesterModel);
                }
//                shimmerlayout.setVisibility(View.GONE);
                semesterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    public void updateurl(View view)
    {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.phychemupdateurl);
        url_text = bottomSheetDialog.findViewById(R.id.url_text);
        update_url_button = bottomSheetDialog.findViewById(R.id.update_url_button);


        url_text.setText("Loading...");
        final String[] url = {null};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Scheme").child(db_code).child(scheme_year);

        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                url[0] = snapshot.getValue(String.class);
                url_text.setText(url[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        update_url_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_url = url_text.getText().toString();

                if (new_url.isEmpty())
                {

                }
                else if(url[0].equals(new_url))
                {
                    Toast.makeText(semesters.this, "Field contains same url", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase.getInstance()
                            .getReference("Scheme")
                            .child(db_code)
                            .child(scheme_year)
                            .setValue(new_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Toast.makeText(semesters.this, "Scheme url was updated successfully.", Toast.LENGTH_SHORT).show();
                            url_text.clearFocus();
                            bottomSheetDialog.dismiss();

                        }
                    });

                }

            }
        });
        bottomSheetDialog.show();
    }

    public void show_scheme(View view) {
        final String[] url = {null};
        Toast.makeText(semesters.this, "Please wait...", Toast.LENGTH_LONG).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Scheme")
                .child(db_code)
                .child(scheme_year);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                url[0] = snapshot.getValue(String.class);
                if (url[0]==null)
                {
                    Toast.makeText(semesters.this, "Loading... Try again", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), webactivity.class);
                    i.putExtra("url", url[0]);
                    startActivity(i);
                }

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });





    }
}