package com.cgg.lrs2020officerapp;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.databinding.ActivityScrutinyCheckListBinding;

public class ScrutinyCheckListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityScrutinyCheckListBinding binding=
               DataBindingUtil.setContentView(this, R.layout.activity_scrutiny_check_list);

       binding.header.headerTitle.setText(R.string.scrutiny_checklist);
    }


}
