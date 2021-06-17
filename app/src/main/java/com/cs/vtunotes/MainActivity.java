package com.cs.vtunotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottombar;
    FrameLayout prentcontainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottombar = findViewById(R.id.bottombar);
        prentcontainer = findViewById(R.id.parentcontainer);
        bottombar.setOnNavigationItemSelectedListener(this);

        loadfragment(new Dashboard());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user==null){
            Intent i = new Intent(MainActivity.this, terms_and_conditions.class);
            startActivity(i);
        }
        else  {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

            databaseReference.child("display_name").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    final String[] user_name = {null};
                    user_name[0] = snapshot.getValue(String.class).toUpperCase();
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar snackbar =  Snackbar.make(parentLayout, "Signed in as "+user_name[0].toUpperCase()+".", Snackbar.LENGTH_SHORT);
                    snackbar.setAnchorView(bottombar).show();
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                }
            });




        }



    }


    public  boolean loadfragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.parentcontainer, fragment)
                    .commit();
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment fragment = null;


        switch (item.getItemId())
        {
            case R.id.dashboard_id:
                fragment = new Dashboard();
                break;
            case R.id.notification_id:
                fragment = new Notifications();
                break;

            case R.id.discussion:
                fragment = new blog();
                break;

            case R.id.profile_id:
                fragment = new MyProfile();



        }


        return loadfragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if(bottombar.getSelectedItemId() == R.id.dashboard_id)
        {
            super.onBackPressed();
            finish();
        }
        else
        {
            bottombar.setSelectedItemId(R.id.dashboard_id);
        }
    }
}