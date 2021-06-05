package com.cs.vtunotes.physics_chemistry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cs.vtunotes.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class phychem2015page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phychem2015page);
    }

    public void updateurl(View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.phychemupdateurl);

//                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);


        bottomSheetDialog.show();
    }

    public void addsubject(View view) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_subject);

//                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);


        bottomSheetDialog.show();
    }
}