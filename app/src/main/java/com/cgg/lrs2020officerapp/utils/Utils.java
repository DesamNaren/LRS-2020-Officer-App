package com.cgg.lrs2020officerapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cgg.lrs2020officerapp.BuildConfig;
import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.ui.LoginActivity;
import com.cgg.lrs2020officerapp.ui.QuitAppActivity;
import com.cgg.lrs2020officerapp.ui.SplashActivity;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.ResponseBody;

public class Utils {

    public static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public static void loadSpinnerData(Context context, ArrayList<String> arrayList, Spinner
            spinner) {
        try {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_text, arrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner_text);
            spinner.setAdapter(spinnerArrayAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersionName(Context context) {
        String version;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "";
            e.printStackTrace();
        }
        return version;
    }

    public static void hideKeyboard(Context context, View mView) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr != null && conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static void openSettings(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    public static boolean isTimeAutomaticZone(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }
    }

    public static String getDisplayDate(String actDate) {
        String parsedDate = "";
        try {
            Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(actDate);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            if (initDate != null) {
                parsedDate = formatter.format(initDate);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return parsedDate;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
    }

    public static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static void customErrorAlert(Context activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_error);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnOK = dialog.findViewById(R.id.btDialogYes);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

/*    public static void ShowPlayAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnYes = dialog.findViewById(R.id.btDialogYes);
                Button btnNo = dialog.findViewById(R.id.btDialogNo);
                btnNo.setVisibility(View.GONE);
                btnYes.setText(R.string.update);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        try {
                            Intent localIntent = new Intent("android.intent.action.VIEW",
                                    Uri.parse("market://details?id=" + activity.getPackageName()));
                            activity.startActivity(localIntent);
                            activity.finish();
                        } catch (Exception e) {
                            Toast.makeText(activity, activity.getString(R.string.google_play), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static void customTimeAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);

                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                no.setVisibility(View.GONE);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                        activity.finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customExitAlert(Activity activity, String title, String
            msg, SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        Intent newIntent = new Intent(activity, QuitAppActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customLogoutAlert(Activity activity, String title, String
            msg, SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        editor.clear();
                        editor.commit();
                        Intent newIntent = new Intent(activity, LoginActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customSuccessAlert(Activity activity, String title, String msg,
                                          boolean flag) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_success);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                if (flag) {
                    dialogMessage.setVisibility(View.VISIBLE);
                } else {
                    dialogMessage.setVisibility(View.GONE);
                }
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customInfoAlert(Activity activity, String title, String msg, boolean flag) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_info_ok);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                if (flag) {
                    dialogMessage.setVisibility(View.VISIBLE);
                } else {
                    dialogMessage.setVisibility(View.GONE);
                }
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customWarningAlert(Activity activity, String title, String msg,
                                          boolean flag) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_warning);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                if (flag) {
                    dialogMessage.setVisibility(View.VISIBLE);
                } else {
                    dialogMessage.setVisibility(View.GONE);
                }
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

/*
    public static void customMPINSuccessAlert(Activity activity, String title, String
            msg, String mPIN, SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_success);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setVisibility(View.VISIBLE);
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        editor.putString(AppConstants.MPIN, mPIN);
                        editor.commit();
                        activity.startActivity(new Intent(activity, ValidateMPINActivity.class));
                        activity.finish();
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customForgotMPINSuccessAlert(Activity activity, String title, String
            msg, SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_success);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setVisibility(View.VISIBLE);
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        editor.clear();
                        editor.commit();
                        Intent newIntent = new Intent(activity, LoginActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
*/

    public static String getDeviceID(Context context) {
        String deviceID = "";
        try {
            deviceID = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceID;
    }

    public static String getUniqueID(Context context,
                                     SharedPreferences sharedPreferences,
                                     SharedPreferences.Editor editor) {
        String uniqueID = sharedPreferences.getString(AppConstants.UNIQUE_ID, "");
        if (!TextUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            editor.putString(AppConstants.UNIQUE_ID, uniqueID);
            editor.commit();
        }

        return uniqueID;
    }

    public static String getLocalIpAddress() {
        String ipAddress = "0.0.0.0";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ipAddress = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ipAddress;
    }


    public static void customSessionAlert(Activity activity, String title, String
            msg, SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_error);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(activity));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnOK = dialog.findViewById(R.id.btDialogYes);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        editor.clear();
                        editor.commit();
                        Intent newIntent = new Intent(activity, LoginActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


}
