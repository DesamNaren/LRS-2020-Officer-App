package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL2ViewScrutinyChecklistBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L2ScrutinyChecklistViewModel;

public class L2ViewScrutinyChecklistActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityL2ViewScrutinyChecklistBinding binding;
    L2ScrutinyChecklistViewModel scrutinyCheckListViewModel;
    Context context;
    CustomProgressDialog customProgressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String applicationId, applicantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l2_view_scrutiny_checklist);
        context = L2ViewScrutinyChecklistActivity.this;
        binding.header.headerTitle.setText(R.string.scrutiny_checklist);
        customProgressDialog = new CustomProgressDialog(context);

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(L2ViewScrutinyChecklistActivity.this);
            }
        });

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();
        try {
            applicationId = sharedPreferences.getString(AppConstants.APPLICATION_ID, "");
            applicantName = sharedPreferences.getString(AppConstants.APPLICANT_NAME, "");

            binding.basicLayout.applicationNo.setText(applicationId);
            binding.basicLayout.applicantName.setText(applicantName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scrutinyCheckListViewModel = new L2ScrutinyChecklistViewModel(context, getApplication());

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSroRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WebActivity.class));
            }
        });


        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(L2ViewScrutinyChecklistActivity.this,
                        L2ShortfallActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);

    }

    @Override
    public void handleError(String e, Context context) {
        Utils.customErrorAlert(context, getString(R.string.app_name), e);
    }
}
