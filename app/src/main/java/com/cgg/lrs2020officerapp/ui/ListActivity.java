package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ViewTaskAdapter;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityListBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListCustomViewModel;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private TextView tv;
    private Context context;
    private ApplicationListViewModel viewModel;
    private ActivityListBinding binding;
    private List<ApplicationListData> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;
    private ViewTaskAdapter viewTaskAdapter;
    SearchView mySearchView;
    ApplicationRes applicationRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        context = ListActivity.this;

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();
        binding.header.headerTitle.setText(R.string.pending_for_scrutiny);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(ListActivity.this);
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

            viewModel = new ViewModelProvider(
                    this, new ApplicationListCustomViewModel(binding, context)).
                    get(ApplicationListViewModel.class);
            binding.setViewModel(viewModel);

            binding.swipeRV.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ApplicationReq request = new ApplicationReq();
                    request.setOFFICEID(loginResponse.getOFFICEID());
                    request.setAUTHORITYID(loginResponse.getAUTHORITYID());
                    request.setROLEID(loginResponse.getROLEID());
                    request.setSTATUSID(AppConstants.STATUS_ID);
                    request.setUSERID(loginResponse.getUSERID());

                    if (Utils.checkInternetConnection(ListActivity.this)) {
                        viewModel.getApplicationListCall(request).observe(ListActivity.this, new Observer<ApplicationRes>() {
                            @Override
                            public void onChanged(ApplicationRes response) {

                                if (response != null && response.getStatusCode() != null) {
                                    if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                        list = response.getData();
                                        if (list != null && list.size() > 0) {
                                            binding.recyclerView.setVisibility(View.VISIBLE);
                                            binding.tvEmpty.setVisibility(View.GONE);
                                            viewTaskAdapter = new ViewTaskAdapter(context, list);
                                            binding.recyclerView.setAdapter(viewTaskAdapter);
                                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                            binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                                        } else {
                                            binding.recyclerView.setVisibility(View.GONE);
                                            binding.tvEmpty.setVisibility(View.VISIBLE);
                                        }

                                    } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                                        Utils.customErrorAlert(ListActivity.this, getString(R.string.app_name), response.getStatusMessage());
                                    } else {
                                        Utils.customErrorAlert(ListActivity.this, getString(R.string.app_name), getString(R.string.something));
                                    }
                                } else {
                                    Utils.customErrorAlert(ListActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                                }
                            }
                        });
                    } else {
                        Utils.customErrorAlert(ListActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.plz_check_int));
                    }
                    binding.swipeRV.setRefreshing(false); // Disables the refresh icon
                }
            });

            Gson gson = new Gson();
            applicationRes = gson.fromJson(sharedPreferences.getString(AppConstants.APPLICATION_LIST_RESPONSE, ""), ApplicationRes.class);
            if (applicationRes != null && applicationRes.getStatusCode() != null) {
                list = applicationRes.getData();
                if (list != null && list.size() > 0) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setVisibility(View.GONE);

                    viewTaskAdapter = new ViewTaskAdapter(context, list);
                    binding.recyclerView.setAdapter(viewTaskAdapter);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                }else {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                }
            } else {
                Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    if (viewTaskAdapter != null) {
                        viewTaskAdapter.getFilter().filter(newText);
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
                    if (viewTaskAdapter != null) {
                        viewTaskAdapter.getFilter().filter(newText);
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

        Intent intent = new Intent(context, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
