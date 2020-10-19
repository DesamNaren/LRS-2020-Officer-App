package com.cgg.lrs2020officerapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityDashboardBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListViewModel;
import com.google.gson.Gson;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;
    ActivityDashboardBinding binding;
    private ApplicationListViewModel viewModel;
    private List<ApplicationListData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        sharedPreferences = LRSApplication.get(DashboardActivity.this).getPreferences();
        editor = sharedPreferences.edit();
        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);
            editor.putString(AppConstants.APPLICATION_LIST_RESPONSE, "");
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        viewModel = new ApplicationListViewModel(DashboardActivity.this, getApplication());
        binding.name.setText("" + loginResponse.getUserName());
        binding.designation.setText("" + loginResponse.getdESIGNATION());
        binding.pendingForScrutiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (list != null && list.size() > 0) {
                if (loginResponse.getROLEID().equalsIgnoreCase("3"))
                    startActivity(new Intent(DashboardActivity.this, ClusterActivity.class));
                else if (loginResponse.getROLEID().equalsIgnoreCase("4") || loginResponse.getROLEID().equalsIgnoreCase("5"))
                    startActivity(new Intent(DashboardActivity.this, ApplicationListActivity.class));
//                } else {
//                    Toast.makeText(Dashboard.this, R.string.data_empty, Toast.LENGTH_SHORT).show();
//                }
            }
        });
        binding.logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.customLogoutAlert(DashboardActivity.this, getString(R.string.app_name), "Do you want to logout from app?", editor);
            }
        });

        binding.About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, WebActivity.class);
                startActivity(i);
            }
        });

        ApplicationReq request = new ApplicationReq();
        request.setOFFICEID(loginResponse.getOFFICEID());
        request.setAUTHORITYID(loginResponse.getAUTHORITYID());
        request.setROLEID(loginResponse.getROLEID());
        if (loginResponse.getROLEID().equalsIgnoreCase("3"))
            request.setSTATUSID(AppConstants.STATUS_ID);
        else if (loginResponse.getROLEID().equalsIgnoreCase("4"))
            request.setSTATUSID("50");
        else if (loginResponse.getROLEID().equalsIgnoreCase("5"))
            request.setSTATUSID("80");

        request.setUSERID(loginResponse.gettOKEN_ID());

        if (Utils.checkInternetConnection(DashboardActivity.this)) {
            viewModel.getApplicationListCall(request).observe(this, new Observer<ApplicationRes>() {
                @Override
                public void onChanged(ApplicationRes response) {

                    if (response != null && response.getStatusCode() != null) {
                        if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            list = response.getData();
                            if (list != null && list.size() > 0) {
                                binding.tvPendCnt.setText("" + list.size());

                                Gson gson = new Gson();
                                String applicationListDetails = gson.toJson(response);
                                editor.putString(AppConstants.APPLICATION_LIST_RESPONSE, applicationListDetails);
                                editor.commit();
                            }
                        } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
//                            Utils.customErrorAlert(Dashboard.this, getString(R.string.app_name), response.getStatusMessage());
                        } else {
                            Utils.customErrorAlert(DashboardActivity.this, getString(R.string.app_name), getString(R.string.something));
                        }

                    } else {
                        Utils.customErrorAlert(DashboardActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DashboardActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.plz_check_int));
        }
    }

    @Override
    public void onBackPressed() {
        Utils.customLogoutAlert(this, getString(R.string.app_name), "Do you want to logout from app?", editor);
        //exitHandler();
    }

    private void exitHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setTitle("Exit application?");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Are you sure you want to Exit From this application?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // ExitActivity.exitApplication(MainActivity.this);

                            finish();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAndRemoveTask();
                            }





                       /* System.exit(0);
                            finishAffinity();*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
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
}
