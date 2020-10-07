package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsReq;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsRes;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.network.LRSService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayoutViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ApplicantDetailsRes> applicantDetailsResMutableLiveData;
     private ErrorHandlerInterface errorHandlerInterface;
    public LayoutViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        applicantDetailsResMutableLiveData=new MutableLiveData<>();

    }


    public LiveData<ApplicantDetailsRes> getRoadDetailsResponse(ApplicantDetailsReq applicantDetailsReq) {
        if (applicantDetailsResMutableLiveData != null) {
            getApplicantDetailsResponseCall(applicantDetailsReq);
        }
        return applicantDetailsResMutableLiveData;
    }
    private void getApplicantDetailsResponseCall(ApplicantDetailsReq applicantDetailsReq) {
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getApplicantDetailsResponse(applicantDetailsReq).enqueue(new Callback<ApplicantDetailsRes>() {
            @Override
            public void onResponse(@NonNull Call<ApplicantDetailsRes> call, @NonNull Response<ApplicantDetailsRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    applicantDetailsResMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApplicantDetailsRes> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


}
