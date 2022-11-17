package com.mart.petsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TipsViewActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_view);

//        bottomNavigationView =(BottomNavigationView) findViewById(R.id.nav_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://petsforpets.000webhostapp.com/");


    }
}