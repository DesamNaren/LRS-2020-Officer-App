package com.cgg.lrs2020officerapp.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL1UploadBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.interfaces.L1SubmitInterface;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitRequest;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitResponse;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitRequest;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.L1UploadViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class L1UploadActivity extends LocBaseActivity implements ErrorHandlerInterface, L1SubmitInterface {

    private CustomProgressDialog customProgressDialog;
    private L1UploadViewModel uploadViewModel;
    private static final int SELECT_FILE = 1;
    private Context context;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String IMAGE_FLAG = null, PIC_NAME, FilePath;
    ActivityL1UploadBinding binding;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "LRS_IMAGES";
    Bitmap bm;
    public static String P_IMAGE1_PATH = "";
    public static String P_IMAGE2_PATH = "";
    public static String P_IMAGE3_PATH = "";
    public static String P_IMAGE4_PATH = "";
    public static String P_IMAGE5_PATH = "";
    private boolean siteImage = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private L1SubmitRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(L1UploadActivity.this,
                R.layout.activity_l1_upload);
        context = L1UploadActivity.this;

        binding.header.headerTitle.setText(R.string.upload_files);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(L1UploadActivity.this);
            }
        });
        customProgressDialog = new CustomProgressDialog(context);

        sharedPreferences = LRSApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();

        try {
            String str = sharedPreferences.getString(AppConstants.L1_SUBMIT_REQUEST, "");
            request = new Gson().fromJson(str, L1SubmitRequest.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnLayout.btnCancel.setVisibility(View.VISIBLE);
//        binding.btnLayout.btnProceed.setText(getResources().getString(R.string.submit));


        uploadViewModel = new L1UploadViewModel(L1UploadActivity.this, getApplication());

        binding.btnLayout.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnLayout.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validations()) {

                    request.setIMAGE1PATH(P_IMAGE1_PATH);
                    request.setIMAGE2PATH(P_IMAGE2_PATH);
                    request.setIMAGE3PATH(P_IMAGE3_PATH);
                    request.setIMAGE4PATH(P_IMAGE4_PATH);
                    request.setIMAGE5PATH(P_IMAGE5_PATH);

                    String jsonValue = new Gson().toJson(request);
                    customInfoAlert(request);
                }

            }
        });

        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE1;

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    if (fileUri != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                    }
                }
            }
        });
        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    if (fileUri != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                    }
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE3;

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    if (fileUri != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                    }
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE4;

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    if (fileUri != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                    }
                }
            }
        });
        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE5;

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                    if (fileUri != null) {
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                    }
                }
            }
        });

    }

    private boolean validations() {

        if (!siteImage) {
            Toast.makeText(context, "Upload at least one site inspection image", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

//                FilePath = getExternalFilesDir(null)
//                        + "/" + IMAGE_DIRECTORY_NAME;
//
//                String Image_name = IMAGE_FLAG + ".png";
//                FilePath = FilePath + "/" + Image_name;
//
//                FilePath = compressImage(FilePath);
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 8;
//                bm = BitmapFactory.decodeFile(FilePath, options);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.PNG, 50, stream);

                if (IMAGE_FLAG.equals(AppConstants.IMAGE1)) {
                    siteImage = true;

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.image1.setImageBitmap(bmp);
                        P_IMAGE1_PATH = convertBase64(bmp);
                    }

//                    binding.image1.setPadding(0, 0, 0, 0);
//                    binding.image1.setBackgroundColor(getResources().getColor(R.color.white));
//                    file_image1 = new File(FilePath);
//                    Glide.with(ImageUploadActivity.this).load(file_image1).into(binding.image1);
//
//                    P_IMAGE1_PATH = convertBase64(bm);

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE2)) {
                    siteImage = true;

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.image2.setImageBitmap(bmp);
                        P_IMAGE2_PATH = convertBase64(bmp);
                    }

//                    binding.image2.setPadding(0, 0, 0, 0);
//                    binding.image2.setBackgroundColor(getResources().getColor(R.color.white));
//                    file_image2 = new File(FilePath);
//                    Glide.with(ImageUploadActivity.this).load(file_image2).into(binding.image2);
//
//                    P_IMAGE2_PATH = convertBase64(bm);
                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE3)) {
                    siteImage = true;

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.image3.setImageBitmap(bmp);
                        P_IMAGE3_PATH = convertBase64(bmp);
                    }

//                    binding.image3.setPadding(0, 0, 0, 0);
//                    binding.image3.setBackgroundColor(getResources().getColor(R.color.white));
//                    file_image3 = new File(FilePath);
//                    Glide.with(ImageUploadActivity.this).load(file_image3).into(binding.image3);
//                    P_IMAGE3_PATH = convertBase64(bm);
                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE4)) {
                    siteImage = true;

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.image4.setImageBitmap(bmp);
                        P_IMAGE4_PATH = convertBase64(bmp);
                    }

