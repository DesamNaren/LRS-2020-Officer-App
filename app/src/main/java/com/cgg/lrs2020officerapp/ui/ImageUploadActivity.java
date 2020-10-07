package com.cgg.lrs2020officerapp.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.lrs2020officerapp.BuildConfig;
import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.application.LRSApplication;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityImageUploadBinding;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandler;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.error_handler.SubmitScrutinyInterface;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.submit.SubmitScrutinyRequest;
import com.cgg.lrs2020officerapp.model.submit.SubmitScrutinyResponse;
import com.cgg.lrs2020officerapp.utils.CustomProgressDialog;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.cgg.lrs2020officerapp.viewmodel.AddScrutinyCustomViewModel;
import com.cgg.lrs2020officerapp.viewmodel.AddScrutinyViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageUploadActivity extends LocBaseActivity implements ErrorHandlerInterface, SubmitScrutinyInterface {

    private CustomProgressDialog customProgressDialog;
    private AddScrutinyViewModel addScrutinyViewModel;
    private static final int SELECT_FILE = 1;
    private Context context;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String IMAGE_FLAG = null, PIC_NAME, FilePath;
    ActivityImageUploadBinding binding;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "LRS_IMAGES";
    Bitmap bm;
    File file_image1, file_image2, file_image3, file_image4, file_image_doc, file_image_plot;
    private String userChoosenTask;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42042;
    public static final int REQUEST_PDF = 3;
    private int pic_number;
    public static String P_EX_FILE_PATH = "";
    public static String P_PLAN_PATH = "";
    public static String P_IMAGE1_PATH = "";
    public static String P_IMAGE2_PATH = "";
    public static String P_IMAGE3_PATH = "";
    public static String P_IMAGE4_PATH = "";
    Uri uriPdf1, uriPdf2;
    private boolean extractDoc = false;
    private boolean plotDoc = false;
    private boolean siteImage = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SubmitScrutinyRequest submitScrutinyRequest;
    private String applicationId;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ImageUploadActivity.this,
                R.layout.activity_image_upload);
        context = ImageUploadActivity.this;

        binding.header.headerTitle.setText(R.string.upload_files);

        customProgressDialog = new CustomProgressDialog(context);

        sharedPreferences = LRSApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {
            String strLogin = sharedPreferences.getString(AppConstants.LOGIN_RES, "");
            loginResponse = new Gson().fromJson(strLogin, LoginResponse.class);
            applicationId = sharedPreferences.getString(AppConstants.APPLICATION_ID, "");
            String str = sharedPreferences.getString(AppConstants.SUBMIT_REQUEST, "");
            submitScrutinyRequest = new Gson().fromJson(str, SubmitScrutinyRequest.class);
            if (TextUtils.isEmpty(applicationId) || submitScrutinyRequest == null || loginResponse == null) {
                Toast.makeText(context, R.string.something, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addScrutinyViewModel = new ViewModelProvider(
                this, new AddScrutinyCustomViewModel(context)).
                get(AddScrutinyViewModel.class);

        binding.btnSroRegDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic_number = 1;
                selectImageDialog(pic_number);
            }
        });

        binding.btnUploadPlotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic_number = 2;
                selectImageDialog(pic_number);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitScrutinyRequest.setPIPADDRESS(Utils.getLocalIpAddress());
                submitScrutinyRequest.setPOFFICERTYPE(AppConstants.OFFICER_TYPE);
                submitScrutinyRequest.setPEMPLOYEEID(loginResponse.getUSERID());
                submitScrutinyRequest.setPCREATEDBY(loginResponse.getUSERID());
                submitScrutinyRequest.setPOTPNO(AppConstants.OTP);
                submitScrutinyRequest.setPSRODOCLINK(loginResponse.getsRO_DOC_LINK());
                submitScrutinyRequest.setPAPPLICANTID(applicationId);
                submitScrutinyRequest.setPIMAGE1PATH(P_IMAGE1_PATH);
                submitScrutinyRequest.setPIMAGE2PATH(P_IMAGE2_PATH);
                submitScrutinyRequest.setPIMAGE3PATH(P_IMAGE3_PATH);
                submitScrutinyRequest.setPIMAGE4PATH(P_IMAGE4_PATH);
                submitScrutinyRequest.setPEXFILEPATH(P_EX_FILE_PATH);
                submitScrutinyRequest.setPPLANPATH(P_PLAN_PATH);


                customInfoAlert(submitScrutinyRequest);
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

    }

    private boolean validations() {
        if (!extractDoc) {
            Toast.makeText(context, "Upload extract image/document", Toast.LENGTH_SHORT).show();
            return false;
        }

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

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE_DOC)) {
                    extractDoc = true;

                    binding.tvDoc.setVisibility(View.GONE);
                    binding.tvDoc.setText("");

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.imageExtract.setImageBitmap(bmp);
                        P_EX_FILE_PATH = convertBase64(bmp);
                        binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    }

