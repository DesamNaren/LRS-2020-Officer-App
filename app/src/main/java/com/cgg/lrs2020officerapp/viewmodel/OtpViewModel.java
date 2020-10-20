package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.otp.OtpReq;
import com.cgg.lrs2020officerapp.model.otp.OtpRes;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpViewModel extends AndroidViewModel {
    private MutableLiveData<OtpRes> responseMutableLiveData;

    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    private Context context;

    public OtpViewModel(@NonNull Application application, Context context) {
        super(application);
        this.context= context;
        customProgressDialog = new CustomProgressDialog(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<OtpRes> getOtpCall(OtpReq request) {
        responseMutableLiveData = new MutableLiveData<>();
        callOtp(request);
        return responseMutableLiveData;
    }


    public void callOtp(OtpReq req){
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getOtpResponse(req)
                .enqueue(new Callback<OtpRes>() {
                             @Override
                             public void onResponse(@NonNull Call<OtpRes> call, @NonNull Response<OtpRes> response) {
                                 customProgressDialog.dismiss();
                                 if (response.isSuccessful() && response.body() != null) {
                                     responseMutableLiveData.setValue(response.body());
                                 } else {
                                     errorHandlerInterface.handleError(getApplication().getString(R.string.server_not), getApplication().getApplicationContext());
                                 }
                             }


                    @Override
                             public void onFailure(@NonNull Call<OtpRes> call, @NonNull Throwable t) {
                                 customProgressDialog.dismiss();
                                 errorHandlerInterface.handleError(t, getApplication().getApplicationContext());
                             }
                         }
                );
    }
}
