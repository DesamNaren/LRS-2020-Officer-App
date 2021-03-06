package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.interfaces.L2SubmitInterface;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitRequest;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitResponse;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L2UploadViewModel extends ViewModel {

    private L2SubmitInterface submitScrutinyInterface;
    private ErrorHandlerInterface errorHandlerInterface;

    private Context context;

    public L2UploadViewModel(Context context) {
        this.context = context;
//        submitScrutinyInterface = (SubmitScrutinyInterface) context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public void callSubmitAPI(L2SubmitRequest request) {
        LRSService lrsService = LRSService.Factory.create();
        String str = new Gson().toJson(request);
        lrsService.getSubmitResponse(request)
                .enqueue(new Callback<List<L2SubmitResponse>>() {
                             @Override
                             public void onResponse(@NonNull Call<List<L2SubmitResponse>> call, @NonNull Response<List<L2SubmitResponse>> response) {
                                 if (response.isSuccessful() && response.body() != null) {
                                     submitScrutinyInterface.submitResponse(response.body());

                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<List<L2SubmitResponse>> call, @NonNull Throwable t) {
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }
}
