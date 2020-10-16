package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityLayoutBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsListData;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsReq;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsRes;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.LayoutViewModel;
import com.google.gson.Gson;

public class LayoutActivity extends AppCompatActivity implements ErrorHandlerInterface {
    ActivityLayoutBinding binding;
    private String applicationID;
    private LayoutViewModel viewModel;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout);
        binding.header.headerTitle.setText(getString(R.string.scrutiny_details));
        context = LayoutActivity.this;
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(LayoutActivity.this);
            }
        });
        viewModel = new LayoutViewModel(context, getApplication());
        binding.setViewModel(viewModel);

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();

        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            applicationID = sharedPreferences.getString(AppConstants.APPLICATION_ID, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        callApplicantDetails();
        ApplicantDetailsReq request = new ApplicantDetailsReq();
        request.setPAPPLICATIONID(applicationID);
        request.setPROLEID(loginResponse.getROLEID());

        viewModel.getApplicantDetailsResponse(request).observe(this, new Observer<ApplicantDetailsRes>() {
            @Override
            public void onChanged(ApplicantDetailsRes response) {

                if (response != null && response.getStatus() != null) {
                    if (response.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                        ApplicantDetailsListData data = response.getApplicantList().get(0);
                        binding.setData(data);
                    } else if (response.getStatus().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                        Utils.customErrorAlert(context, getString(R.string.app_name), response.getMessage());
                    } else {
                        Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
                    }
                } else {
                    Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
                }
            }
        });

        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginResponse.getROLEID().equalsIgnoreCase("3"))
                    startActivity(new Intent(LayoutActivity.this, ScrutinyCheckListActivity.class));
                else if (loginResponse.getROLEID().equalsIgnoreCase("4"))
                    startActivity(new Intent(LayoutActivity.this, L2ScrutinyCheckListActivity.class));
                else if (loginResponse.getROLEID().equalsIgnoreCase("5"))
                    startActivity(new Intent(LayoutActivity.this, L2ScrutinyCheckListActivity.class));
            }
        });
    }

    private void callApplicantDetails() {
        ApplicantDetailsReq request = new ApplicantDetailsReq();
        request.setPAPPLICATIONID(applicationID);
        request.setPROLEID(loginResponse.getROLEID());

        if (Utils.checkInternetConnection(context)) {
            viewModel.getApplicantDetailsResponseCall(request);
        } else {
            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }

    @Override
    public void handleError(String errMsg, Context context) {
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}