package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cs.vtunotes.modules.*;

public class module_parent extends AppCompatActivity {

    TextView head_text;
    FrameLayout module1, module2, module3, module4, module5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_parent);
        head_text = findViewById(R.id.head_text);
        module1 = findViewById(R.id.module1);
        module2 = findViewById(R.id.module2);
        module3 = findViewById(R.id.module3);
        module4 = findViewById(R.id.module4);
        module5 = findViewById(R.id.module5);


        Intent intent = getIntent();
        String new_subject_code = intent.getStringExtra("subject_code");
        String new_subject_name = intent.getStringExtra("subject_name");
        String new_scheme = intent.getStringExtra("scheme");
        String branch = intent.getStringExtra("branch");

        head_text.setText(new_subject_code+" - "+new_subject_name.toLowerCase());

        module1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), module_one.class);
                i.putExtra("subject_code", new_subject_code);
                i.putExtra("subject_name", new_subject_name);
                i.putExtra("scheme_code", new_scheme);
                i.putExtra("branch", branch);
                startActivity(i);
            }
        });

        module2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), module_two.class);
                i.putExtra("subject_code", new_subject_code);
                i.putExtra("subject_name", new_subject_name);
                i.putExtra("scheme_code", new_scheme);
                i.putExtra("branch", branch);
                startActivity(i);
            }
        });

        module3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), module_three.class);
                i.putExtra("subject_code", new_subject_code);
                i.putExtra("subject_name", new_subject_name);
                i.putExtra("scheme_code", new_scheme);
                i.putExtra("branch", branch);
                startActivity(i);
            }
        });

        module4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), module_four.class);
                i.putExtra("subject_code", new_subject_code);
                i.putExtra("subject_name", new_subject_name);
                i.putExtra("scheme_code", new_scheme);
                i.putExtra("branch", branch);
                startActivity(i);
            }
        });

        module5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), module_five.class);
                i.putExtra("subject_code", new_subject_code);
                i.putExtra("subject_name", new_subject_name);
                i.putExtra("scheme_code", new_scheme);
                i.putExtra("branch", branch);
                startActivity(i);
            }
        });

    }

}