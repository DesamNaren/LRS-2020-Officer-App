package com.cgg.lrs2020officerapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.databinding.ActivityLoginBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.login.LoginRequest;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.network.LRSService;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<List<LoginResponse>> responseMutableLiveData;
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> mobileNum = new MutableLiveData<>();
    private Context context;
    private ActivityLoginBinding binding;
    private ErrorHandlerInterface errorHandlerInterface;
    private CustomProgressDialog customProgressDialog;

    public LoginViewModel(Context context) {
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LoginViewModel(ActivityLoginBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        customProgressDialog = new CustomProgressDialog(context);
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<List<LoginResponse>> getLoginCall() {
        responseMutableLiveData = new MutableLiveData<>();
        return responseMutableLiveData;
    }


    public void callLoginAPI(LoginRequest loginRequest) {
        Utils.hideKeyboard(context, binding.btnSubmit);
        customProgressDialog.show();
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getLoginResponse(loginRequest)
                .enqueue(new Callback<List<LoginResponse>>() {
                             @Override
                             public void onResponse(@NonNull Call<List<LoginResponse>> call, @NonNull Response<List<LoginResponse>> response) {
                                 customProgressDialog.dismiss();
                                 if (response.isSuccessful() && response.body() != null) {
                                     List<LoginResponse> list = response.body();
                                     if (list != null && list.size() > 0)
                                         responseMutableLiveData.setValue(list);
                                 } else {
                                     errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<List<LoginResponse>> call, @NonNull Throwable t) {
                                 customProgressDialog.dismiss();
                                 errorHandlerInterface.handleError(t, context);
                             }
                         }
                );
    }
}
