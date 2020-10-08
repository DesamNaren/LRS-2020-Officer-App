package com.cgg.lrs2020officerapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityDashboardBinding;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.google.gson.Gson;

public class Dashboard extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginResponse loginResponse;
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        sharedPreferences = LRSApplication.get(Dashboard.this).getPreferences();
        editor = sharedPreferences.edit();
        try {
            String str = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(str, LoginResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.name.setText(""+loginResponse.getUserName());
        binding.designation.setText(""+loginResponse.getDESIGNATION());
        binding.pendingForScrutiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, ListActivity.class);
                startActivity(i);
            }
        });
        binding.logoutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.customLogoutAlert(Dashboard.this, getString(R.string.app_name),"Do you want to logout from app?", editor);
            }
        });

        binding.About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, WebActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Utils.customLogoutAlert(this, getString(R.string.app_name),"Do you want to logout from app?", editor);
        //exitHandler();
    }

    private void exitHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
        alertDialog.setTitle("Exit application?");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Are you sure you want to Exit From this application?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // ExitActivity.exitApplication(MainActivity.this);

                            finish();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                finishAndRemoveTask();
                            }





                       /* System.exit(0);
                            finishAffinity();*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
