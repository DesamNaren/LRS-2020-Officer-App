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
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL3UploadBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L2ScrutinyChecklistViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

public class L3UploadActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityL3UploadBinding binding;
    L2ScrutinyChecklistViewModel scrutinyCheckListViewModel;
    Context context;
    CustomProgressDialog customProgressDialog;
    String recommended_officer_remarks;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String applicationId, applicantName;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l3_upload);
        context = L3UploadActivity.this;
        binding.header.headerTitle.setText(R.string.upload_files);
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(L3UploadActivity.this);
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

        scrutinyCheckListViewModel = new L2ScrutinyChecklistViewModel(context, getApplication());

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customWarningAlert(L3UploadActivity.this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
            }
        });

        /*if (Utils.checkInternetConnection(context)) {
            customProgressDialog.show();
            LiveData<RoadDetailsResponse> roadDetailsResponseLiveData = scrutinyCheckListViewModel.getRoadDetailsResponse();
            roadDetailsResponseLiveData.observe(L2UploadActivity.this, new Observer<RoadDetailsResponse>() {
                @Override
                public void onChanged(RoadDetailsResponse roadDetailsResponse) {
                    roadDetailsResponseLiveData.removeObservers(L2UploadActivity.this);
                    if (roadDetailsResponse != null && roadDetailsResponse.getStatusCode() != null) {
                        if (roadDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            if (roadDetailsResponse.getData() != null && roadDetailsResponse.getData().size() > 0) {
                                roadListData = roadDetailsResponse.getData();
                                roadList.clear();
                                roadList.add(getString(R.string.select));
                                for (int i = 0; i < roadDetailsResponse.getData().size(); i++) {
                                    roadList.add(roadDetailsResponse.getData().get(i).getABBUTINGNAME());
                                }

                                ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                        roadList);
                                binding.spAbuttingRoadDetails.setAdapter(adapter);
                                LiveData<LandDetailsResponse> landDetailsResponse = scrutinyCheckListViewModel.getLandDetailsResponse();
                                landDetailsResponse.observe(L2UploadActivity.this, new Observer<LandDetailsResponse>() {
                                    @Override
                                    public void onChanged(LandDetailsResponse landDetailsResponse1) {
                                        landDetailsResponse.removeObservers(L2UploadActivity.this);
                                        if (landDetailsResponse1 != null && landDetailsResponse1.getStatus() != null) {
                                            if (landDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                                if (landDetailsResponse1.getRemarks() != null && landDetailsResponse1.getRemarks().size() > 0) {
                                                    landListData = landDetailsResponse1.getRemarks();
                                                    landUseList.clear();
                                                    landUseList.add(getString(R.string.select));
                                                    for (int i = 0; i < landDetailsResponse1.getRemarks().size(); i++) {
                                                        landUseList.add(landDetailsResponse1.getRemarks().get(i).getUSAGETYPE());
                                                    }

                                                    ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                                            landUseList);
                                                    binding.spLandUse.setAdapter(adapter);

                                                    LiveData<RecommendDetailsResponse> recommendDetailsResponse = scrutinyCheckListViewModel.getRecommendDetailsResponse();
                                                    recommendDetailsResponse.observe(L2UploadActivity.this, new Observer<RecommendDetailsResponse>() {
                                                        @Override
                                                        public void onChanged(RecommendDetailsResponse recommendDetailsResponse1) {
                                                            recommendDetailsResponse.removeObservers(L2UploadActivity.this);
                                                            if (recommendDetailsResponse1 != null && recommendDetailsResponse1.getStatus() != null) {
                                                                if (recommendDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                                                    if (recommendDetailsResponse1.getRecommendMasterList() != null && recommendDetailsResponse1.getRecommendMasterList().size() > 0) {
                                                                        recommendListData = recommendDetailsResponse1.getRecommendMasterList();
                                                                        customProgressDialog.dismiss();
                                                                        recommendList.clear();
                                                                        recommendList.add(getString(R.string.select));
                                                                        for (int i = 0; i < recommendDetailsResponse1.getRecommendMasterList().size(); i++) {
                                                                            recommendList.add(recommendDetailsResponse1.getRecommendMasterList().get(i).getRECNAME());
                                                                        }

                                                                        ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                                                                recommendList);
                                                                        binding.spRecommendationFor.setAdapter(adapter);
                                                                    } else {
                                                                        customProgressDialog.dismiss();
                                                                        Utils.customErrorAlert(L2UploadActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_recomm));
                                                                    }
                                                                } else if (recommendDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                                                                    customProgressDialog.dismiss();
                                                                    Snackbar.make(binding.scroll, recommendDetailsResponse1.getMessage(), Snackbar.LENGTH_SHORT).show();
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
                                                    customProgressDialog.dismiss();
                                                    Utils.customErrorAlert(L2UploadActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_land));
                                                }
                                            } else if (landDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                                                customProgressDialog.dismiss();
                                                Snackbar.make(binding.scroll, landDetailsResponse1.getMessage(), Snackbar.LENGTH_SHORT).show();
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
                                customProgressDialog.dismiss();
                                Utils.customErrorAlert(L2UploadActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_roads));
                            }
                        } else if (roadDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                            customProgressDialog.dismiss();
                            Snackbar.make(binding.scroll, roadDetailsResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
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
            Utils.customErrorAlert(L2UploadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }*/

        binding.btnLayout.btnCancel.setVisibility(View.VISIBLE);

        binding.btnLayout.btnCancel.setText(getResources().getString(R.string.reject));
        binding.btnLayout.btnProceed.setText(getResources().getString(R.string.approve));

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
        if (TextUtils.isEmpty(recommended_officer_remarks)) {
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
                            Utils.customSuccessAlert(L3UploadActivity.this, getString(R.string.app_name),
                                    "Approved Successfully", true, editor);

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
        Utils.customWarningAlert(this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
    }
}
