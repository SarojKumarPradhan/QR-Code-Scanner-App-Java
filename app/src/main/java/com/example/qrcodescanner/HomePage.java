package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    WebView webView;
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        textView = (TextView)findViewById(R.id.TextV);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String displayUserID = sharedPreferences.getString("displayUserID","");


        String url = "http://omshree"+displayUserID+"-64743.portmap.host:64743/";

//        String url = "https://www.g"+displayUserID+"gle.com/";
//        String getqrdata = getIntent().getStringExtra("qrdata");
//
//       String url = "https://www.g"+getqrdata+"gle.com/";
//        String url = "https://www."+getqrdata+"ltair.com/";


//        textView.setText(String.valueOf(url));

        webView = (WebView) findViewById(R.id.WebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);


    }
    //This code is used for direct back to home screen from this activity........
    @Override
    public void onBackPressed() {
        //this is very good code for directly back to home page with out any alert msg*******************
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}