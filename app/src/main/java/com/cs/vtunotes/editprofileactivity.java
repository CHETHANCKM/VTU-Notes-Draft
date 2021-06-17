package com.cs.vtunotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class editprofileactivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView useremail, username, edit_name_info, usn_new;
    TextInputEditText edit_name;
    Button submit;
    TextInputLayout block_name, customSpinnerLayout, customBranchLayout;
    AutoCompleteTextView customTextView, customBranchView;
    ProgressBar loading_pgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileactivity);
        useremail = findViewById(R.id.useremail);
        username = findViewById(R.id.username);
        block_name = findViewById(R.id.block_name);
        edit_name = findViewById(R.id.edit_name);
        usn_new = findViewById(R.id.usn_new);
        edit_name_info = findViewById(R.id.edit_name_info);
        customTextView = findViewById(R.id.customTextView);
        customSpinnerLayout = findViewById(R.id.customSpinnerLayout);

        customBranchView = findViewById(R.id.customBranchView);
        customBranchLayout = findViewById(R.id.customBranchLayout);

        loading_pgs = findViewById(R.id.loading_pgs);
        loading_pgs.setVisibility(View.VISIBLE);


        submit = findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();
        String user_email = mAuth.getCurrentUser().getEmail().toString();
        useremail.setText(user_email);
        edit_name.setEnabled(false);
        customSpinnerLayout.setEnabled(false);
        customBranchLayout.setEnabled(false);
        usn_new.setEnabled(false);
        customSpinnerLayout.setEnabled(false);



        final String[] user_name = {null};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users").child(mAuth.getCurrentUser().getUid());

        DatabaseReference college_db = FirebaseDatabase.getInstance().getReference();


        college_db.child("branches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> branch_list = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("branch_name").getValue(String.class).toUpperCase();
                    branch_list.add(areaName);
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item,branch_list);
                customBranchView.setAdapter(arrayAdapter);
                loading_pgs.setVisibility(View.GONE);
                customSpinnerLayout.setEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        college_db.child("colleges").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> collegenames_list = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("college_name").getValue(String.class).toUpperCase();
                    collegenames_list.add(areaName);
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item,collegenames_list);
                customTextView.setAdapter(arrayAdapter);
                loading_pgs.setVisibility(View.GONE);
                customSpinnerLayout.setEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.child("display_name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                user_name[0] = snapshot.getValue(String.class).toUpperCase();
                username.setText(user_name[0]);
                edit_name.setText(user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        databaseReference.child("usn").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                user_name[0] = snapshot.getValue(String.class).toUpperCase();
                usn_new.setText(user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        databaseReference.child("college_name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                user_name[0] = snapshot.getValue(String.class).toUpperCase();
                customTextView.setText(user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        databaseReference.child("branch").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                user_name[0] = snapshot.getValue(String.class).toUpperCase();
                customBranchView.setText(user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });



        databaseReference.child("user_updated").addValueEventListener(new ValueEventListener() {
            String[] user_updated = {null};
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                user_updated[0] = snapshot.getValue(String.class);
                if (user_updated[0].equals("1"))
                {
                    edit_name.setEnabled(false);
                    customSpinnerLayout.setEnabled(false);
                    customBranchLayout.setEnabled(false);
                    usn_new.setEnabled(false);
                    submit.setVisibility(View.GONE);
                    edit_name_info.setText("ⓘ :- These details cannot be changed anymore");
                }
                else
                {
                    edit_name.setEnabled(true);
                    submit.setVisibility(View.VISIBLE);
                    customSpinnerLayout.setEnabled(true);
                    customBranchLayout.setEnabled(true);
                    usn_new.setEnabled(true);
                    edit_name_info.setText("ⓘ :- You can update only once.");
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = usn_new.getText().toString().toUpperCase();
                String name = edit_name.getText().toString().toUpperCase();
                String branch = customBranchView.getText().toString().toUpperCase();
                String college = customTextView.getText().toString().toUpperCase();

                if (usn.isEmpty() || name.isEmpty() || branch.isEmpty() || college.isEmpty())
                {
                    Toast.makeText(editprofileactivity.this, "All fields required.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(mAuth.getCurrentUser().getUid())
                            .child("display_name")
                            .setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                        }
                    });


                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(mAuth.getCurrentUser().getUid())
                            .child("college_name")
                            .setValue(college).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                        }
                    });

                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(mAuth.getCurrentUser().getUid())
                            .child("branch")
                            .setValue(branch).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                        }
                    });

                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(mAuth.getCurrentUser().getUid())
                            .child("usn")
                            .setValue(usn).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                        }
                    });
                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(mAuth.getCurrentUser().getUid())
                            .child("user_updated")
                            .setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                        }
                    });
                }



            }
        });



    }
}