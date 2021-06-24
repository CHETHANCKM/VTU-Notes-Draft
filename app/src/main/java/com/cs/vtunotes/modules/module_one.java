package com.cs.vtunotes.modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.cs.vtunotes.R;
import com.cs.vtunotes.modules.post_notes.post_new_title;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class module_one extends AppCompatActivity {

//    TextView head_text;
    FloatingActionButton floatingActionButton;
    MaterialToolbar toolbar;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_one);
//        head_text = findViewById(R.id.head_text);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = findViewById(R.id.floating_addquestion);


        Intent intent = getIntent();
        String m_subject_code = intent.getStringExtra("subject_code");
        String m_subject_name = intent.getStringExtra("subject_name");
        String m_scheme = intent.getStringExtra("scheme_code");
        String branch = intent.getStringExtra("branch");
        String semester_name = intent.getStringExtra("semester_name");
        toolbar.setTitle(m_subject_name+" - Module 1");
//        head_text.setText();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), post_new_title.class);
                i.putExtra("scheme", m_scheme);
                i.putExtra("subject_code", m_subject_code);
                i.putExtra("subject_name", m_subject_name);
                i.putExtra("module", "Module 1");
                i.putExtra("branch", branch);
                i.putExtra("semester_name", semester_name);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       getMenuInflater().inflate(R.menu.top_app_bar, menu);
       return true;

    }
}