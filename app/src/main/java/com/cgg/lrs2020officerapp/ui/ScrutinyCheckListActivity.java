package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityScrutinyCheckListBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ScrutinyCheckListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ScrutinyCheckListActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityScrutinyCheckListBinding binding;
    ScrutinyCheckListViewModel scrutinyCheckListViewModel;
    Context context;
    CustomProgressDialog customProgressDialog;
    ArrayList<String> roadList,landUseList,recommendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrutiny_check_list);
        context = ScrutinyCheckListActivity.this;
        binding.header.headerTitle.setText(R.string.scrutiny_checklist);
        customProgressDialog = new CustomProgressDialog(context);
        roadList=new ArrayList<>();
        landUseList=new ArrayList<>();
        recommendList=new ArrayList<>();

        scrutinyCheckListViewModel = new ScrutinyCheckListViewModel(context, getApplication());

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScrutinyCheckListActivity.this, ImageUploadActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        if (Utils.checkInternetConnection(context)) {
            customProgressDialog.show();
            LiveData<RoadDetailsResponse> roadDetailsResponseLiveData = scrutinyCheckListViewModel.getRoadDetailsResponse();
            roadDetailsResponseLiveData.observe(ScrutinyCheckListActivity.this, new Observer<RoadDetailsResponse>() {
                @Override
                public void onChanged(RoadDetailsResponse roadDetailsResponse) {
                    roadDetailsResponseLiveData.removeObservers(ScrutinyCheckListActivity.this);
                    if (roadDetailsResponse != null && roadDetailsResponse.getStatusCode() != null) {
                        if (roadDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                            if (roadDetailsResponse.getData() != null && roadDetailsResponse.getData().size() > 0) {
                               roadList.clear();
                               roadList.add(getString(R.string.select));
                                for(int i=0;i<roadDetailsResponse.getData().size();i++){
                                    roadList.add(roadDetailsResponse.getData().get(i).getABBUTINGNAME());
                                }

                                ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                        roadList);
                                binding.spAbuttingRoadDetails.setAdapter(adapter);
                                LiveData<LandDetailsResponse> landDetailsResponse = scrutinyCheckListViewModel.getLandDetailsResponse();
                                landDetailsResponse.observe(ScrutinyCheckListActivity.this, new Observer<LandDetailsResponse>() {
                                    @Override
                                    public void onChanged(LandDetailsResponse landDetailsResponse1) {
                                        landDetailsResponse.removeObservers(ScrutinyCheckListActivity.this);
                                        if (landDetailsResponse1 != null && landDetailsResponse1.getStatus() != null) {
                                            if (landDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                                if (landDetailsResponse1.getRemarks() != null && landDetailsResponse1.getRemarks().size() > 0) {
                                                    landUseList.clear();
                                                    landUseList.add(getString(R.string.select));
                                                    for(int i=0;i<landDetailsResponse1.getRemarks().size();i++){
                                                        landUseList.add(landDetailsResponse1.getRemarks().get(i).getUSAGETYPE());
                                                    }

                                                    ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                                            landUseList);
                                                    binding.spLandUse.setAdapter(adapter);

                                                    LiveData<RecommendDetailsResponse> recommendDetailsResponse = scrutinyCheckListViewModel.getRecommendDetailsResponse();
                                                    recommendDetailsResponse.observe(ScrutinyCheckListActivity.this, new Observer<RecommendDetailsResponse>() {
                                                        @Override
                                                        public void onChanged(RecommendDetailsResponse recommendDetailsResponse1) {
                                                            recommendDetailsResponse.removeObservers(ScrutinyCheckListActivity.this);
                                                            if (recommendDetailsResponse1 != null && recommendDetailsResponse1.getStatus() != null) {
                                                                if (recommendDetailsResponse1.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                                                                    if (recommendDetailsResponse1.getRecommendMasterList() != null && recommendDetailsResponse1.getRecommendMasterList().size() > 0) {
                                                                        customProgressDialog.dismiss();
                                                                        recommendList.clear();
                                                                        recommendList.add(getString(R.string.select));
                                                                        for(int i=0;i<recommendDetailsResponse1.getRecommendMasterList().size();i++){
                                                                            recommendList.add(recommendDetailsResponse1.getRecommendMasterList().get(i).getRECNAME());
                                                                        }

                                                                        ArrayAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                                                                                recommendList);
                                                                        binding.spRecommendationFor.setAdapter(adapter);
                                                                    } else {
                                                                        customProgressDialog.dismiss();
                                                                        Utils.customErrorAlert(ScrutinyCheckListActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_recomm));
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
                                                    Utils.customErrorAlert(ScrutinyCheckListActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_land));
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
                                Utils.customErrorAlert(ScrutinyCheckListActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_roads));
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
            Utils.customErrorAlert(ScrutinyCheckListActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
        binding.spAbuttingRoadDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(binding.spAbuttingRoadDetails.getSelectedItem().toString().equalsIgnoreCase("Road")){
                    binding.cvEastWestNorSou.setVisibility(View.GONE);
                }else{
                    binding.cvEastWestNorSou.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.scroll, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

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

}
