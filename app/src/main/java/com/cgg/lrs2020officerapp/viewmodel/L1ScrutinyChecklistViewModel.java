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
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L1ScrutinyChecklistViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<L1ScrutinityResponse> scrutinityResponseMutableLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    public L1ScrutinyChecklistViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        scrutinityResponseMutableLiveData =new MutableLiveData<>();

    }


    public LiveData<L1ScrutinityResponse> getScrutinyCheckListResponse(L1ScrutinyChecklistRequest scrutinyChecklistRequest) {
        if (scrutinityResponseMutableLiveData != null) {
            getScrutinyCheckListCall(scrutinyChecklistRequest);
        }
        return scrutinityResponseMutableLiveData;
    }
    private void getScrutinyCheckListCall(L1ScrutinyChecklistRequest scrutinyChecklistRequest) {
        LRSService lrsService = LRSService.Factory.create();
        String str = new Gson().toJson(scrutinyChecklistRequest);
        lrsService.getScrutinyCheckList(scrutinyChecklistRequest).enqueue(new Callback<L1ScrutinityResponse>() {
            @Override
            public void onResponse(@NonNull Call<L1ScrutinityResponse> call, @NonNull Response<L1ScrutinityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    scrutinityResponseMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<L1ScrutinityResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
