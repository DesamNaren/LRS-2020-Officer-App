package com.cgg.lrs2020officerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityLoginBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.login.LoginRequest;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.LoginCustomViewModel;
import com.cgg.lrs2020officerapp.viewmodel.LoginViewModel;
import com.google.gson.Gson;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements ErrorHandlerInterface {

    private ActivityLoginBinding binding;
    private Context context;
    private boolean isLocReceived;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginViewModel loginViewModel;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = LoginActivity.this;

        sharedPreferences = LRSApplication.get(context).getPreferences();
        editor = sharedPreferences.edit();

        loginViewModel = new ViewModelProvider(
                this, new LoginCustomViewModel(binding, context)).
                get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        loginViewModel.getLoginCall().observe(this, new Observer<List<LoginResponse>>() {
            @Override
            public void onChanged(List<LoginResponse> loginResponse) {

                if (loginResponse != null && loginResponse.get(0).getStatusCode() != null) {

                    if (loginResponse.get(0).getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                        LoginResponse loginResponse1 = loginResponse.get(0);
                        String str = new Gson().toJson(loginResponse1);
                        editor.putString(AppConstants.LOGIN_RES, str);
                        editor.commit();
                        startActivity(new Intent(context, Dashboard.class));
//                        finish();
                    } else if (loginResponse.get(0).getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                        Utils.customErrorAlert(context, getString(R.string.app_name), loginResponse.get(0).getStatusMessage());
                    } else {
                        Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
                    }
                } else {
                    Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
                }
            }
        });


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginValidations();
            }
        });
    }

    private void loginValidations() {
        if (TextUtils.isEmpty(binding.etUserName.getText())) {
            binding.etUserName.setError(context.getString(R.string.please_enter_username));
            binding.etUserName.requestFocus();
            return;

        } else {
            binding.etUserName.setError(null);
        }
        if (TextUtils.isEmpty(binding.etPwd.getText())) {
            binding.etPwd.setError(context.getString(R.string.please_enter_password));
            binding.etPwd.requestFocus();
            return;
        } else {
            binding.etPwd.setError(null);
        }
        loginRequest = new LoginRequest();
        loginRequest.setUserName(binding.etUserName.getText().toString().trim());
        loginRequest.setPassword(binding.etPwd.getText().toString().trim());
        callLogin(loginRequest);
    }

    private void callLogin(LoginRequest loginRequest) {
        if (Utils.checkInternetConnection(context)) {
            loginViewModel.callLoginAPI(loginRequest);
        } else {
            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
        }
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }

    @Override
    public void handleError(String errMsg, Context context) {
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }
}
