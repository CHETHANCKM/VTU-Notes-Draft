package com.cs.vtunotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class Dashboard extends Fragment {
    TextView hello_msg;
    private AdView mAdView1, mAdView2, mAdView3;
    FirebaseAuth mAuth;
    FrameLayout complete_profile,branches_more,more_questionpapers,physicsandchemi;
    CardView webiste;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        hello_msg = v.findViewById(R.id.hello_msg);
        complete_profile = v.findViewById(R.id.complete_profile);
        webiste = v.findViewById(R.id.webiste);
        branches_more = v.findViewById(R.id.branches_more);
        more_questionpapers = v.findViewById(R.id.more_questionpapers);
        physicsandchemi = v.findViewById(R.id.physicsandchemi);

        mAuth = FirebaseAuth.getInstance();
        getTimeFromAndroid();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");


        mAdView1 = v.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest);

        mAdView2 = v.findViewById(R.id.adView2);
        mAdView2.loadAd(adRequest);


        complete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), editprofileactivity.class);
                startActivity(i);
            }
        });

        physicsandchemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.cs.vtunotes.physics_chemistry.physicsandchemi.class);
                startActivity(i);
            }
        });

        branches_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(R.layout.more_branches);

//                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);


                bottomSheetDialog.show();
            }
        });


        more_questionpapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(R.layout.more_questionpapers);

//                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);


                bottomSheetDialog.show();
            }
        });

        webiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://results.vtu.ac.in/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getContext(), Uri.parse(url));
            }
        });

        return v;
    }

    private void getTimeFromAndroid() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext().getApplicationContext());

        String name = account.getGivenName().toLowerCase().toString();
        Date dt = new Date();
        int hours = dt.getHours();

        if(hours>=1 && hours<=12){
            hello_msg.setText("Good morning, "+name);
        }else if(hours>=12 && hours<=16){
            hello_msg.setText("Good afternoon, "+name);
        }else if(hours>=16 && hours<=24){
            hello_msg.setText("Good evening, "+name);
        }
    }


}