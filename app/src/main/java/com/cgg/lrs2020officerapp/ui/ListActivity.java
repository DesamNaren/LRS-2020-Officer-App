package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ViewTaskAdapter;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityListBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationListData;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListCustomViewModel;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListViewModel;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private Context context;
    private ApplicationListViewModel viewModel;
    private ActivityListBinding binding;
    private List<ApplicationListData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        context = ListActivity.this;

        list = new ArrayList<>();
        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.pending_for_scrutiny));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            }

            viewModel = new ViewModelProvider(
                    this, new ApplicationListCustomViewModel(binding, context)).
                    get(ApplicationListViewModel.class);
            binding.setViewModel(viewModel);

            callLogin();

            viewModel.getApplicationListCall().observe(this, new Observer<ApplicationRes>() {
                @Override
                public void onChanged(ApplicationRes response) {

                    if (response != null && response.getStatusCode() != null) {
                        if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            list = response.getData();
                            if (list != null && list.size() > 0) {
                                binding.tvRecords.setText("Total Records: " + list.size());
                                ViewTaskAdapter viewTaskAdapter = new ViewTaskAdapter(context, list);
                                binding.recyclerView.setAdapter(viewTaskAdapter);
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                            }
                        } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                            Utils.customErrorAlert(context, getString(R.string.app_name), response.getStatusMessage());
                        } else {
                            Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
                        }
                    } else {
                        Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });

            /*binding.cv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LayoutActivity.class));

                }
            });

            binding.cv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LayoutActivity.class));
                }
            });

            binding.cv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LayoutActivity.class));
                }
            });

            binding.cv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LayoutActivity.class));
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callLogin() {
        ApplicationReq request = new ApplicationReq();
        request.setOFFICEID("119");
        request.setAUTHORITYID("3");
        request.setROLEID("3");
        request.setSTATUSID("30");
        request.setUSERID("IO000001");

        if (Utils.checkInternetConnection(context)) {
            viewModel.callApplicationList(request);
        } else {
            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
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
