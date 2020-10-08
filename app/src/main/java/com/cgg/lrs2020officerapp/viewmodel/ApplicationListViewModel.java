package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationListViewModel extends ViewModel {

    private MutableLiveData<ApplicationRes> responseMutableLiveData;

    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    public ApplicationListViewModel(Context context) {
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<ApplicationRes> getApplicationListCall(ApplicationReq request) {
        responseMutableLiveData = new MutableLiveData<>();
        callApplicationList(request);
        return responseMutableLiveData;
    }


    public void callApplicationList(ApplicationReq request) {
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getApplicationListResponse(request)
                .enqueue(new Callback<ApplicationRes>() {
                             @Override
                             public void onResponse(@NonNull Call<ApplicationRes> call, @NonNull Response<ApplicationRes> response) {
                                 customProgressDialog.dismiss();
                                 if (response.isSuccessful() && response.body() != null) {
                                     responseMutableLiveData.setValue(response.body());
                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<ApplicationRes> call, @NonNull Throwable t) {
                                 customProgressDialog.dismiss();
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }
}
