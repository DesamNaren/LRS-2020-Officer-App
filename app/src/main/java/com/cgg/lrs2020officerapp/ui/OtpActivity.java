package com.cgg.lrs2020officerapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityLoginBinding;
import com.cgg.lrs2020officerapp.databinding.ActivityOtpBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.otp.OtpReq;
import com.cgg.lrs2020officerapp.model.otp.OtpRes;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.ApplicationListViewModel;
import com.cgg.lrs2020officerapp.viewmodel.OtpViewModel;
import com.google.gson.Gson;

import java.util.List;

public class OtpActivity extends AppCompatActivity implements ErrorHandlerInterface {
    private Context context;
    private OtpViewModel otpViewModel;
    private ActivityOtpBinding otpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding = DataBindingUtil.setContentView(OtpActivity.this, R.layout.activity_otp);
        context = OtpActivity.this;

        //   otpViewModel = new OtpViewModel(context,getApplication());
//        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        otpViewModel =  new OtpViewModel(getApplication(), this);

        otpBinding.buttonOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtpReq request = new OtpReq();
                request.setAPPLICATIONID("M/NALG/000194/2020");
                request.setMobileNo("8333969129");
                request.setTOKENID("MH&3.7/4");
                if (Utils.checkInternetConnection(OtpActivity.this)) {
                    otpViewModel.getOtpCall(request).observe(OtpActivity.this, new Observer<OtpRes>() {
                        @Override
                        public void onChanged(OtpRes otpRes) {
                            if (otpRes != null && otpRes.getStatusCode() != null) {
                                Toast.makeText(OtpActivity.this,"Otp generated successfuly : "+otpRes.getOtp(),Toast.LENGTH_SHORT).show();

                            } else {
                                Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void handleError(Throwable e, Context context) {

    }

    @Override
    public void handleError(String e, Context context) {

    }
}