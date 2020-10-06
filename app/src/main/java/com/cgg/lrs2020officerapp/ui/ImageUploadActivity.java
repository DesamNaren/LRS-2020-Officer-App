package com.cgg.lrs2020officerapp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityImageUploadBinding;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageUploadActivity extends AppCompatActivity {


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String IMAGE_FLAG = null;
    ActivityImageUploadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ImageUploadActivity.this, R.layout.activity_image_upload);
        binding.header.headerTitle.setText(R.string.upload_files);
        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        IMAGE_FLAG = AppConstants.IMAGE1;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        IMAGE_FLAG = AppConstants.IMAGE2;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        IMAGE_FLAG = AppConstants.IMAGE3;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        IMAGE_FLAG = AppConstants.IMAGE4;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, MY_CAMERA_REQUEST_CODE
                );
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (IMAGE_FLAG.equalsIgnoreCase(AppConstants.IMAGE1)){
                binding.image1.setImageBitmap(photo);
            }else if(IMAGE_FLAG.equalsIgnoreCase(AppConstants.IMAGE2)){
                binding.image2.setImageBitmap(photo);
            }else if(IMAGE_FLAG.equalsIgnoreCase(AppConstants.IMAGE3)){
                binding.image3.setImageBitmap(photo);
            }else if(IMAGE_FLAG.equalsIgnoreCase(AppConstants.IMAGE4)){
                binding.image4.setImageBitmap(photo);
            }
        }
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
//    public Uri getOutputMediaFileUri(int type) {
//        File imageFile = getOutputMediaFile(type);
//        Uri imageUri = null;
//        if (imageFile != null) {
//            imageUri = FileProvider.getUriForFile(
//                    context,
//                    BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
//                    imageFile);
//        }
//        return imageUri;
//    }

//    private File getOutputMediaFile(int type) {
//        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
//                        + " directory");
//                return null;
//            }
//        }
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            PIC_NAME = "EMP_ID" + "~" + Utils.getAttendanceTime() + "~" +
//                    Utils.getDeviceID(context) + "~" + Utils.getVersionName(context) + ".png";
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + PIC_NAME);
//        } else {
//            return null;
//        }
//
//        return mediaFile;
//    }


}
