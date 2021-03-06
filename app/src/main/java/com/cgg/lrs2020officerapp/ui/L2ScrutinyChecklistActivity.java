package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL2ScrutinyChecklistBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.land.LandListData;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendListData;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadListData;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitRequest;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L2ScrutinyChecklistViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class L2ScrutinyChecklistActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityL2ScrutinyChecklistBinding binding;
    L2ScrutinyChecklistViewModel scrutinyCheckListViewModel;
    Context context;
    CustomProgressDialog customProgressDialog;
    ArrayList<String> roadList, landUseList, recommendList;
    String plot_registered, name_tally, area_match, abutting_road, abuting_road_id, east, west, north, south, value_sale_deed, value_ason, pNo_objection, land_use, land_use_id, recomend, recomend_id, verification;
    List<RoadListData> roadListData;
    List<LandListData> landListData;
    List<RecommendListData> recommendListData;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String applicationId, applicantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_l2_scrutiny_checklist);
        context = L2ScrutinyChecklistActivity.this;
        binding.header.headerTitle.setText(R.string.scrutiny_checklist);
        customProgressDialog = new CustomProgressDialog(context);
        roadList = new ArrayList<>();
        landUseList = new ArrayList<>();
        recommendList = new ArrayList<>();
        roadListData = new ArrayList<>();
        landListData = new ArrayList<>();
        recommendListData = new ArrayList<>();
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(L2ScrutinyChecklistActivity.this);
            }
        });

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();
        try {
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
                Utils.customWarningAlert(L2ScrutinyChecklistActivity.this, getString(R.string.app_name), "Data will be lost. Do you want to go back?", editor);
            }
        });

        binding.btnSroRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WebActivity.class));
            }
        });

        if (Utils.checkInternetConnection(context)) {
            customProgressDialog.show();
            LiveData<RoadDetailsResponse> roadDetailsResponseLiveData = scrutinyCheckListViewModel.getRoadDetailsResponse();
            roadDetailsResponseLiveData.observe(L2ScrutinyChecklistActivity.this, new Observer<RoadDetailsResponse>() {
                @Override
                public void onChanged(RoadDetailsResponse roadDetailsResponse) {
                    roadDetailsResponseLiveData.removeObservers(L2ScrutinyChecklistActivity.this);
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
                                landDetailsResponse.observe(L2ScrutinyChecklistActivity.this, new Observer<LandDetailsResponse>() {
                                    @Override
                                    public void onChanged(LandDetailsResponse landDetailsResponse1) {
                                        landDetailsResponse.removeObservers(L2ScrutinyChecklistActivity.this);
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
                                                    recommendDetailsResponse.observe(L2ScrutinyChecklistActivity.this, new Observer<RecommendDetailsResponse>() {
                                                        @Override
                                                        public void onChanged(RecommendDetailsResponse recommendDetailsResponse1) {
                                                            recommendDetailsResponse.removeObservers(L2ScrutinyChecklistActivity.this);
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
                                                                        Utils.customErrorAlert(L2ScrutinyChecklistActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_recomm));
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
                                                    Utils.customErrorAlert(L2ScrutinyChecklistActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_land));
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
                                Utils.customErrorAlert(L2ScrutinyChecklistActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_roads));
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
            Utils.customErrorAlert(L2ScrutinyChecklistActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
        binding.rgWhetherPlotRegPrior.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.whether_plot_reg_prior_yes) {
                    plot_registered = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.whether_plot_reg_prior_no) {
                    plot_registered = AppConstants.NO;
                }
            }
        });
        binding.rgNameOfLrsTallying.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.name_of_lrs_tallying_yes) {
                    name_tally = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.name_of_lrs_tallying_no) {
                    name_tally = AppConstants.NO;
                }
            }
        });
        binding.rgAreaMatchingWithApplication.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.area_matching_with_application_yes) {
                    area_match = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.area_matching_with_application_no) {
                    area_match = AppConstants.NO;
                }
            }
        });
        binding.spAbuttingRoadDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                abutting_road = binding.spAbuttingRoadDetails.getSelectedItem().toString();
                for (int z = 0; z < roadListData.size(); z++) {
                    if (roadListData.get(z).getABBUTINGNAME().equalsIgnoreCase(abutting_road)) {
                        abuting_road_id = roadListData.get(z).getABBUTINGID();
                    }
                }
                if (abutting_road.equalsIgnoreCase("Road")) {
                    binding.cvEastWestNorSou.setVisibility(View.VISIBLE);
                } else {
                    binding.cvEastWestNorSou.setVisibility(View.GONE);
                    binding.etEast.setText(null);
                    binding.etWest.setText(null);
                    binding.etNorth.setText(null);
                    binding.etSouth.setText(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.rgSynoPlotnoExistingInDatabase.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.syno_plotno_existing_in_database_yes) {
                    pNo_objection = AppConstants.YES;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.syno_plotno_existing_in_database_no) {
                    pNo_objection = AppConstants.NO;
                }
            }
        });
        binding.spLandUse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                land_use = binding.spLandUse.getSelectedItem().toString();
                for (int z = 0; z < landListData.size(); z++) {
                    if (landListData.get(z).getUSAGETYPE().equalsIgnoreCase(land_use)) {
                        land_use_id = landListData.get(z).getUSAGETYPEID();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spRecommendationFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                recomend = binding.spRecommendationFor.getSelectedItem().toString();
                for (int z = 0; z < recommendListData.size(); z++) {
                    if (recommendListData.get(z).getRECNAME().equalsIgnoreCase(recomend)) {
                        recomend_id = recommendListData.get(z).getRECID();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                east = binding.etEast.getText().toString().trim();
                west = binding.etWest.getText().toString().trim();
                north = binding.etNorth.getText().toString().trim();
                south = binding.etSouth.getText().toString().trim();
                value_sale_deed = binding.etValueSaleDeed.getText().toString().trim();
                value_ason = binding.etValueAson.getText().toString().trim();
                verification = binding.etVerification.getText().toString().trim();
                if (validate()) {
                    L2SubmitRequest l2SubmitRequest = new L2SubmitRequest();
                    l2SubmitRequest.setPPRIDate(plot_registered);
                    l2SubmitRequest.setPNAMEMATCH(name_tally);
                    l2SubmitRequest.setPAREAMATCH(area_match);
                    l2SubmitRequest.setPROADDETAILS(abuting_road_id);
                    l2SubmitRequest.setPROADEAST(east);
                    l2SubmitRequest.setPROADWEST(west);
                    l2SubmitRequest.setPROADNORTH(north);
                    l2SubmitRequest.setPROADSOUTH(south);
                    l2SubmitRequest.setPMARKETVALUE(value_sale_deed);
                    l2SubmitRequest.setPMVASONDATE(value_ason);
                    l2SubmitRequest.setPPLOTOBJECTON(pNo_objection);
                    l2SubmitRequest.setPLANDUSEID(land_use_id);
                    l2SubmitRequest.setPRECOMMENDEDFOR(recomend_id);
                    l2SubmitRequest.setPREMARKS(verification);

                    Gson gson = new Gson();
                    String request = gson.toJson(l2SubmitRequest);
                    editor.putString(AppConstants.L2_SUBMIT_REQUEST, request);
                    editor.commit();

                    startActivity(new Intent(L2ScrutinyChecklistActivity.this,
                            L2UploadActivity.class).
                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
            }
        });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(plot_registered)) {
            callSnackBar(getString(R.string.whether_plot_registered_prior));
            return false;
        } else if (TextUtils.isEmpty(name_tally)) {
            callSnackBar(getString(R.string.whether_name_tallying));
            return false;
        } else if (TextUtils.isEmpty(area_match)) {
            callSnackBar(getString(R.string.area_matching_with_application));
            return false;
        } else if (abutting_road.equalsIgnoreCase(getString(R.string.select))) {
            callSnackBar(getString(R.string.select_road_details));
            return false;
        } else if (TextUtils.isEmpty(east) && binding.cvEastWestNorSou.getVisibility() == View.VISIBLE) {
            callSnackBar(getString(R.string.enter_east));
            return false;
        } else if (TextUtils.isEmpty(west) && binding.cvEastWestNorSou.getVisibility() == View.VISIBLE) {
            callSnackBar(getString(R.string.enter_west));
            return false;
        } else if (TextUtils.isEmpty(north) && binding.cvEastWestNorSou.getVisibility() == View.VISIBLE) {
            callSnackBar(getString(R.string.enter_north));
            return false;
        } else if (TextUtils.isEmpty(south) && binding.cvEastWestNorSou.getVisibility() == View.VISIBLE) {
            callSnackBar(getString(R.string.enter_south));
            return false;
        } else if (TextUtils.isEmpty(value_sale_deed)) {
            callSnackBar(getString(R.string.enter_value_as_per_sale));
            return false;
        } else if (Double.valueOf(value_sale_deed) <= 0) {
            callSnackBar(getString(R.string.enter_valid_value_as_per_sale));
            return false;
        } else if (TextUtils.isEmpty(value_ason)) {
            callSnackBar(getString(R.string.enter_value_as_one));
            return false;
        } else if (Double.valueOf(value_ason) <= 0) {
            callSnackBar(getString(R.string.enter_valid_value_as_one));
            return false;
        } else if (TextUtils.isEmpty(pNo_objection)) {
            callSnackBar(getString(R.string.plot_no_objection));
            return false;
        } else if (land_use.equalsIgnoreCase(getString(R.string.select))) {
            callSnackBar(getString(R.string.select_land_use));
            return false;
        } else if (recomend.equalsIgnoreCase(getString(R.string.select))) {
            callSnackBar(getString(R.string.select_recommend));
            return false;
        } else if (TextUtils.isEmpty(verification)) {
            callSnackBar(getString(R.string.enter_verification_remarks));
            return false;
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.scroll, msg, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
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
