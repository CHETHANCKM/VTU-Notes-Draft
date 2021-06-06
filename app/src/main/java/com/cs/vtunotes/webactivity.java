package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class webactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webactivity);

        Intent intent = getIntent();
        String geturl = intent.getStringExtra("url");

        WebView mywebview = (WebView) findViewById(R.id.webview);
        String unencodedHtml =
                "<html><body>'%23' is the percent code for ‘#‘ </body></html>";
        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                Base64.NO_PADDING);
        mywebview.loadData(encodedHtml, "text/html", "base64");
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebview.loadUrl("https://docs.google.com/viewerng/viewer?url="+geturl);
    }
}