package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.interfaces.L1SubmitInterface;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitRequest;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitResponse;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L1UploadViewModel extends AndroidViewModel {

    private L1SubmitInterface submitInterface;
    private ErrorHandlerInterface errorHandlerInterface;

    private Context context;

    public L1UploadViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        submitInterface = (L1SubmitInterface) context;
    }

    public void callSubmitAPI(L1SubmitRequest request) {
        LRSService lrsService = LRSService.Factory.create();
        String str = new Gson().toJson(request);
        lrsService.getL1SubmitResponse(request)
                .enqueue(new Callback<L1SubmitResponse>() {
                             @Override
                             public void onResponse(@NonNull Call<L1SubmitResponse> call, @NonNull Response<L1SubmitResponse> response) {
                                 if (response.isSuccessful() && response.body() != null) {
                                     submitInterface.submitResponse(response.body());

                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<L1SubmitResponse> call, @NonNull Throwable t) {
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }
}
