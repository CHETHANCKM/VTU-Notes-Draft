package com.cs.vtunotes.modules.post_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.vtunotes.R;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class post_new_title extends AppCompatActivity {
    TextView path;
    ImageView ai_camera;
    Button uploadnotes;
    TextView ai_text;


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_title);
        path = findViewById(R.id.path);

        ai_camera = findViewById(R.id.ai_camera);
        uploadnotes = findViewById(R.id.uploadnotes);
        ai_text = findViewById(R.id.ai_text);

        Intent intent = getIntent();
        String m_subject_code = intent.getStringExtra("subject_code");
        String m_subject_name = intent.getStringExtra("subject_name");
        String m_module = intent.getStringExtra("module");
        String m_scheme = intent.getStringExtra("scheme");

        Date date = new Date();
        long questionid = date.getTime();

        path.setText(m_scheme+" -> "+m_subject_name+" -> "+m_module);





    }





}