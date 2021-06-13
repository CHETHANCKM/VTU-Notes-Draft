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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class post_new_title extends AppCompatActivity {
    TextView path;
    ImageView ai_camera;
    StorageReference mStorageRef;
    Button uploadnotes;
    TextView ai_text, file_name, file_size, username_text;
    FrameLayout choosefile,file_null,guidelines_pdf;
    Uri image_uri, file_uri=null;
    ProgressBar file_percentage;

    private  static final int CAMERA_REQUEST_CODE= 100;
    private static  final  int IMAGE_PICK_CAMERA_CODE= 300;
    private static final int FILE_SELECT_CODE = 0;

    String [] campermission;
    String [] storagepermission;

    InputImage image;
    String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_title);
        path = findViewById(R.id.path);
        choosefile = findViewById(R.id.choosefile);
        ai_camera = findViewById(R.id.ai_camera);
        uploadnotes = findViewById(R.id.uploadnotes);
        ai_text = findViewById(R.id.ai_text);
        file_name = findViewById(R.id.file_name);
        file_size = findViewById(R.id.file_size);
        file_percentage= findViewById(R.id.file_percentage);
        file_null = findViewById(R.id.file_null);
        username_text = findViewById(R.id.username_text);
        guidelines_pdf = findViewById(R.id.guidelines_pdf);
        View parentLayout = findViewById(android.R.id.content);
        Intent intent = getIntent();

        file_null.setVisibility(View.GONE);
        guidelines_pdf.setVisibility(View.VISIBLE);

        String m_subject_code = intent.getStringExtra("subject_code");
        String m_subject_name = intent.getStringExtra("subject_name");
        String m_module = intent.getStringExtra("module");
        String m_scheme = intent.getStringExtra("scheme");
        String branch = intent.getStringExtra("branch");
        String semester_name = intent.getStringExtra("semester_name");

        Toast.makeText(this, ""+semester_name, Toast.LENGTH_SHORT).show();

        path.setText(m_scheme+" -> "+m_subject_name+" -> "+m_module);

        campermission = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        FirebaseAuth mauth = FirebaseAuth.getInstance();

        user_name = mauth.getCurrentUser().getDisplayName();

        username_text.setText(user_name);


        uploadnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = ai_text.getText().toString().trim();

                if (title.isEmpty())
                {
                    Snackbar.make(parentLayout,"Question required" , Snackbar.LENGTH_LONG).show();
                }
                else if (file_uri==null)
                {
                    Snackbar.make(parentLayout,"Document required" , Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    upload_data(title, m_scheme, m_subject_name, m_subject_code, m_module, branch, semester_name);
                    ai_text.setEnabled(false);
                    ai_camera.setEnabled(false);
                    choosefile.setEnabled(false);
                }

            }
        });


        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try
                {
                    startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(post_new_title.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ai_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showimagepicdilog();

            }
        });
    }


    private void upload_data(String title, String m_scheme,
                             String m_subject_name, String m_subject_code, String m_module, String branch, String semester_name) {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        String  filepathandname = "Notes/questions/"+m_scheme+"/"+branch+"/"+semester_name+"/"+m_subject_name+"/"+m_subject_code+"/"+m_module;

        if (file_uri!=null)
        {


            mStorageRef = FirebaseStorage.getInstance().getReference().child(filepathandname);
            mStorageRef.putFile(file_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(@NonNull @NotNull UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    String downloadurl = uriTask.getResult().toString();

                    if (uriTask.isSuccessful())
                    {
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("document_url", downloadurl);
                        hashMap.put("question", title);
                        hashMap.put("question_id", timestamp);
                        hashMap.put("rating", "0");
                        hashMap.put("reports", "0");
                        hashMap.put("username", user_name);

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("Notes")
                                .child("questions")
                                .child(m_scheme)
                                .child(branch)
                                .child(semester_name)
                                .child(m_subject_name)
                                .child(m_subject_code)
                                .child(m_module);

                        reference.child(timestamp).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Toast.makeText(post_new_title.this, "com", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        }


    }

    private void showimagepicdilog() {
        if (!checkcampermission()) {
            requestcameraepermission();
        } else {
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
                Task<Text> result =  recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
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
        else if (resultCode == RESULT_OK && requestCode == FILE_SELECT_CODE)
        {
            file_uri = data.getData();

            file_percentage.setVisibility(View.VISIBLE);
            file_null.setVisibility(View.VISIBLE);
            guidelines_pdf.setVisibility(View.GONE);

            File fileToProcess = new File(""+file_uri);
            String FileName = fileToProcess.getName();
            String[] tempArray;
            String delimiter = "%2F";
            tempArray = FileName.split(delimiter);
            String result = tempArray[tempArray.length - 1];
            result = result.replace("%20", " ");
            file_name.setText(result);


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