package com.cs.vtunotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class Dashboard extends Fragment {
    TextView hello_msg;
    private AdView mAdView1, mAdView2, mAdView3;
    FirebaseAuth mAuth;
    FrameLayout complete_profile;
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
        String name = mAuth.getCurrentUser().getDisplayName().toLowerCase().toString();
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