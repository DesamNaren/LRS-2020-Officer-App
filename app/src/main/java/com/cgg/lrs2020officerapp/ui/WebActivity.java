package com.cgg.lrs2020officerapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityWebBinding;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.google.gson.Gson;

public class WebActivity extends AppCompatActivity {

    ActivityWebBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        binding.header.headerTitle.setText("SRO Registered Document");

        sharedPreferences = LRSApplication.get(WebActivity.this).getPreferences();
        editor = sharedPreferences.edit();

        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        WebView webView = findViewById(R.id.webview);
//        WebSettings ws = wb.getSettings();
//        ws.setJavaScriptEnabled(true);
//        wb.loadUrl(loginResponse.getsRO_DOC_LINK());
//        wb.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
//                progDailog.dismiss();
            }
        });

        webView.loadUrl(loginResponse.getsRO_DOC_LINK());
    }
}