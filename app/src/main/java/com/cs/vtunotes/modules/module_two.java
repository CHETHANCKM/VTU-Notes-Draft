package com.cs.vtunotes.modules;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cs.vtunotes.R;
import com.cs.vtunotes.modules.post_notes.post_new_title;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class module_two extends AppCompatActivity {
    TextView head_text;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_two);

        head_text = findViewById(R.id.head_text);
        floatingActionButton = findViewById(R.id.floating_addquestion);
        Intent intent = getIntent();
        String m_subject_code = intent.getStringExtra("subject_code");
        String m_subject_name = intent.getStringExtra("subject_name");
        String m_scheme = intent.getStringExtra("scheme_code");
        String branch = intent.getStringExtra("branch");
        String semester_name = intent.getStringExtra("semester_name");
        head_text.setText(m_subject_name+" - Module 2");



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), post_new_title.class);
                i.putExtra("scheme", m_scheme);
                i.putExtra("subject_code", m_subject_code);
                i.putExtra("subject_name", m_subject_name);
                i.putExtra("module", "Module 2");
                i.putExtra("branch", branch);
                i.putExtra("semester_name", semester_name);
                startActivity(i);
            }
        });

    }
}