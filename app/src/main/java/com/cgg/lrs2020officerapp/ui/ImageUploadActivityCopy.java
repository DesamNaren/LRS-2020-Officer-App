package com.cgg.lrs2020officerapp.ui;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.cgg.lrs2020officerapp.BuildConfig;
import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.constants.AppConstants;
import com.cgg.lrs2020officerapp.databinding.ActivityL2UploadBinding;
import com.cgg.lrs2020officerapp.utils.Utils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class ImageUploadActivityCopy extends LocBaseActivity {

    private static final int SELECT_FILE = 1;
    private Context context;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String IMAGE_FLAG = null, PIC_NAME, FilePath;
    ActivityL2UploadBinding binding;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "LRS_IMAGES";
    Bitmap bm;
    File file_image1, file_image2, file_image3, file_image4, file_image_doc, file_image_plot;
    private String userChoosenTask;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42042;
    public static final int REQUEST_PDF = 3;
    private int pic_number;
    public static String P_EX_FILE_PATH;
    public static String P_PLAN_PATH;
    public static String P_IMAGE1_PATH;
    public static String P_IMAGE2_PATH;
    public static String P_IMAGE3_PATH;
    public static String P_IMAGE4_PATH;
    Uri uriPdf1, uriPdf2;
    private boolean extractDoc = false;
    private boolean plotDoc = false;
    private boolean siteImage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ImageUploadActivityCopy.this,
                R.layout.activity_l2_upload);
        binding.header.headerTitle.setText(R.string.upload_files);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.DashboardActivity(ImageUploadActivityCopy.this);
            }
        });
        context = ImageUploadActivityCopy.this;
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


       /* binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE3;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callPermissions()) {
                    IMAGE_FLAG = AppConstants.IMAGE4;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = IMAGE_FLAG + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 50, stream);

              /*  if (IMAGE_FLAG.equals(AppConstants.IMAGE1)) {
                    siteImage = true;
                    binding.image1.setPadding(0, 0, 0, 0);
                    binding.image1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_image1 = new File(FilePath);
                    Glide.with(ImageUploadActivityCopy.this).load(file_image1).into(binding.image1);

                    P_IMAGE1_PATH = convertBase64(bm);

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE2)) {
                    siteImage = true;
                    binding.image2.setPadding(0, 0, 0, 0);
                    binding.image2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_image2 = new File(FilePath);
                    Glide.with(ImageUploadActivityCopy.this).load(file_image2).into(binding.image2);

                    P_IMAGE2_PATH = convertBase64(bm);
                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE3)) {
                    siteImage = true;
                    binding.image3.setPadding(0, 0, 0, 0);
                    binding.image3.setBackgroundColor(getResources().getColor(R.color.white));
                    file_image3 = new File(FilePath);
                    Glide.with(ImageUploadActivityCopy.this).load(file_image3).into(binding.image3);
                    P_IMAGE3_PATH = convertBase64(bm);
                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE4)) {
                    siteImage = true;
                    binding.image4.setPadding(0, 0, 0, 0);
                    binding.image4.setBackgroundColor(getResources().getColor(R.color.white));
                    file_image4 = new File(FilePath);
                    Glide.with(ImageUploadActivityCopy.this).load(file_image4).into(binding.image4);

                    P_IMAGE4_PATH = convertBase64(bm);

                } else*/
                if (IMAGE_FLAG.equals(AppConstants.IMAGE_DOC)) {
                    extractDoc = true;
                    file_image_doc = new File(FilePath);
                    binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    binding.imageExtract.setVisibility(View.VISIBLE);
                    binding.imageExtract.setPadding(0, 0, 0, 0);
                    binding.imageExtract.setBackgroundColor(getResources().getColor(R.color.white));
                    Glide.with(ImageUploadActivityCopy.this).load(file_image_doc).into(binding.imageExtract);

                    P_EX_FILE_PATH = convertBase64(bm);

                } else if (IMAGE_FLAG.equals(AppConstants.IMAGE_PLOT)) {
                    plotDoc = true;
                    file_image_plot = new File(FilePath);
                    binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    binding.imagePlot.setVisibility(View.VISIBLE);
                    binding.imagePlot.setPadding(0, 0, 0, 0);
                    binding.imagePlot.setBackgroundColor(getResources().getColor(R.color.white));
                    Glide.with(ImageUploadActivityCopy.this).load(file_image_plot).into(binding.imagePlot);

                    P_PLAN_PATH = convertBase64(bm);
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
                            extractDoc = true;
                            binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            binding.imageExtract.setVisibility(View.GONE);
                            InputStream in = getContentResolver().openInputStream(uri);

                            byte[] bytes;
                            assert in != null;
                            bytes = getBytesPDF1(in);

                            P_EX_FILE_PATH = Base64.encodeToString(bytes, Base64.DEFAULT);

                            binding.imageExtract.setVisibility(View.GONE);
                            binding.tvDoc.setVisibility(View.VISIBLE);
                            binding.tvDoc.setText(result);
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
                            plotDoc = true;
                            binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            binding.imagePlot.setVisibility(View.GONE);
                            InputStream in = getContentResolver().openInputStream(uri);

                            byte[] bytes;
                            assert in != null;
                            bytes = getBytesPDF1(in);

                            P_PLAN_PATH = Base64.encodeToString(bytes, Base64.DEFAULT);

                            binding.imagePlot.setVisibility(View.GONE);
                            binding.tvPlot.setVisibility(View.VISIBLE);
                            binding.tvPlot.setText(result);
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
            extractDoc = true;
            binding.tvDoc.setVisibility(View.GONE);
            binding.imageExtract.setVisibility(View.VISIBLE);
            binding.imageExtract.setImageBitmap(selectedImage);
            img_bitmap = ((BitmapDrawable) binding.imageExtract.getDrawable()).getBitmap();
            binding.btnSroRegDoc.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            P_EX_FILE_PATH = convertBitMap(img_bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void galleryImageSetter2(Uri imageUri) {

        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = getResizedBitmap(selectedImage, 200);// 400 is for example, replace with desired size
            ImageView imageView;
            Bitmap img_bitmap;
            plotDoc = true;
            binding.tvPlot.setVisibility(View.GONE);
            binding.imagePlot.setVisibility(View.VISIBLE);
            binding.imagePlot.setImageBitmap(selectedImage);
            img_bitmap = ((BitmapDrawable) binding.imagePlot.getDrawable()).getBitmap();
            binding.btnUploadPlotLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            P_PLAN_PATH = convertBitMap(img_bitmap);
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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    private void callSettings() {
        Snackbar snackbar = Snackbar.make(binding.root, getString(R.string.all_cam_per_setting), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                Utils.openSettings(ImageUploadActivityCopy.this);
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
            String deviceId = Utils.getDeviceID(ImageUploadActivityCopy.this);
            String versionName = Utils.getVersionName(ImageUploadActivityCopy.this);
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
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                        }
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
                    ImageUploadActivityCopy.this,
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
}
