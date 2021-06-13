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

import com.cs.vtunotes.physics_chemistry.scheme;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class Dashboard extends Fragment {
    TextView hello_msg;
//    private AdView mAdView1, mAdView2, mAdView3;
    FirebaseAuth mAuth;
    FrameLayout complete_profile,branches_more,more_questionpapers,physicsandchemi,mathematics,civil, cse,eee, ece, ise, mech;
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
//        branches_more = v.findViewById(R.id.branches_more);
//        more_questionpapers = v.findViewById(R.id.more_questionpapers);
        physicsandchemi = v.findViewById(R.id.physicsandchemi);
        mathematics  = v.findViewById(R.id.mathematics);

        civil = v.findViewById(R.id.civil);
        cse = v.findViewById(R.id.cse);
        eee = v.findViewById(R.id.eee);
        ece = v.findViewById(R.id.ece);
        ise = v.findViewById(R.id.ise);
        mech = v.findViewById(R.id.mech);

        mAuth = FirebaseAuth.getInstance();
        getTimeFromAndroid();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                //        AdView adView = new AdView(getContext());
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

//
//        mAdView1 = v.findViewById(R.id.adView1);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView1.loadAd(adRequest);
//
//        mAdView2 = v.findViewById(R.id.adView2);
//        mAdView2.loadAd(adRequest);
            }
        });




        complete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), editprofileactivity.class);
                startActivity(i);
            }
        });


        mathematics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Mathematics");
                i.putExtra("db_code", "maths");
                startActivity(i);
            }
        });

        physicsandchemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Physics and Chemistry");
                i.putExtra("db_code", "physics_chemi");
                startActivity(i);
            }
        });
        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Civil Engineering");
                i.putExtra("db_code", "civil");
                startActivity(i);
            }
        });
        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Computer Science");
                i.putExtra("db_code", "cse");
                startActivity(i);
            }
        });
        eee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Elec and Electronics");
                i.putExtra("db_code", "eee");
                startActivity(i);
            }
        });
        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Elec and Communication");
                i.putExtra("db_code", "ece");
                startActivity(i);
            }
        });

        ise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Information Science");
                i.putExtra("db_code", "ise");
                startActivity(i);
            }
        });

        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), scheme.class);
                i.putExtra("branch","Mechanical Engineering");
                i.putExtra("db_code", "mech");
                startActivity(i);
            }
        });


//        branches_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//                bottomSheetDialog.setContentView(R.layout.more_branches);
//
////                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
//
//
//                bottomSheetDialog.show();
//            }
//        });


//        more_questionpapers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//                bottomSheetDialog.setContentView(R.layout.more_questionpapers);
//
////                LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
//
//
//                bottomSheetDialog.show();
//            }
//        });

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
        FirebaseAuth mauth = FirebaseAuth.getInstance();


        String name = mauth.getCurrentUser().getDisplayName().toLowerCase();
        Date dt = new Date();
        int hours = dt.getHours();

        if(hours>=0 && hours<12){
            hello_msg.setText("Good morning, "+name);
        }else if(hours>=12 && hours<=16){
            hello_msg.setText("Good afternoon, "+name);
        }else if(hours>=16 && hours<=24){
            hello_msg.setText("Good evening, "+name);
        }
    }


}