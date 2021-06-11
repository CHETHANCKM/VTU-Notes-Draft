package com.cs.vtunotes;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class login extends AppCompatActivity {
    Button gsignin;
    ProgressBar progressBar;
    private static final int RC_SIGN_IN = 1 ;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gsignin = findViewById(R.id.gsignin);
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        gsignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                gsignin.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FirebaseGoogleAuth (account);
            }
            catch (ApiException e) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Sign in failed.", Snackbar.LENGTH_LONG).show();
                FirebaseGoogleAuth (null);
            }
        }
    }



    private void FirebaseGoogleAuth(GoogleSignInAccount account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }
                else
                {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Login Failed. Please try later.", Snackbar.LENGTH_LONG).show();
                    updateUI(null);

                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        if (user!=null)
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();


            String name = user.getDisplayName();
            String email = user.getEmail();
            String id = account.getId();
            String uid = user.getUid();

            HashMap<Object, String> userdetails = new HashMap<>();
            userdetails.put("name", name);
            userdetails.put("uid", uid);
            userdetails.put("email", email);
            userdetails.put("account_id", id);
            userdetails.put("invite_code","");
            userdetails.put("user_banned", "false");
            userdetails.put("usn", "");
            userdetails.put("user_updated", "0");
            userdetails.put("display_name", account.getDisplayName());
            userdetails.put("user_created_on", dtf.format(now));

            DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");

            users.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Intent i = new Intent(login.this, MainActivity.class);
                        startActivity(i);
                        progressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        users.child(uid).setValue(userdetails)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                        Intent i = new Intent(login.this, MainActivity.class);
                                        startActivity(i);

                                        progressBar.setVisibility(View.GONE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                gsignin.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                View parentLayout = findViewById(android.R.id.content);
                                Snackbar.make(parentLayout, "Login Failed. Please try later.", Snackbar.LENGTH_LONG).show();
                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                gsignin.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                View parentLayout = findViewById(android.R.id.content);
                                Snackbar.make(parentLayout, "Cancelled by user.", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });







        }
        else
        {
            mAuth.signOut();
            Intent i = new Intent(login.this, MainActivity.class);
            startActivity(i);
        }

    }
}