package com.cs.vtunotes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class inviteactivity extends AppCompatActivity {

    TextView invite_code_text;
    Button share;
    Uri shortLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviteactivity);
        invite_code_text = findViewById(R.id.invite_code_text);
        share = findViewById(R.id.share);

        String mauth = FirebaseAuth.getInstance().getUid();


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortLink.toString().isEmpty())
                {
                }
                else
                {
                    String referrerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String subject = String.format("%s wants you to play MyExampleGame!", referrerName);
                    String invitationLink = shortLink.toString();
                    String msg = "Let's play MyExampleGame together! Use my referrer link: "
                            + invitationLink;
                    String msgHtml = String.format("<p>Let's play MyExampleGame together! Use my "
                            + "<a href=\"%s\">referrer link</a>!</p>", invitationLink);

                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_TEXT, msg);
                    i.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
                    i.setType("text/plain");
                    startActivity(i);
                }

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String invitelink = "https://coronakarnataka.in/?invitedby"+uid;

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(invitelink))
                .setAndroidParameters(
                new DynamicLink.AndroidParameters.Builder("com.cs.vtunotes")
                        .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            invite_code_text.setText(shortLink.toString());
                            Toast.makeText(inviteactivity.this, ""+shortLink, Toast.LENGTH_SHORT).show();

                        }
                        else
                            {
                                Toast.makeText(inviteactivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }



}