//                    binding.image4.setPadding(0, 0, 0, 0);
//                    binding.image4.setBackgroundColor(getResources().getColor(R.color.white));
//                    file_image4 = new File(FilePath);
//                    Glide.with(ImageUploadActivity.this).load(file_image4).into(binding.image4);
//
//                    P_IMAGE4_PATH = convertBase64(bm);

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE5)) {
                    siteImage = true;

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.image5.setImageBitmap(bmp);
                        P_IMAGE5_PATH = convertBase64(bmp);
                    }

//                    binding.image4.setPadding(0, 0, 0, 0);
//                    binding.image4.setBackgroundColor(getResources().getColor(R.color.white));
//                    file_image4 = new File(FilePath);
//                    Glide.with(ImageUploadActivity.this).load(file_image4).into(binding.image4);
//
//                    P_IMAGE4_PATH = convertBase64(bm);

                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private String convertBase64(Bitmap bitmap) {
        String base64 = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }


    private Bitmap ProcessingBitmap(Bitmap bitmap) {
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = bitmap;
            Bitmap.Config config = bm1.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);


            newCanvas.drawBitmap(bm1, 0, 0, null);

            String currentDateTimeString = (String) DateFormat.format("yyyy/MM/dd hh:mm",
                    System.currentTimeMillis());

            if (mCurrentLocation != null && mCurrentLocation.getLatitude() > 0 && mCurrentLocation.getLongitude() > 0) {

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);

                paintText.setTextSize(10);
                paintText.setStyle(Paint.Style.FILL);

                Rect rectText = new Rect();
                paintText.getTextBounds(String.valueOf(mCurrentLocation.getLatitude()), 0, String.valueOf(mCurrentLocation.getLatitude()).length(), rectText);

                paintText.setColor(Color.WHITE);
                newCanvas.drawText(currentDateTimeString,
                        bm1.getWidth() / 2, bm1.getHeight() - 25, paintText);
                paintText.setColor(Color.WHITE);
                newCanvas.drawText(String.valueOf(mCurrentLocation.getLatitude()),
                        10, bm1.getHeight() - 25, paintText);
                paintText.setColor(Color.WHITE);
                newCanvas.drawText(String.valueOf(mCurrentLocation.getLongitude()),
                        10, bm1.getHeight() - 10, paintText);

            } else {
//                Toast.makeText(this, "caption empty!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }


    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            String deviceId = Utils.getDeviceID(L1UploadActivity.this);
            String versionName = Utils.getVersionName(L1UploadActivity.this);
            PIC_NAME = IMAGE_FLAG + Utils.getCurrentDateTimeFormat() + ".png";

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + IMAGE_FLAG + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;

//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return FilePath;

    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.dismiss();
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }

    @Override
    public void handleError(String errMsg, Context context) {
        customProgressDialog.dismiss();
        Utils.customErrorAlert(context, getString(R.string.app_name), errMsg);
    }

    @Override
    public void submitResponse(L1SubmitResponse responses) {
        customProgressDialog.dismiss();
        if (responses != null && responses.getStatusCode() != null) {

            if (responses.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                Utils.customL1SuccessAlert(this, getString(R.string.app_name),
                        responses.getStatusMessage(), true, editor);

            } else if (responses.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                Utils.customErrorAlert(context, getString(R.string.app_name), responses.getStatusMessage());
            } else {
                Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
        }
    }

    public void customInfoAlert(L1SubmitRequest submitScrutinyRequest) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_submit);
                dialog.setCancelable(false);
                TextView versionTitle = dialog.findViewById(R.id.version_tv);
                versionTitle.setText("Version: " + Utils.getVersionName(context));
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(R.string.app_name);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                if (flag) {
                    dialogMessage.setVisibility(View.VISIBLE);
                } else {
                    dialogMessage.setVisibility(View.GONE);
                }
                dialogMessage.setText("Are you sure you want to submit data?");
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setText(R.string.yes);
                btDialogYes.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setBackgroundColor(getResources().getColor(R.color.red));
                btDialogNo.setText(R.string.cancel);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (Utils.checkInternetConnection(context)) {
                            customProgressDialog.show();
                            uploadViewModel.callSubmitAPI(submitScrutinyRequest);
                        } else {
                            Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getString(R.string.plz_check_int));
                        }
                    }
                });

                btDialogNo.setOnClickListener(new View.OnClickListener() {
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

}
