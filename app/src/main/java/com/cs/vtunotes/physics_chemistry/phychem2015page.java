package com.cs.vtunotes.physics_chemistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.cs.vtunotes.editprofileactivity;
import com.cs.vtunotes.webactivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class phychem2015page extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Button add_subject_button, update_url_button;
    TextInputEditText add_subject_code, add_subject_name;
    EditText url_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phychem2015page);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    public void updateurl(View view)
    {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.phychemupdateurl);
        url_text = bottomSheetDialog.findViewById(R.id.url_text);
        update_url_button = bottomSheetDialog.findViewById(R.id.update_url_button);


        url_text.setText("Loading...");
        final String[] url = {null};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scheme").child("physics_chemi").child("2015");
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
                    Toast.makeText(phychem2015page.this, "Field contains same url", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase.getInstance()
                            .getReference("Scheme")
                            .child("physics_chemi")
                            .child("2015")
                            .setValue(new_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Toast.makeText(phychem2015page.this, "Scheme url was updated successfully.", Toast.LENGTH_SHORT).show();
                            url_text.clearFocus();
                            bottomSheetDialog.dismiss();

                        }
                    });

                }

            }
        });
        bottomSheetDialog.show();
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
                            Toast.makeText(phychem2015page.this, "All feilds required", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            FirebaseDatabase.getInstance()
                                    .getReference("Notes")
                                    .child("subjects")
                                    .child("2015")
                                    .child("physics_chemi")
                                    .child(subjectcode)
                                    .setValue(subjectname)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            Toast.makeText(phychem2015page.this, "New subject was added successfully.", Toast.LENGTH_SHORT).show();
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scheme").child("physics_chemi").child("2015");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                url[0] = snapshot.getValue(String.class);

                Toast.makeText(phychem2015page.this, ""+url[0], Toast.LENGTH_SHORT).show();

                if (url[0]==null)
                {
                    Toast.makeText(phychem2015page.this, "Loading... Try again", Toast.LENGTH_LONG).show();
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