package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.databinding.ActivityL1ScrutinyCheckListBinding;
import com.cgg.lrs2020officerapp.utils.Utils;


public class L1ChecklistActivity extends AppCompatActivity {

    ActivityL1ScrutinyCheckListBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l1_scrutiny_check_list);
        context = L1ChecklistActivity.this;
        binding.header.headerTitle.setText(R.string.scrutiny_checklist);
        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customWarningAlert(L1ChecklistActivity.this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
            }
        });
        binding.rgColonyAffectedLocalPlan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_affected_Local_plan_yes) {
                    binding.tvPlotNoEffected.setVisibility(View.VISIBLE);
                } else {
                    binding.tvPlotNoEffected.setVisibility(View.GONE);
                }
            }
        });

        binding.rgOpenSpaceAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.open_space_avail_yes) {
                    binding.tvPercentOpenSpace.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.open_space_avail_no) {
                    binding.tvPercentOpenSpace.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(L1ChecklistActivity.this, ImageUploadActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        Utils.customWarningAlert(this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
    }
}