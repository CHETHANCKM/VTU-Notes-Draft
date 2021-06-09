package com.cs.vtunotes.modules.post_notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;


import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class post_new_title extends AppCompatActivity {
    TextView path;
    ImageView ai_camera;
    Button uploadnotes;
    TextView ai_text;
    Uri image_uri = null;

    private  static final int CAMERA_REQUEST_CODE= 100;
    private static  final  int IMAGE_PICK_CAMERA_CODE= 300;


    String [] campermission;
    String [] storagepermission;

    InputImage image;


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

        campermission = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ai_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showimagepicdilog();
            }
        });
    }

    private void showimagepicdilog() {
        if (!checkcampermission())
        {
            requestcameraepermission();
        }
        else
        {
            pickfromcamera();
        }
    }


    private void requestcameraepermission() {
        ActivityCompat.requestPermissions(this, campermission, CAMERA_REQUEST_CODE);
    }

    private void pickfromcamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Temp pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "desc");
        image_uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(i, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkcampermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:
            {
                if (grantResults.length>0)
                {
                    boolean cameraaccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if (cameraaccepted)
                    {
                        pickfromcamera();
                    }
                    else
                    {
                        Toast.makeText(this, "Camera adnd storge permisson requiried", Toast.LENGTH_SHORT).show();
                    }

                }
                else

                {

                }
            }
            break;

        }


    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CAMERA_CODE)
        {
            try
            {
                image = InputImage.fromFilePath(getApplicationContext(), image_uri);
                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                Task<Text> result =
                        recognizer.process(image)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        // Task completed successfully
                                        processTextBlock(visionText);


                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


    private void processTextBlock(Text result) {
        // [START mlkit_process_text_block]
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks())
        {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines())
            {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements())
                {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }

        }
        ai_text.setText(resultText.toString());
    }

}