//                    file_image_doc = new File(FilePath);
//                    binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                    binding.imageExtract.setVisibility(View.VISIBLE);
//                    binding.imageExtract.setPadding(0, 0, 0, 0);
//                    binding.imageExtract.setBackgroundColor(getResources().getColor(R.color.white));
//                    Glide.with(ImageUploadActivity.this).load(file_image_doc).into(binding.imageExtract);
//
//                    P_EX_FILE_PATH = convertBase64(bm);

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE_PLOT)) {
                    plotDoc = true;

                    binding.tvPlot.setVisibility(View.GONE);
                    binding.tvPlot.setText("");

                    if (data != null && data.getExtras() != null) {
                        Bitmap capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bmp = ProcessingBitmap(capturePhotoBitmap);
                        binding.imagePlot.setImageBitmap(bmp);
                        P_PLAN_PATH = convertBase64(bmp);
                        binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    }

//                    file_image_plot = new File(FilePath);
//                    binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                    binding.imagePlot.setVisibility(View.VISIBLE);
//                    binding.imagePlot.setPadding(0, 0, 0, 0);
//                    binding.imagePlot.setBackgroundColor(getResources().getColor(R.color.white));
//                    Glide.with(ImageUploadActivity.this).load(file_image_plot).into(binding.imagePlot);
//
//                    P_PLAN_PATH = convertBase64(bm);
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
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK
                && null != data) {
            onSelectFromGalleryResult(data);
        } else if (requestCode == REQUEST_PDF) {
            if (pic_number == 1) {
                uriPdf1 = data.getData();
                displayFromUriPDF1(uriPdf1);
            } else if (pic_number == 2) {
                uriPdf2 = data.getData();
                displayFromUriPDF2(uriPdf2);
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

    private void displayFromUriPDF1(Uri uri) {
        try {
            if (uri != null && uri.getScheme() != null && uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        String result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        if (result.contains(".pdf")) {

                            InputStream in = getContentResolver().openInputStream(uri);

                            byte[] bytes;
                            assert in != null;
                            bytes = getBytesPDF1(in);

                            if (bytes.length <= 1000000) {
                                extractDoc = true;
                                binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                binding.imageExtract.setVisibility(View.GONE);

                                P_EX_FILE_PATH = Base64.encodeToString(bytes, Base64.DEFAULT);

                                binding.imageExtract.setVisibility(View.GONE);
                                binding.tvDoc.setVisibility(View.VISIBLE);
                                binding.tvDoc.setText(result);
                            } else {
                                Toast.makeText(context, "Upload file with maximum 1 MB only", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            extractDoc = false;
                            binding.imageExtract.setVisibility(View.GONE);
                            binding.tvDoc.setVisibility(View.GONE);
                            binding.tvDoc.setText("");
                            binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.gray));
                            Toast.makeText(context, "Selected file is not pdf", Toast.LENGTH_SHORT).show();
                            P_EX_FILE_PATH = "";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    assert cursor != null;
                    cursor.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayFromUriPDF2(Uri uri) {
        try {
            if (uri != null && uri.getScheme() != null && uri.getScheme().equals("content")) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        String result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        if (result.contains(".pdf")) {

                            InputStream in = getContentResolver().openInputStream(uri);

                            byte[] bytes;
                            assert in != null;
                            bytes = getBytesPDF1(in);

                            if (bytes.length <= 1000000) {
                                plotDoc = true;
                                binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                binding.imagePlot.setVisibility(View.GONE);

                                P_PLAN_PATH = Base64.encodeToString(bytes, Base64.DEFAULT);


                                binding.imagePlot.setVisibility(View.GONE);
                                binding.tvPlot.setVisibility(View.VISIBLE);
                                binding.tvPlot.setText(result);
                            } else {
                                Toast.makeText(context, "Upload file with maximum 1 MB only", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            plotDoc = false;
                            binding.imagePlot.setVisibility(View.GONE);
                            binding.tvPlot.setVisibility(View.GONE);
                            binding.tvPlot.setText("");
                            binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.gray));
                            Toast.makeText(context, "Selected file is not pdf", Toast.LENGTH_SHORT).show();
                            P_PLAN_PATH = "";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    assert cursor != null;
                    cursor.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public byte[] getBytesPDF1(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Uri imageUri = data.getData();

                if (pic_number == 1) {
                    galleryImageSetter(imageUri);
                } else if (pic_number == 2) {
                    galleryImageSetter2(imageUri);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public void galleryImageSetter(Uri imageUri) {

        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = getResizedBitmap(selectedImage, 200);// 400 is for example, replace with desired size
            ImageView imageView;
            Bitmap img_bitmap;

            img_bitmap = ((BitmapDrawable) binding.imageExtract.getDrawable()).getBitmap();
            P_EX_FILE_PATH = convertBitMap(img_bitmap);
            if (!TextUtils.isEmpty(P_EX_FILE_PATH)) {
                extractDoc = true;
                binding.tvDoc.setVisibility(View.GONE);
                binding.imageExtract.setVisibility(View.VISIBLE);
                binding.imageExtract.setImageBitmap(selectedImage);
                binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                binding.tvDoc.setVisibility(View.GONE);
                binding.tvDoc.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void galleryImageSetter2(Uri imageUri) {

        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = getResizedBitmap(selectedImage, 200);// 400 is for example, replace with desired size
            Bitmap img_bitmap;

            img_bitmap = ((BitmapDrawable) binding.imagePlot.getDrawable()).getBitmap();
            P_PLAN_PATH = convertBitMap(img_bitmap);

            if (!TextUtils.isEmpty(P_PLAN_PATH)) {
                plotDoc = true;
                binding.tvPlot.setVisibility(View.GONE);
                binding.imagePlot.setVisibility(View.VISIBLE);
                binding.imagePlot.setImageBitmap(selectedImage);
                binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                binding.tvPlot.setVisibility(View.GONE);
                binding.tvPlot.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                        0, 120, paintText);
                paintText.setColor(Color.WHITE);
                newCanvas.drawText(String.valueOf(mCurrentLocation.getLatitude()),
                        0, 140, paintText);
                paintText.setColor(Color.WHITE);
                newCanvas.drawText(String.valueOf(mCurrentLocation.getLongitude()),
                        0, 150, paintText);

            } else {
                Toast.makeText(this, "caption empty!", Toast.LENGTH_LONG).show();
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


    public String convertBitMap(Bitmap img_bitmap) {

        String encodedImage = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();
        if (imageBytes.length <= 1000000) {
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } else {
            Toast.makeText(context, "Upload file with maximum 1 MB only", Toast.LENGTH_SHORT).show();
        }

        return encodedImage;
    }

    private void callSettings() {
        Snackbar snackbar = Snackbar.make(binding.root, getString(R.string.all_cam_per_setting), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                Utils.openSettings(ImageUploadActivity.this);
            }
        });

        snackbar.show();
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

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
            String deviceId = Utils.getDeviceID(ImageUploadActivity.this);
            String versionName = Utils.getVersionName(ImageUploadActivity.this);
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


    private void selectImageDialog(int snO) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Choose Documents",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pick Image/Pdf");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (callPermissions()) {
                        if (snO == 1) {
                            IMAGE_FLAG = AppConstants.IMAGE_DOC;
                        } else if (snO == 2) {
                            IMAGE_FLAG = AppConstants.IMAGE_PLOT;
                        }

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);

//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                        if (fileUri != null) {
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
//                        }
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();

                } else if (items[item].equals("Choose Documents")) {

                    chooseDocs();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_FILE);
    }

    private void chooseDocs() {

        int permissionCheck = ContextCompat.checkSelfPermission(context,
                READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ImageUploadActivity.this,
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );

            return;
        }

        launchPicker();
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_PDF);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "Unable to pick file. Check status of file manager.", Toast.LENGTH_SHORT).show();
        }
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
    public void submitResponse(List<SubmitScrutinyResponse> responses) {
        customProgressDialog.dismiss();
        if (responses != null && responses.get(0).getStatusCode() != null) {

            if (responses.get(0).getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_CODE)) {
                Utils.customSuccessAlert(this, getString(R.string.app_name),
                        responses.get(0).getStatusMessage(), true, editor);

            } else if (responses.get(0).getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_CODE)) {
                Utils.customErrorAlert(context, getString(R.string.app_name), responses.get(0).getStatusMessage());
            } else {
                Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.something));
            }
        } else {
            Utils.customErrorAlert(context, getString(R.string.app_name), getString(R.string.server_not));
        }
    }

    public void customInfoAlert(SubmitScrutinyRequest submitScrutinyRequest) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
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
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setText(R.string.cancel);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (Utils.checkInternetConnection(context)) {
                            if (validations()) {
                                customProgressDialog.show();
                                addScrutinyViewModel.callSubmitAPI(submitScrutinyRequest);
                            }
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
