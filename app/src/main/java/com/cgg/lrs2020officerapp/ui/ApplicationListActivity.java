package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ApplicationListAdapter;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityApplicationListBinding;
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

import java.util.ArrayList;
import java.util.List;


public class ApplicationListActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private TextView tv;
    private Context context;
    private ApplicationListViewModel viewModel;
    private ActivityApplicationListBinding binding;
    private List<ApplicationListData> list, applicationListData;
    private List<String> templist;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;
    private ApplicationListAdapter adapter;
    SearchView mySearchView;
    ApplicationRes applicationRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_application_list);
        context = ApplicationListActivity.this;

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();
        binding.header.headerTitle.setText(R.string.pending_for_scrutiny);
        applicationListData = new ArrayList<>();

        editor.putString(AppConstants.TEMP_APPLICATION_LIST, "");
        editor.commit();
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(ApplicationListActivity.this);
            }
        });


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        list = new ArrayList<>();
        try {
//            if (getSupportActionBar() != null) {
//                tv = new TextView(getApplicationContext());
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
//                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
//                tv.setLayoutParams(lp);
//                tv.setText(getResources().getString(R.string.pending_for_scrutiny));
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextColor(Color.WHITE);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//                getSupportActionBar().setCustomView(tv);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
//            }
            setupSearchView(binding.searchView);

            viewModel = new ApplicationListViewModel(context, getApplication());
            binding.setViewModel(viewModel);
            binding.btnLayout.btnProceed.setText("Update Field Inspection");

            if (loginResponse.getROLEID().equalsIgnoreCase("3")) {
                binding.btnLayout.llBtn.setVisibility(View.VISIBLE);
            } else if (loginResponse.getROLEID().equalsIgnoreCase("4") ||
                    loginResponse.getROLEID().equalsIgnoreCase("5")) {
                binding.btnLayout.llBtn.setVisibility(View.GONE);
            }

            /*binding.cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String selectAllFlag;
                    if (isChecked)
                        selectAllFlag = AppConstants.YES;
                    else
                        selectAllFlag = AppConstants.NO;
                    for (int i = 0; i < applicationListData.size(); i++)
                        applicationListData.get(i).setFlag(selectAllFlag);
//                    setAdapter();
                    adapter.notifyDataSetChanged();
                }
            });*/


            binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    templist = new ArrayList<>();
                    String clusterId = sharedPreferences.getString(AppConstants.SELECTED_CLUSTERID, "");
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getCLUSTER_ID().equalsIgnoreCase(clusterId)) {
                            templist.add(list.get(i).getAPPLICATIONID());
                        }
                    }

