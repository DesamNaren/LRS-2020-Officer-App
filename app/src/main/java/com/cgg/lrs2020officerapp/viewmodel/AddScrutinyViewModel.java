package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.error_handler.SubmitScrutinyInterface;
import com.cgg.lrs2020officerapp.model.submit.SubmitScrutinyRequest;
import com.cgg.lrs2020officerapp.model.submit.SubmitScrutinyResponse;
import com.cgg.lrs2020officerapp.network.LRSService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScrutinyViewModel extends AndroidViewModel {

    private SubmitScrutinyInterface submitScrutinyInterface;
    private ErrorHandlerInterface errorHandlerInterface;

    private Context context;


    public AddScrutinyViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
        submitScrutinyInterface = (SubmitScrutinyInterface) application;
        errorHandlerInterface = (ErrorHandlerInterface) application;
    }

    public void callSubmitAPI(SubmitScrutinyRequest request) {
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getSubmitResponse(request)
                .enqueue(new Callback<List<SubmitScrutinyResponse>>() {
                             @Override
                             public void onResponse(@NonNull Call<List<SubmitScrutinyResponse>> call, @NonNull Response<List<SubmitScrutinyResponse>> response) {
                                 if (response.isSuccessful() && response.body() != null) {
                                     submitScrutinyInterface.submitResponse(response.body());

                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<List<SubmitScrutinyResponse>> call, @NonNull Throwable t) {
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }
}
