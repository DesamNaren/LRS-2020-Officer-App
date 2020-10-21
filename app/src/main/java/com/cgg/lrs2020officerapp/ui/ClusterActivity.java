package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ApplicationListAdapter;
import com.cgg.lrs2020officerapp.adapter.ClusterListAdapter;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityClusterBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.Cluster;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClusterActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;
    ActivityClusterBinding binding;
    private ApplicationListViewModel viewModel;
    private List<Cluster> clusterList;
    ClusterListAdapter clusterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cluster);

        clusterList=new ArrayList<>();
        sharedPreferences = LRSApplication.get(ClusterActivity.this).getPreferences();
        editor = sharedPreferences.edit();
        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            Gson gson=new Gson();
            loginResponse =gson.fromJson(str, LoginResponse.class);
            Type type = new TypeToken<List<Cluster>>(){}.getType();
            String clusterString = sharedPreferences.getString(AppConstants.CLUSTERLIST, "");
            clusterList = gson.fromJson(clusterString, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        viewModel = new ApplicationListViewModel(ClusterActivity.this, getApplication());
        binding.name.setText("" + loginResponse.getUserName());
        binding.designation.setText("" + loginResponse.getdESIGNATION());

        if(clusterList!=null && clusterList.size()>0){
            clusterListAdapter = new ClusterListAdapter(ClusterActivity.this, clusterList);
            binding.rvCluster.setAdapter(clusterListAdapter);
            binding.rvCluster.setLayoutManager(new LinearLayoutManager(ClusterActivity.this));

        }

        /*ApplicationReq request = new ApplicationReq();
        request.setOFFICEID(loginResponse.getOFFICEID());
        request.setAUTHORITYID(loginResponse.getAUTHORITYID());
        request.setROLEID(loginResponse.getROLEID());
        if (loginResponse.getROLEID().equalsIgnoreCase("3"))
            request.setSTATUSID(AppConstants.STATUS_ID);
        else if (loginResponse.getROLEID().equalsIgnoreCase("4"))
            request.setSTATUSID("50");
        else if (loginResponse.getROLEID().equalsIgnoreCase("5"))
            request.setSTATUSID("80");

        request.setUSERID(loginResponse.getUSERID());

        if (Utils.checkInternetConnection(ClusterActivity.this)) {
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
                            Utils.customErrorAlert(ClusterActivity.this, getString(R.string.app_name), getString(R.string.something));
                        }

                    } else {
                        Utils.customErrorAlert(ClusterActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(ClusterActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.plz_check_int));
        }*/
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
