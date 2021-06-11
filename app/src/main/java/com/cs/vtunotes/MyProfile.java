package com.cs.vtunotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MyProfile extends Fragment {

    TextView editprofile_button;
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