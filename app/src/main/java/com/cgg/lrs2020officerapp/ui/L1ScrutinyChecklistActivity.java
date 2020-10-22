package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.adapter.MultiSelectionSpinner;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL1ScrutinyCheckListBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.applicationList.Cluster;
import com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList.L1ScrutinityResponse;
import com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList.L1ScrutinyChecklistRequest;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitRequest;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L1ScrutinyChecklistViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class L1ScrutinyChecklistActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, ErrorHandlerInterface {

    ActivityL1ScrutinyCheckListBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String name_colony_locality, surveyNo_nameVillage, layout_extent, plot_no_applied, colony_falls_objectionable, colony_falls_prohibitory_land,
            colony_falls_master_plan, colony_affected_master_plan, plot_no_affected, open_space_avail, percent_open_space, land_use_asper_master_plan,
            lrs_permitted, legal_disputes;
    Context context;
    private List<String> approvelist;
    private List<String> shortfalllist;
    private List<String> rejectlist;
    private String selectedApprovalList, selectedShortfallList, selectedRejectList;
    L1ScrutinyChecklistViewModel scrutinyChecklistViewModel;
    LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l1_scrutiny_check_list);
        context = L1ScrutinyChecklistActivity.this;
        binding.header.headerTitle.setText(R.string.scrutiny_checklist);
        scrutinyChecklistViewModel=new L1ScrutinyChecklistViewModel(L1ScrutinyChecklistActivity.this,getApplication());

        shortfalllist = new ArrayList<>();
        rejectlist = new ArrayList<>();

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            approvelist = gson.fromJson(sharedPreferences.getString(AppConstants.TEMP_APPLICATION_LIST, ""), type);
            loginResponse = gson.fromJson(sharedPreferences.getString(AppConstants.LOGIN_RES, ""), LoginResponse.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

        L1ScrutinyChecklistRequest scrutinyChecklistRequest=new L1ScrutinyChecklistRequest();
        scrutinyChecklistRequest.setCLUSTER_ID("62");
        scrutinyChecklistRequest.setAPP_LIST("C/GHMC/003787/2020");
        scrutinyChecklistRequest.setAUTHORITY_ID(loginResponse.getAUTHORITYID());
        if (Utils.checkInternetConnection(L1ScrutinyChecklistActivity.this)) {
            scrutinyChecklistViewModel.getScrutinyCheckListResponse(scrutinyChecklistRequest).observe(this, new Observer<L1ScrutinityResponse>() {
                @Override
                public void onChanged(L1ScrutinityResponse response) {

                    if (response != null && response.getStatusCode() != null) {
                        if (response.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            binding.setChecklist(response.getCheckList().get(0));

                        } else if (response.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
//                            Utils.customErrorAlert(Dashboard.this, getString(R.string.app_name), response.getStatusMessage());
                        } else {
                            Utils.customErrorAlert(L1ScrutinyChecklistActivity.this, getString(R.string.app_name), getString(R.string.something));
                        }

                    } else {
                        Utils.customErrorAlert(L1ScrutinyChecklistActivity.this, getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(L1ScrutinyChecklistActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.plz_check_int));
        }
        binding.spPlotNoApprove.setListener(L1ScrutinyChecklistActivity.this, AppConstants.APPROVE);
        binding.spPlotNoShortfall.setListener(L1ScrutinyChecklistActivity.this, AppConstants.SHORTFALL);
        binding.spPlotNoReject.setListener(L1ScrutinyChecklistActivity.this, AppConstants.REJECT);

        approvelist.add(0, AppConstants.NONE);
        binding.spPlotNoApprove.setItems(approvelist);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customWarningAlert(L1ScrutinyChecklistActivity.this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
            }
        });
        binding.rgColonyAffectedLocalPlan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_affected_Local_plan_yes) {
                    binding.tvPlotNoEffected.setVisibility(View.VISIBLE);
                } else {
                    binding.tvPlotNoEffected.setVisibility(View.GONE);
                    binding.etPlotNoEffected.setText(null);
                }
            }
        });

        binding.rgColonyFallsObjectionableLands.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_objectionable_lands_yes) {
                    colony_falls_objectionable = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_objectionable_lands_no) {
                    colony_falls_objectionable = AppConstants.NO;
                }
            }
        });
        binding.rgColonyFallsProhibitoryLands.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_prohibitory_lands_yes) {
                    colony_falls_prohibitory_land = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_prohibitory_lands_no) {
                    colony_falls_prohibitory_land = AppConstants.NO;
                }
            }
        });
        binding.rgColonyFallsMasterPlan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_master_plan_yes) {
                    colony_falls_master_plan = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.colony_falls_master_plan_no) {
                    colony_falls_master_plan = AppConstants.NO;

                }
            }
        });
        binding.rgColonyAffectedLocalPlan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.colony_affected_Local_plan_yes) {
                    colony_affected_master_plan = AppConstants.YES;
                    binding.tvPlotNoEffected.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.colony_affected_Local_plan_no) {
                    colony_affected_master_plan = AppConstants.NO;
                    binding.tvPlotNoEffected.setVisibility(View.GONE);
                    binding.etPlotNoEffected.setText(null);
                }
            }
        });
        binding.rgOpenSpaceAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.open_space_avail_yes) {
                    binding.tvPercentOpenSpace.setVisibility(View.GONE);
                    binding.etPercentOpenSpace.setText(null);
                    open_space_avail = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.open_space_avail_no) {
                    binding.tvPercentOpenSpace.setVisibility(View.VISIBLE);
                    open_space_avail = AppConstants.NO;
                }
            }
        });
        binding.rgLrsPermitted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.lrs_permitted_yes) {
                    lrs_permitted = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.lrs_permitted_no) {
                    lrs_permitted = AppConstants.NO;
                }
            }
        });
        binding.rgLegalDisputesComplaintsAgainstTheLand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.legal_disputes_complaints_against_the_land_yes) {
                    legal_disputes = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.legal_disputes_complaints_against_the_land_no) {
                    legal_disputes = AppConstants.NO;
                }
            }
        });
        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_colony_locality = binding.etNameColonyLocalilty.getText().toString().trim();
                surveyNo_nameVillage = binding.etSurveyNo.getText().toString().trim();
                layout_extent = binding.etLayoutExtent.getText().toString().trim();
                plot_no_applied = binding.etPlotNo.getText().toString().trim();
                plot_no_affected = binding.etPlotNoEffected.getText().toString().trim();
                percent_open_space = binding.etPercentOpenSpace.getText().toString().trim();
                land_use_asper_master_plan = binding.etLandUseAsPerMasterPlan.getText().toString().trim();

                if (validate()) {
                    L1SubmitRequest l1SubmitRequest = new L1SubmitRequest();

                    l1SubmitRequest.setAPPLSAPPROVED(selectedApprovalList);
                    l1SubmitRequest.setAPPLSREJECTED(selectedRejectList);
                    l1SubmitRequest.setAPPLSSHORTFALL(selectedShortfallList);
                    l1SubmitRequest.setCREATEDBY(loginResponse.getUSERID());
                    l1SubmitRequest.setIPADDRESS("");
                    l1SubmitRequest.setLANDUSEASPERMAPLAN(land_use_asper_master_plan);
                    l1SubmitRequest.setLEGALDISPUTES(legal_disputes);
                    l1SubmitRequest.setLOCALITYAFFECTEDBYMP(colony_affected_master_plan);
                    l1SubmitRequest.setOBJECTIONABLELANDS(colony_falls_objectionable);
                    l1SubmitRequest.setOFFICERTYPE(loginResponse.getROLEID());
                    l1SubmitRequest.setOPENSPACE10PERCENT(open_space_avail);
                    l1SubmitRequest.setOPENSPACEHTLINE("");
                    l1SubmitRequest.setPROHIBITORYLANDS(colony_falls_prohibitory_land);
                    l1SubmitRequest.setREMARKS("");
                    l1SubmitRequest.setTOKENID(loginResponse.gettOKEN_ID());

                    Gson gson = new Gson();
                    String request = gson.toJson(l1SubmitRequest);
                    editor.putString(AppConstants.L1_SUBMIT_REQUEST, request);
                    editor.commit();
                    startActivity(new Intent(L1ScrutinyChecklistActivity.this, L1UploadActivity.class));
                }
            }
        });


    }

    private boolean validate() {
        if (TextUtils.isEmpty(name_colony_locality)) {
            callSnackBar(getString(R.string.enter_name_colony_locality));
            return false;
        } else if (TextUtils.isEmpty(surveyNo_nameVillage)) {
            callSnackBar(getString(R.string.enter_survey_no_name_village));
            return false;
        } else if (TextUtils.isEmpty(layout_extent)) {
            callSnackBar(getString(R.string.enter_extent_layout));
            return false;
        } else if (TextUtils.isEmpty(plot_no_applied)) {
            callSnackBar(getString(R.string.enter_plot_applied_for_lrs));
            return false;
        } else if (TextUtils.isEmpty(colony_falls_objectionable)) {
            callSnackBar(getString(R.string.check_colony_falls_objectionable_land));
            return false;
        } else if (TextUtils.isEmpty(colony_falls_prohibitory_land)) {
            callSnackBar(getString(R.string.check_colony_falls_prohibitory_land));
            return false;
        } else if (TextUtils.isEmpty(colony_falls_master_plan)) {
            callSnackBar(getString(R.string.check_colony_falls_master_plan));
            return false;
        } else if (TextUtils.isEmpty(colony_affected_master_plan)) {
            callSnackBar(getString(R.string.check_colony_affected_master_plan));
            return false;
        } else if (colony_affected_master_plan.equalsIgnoreCase(AppConstants.YES) && TextUtils.isEmpty(plot_no_affected)) {
            callSnackBar(getString(R.string.specify_plot_no_affected));
            return false;
        } else if (TextUtils.isEmpty(open_space_avail)) {
            callSnackBar(getString(R.string.check_open_space_available));
            return false;
        } else if (open_space_avail.equalsIgnoreCase(AppConstants.NO) && TextUtils.isEmpty(percent_open_space)) {
            callSnackBar(getString(R.string.enter_percentage_open_space));
            return false;
        } else if (TextUtils.isEmpty(land_use_asper_master_plan)) {
            callSnackBar(getString(R.string.specify_land_use));
            return false;
        } else if (TextUtils.isEmpty(lrs_permitted)) {
            callSnackBar(getString(R.string.check_lrs_permitted));
            return false;
        } else if (TextUtils.isEmpty(legal_disputes)) {
            callSnackBar(getString(R.string.whether_any_legal_disputes_complaints_against_the_land_covered_in_this_colony));
            return false;
        } else if (TextUtils.isEmpty(selectedApprovalList) || selectedApprovalList.equalsIgnoreCase(AppConstants.SELECT)) {
            callSnackBar(getString(R.string.select_plot_numbers_recommended_for_approval_of_lrs));
            return false;
        } else if (TextUtils.isEmpty(selectedShortfallList) || selectedShortfallList.equalsIgnoreCase(AppConstants.SELECT)) {
            callSnackBar(getString(R.string.select_plot_numbers_recommended_for_shortfall_of_lrs));
            return false;
        } else if (TextUtils.isEmpty(selectedRejectList) || selectedRejectList.equalsIgnoreCase(AppConstants.SELECT)) {
            callSnackBar(getString(R.string.select_plot_numbers_recommended_for_reject_of_lrs));
            return false;
        }
        return true;
    }

    public void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.scroll, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(3);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        Utils.customWarningAlert(this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
    }

    @Override
    public void selectedStrings(List<String> selectedlist, List<String> notSelectedlist, String flag) {

        if (flag.equalsIgnoreCase(AppConstants.APPROVE)) {
            if (selectedlist != null && selectedlist.size() > 0) {
                shortfalllist = notSelectedlist;
                if (shortfalllist != null && shortfalllist.size() > 0) {
                    if (!shortfalllist.get(0).equalsIgnoreCase(AppConstants.NONE))
                        shortfalllist.add(0, AppConstants.NONE);
                } else {
                    shortfalllist.add(0, AppConstants.NONE);
                }
            } else {
                shortfalllist.clear();
            }

            rejectlist.clear();
            binding.spPlotNoShortfall.setItems(shortfalllist);
            binding.spPlotNoReject.setItems(rejectlist);
            selectedApprovalList = selectedList(selectedlist);
            selectedShortfallList = "";
            selectedRejectList = "";
        } else if (flag.equalsIgnoreCase(AppConstants.SHORTFALL)) {
            if (selectedlist != null && selectedlist.size() > 0) {
                rejectlist = notSelectedlist;
                if (rejectlist != null && rejectlist.size() > 0) {
                    if (!rejectlist.get(0).equalsIgnoreCase(AppConstants.NONE))
                        rejectlist.add(0, AppConstants.NONE);
                } else {
                    rejectlist.add(0, AppConstants.NONE);
                }
            } else
                rejectlist.clear();
            binding.spPlotNoReject.setItems(rejectlist);
            selectedShortfallList = selectedList(selectedlist);
            selectedRejectList = "";
        } else if (flag.equalsIgnoreCase(AppConstants.REJECT)) {
            selectedRejectList = selectedList(selectedlist);
        }

    }

    private String selectedList(List<String> selectedlist) {

        String string, selectedStrings;

        string = selectedlist.toString();
        selectedStrings = string.substring(1, string.length() - 1);
        if (!selectedStrings.equals("")) {
            Toast.makeText(this, selectedStrings, Toast.LENGTH_LONG).show();
        }
        return selectedStrings;
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