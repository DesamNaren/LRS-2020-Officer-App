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
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayoutViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ApplicantDetailsRes> applicantDetailsResMutableLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    public LayoutViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        customProgressDialog = new CustomProgressDialog(context);
        applicantDetailsResMutableLiveData = new MutableLiveData<>();
    }


    public LiveData<ApplicantDetailsRes> getApplicantDetailsResponse(ApplicantDetailsReq request) {
        applicantDetailsResMutableLiveData = new MutableLiveData<>();
        getApplicantDetailsResponseCall(request);
        return applicantDetailsResMutableLiveData;
    }

    public void getApplicantDetailsResponseCall(ApplicantDetailsReq request) {
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getApplicantDetailsResponse(request)
                .enqueue(new Callback<ApplicantDetailsRes>() {
                             @Override
                             public void onResponse(@NonNull Call<ApplicantDetailsRes> call, @NonNull Response<ApplicantDetailsRes> response) {
                                 customProgressDialog.dismiss();
                                 if (response.isSuccessful() && response.body() != null) {
                                     applicantDetailsResMutableLiveData.setValue(response.body());
                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<ApplicantDetailsRes> call, @NonNull Throwable t) {
                                 customProgressDialog.dismiss();
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }

}
