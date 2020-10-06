package com.cgg.lrs2020officerapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.databinding.ActivityScrutinyCheckListBinding;

public class ScrutinyCheckListActivity extends AppCompatActivity {

    ActivityScrutinyCheckListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrutiny_check_list);
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScrutinyCheckListActivity.this,ImageUploadActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
