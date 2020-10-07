package com.cgg.lrs2020officerapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.databinding.ActivityLayoutBinding;

public class LayoutActivity extends AppCompatActivity {
    ActivityLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout);
        binding.header.headerTitle.setText(getString(R.string.scrutiny_details));
        binding.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayoutActivity.this, ScrutinyCheckListActivity.class));
            }
        });
    }
}