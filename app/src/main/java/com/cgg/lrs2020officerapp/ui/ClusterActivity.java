package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ClusterListAdapter;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityClusterBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
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
    Gson gson;
    private List<ApplicationListData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cluster);

        clusterList = new ArrayList<>();
        sharedPreferences = LRSApplication.get(ClusterActivity.this).getPreferences();
        editor = sharedPreferences.edit();
        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            gson = new Gson();
            loginResponse = gson.fromJson(str, LoginResponse.class);

            binding.name.setText("" + loginResponse.getUserName());
            binding.designation.setText("" + loginResponse.getdESIGNATION());

        } catch (Exception e) {
            e.printStackTrace();
        }
        viewModel = new ApplicationListViewModel(ClusterActivity.this, getApplication());

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Type type = new TypeToken<List<Cluster>>() {
            }.getType();
            String clusterString = sharedPreferences.getString(AppConstants.CLUSTERLIST, "");
            clusterList = gson.fromJson(clusterString, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (clusterList != null && clusterList.size() > 0) {
            setAdapter();
        } else {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvCluster.setVisibility(View.GONE);
        }

        binding.swipeRV.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApplicationReq request = new ApplicationReq();
                request.setOFFICEID(loginResponse.getOFFICEID());
                request.setAUTHORITYID(loginResponse.getAUTHORITYID());
                request.setROLEID(loginResponse.getROLEID());
                request.setSTATUSID(loginResponse.getsTATUS_ID());
                request.setTOKENID(loginResponse.gettOKEN_ID());

                if (Utils.checkInternetConnection(ClusterActivity.this)) {
                    viewModel.getApplicationListCall(request).observe(ClusterActivity.this, new Observer<ApplicationRes>() {
                        @Override
                        public void onChanged(ApplicationRes response) {

                            if (response != null && response.getStatusCode() != null) {
                                if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                    list = response.getData();
                                    if (list != null && list.size() > 0) {
                                        binding.rvCluster.setVisibility(View.VISIBLE);
                                        binding.tvEmpty.setVisibility(View.GONE);

                                    } else {
                                        binding.rvCluster.setVisibility(View.GONE);
//                                            binding.cbSelectAll.setVisibility(View.GONE);
                                        binding.tvEmpty.setVisibility(View.VISIBLE);
                                    }

                                    boolean flag;
                                    Cluster cluster = new Cluster();
                                    cluster.setCluster_id(list.get(0).getCLUSTER_ID());
                                    cluster.setCluster_name(list.get(0).getCLUSTER_NAME());
                                    cluster.setCount(1);
                                    clusterList.clear();
                                    clusterList.add(cluster);
                                    for (int z = 1; z < list.size(); z++) {
                                        flag = true;
                                        for (int y = 0; y < clusterList.size(); y++) {
                                            if (clusterList.get(y).getCluster_id().equalsIgnoreCase(list.get(z).getCLUSTER_ID())) {
                                                flag = false;
                                                clusterList.get(y).setCount((clusterList.get(y).getCount()) + 1);
                                            }
                                        }
                                        if (flag) {
                                            Cluster cluster1 = new Cluster();
                                            cluster1.setCluster_id(list.get(z).getCLUSTER_ID());
                                            cluster1.setCluster_name(list.get(z).getCLUSTER_NAME());
                                            cluster1.setCount(1);
                                            clusterList.add(cluster1);
                                        }
                                    }

                                } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                                    binding.rvCluster.setVisibility(View.GONE);
                                    binding.tvEmpty.setVisibility(View.VISIBLE);
                                    binding.tvEmpty.setText(response.getStatusMessage());
                                    clusterList.clear();
                                } else {
                                    Utils.customErrorAlert(ClusterActivity.this, getString(R.string.app_name), getString(R.string.something));
                                }

                            } else {
                                Utils.customErrorAlert(ClusterActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                            }

                            Gson gson = new Gson();
                            String applicationListDetails = gson.toJson(response);
                            editor.putString(AppConstants.APPLICATION_LIST_RESPONSE, applicationListDetails);
                            String clusterListString = gson.toJson(clusterList);
                            editor.putString(AppConstants.CLUSTERLIST, clusterListString);
                            editor.commit();

                            setAdapter();
                        }
                    });
                } else {
                    Utils.customErrorAlert(ClusterActivity.this, getResources().getString(R.string.app_name),
                            getString(R.string.plz_check_int));
                }
                binding.swipeRV.setRefreshing(false); // Disables the refresh icon
            }
        });

    }

    private void setAdapter() {
        binding.tvEmpty.setVisibility(View.GONE);
        binding.rvCluster.setVisibility(View.VISIBLE);

        clusterListAdapter = new ClusterListAdapter(ClusterActivity.this, clusterList);
        binding.rvCluster.setAdapter(clusterListAdapter);
        binding.rvCluster.setLayoutManager(new LinearLayoutManager(ClusterActivity.this));
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
