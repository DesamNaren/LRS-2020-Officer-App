package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity  /*, DeleteVendingInterface*/ {

    private ActivityLoginBinding binding;
    private Context context;
    private boolean isLocReceived;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

//    private SVSSyncPlacesRepository svsSyncPlacesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = LoginActivity.this;


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ListActivity.class));
            }
        });
    }
}
