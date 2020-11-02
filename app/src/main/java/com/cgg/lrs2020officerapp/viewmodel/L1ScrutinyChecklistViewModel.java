package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList.L1ScrutinityResponse;
import com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList.L1ScrutinyChecklistRequest;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L1ScrutinyChecklistViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<L1ScrutinityResponse> scrutinityResponseMutableLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    public L1ScrutinyChecklistViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        scrutinityResponseMutableLiveData =new MutableLiveData<>();
        customProgressDialog = new CustomProgressDialog(context);
    }


    public LiveData<L1ScrutinityResponse> getScrutinyCheckListResponse(L1ScrutinyChecklistRequest scrutinyChecklistRequest) {
        if (scrutinityResponseMutableLiveData != null) {
            getScrutinyCheckListCall(scrutinyChecklistRequest);
        }
        return scrutinityResponseMutableLiveData;
    }
    private void getScrutinyCheckListCall(L1ScrutinyChecklistRequest scrutinyChecklistRequest) {
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        String str = new Gson().toJson(scrutinyChecklistRequest);
        lrsService.getScrutinyCheckList(scrutinyChecklistRequest).enqueue(new Callback<L1ScrutinityResponse>() {
            @Override
            public void onResponse(@NonNull Call<L1ScrutinityResponse> call, @NonNull Response<L1ScrutinityResponse> response) {
                customProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    scrutinityResponseMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<L1ScrutinityResponse> call, @NonNull Throwable t) {
                customProgressDialog.dismiss();
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