//                    for (int i = 0; i < applicationListData.size(); i++) {
//                        if (applicationListData.get(i).getFlag().equalsIgnoreCase(AppConstants.YES))
//                            templist.add(list.get(i).getAPPLICATIONID());
//                    }

                    if (templist != null && templist.size() > 0) {
//                        Toast.makeText(context, "" + templist.size(), Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String applicationListDetails = gson.toJson(templist);
                        editor.putString(AppConstants.TEMP_APPLICATION_LIST, applicationListDetails);
                        editor.commit();

                        Intent intent = new Intent(ApplicationListActivity.this, L1ScrutinyChecklistActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Please select atleast one application", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            binding.swipeRV.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ApplicationReq request = new ApplicationReq();
                    request.setOFFICEID(loginResponse.getOFFICEID());
                    request.setAUTHORITYID(loginResponse.getAUTHORITYID());
                    request.setROLEID(loginResponse.getROLEID());
                    request.setSTATUSID(loginResponse.getsTATUS_ID());
                    request.setTOKENID(loginResponse.gettOKEN_ID());

                    if (Utils.checkInternetConnection(ApplicationListActivity.this)) {
                        viewModel.getApplicationListCall(request).observe(ApplicationListActivity.this, new Observer<ApplicationRes>() {
                            @Override
                            public void onChanged(ApplicationRes response) {

                                if (response != null && response.getStatusCode() != null) {
                                    if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                        list = response.getData();
                                        if (list != null && list.size() > 0) {
                                            binding.recyclerView.setVisibility(View.VISIBLE);
//                                            binding.cbSelectAll.setVisibility(View.VISIBLE);
//                                            binding.cbSelectAll.setChecked(false);
                                            binding.tvEmpty.setVisibility(View.GONE);

                                            for (int i = 0; i < list.size(); i++) {
                                                list.get(i).setFlag(AppConstants.YES);
                                            }
                                            applicationListData.clear();
                                            String clusterId = sharedPreferences.getString(AppConstants.SELECTED_CLUSTERID, "");

                                            for (int i = 0; i < list.size(); i++) {
                                                if (list.get(i).getCLUSTER_ID().equalsIgnoreCase(clusterId)) {
                                                    applicationListData.add(list.get(i));
                                                }
                                            }
                                            setAdapter();
                                        } else {
                                            binding.recyclerView.setVisibility(View.GONE);
//                                            binding.cbSelectAll.setVisibility(View.GONE);
                                            binding.tvEmpty.setVisibility(View.VISIBLE);
                                        }

                                    } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                                        binding.recyclerView.setVisibility(View.GONE);
                                        binding.tvEmpty.setVisibility(View.VISIBLE);
                                        binding.tvEmpty.setText(response.getStatusMessage());
//                                        Utils.customErrorAlert(ListActivity.this, getString(R.string.app_name), response.getStatusMessage());
                                    } else {
                                        Utils.customErrorAlert(ApplicationListActivity.this, getString(R.string.app_name), getString(R.string.something));
                                    }

                                } else {
                                    Utils.customErrorAlert(ApplicationListActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                                }

                                boolean flag;
                                List<Cluster> clusterList = new ArrayList<>();
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

                                Gson gson = new Gson();
                                String applicationListDetails = gson.toJson(response);
                                editor.putString(AppConstants.APPLICATION_LIST_RESPONSE, applicationListDetails);
                                String clusterListString = gson.toJson(clusterList);
                                editor.putString(AppConstants.CLUSTERLIST, clusterListString);
                                editor.commit();

                            }
                        });
                    } else {
                        Utils.customErrorAlert(ApplicationListActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.plz_check_int));
                    }
                    binding.swipeRV.setRefreshing(false); // Disables the refresh icon
                }
            });

            Gson gson = new Gson();
            applicationRes = gson.fromJson(sharedPreferences.getString(AppConstants.APPLICATION_LIST_RESPONSE, ""), ApplicationRes.class);
            if (applicationRes != null && applicationRes.getStatusCode() != null) {
                if (applicationRes.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                    list = applicationRes.getData();
                    if (list != null && list.size() > 0) {
                        binding.recyclerView.setVisibility(View.VISIBLE);
//                        binding.cbSelectAll.setVisibility(View.VISIBLE);
                        binding.tvEmpty.setVisibility(View.GONE);

                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setFlag(AppConstants.YES);
                        }
                        applicationListData.clear();
                        String clusterId = sharedPreferences.getString(AppConstants.SELECTED_CLUSTERID, "");
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCLUSTER_ID().equalsIgnoreCase(clusterId)) {
                                applicationListData.add(list.get(i));
                            }
                        }

                        setAdapter();
                    } else {
                        binding.recyclerView.setVisibility(View.GONE);
//                        binding.cbSelectAll.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                    }
                } else if (applicationRes.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setText(applicationRes.getStatusMessage());
//                Utils.customErrorAlert(context, getString(R.string.app_name), applicationRes.getStatusMessage());
                } else {
                    Utils.customErrorAlert(ApplicationListActivity.this, getString(R.string.app_name), getString(R.string.something));
                }
            } else {
                Utils.customErrorAlert(ApplicationListActivity.this, getString(R.string.app_name), getString(R.string.server_not));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        adapter = new ApplicationListAdapter(context, applicationListData, loginResponse.getROLEID());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
    }

    //    private void callList() {
//        ApplicationReq request = new ApplicationReq();
//        request.setOFFICEID(loginResponse.getOFFICEID());
//        request.setAUTHORITYID(loginResponse.getAUTHORITYID());
//        request.setROLEID(loginResponse.getROLEID());
//        request.setSTATUSID(AppConstants.STATUS_ID);
//        request.setUSERID(loginResponse.getUSERID());
//
//        if (Utils.checkInternetConnection(context)) {
//            viewModel.callApplicationList(request);
//        } else {
//            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
//        }
//    }
    private void setupSearchView(SearchView searchView) {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (adapter != null) {
                        adapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }


        });
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search here");
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (adapter != null) {
                        adapter.getFilter().filter(newText);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }


        });
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//        return false;
//    }
//
//    private SearchView searchView = null;
//    private Menu mMenu;
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        mMenu = menu;
//
//        mMenu.findItem(R.id.action_search).setVisible(true);
//
//        MenuItem menuItem = mMenu.findItem(R.id.action_search);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setQueryHint("Search by Applicant Name or OT Application Number");
//
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv.setVisibility(View.GONE);
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                searchView.clearFocus();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                search(searchView);
//                return true;
//            }
//        });
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                tv.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });
//        return true;
//    }

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
        //Utils.customLogoutAlert(this, getString(R.string.app_name),"Do you want to logout from app?", editor);

        /*Intent intent = new Intent(context, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
    }

    /*@Override
    public void selectAllApplications() {
        boolean yes_flag = true;
        for (int i = 0; i < applicationListData.size(); i++) {
            if (applicationListData.get(i).getFlag().equalsIgnoreCase(AppConstants.NO)) {
                yes_flag = false;
                break;
            }
        }
        if (yes_flag)
            binding.cbSelectAll.setChecked(true);
        else
            binding.cbSelectAll.setChecked(false);
    }

    @Override
    public void deSelectAllApplications() {
        boolean no_flag = true;
        for (int i = 0; i < applicationListData.size(); i++) {
            if (applicationListData.get(i).getFlag().equalsIgnoreCase(AppConstants.YES)) {
                no_flag = false;
                break;
            }
        }
        if (no_flag)
            binding.cbSelectAll.setChecked(false);
        else
            binding.cbSelectAll.setChecked(true);
    }*/
}
