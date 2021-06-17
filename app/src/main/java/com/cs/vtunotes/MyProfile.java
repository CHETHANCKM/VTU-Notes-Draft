package com.cs.vtunotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class MyProfile extends Fragment {

    TextView editprofile_button,username_profile,email_profile;
    FrameLayout invitefriend;
    CardView rewardcard,signout;
    public MyProfile() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_my_profile, container, false);
        editprofile_button = v.findViewById(R.id.editprofile_button);
        invitefriend = v.findViewById(R.id.invitefriend);
        rewardcard = v.findViewById(R.id.rewardcard);
        signout = v.findViewById(R.id.signout);
        username_profile = v.findViewById(R.id.username_profile);
        email_profile = v.findViewById(R.id.email_profile);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail().toString().toLowerCase();
        email_profile.setText(email);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users").child(user.getUid());

        databaseReference.child("display_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final String[] user_name = {null};
                user_name[0] = snapshot.getValue(String.class).toUpperCase();
                username_profile.setText(user_name[0]);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mauth = FirebaseAuth.getInstance();

                mauth.signOut();
                Intent i = new Intent(getContext(), SplashActivity.class);
                startActivity(i);



            }
        });

        editprofile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), editprofileactivity.class);
                startActivity(i);
            }
        });

        rewardcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), reward.class);
                startActivity(i);
            }
        });

        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), inviteactivity.class);
                startActivity(i);
            }
        });

        return v;
    }

}