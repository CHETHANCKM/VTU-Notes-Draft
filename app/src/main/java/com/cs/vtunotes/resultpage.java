package com.cs.vtunotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class resultpage extends AppCompatActivity {
//    private WebView webView;
    SwipeRefreshLayout swiperefresh;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resultpage);
//        webView = (WebView) findViewById(R.id.webview);
//
//        String unencodedHtml =
//                "<html><body>'%23' is the percent code for ‘#‘ </body></html>";
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        webView.loadData(encodedHtml, "text/html", "base64");
//
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setAppCacheEnabled(false);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webView.clearHistory();
//        webView.clearFormData();
//        webView.clearCache(true);
//        webView.loadUrl("https://results.vtu.ac.in/");



    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

}
