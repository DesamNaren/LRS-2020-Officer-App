package com.cgg.lrs2020officerapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.ApplicationListAdapter;
import com.cgg.lrs2020officerapp.adapter.ShortFallListAdapter;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL2ShortfallBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.model.shortfall.ShortFallResponse;
import com.cgg.lrs2020officerapp.model.shortfall.ShortfallList;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L2ScrutinyChecklistViewModel;
import com.cgg.lrs2020officerapp.viewmodel.L2ShortfallViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class L2ShortfallActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityL2ShortfallBinding binding;
    L2ScrutinyChecklistViewModel scrutinyCheckListViewModel;
    L2ShortfallViewModel shortfallViewModel;
    Context context;
    CustomProgressDialog customProgressDialog;
    String short_fall, fee_intimation, recommended_officer_remarks;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String applicationId, applicantName;
    private LoginResponse loginResponse;
    List<ShortfallList> shortfallLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l2_shortfall);
        context = L2ShortfallActivity.this;
        binding.header.headerTitle.setText(R.string.upload_files);
        customProgressDialog = new CustomProgressDialog(context);

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(L2ShortfallActivity.this);
            }
        });

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();
        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);
            applicationId = sharedPreferences.getString(AppConstants.APPLICATION_ID, "");
            applicantName = sharedPreferences.getString(AppConstants.APPLICANT_NAME, "");

            binding.basicLayout.applicationNo.setText(applicationId);
            binding.basicLayout.applicantName.setText(applicantName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shortfallViewModel = new L2ShortfallViewModel(context, getApplication());
        if (Utils.checkInternetConnection(context)) {
            customProgressDialog.show();
            LiveData<ShortFallResponse> shortFallResponseLiveData = shortfallViewModel.getShortFallResponse();
            shortFallResponseLiveData.observe(L2ShortfallActivity.this, new Observer<ShortFallResponse>() {
                @Override
                public void onChanged(ShortFallResponse shortFallResponse) {
                    shortFallResponseLiveData.removeObservers(L2ShortfallActivity.this);
                    if (shortFallResponse != null && shortFallResponse.getStatusCode() != null) {
                        if (shortFallResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            if (shortFallResponse.getShortfallList() != null && shortFallResponse.getShortfallList().size() > 0) {
                                customProgressDialog.dismiss();
                                shortfallLists = shortFallResponse.getShortfallList();
                                ShortFallListAdapter adapter = new ShortFallListAdapter(context, shortfallLists);
                                binding.rvShortfall.setAdapter(adapter);
                                binding.rvShortfall.setLayoutManager(new LinearLayoutManager(context));
                                binding.rvShortfall.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));

                            } else {
                                customProgressDialog.dismiss();
                                Utils.customErrorAlert(L2ShortfallActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_roads));
                            }
                        } else if (shortFallResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                            customProgressDialog.dismiss();
                            Snackbar.make(binding.scroll, shortFallResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                        } else {
                            customProgressDialog.dismiss();
                            callSnackBar(getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.dismiss();
                        callSnackBar(getString(R.string.something));
                    }


                }
            });

        } else {
            Utils.customErrorAlert(L2ShortfallActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Utils.customWarningAlert(L2UploadActivity.this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
            }
        });

        binding.rgShortFall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.short_fall_yes) {
                    short_fall = AppConstants.YES;
                    binding.llShortYes.setVisibility(View.VISIBLE);
                    binding.llShortNo.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.short_fall_no) {
                    short_fall = AppConstants.NO;
                    binding.llShortNo.setVisibility(View.VISIBLE);
                    binding.llShortYes.setVisibility(View.GONE);
                } else {
                    short_fall = null;
                    binding.llShortNo.setVisibility(View.GONE);
                    binding.llShortYes.setVisibility(View.GONE);
                }
            }
        });
        binding.rgFeeIntimation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.fee_intimation_yes) {
                    fee_intimation = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.fee_intimation_no) {
                    fee_intimation = AppConstants.NO;
                }
            }
        });

        binding.btnLayout.btnCancel.setVisibility(View.VISIBLE);
        binding.btnLayout.btnProceed.setText(getResources().getString(R.string.submit));
        binding.btnLayout.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recommended_officer_remarks = binding.etRecommendedOfficerRemarks.getText().toString().trim();

                if (validate()) {
//                    SubmitScrutinyRequest submitScrutinyRequest = new SubmitScrutinyRequest();
//                    submitScrutinyRequest.setPPRIDate(short_fall);
//                    submitScrutinyRequest.setPNAMEMATCH(fee_intimation);
//                    submitScrutinyRequest.setPMARKETVALUE(recommended_officer_remarks);
//
//                    Gson gson = new Gson();
//                    String request = gson.toJson(submitScrutinyRequest);
//                    editor.putString(AppConstants.SUBMIT_REQUEST, request);
//                    editor.commit();
                    customInfoAlert();
                }
            }
        });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(short_fall)) {
            callSnackBar(getString(R.string.select_short_fall));
            return false;
        } else if (short_fall.equalsIgnoreCase(AppConstants.NO) && TextUtils.isEmpty(fee_intimation)) {
            callSnackBar(getString(R.string.select_fee_intimation));
            return false;
        } else if (TextUtils.isEmpty(recommended_officer_remarks)) {
            callSnackBar(getString(R.string.enter_recommended_officer_remarks));
            return false;
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.scroll, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    public void customInfoAlert() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(context));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(R.string.app_name);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                if (flag) {
                dialogMessage.setVisibility(View.VISIBLE);
//                } else {
//                    dialogMessage.setVisibility(View.GONE);
//                }
                dialogMessage.setText("Are you sure you want to submit data?");
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setText(R.string.yes);
                btDialogYes.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setBackgroundColor(getResources().getColor(R.color.red));
                btDialogNo.setText(R.string.cancel);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (Utils.checkInternetConnection(context)) {
                            customProgressDialog.show();
//                            addScrutinyViewModel.callSubmitAPI(submitScrutinyRequest);
                            Utils.customSuccessAlert(L2ShortfallActivity.this, getString(R.string.app_name),
                                    "L2 Review Successfully completed", true, editor);

                        } else {
                            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
                        }
                    }
                });

                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Utils.customWarningAlert(this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
    }
}
