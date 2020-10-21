package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.shortfall.ShortFallResponse;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L2ShortfallViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ShortFallResponse> shortFallResponseMutableLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    public L2ShortfallViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        customProgressDialog = new CustomProgressDialog(context);
        shortFallResponseMutableLiveData = new MutableLiveData<>();
    }


    public LiveData<ShortFallResponse> getShortFallResponse() {
        shortFallResponseMutableLiveData = new MutableLiveData<>();
        getShortFallResponseCall();
        return shortFallResponseMutableLiveData;
    }

    public void getShortFallResponseCall() {
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getShortFallMasterData()
                .enqueue(new Callback<ShortFallResponse>() {
                             @Override
                             public void onResponse(@NonNull Call<ShortFallResponse> call, @NonNull Response<ShortFallResponse> response) {
                                 customProgressDialog.dismiss();
                                 if (response.isSuccessful() && response.body() != null) {
                                     shortFallResponseMutableLiveData.setValue(response.body());
                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<ShortFallResponse> call, @NonNull Throwable t) {
                                 customProgressDialog.dismiss();
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }

}
