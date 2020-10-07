//package com.cgg.lrs2020officerapp.ui;
//
//import android.Manifest;
//import android.content.ActivityNotFoundException;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import cgg.gov.ghmc.R;
//import cgg.gov.ghmc.beanmodule.APIRequest;
//import cgg.gov.ghmc.beanmodule.GrievanceComplaintBean;
//import cgg.gov.ghmc.beanmodule.InsertGrievanceBean;
//import cgg.gov.ghmc.beanmodule.WhereIAmBean;
//import cgg.gov.ghmc.dashboard.DashBoardActivity;
//import cgg.gov.ghmc.grievance.GPSTracker;
//import cgg.gov.ghmc.grievance.GlobalDeclarations;
//import cgg.gov.ghmc.locationwhereiam.Locateonmap;
//import cgg.gov.ghmc.retrofit.ApiClient;
//import cgg.gov.ghmc.retrofit.ApiInterface;
//import cgg.gov.ghmc.util.AppRuntimePermission;
//import cgg.gov.ghmc.util.Utils;
//import cgg.gov.ghmc.view.CustomProgressDialog;
//import cgg.gov.ghmc.view.ShowRetroAlert;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class GrievanceComplaintActivity extends AppCompatActivity {
//    private RadioGroup rg_placepicker;
//    private double latitudevalue_cpt;
//    private double longitudevalue_cpt;
//    private GPSTracker gps;
//    private String latitude_value;
//    private String longitude_value;
//    private int PLACE_PICKER_REQUEST = 1;
//    private String MobileNo = "";
//    private String Device_id = "";
//    private GoogleApiClient mGoogleApiClient;
//    private EditText et_mno;
//    private Spinner sp_complaint_type;
//    private Button btn_submit;
//    private ImageView back_button_newcomp;
//    private EditText et_name, et_landmark, et_remarks;
//    private CustomProgressDialog progressDialog;
//    private JsonObject gsonObject;
//    private String GRV_ID;
//    ArrayAdapter<String> complaintdata;
//    APIRequest apiRequest;
//    public static String PHOTO_ENCODED_STRING1;
//    public static String PHOTO_ENCODED_STRING2;
//    public static String PHOTO_ENCODED_STRING3;
//    private int pic_number = 0;
//    String userChoosenTask;
//    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1,REQUEST_PDF = 3;
//    private Bitmap capturePhotoBitmap;
//    private String spinnervalue;
//    private String UserName = "";
//    private static final int REQUEST_LOCATION_TURN_ON = 2000;
//   // ProgressBar progressbar;
//
//
//
//
//    ImageView iv_cam_one, iv_cam_two, iv_cam_three, home_butlaunchPickerton;
//    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
//    private final static int REQUEST_CODE = 42;
//    public static final int PERMISSION_CODE = 42042;
//    Uri uri;
//     String PDFBase64One,PDFBase64Two,PDFBase64Three;
////PDFView pdfView1,pdfView2,pdfView3;
//    Integer pageNumber = 0;
//    String pdfFileName;
//    private static final String TAG = GrievanceComplaintActivity.class.getSimpleName();
//     Uri uriPdf1,uriPdf2,uriPdf3;
//     GrievanceComplaintCustomAdapter grievanceComplaintCustomAdapter;
//     ArrayList<GrievanceComplaintBean> typeBeanArrayList;
//
//    //PDFView pdfView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        setContentView(R.layout.grievance_complaint_activity);
//        progressDialog = CustomProgressDialog.getInstance();
//
//        Intent intent = getIntent();
//        GRV_ID = intent.getStringExtra("GRV_ID");
//        apiRequest = new APIRequest();
//        turnOnLocation();
//        init();
//        gps = new GPSTracker(GrievanceComplaintActivity.this);
//        if (gps.canGetLocation()) {
//            latitudevalue_cpt = gps.getLatitude();
//            longitudevalue_cpt = gps.getLongitude();
//            latitude_value = String.valueOf(latitudevalue_cpt).replace(" ", "!");
//            longitude_value = String.valueOf(longitudevalue_cpt).replace(" ", "!");
//
//        }
//
//        home_button = (ImageView) findViewById(R.id.home_button);
//        home_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(GrievanceComplaintActivity.this, DashBoardActivity.class);
//                startActivity(i);
//            }
//        });
//    }
//
//
//    private void getSubCategotyService() {
//        progressDialog.showProgress(GrievanceComplaintActivity.this);
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//
//        apiRequest.setGid(GRV_ID);
//        apiRequest.setUserid("cgg@ghmc");
//        apiRequest.setPassword("ghmc@cgg@2018");
//
//        final ArrayList<String> arrayList = new ArrayList<>();
//
//        Call<ArrayList<GrievanceComplaintBean>> call = apiInterface.getSubCategoty(apiRequest);
//        call.enqueue(new Callback<ArrayList<GrievanceComplaintBean>>() {
//            @Override
//            public void onResponse(Call<ArrayList<GrievanceComplaintBean>> call, Response<ArrayList<GrievanceComplaintBean>> response) {
//                progressDialog.hideProgress();
//                if (response.body()!=null) {
//
//                    if (response.body() != null && response.body().size() > 0) {
//
//                      /*  final ArrayList<GrievanceComplaintBean> list = (ArrayList<GrievanceComplaintBean>) response.body();
//
//                        for (int x = 0; x < list.size(); x++) {
//                            arrayList.add(list.get(x).getType());
//                        }
//*/
//                        typeBeanArrayList = response.body();
//                        final LinearLayoutManager layoutManager = new LinearLayoutManager(GrievanceComplaintActivity.this);
//                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//                      /*  complaintdata = new ArrayAdapter<String>(GrievanceComplaintActivity.this, android.R.layout.simple_spinner_item, arrayList);
//                        complaintdata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        sp_complaint_type.setAdapter(complaintdata);
//*/
//                        grievanceComplaintCustomAdapter = new GrievanceComplaintCustomAdapter(typeBeanArrayList, GrievanceComplaintActivity.this);
//                        sp_complaint_type.setAdapter(grievanceComplaintCustomAdapter);
//                        grievanceComplaintCustomAdapter.notifyDataSetChanged();
//
//                      /*  grievanceComplaintCustomAdapter = new GrievanceComplaintCustomAdapter(arrayList, GrievanceComplaintActivity.this);
//                        sp_complaint_type.setAdapter(grievanceComplaintCustomAdapter);
//                        grievanceComplaintCustomAdapter.notifyDataSetChanged();
//
//
//*/
//                        sp_complaint_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view,
//                                                       int position, long id) {
//                                GrievanceComplaintBean data = (GrievanceComplaintBean) typeBeanArrayList.get(position);
//
//                                spinnervalue = data.getId();
//
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//                    }
//                }
//                else {
//                    ShowRetroAlert.show(GrievanceComplaintActivity.this);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<GrievanceComplaintBean>> call, Throwable t) {
//                progressDialog.hideProgress();
//                ShowRetroAlert.show(GrievanceComplaintActivity.this);
//            }
//        });
//
//    }
//
//    private void init() {
//        //progressbar = (ProgressBar) findViewById(R.id.progressbar);
//        iv_cam_one = (ImageView) findViewById(R.id.iv_cam_one);
//        iv_cam_two = (ImageView) findViewById(R.id.iv_cam_two);
//        iv_cam_three = (ImageView) findViewById(R.id.iv_cam_three);
//       /* pdfView1 = findViewById(R.id.pdfView1);
//        pdfView2 = findViewById(R.id.pdfView2);
//        pdfView3 = findViewById(R.id.pdfView3);
//*/
//        try {
//            GlobalDeclarations.preferences = getSharedPreferences(GlobalDeclarations.PREFS_NAME, MODE_PRIVATE);
//            MobileNo = GlobalDeclarations.preferences.getString("mobilenumber", "");
//            UserName = GlobalDeclarations.preferences.getString("name", "");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            MobileNo = "";
//            UserName = "";
//        }
//
//        Device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(GrievanceComplaintActivity.this, null)
//                .build();
//
//        et_mno = (EditText) findViewById(R.id.et_mno);
//        sp_complaint_type = (Spinner) findViewById(R.id.sp_complaint_type);
//        btn_submit = (Button) findViewById(R.id.btn_submit);
//        et_name = (EditText) findViewById(R.id.et_name);
//        et_landmark = (EditText) findViewById(R.id.et_landmark);
//        et_remarks = (EditText) findViewById(R.id.et_remarks);
//
//        et_name.setText(UserName);
//
//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (validateFields()) {
//                        if (latitude_value != null && longitude_value != null) {
//
//                            if (Utils.checkInternetConnection(GrievanceComplaintActivity.this)) {
//                                if (AppRuntimePermission.show(GrievanceComplaintActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})) {
//
//                                    checkUserisInsideHyd(latitudevalue_cpt, latitudevalue_cpt);
//                                }
//                            } else {
//                                Utils.showAlert(GrievanceComplaintActivity.this, "", getResources().getString(R.string.networkconnection_check), false);
//                            }
//
//
//                        } else {
//
//                            Toast.makeText(GrievanceComplaintActivity.this, "Please enable your location", Toast.LENGTH_LONG).show();
//
//
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        back_button_newcomp = (ImageView) findViewById(R.id.back_button_newcomp);
//        back_button_newcomp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PHOTO_ENCODED_STRING1 = null;
//                PHOTO_ENCODED_STRING2 = null;
//                PHOTO_ENCODED_STRING3 = null;
//                Intent intent = new Intent(GrievanceComplaintActivity.this, DynamicGrievanceActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                finish();
//                startActivity(intent);
//            }
//        });
//
//        rg_placepicker = (RadioGroup) findViewById(R.id.rg_location);
//        RadioButton rbCurrentlocation = (RadioButton) findViewById(R.id.rb_currentlocation);
//        RadioButton rbLocationonmap = (RadioButton) findViewById(R.id.rb_locationonmap);
//        rbLocationonmap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(GrievanceComplaintActivity.this, Locateonmap.class);
//                startActivity(i);
//                if (GlobalDeclarations.locateonmap_lat != 0 && GlobalDeclarations.locateonmap_long != 0) {
//                    latitudevalue_cpt = GlobalDeclarations.locateonmap_lat;
//                    longitudevalue_cpt = GlobalDeclarations.locateonmap_long;
//                    Log.e("test", latitudevalue_cpt + "," + longitudevalue_cpt);
//
//                }
//            }
//        });
//        rbCurrentlocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GlobalDeclarations.locateonmap_lat = 0.0;
//                GlobalDeclarations.locateonmap_long = 0.0;
//                gps = new GPSTracker(GrievanceComplaintActivity.this);
//                latitudevalue_cpt = gps.getLatitude();
//                longitudevalue_cpt = gps.getLongitude();
//                Log.e("test11", latitudevalue_cpt + "," + longitudevalue_cpt);
//            }
//        });
//        /*rg_placepicker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.rb_currentlocation) {
//                    GlobalDeclarations.locateonmap_lat = 0.0;
//                    GlobalDeclarations.locateonmap_long = 0.0;
//
//                    gps = new GPSTracker(GrievanceComplaintActivity.this);
//                    latitudevalue_cpt = gps.getLatitude();
//                    longitudevalue_cpt = gps.getLongitude();
//                } else if (checkedId == R.id.rb_locationonmap) {
//                    //progressbar.setVisibility(View.VISIBLE);
//                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                    try {
//                        startActivityForResult(builder.build(GrievanceComplaintActivity.this), PLACE_PICKER_REQUEST);
//                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });*/
//
//        et_mno.setText(MobileNo);
//
//
//        iv_cam_one.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 1;
//                selectImageDialog(1);
//            }
//        });
//
//        iv_cam_two.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 2;
//                selectImageDialog(2);
//            }
//        });
//
//        iv_cam_three.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 3;
//                selectImageDialog(3);
//            }
//        });
//
//      /*  pdfView1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 1;
//                selectImageDialog(pic_number);
//            }
//        });
//
//        pdfView2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 2;
//                selectImageDialog(pic_number);
//            }
//        });
//
//        pdfView3.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pic_number = 3;
//                selectImageDialog(pic_number);
//            }
//        });*/
//
//    }
//
//
//    private void getInsertGrievanceDetails() {
//        //progressDialog.showProgress(GrievanceComplaintActivity.this);
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        try {
//            JSONObject object = new JSONObject();
//            object.put("userid", "cgg@ghmc");
//            object.put("password", "ghmc@cgg@2018");
//            object.put("remarks", et_remarks.getText().toString().trim());
//            object.put("photo", PHOTO_ENCODED_STRING1);
//            object.put("photo2", PHOTO_ENCODED_STRING2);
//            object.put("photo3", PHOTO_ENCODED_STRING3);
//            object.put("latlon", latitudevalue_cpt + "," + longitudevalue_cpt);
//            object.put("mobileno", MobileNo + "-" + et_mno.getText().toString());
//            object.put("deviceid", Device_id);
//            object.put("compType", spinnervalue);
//            object.put("landmark", et_landmark.getText().toString().trim());
//            object.put("username", et_name.getText().toString().trim());
//            object.put("ward", "0");
//            object.put("source", "11");
//            object.put("empid", "");
//            object.put("agentid", "");
//
//            JsonParser jsonParser = new JsonParser();
//            gsonObject = (JsonObject) jsonParser.parse(object.toString());
//            Log.e("sent_json", object.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Call<InsertGrievanceBean> call = apiInterface.getInsertGrievance(gsonObject);
//
//        call.enqueue(new Callback<InsertGrievanceBean>() {
//            @Override
//            public void onResponse(Call<InsertGrievanceBean> call, Response<InsertGrievanceBean> response) {
//                progressDialog.hideProgress();
//
//                if (response.body() != null) {
//
//                    if (response.body().getStatus()) {
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(GrievanceComplaintActivity.this);
//                        alert.setCancelable(false);
//                        alert.setMessage(response.body().getCompid());
//                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent dashboardIntent = new Intent(GrievanceComplaintActivity.this, DashBoardActivity.class);
//                                dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(dashboardIntent);
//                                PHOTO_ENCODED_STRING1 = null;
//                                PHOTO_ENCODED_STRING2 = null;
//                                PHOTO_ENCODED_STRING3 = null;
//
//                                dialog.dismiss();
//                            }
//                        });
//                        alert.show();
//                    } else {
//                        Utils.showAlert(GrievanceComplaintActivity.this, "", response.body().getCompid().toString(), false);
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InsertGrievanceBean> call, Throwable t) {
//                progressDialog.hideProgress();
//                ShowRetroAlert.show(GrievanceComplaintActivity.this);
//            }
//        });
//
//
//    }
//
//    private boolean validateFields() {
//        //Toast.makeText(getActivity(), PHOTO_ENCODED_STRING1+":::::"+capturePhotoBitmap, Toast.LENGTH_SHORT).show();
//       /* if (sp_complaint_type.getSelectedItemPosition() == 0) {
//            errorSpinner(sp_complaint_type, "Select Grievance Type");
//            return false;
//        } else */
//        if (et_name.getText().toString().isEmpty() || et_name.getText().toString().equals("-")) {
//            setFocus(et_name, "Please enter valid name");
//            return false;
//
//        }
//        else if (sp_complaint_type.getSelectedItemPosition() == 0) {
//            errorSpinner(sp_complaint_type, "Please select Subcategory");
//            return false;
//        }
//
//      /*  else if (sp_complaint_type.getSelectedItem().toString().equalsIgnoreCase("Please select")) {
//            Toast.makeText(GrievanceComplaintActivity.this, "Please select Subcategory", Toast.LENGTH_SHORT).show();
//            // mErrorString = "Please Choose Document";
//            return false;
//        }*/
//
//        else if (et_mno.getText().toString().trim().length() == 0) {
//            setFocus(et_mno, "Please enter valid mobile number");
//            return false;
//        } else if (et_mno.getText().toString().trim().length() != 10) {
//            setFocus(et_mno, "Please enter valid mobile number");
//            return false;
//        } else if (!(et_mno.getText().toString().trim().startsWith("9") || et_mno.getText().toString().trim().startsWith("8") || et_mno.getText().toString().trim().startsWith("7")|| et_mno.getText().toString().trim().startsWith("6"))) {
//            setFocus(et_mno, "Please enter valid mobile number");
//            return false;
//        }
//
//       else if (et_remarks.getText().toString().isEmpty()) {
//            setFocus(et_remarks, "Please enter description");
//            return false;
//
//        }
//        //remove comments on photo validation later
//         else if ((PHOTO_ENCODED_STRING1 == null) && (PHOTO_ENCODED_STRING2 == null) && (PHOTO_ENCODED_STRING3 == null)) {
//            Toast.makeText(GrievanceComplaintActivity.this, "Please Take Picture", Toast.LENGTH_SHORT).show();
//            // mErrorString = "Please Choose Document";
//            return false;
//        }
//
//
//        return true;
//    }
//
//    protected void setFocus(final View view, String errorMsg) {
//        view.requestFocus();
//        ((TextView) view).setError(errorMsg);
//    }
//
//    protected void errorSpinner(Spinner mySpinner, String errorMsg) {
//        TextView errorText = (TextView) mySpinner.getSelectedView();
//        errorText.setError("");
//        errorText.setTextColor(Color.RED);//just to highlight that this is an error
//        errorText.setText(errorMsg);
//    }
//
//    private void selectImageDialog(int pic_no) {
//        final CharSequence[] items = {"Take Photo", "Choose from Library","Choose Documents",
//                "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(GrievanceComplaintActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (AppRuntimePermission.show(GrievanceComplaintActivity.this, new String[]{Manifest.permission.CAMERA})) {
//                        cameraIntent();
//                    }
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    galleryIntent();
//
//                } else if (items[item].equals("Choose Documents")) {
//
//                    chooseDocs();
//
//                }
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void chooseDocs() {
//
//        int permissionCheck = ContextCompat.checkSelfPermission(GrievanceComplaintActivity.this,
//                READ_EXTERNAL_STORAGE);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    GrievanceComplaintActivity.this,
//                    new String[]{READ_EXTERNAL_STORAGE},
//                    PERMISSION_CODE
//            );
//
//            return;
//        }
//
//        launchPicker();
//    }
//
//    void launchPicker() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        try {
//            startActivityForResult(intent, REQUEST_PDF);
//        } catch (ActivityNotFoundException e) {
//            //alert user that file manager not working
//            Toast.makeText(this, "Unable to pick file. Check status of file manager.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void galleryIntent() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, SELECT_FILE);
//    }
//
//    private void cameraIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_CAMERA);
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        try {
//            if (requestCode == REQUEST_LOCATION_TURN_ON) {
//                if (resultCode == RESULT_OK) {
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
//                } else {
//                    new AlertDialog.Builder(GrievanceComplaintActivity.this).setCancelable(false).setTitle("Please turn on the location to continue").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            turnOnLocation();
//
//                        }
//                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    }).show();
//
//
//                }
//            }
//            if (requestCode == SELECT_FILE && resultCode == RESULT_OK
//                    && null != data) {
//                onSelectFromGalleryResult(data);
//
//            }
//            if (requestCode == REQUEST_CAMERA) {
//                onCaptureImageResult(data);
//            }
//
//            if(requestCode== REQUEST_PDF){
//                if(pic_number==1) {
//                    uriPdf1 = data.getData();
//                    displayFromUriPDF1(uriPdf1);
//                }
//
//                if(pic_number==2) {
//                    uriPdf2 = data.getData();
//                    displayFromUriPDF2(uriPdf2);
//
//                }
//                if(pic_number==3) {
//                    uriPdf3 = data.getData();
//                    displayFromUriPDF3(uriPdf3);
//
//
//                }
//
//            }
//
//            if (requestCode == PLACE_PICKER_REQUEST) {
//                //progressbar.setVisibility(View.GONE);
//
//                if (resultCode == RESULT_OK) {
//                    Place place = PlacePicker.getPlace(data, GrievanceComplaintActivity.this);
//                    StringBuilder stBuilder = new StringBuilder();
//                    String placename = String.format("%s", place.getName());
//
//
//                    latitude_value = String.valueOf(place.getLatLng().latitude);
//                    longitude_value = String.valueOf(place.getLatLng().longitude);
//
//                    latitudevalue_cpt = 0.0;
//                    longitudevalue_cpt = 0.0;
//                    latitudevalue_cpt = Double.parseDouble(latitude_value);
//                    longitudevalue_cpt = Double.parseDouble(longitude_value);
//
//                    String address = String.format("%s", place.getAddress());
//                    stBuilder.append("Name: ");
//                    stBuilder.append(placename);
//                    stBuilder.append("\n");
//                    stBuilder.append("Latitude: ");
//                    stBuilder.append(latitude_value);
//                    stBuilder.append("\n");
//                    stBuilder.append("Logitude: ");
//                    stBuilder.append(longitude_value);
//                    stBuilder.append("\n");
//                    stBuilder.append("Address: ");
//                    stBuilder.append(address);
//                    // tvPlaceDetails.setText(stBuilder.toString());
//
//                    // Toast.makeText(getActivity(), "Result==="+stBuilder.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void displayFromUriPDF1(Uri uri) {
//        try {
//                iv_cam_one.setVisibility(View.VISIBLE);
//                //pdfView1.setVisibility(View.VISIBLE);
//
//                 iv_cam_one.setImageResource(R.drawable.pdfnew);
//                InputStream in = getContentResolver().openInputStream(uri);
//
//                byte[] bytes;
//                bytes=getBytesPDF1(in);
//                Log.d("data", "onActivityResult: bytes size="+bytes.length);
//
//                PHOTO_ENCODED_STRING1=Base64.encodeToString(bytes,Base64.DEFAULT);
//
//                GlobalDeclarations.PDFBase64String1=PHOTO_ENCODED_STRING1;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("error", "onActivityResult: " + e.toString());
//        }
//
//
//   /*     pdfView1.fromUri(uri)
//                .defaultPage(pageNumber)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError(this)
//                .load();
//*/
//    }
//
//    private void displayFromUriPDF2(Uri uri) {
//        try {
//
//
//                iv_cam_two.setVisibility(View.VISIBLE);
//               // pdfView2.setVisibility(View.VISIBLE);
//
//            iv_cam_two.setImageResource(R.drawable.pdfnew);
//
//            InputStream in = getContentResolver().openInputStream(uri);
//
//                byte[] bytes;
//                bytes=getBytesPDF2(in);
//                Log.d("data", "onActivityResult: bytes size="+bytes.length);
//
//                PHOTO_ENCODED_STRING2=Base64.encodeToString(bytes,Base64.DEFAULT);
//
//                GlobalDeclarations.PDFBase64String2=PHOTO_ENCODED_STRING2;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("error", "onActivityResult: " + e.toString());
//        }
//
//
//      /*  pdfView2.fromUri(uri)
//                .defaultPage(pageNumber)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError(this)
//                .load();
//*/
//    }
//
//    private void displayFromUriPDF3(Uri uri) {
//        try {
//
//
//            iv_cam_three.setVisibility(View.VISIBLE);
//
//            iv_cam_two.setImageResource(R.drawable.pdfnew);
//            //pdfView3.setVisibility(View.VISIBLE);
//            InputStream in = getContentResolver().openInputStream(uri);
//
//            byte[] bytes;
//            bytes=getBytesPDF3(in);
//            Log.d("data", "onActivityResult: bytes size="+bytes.length);
//
//            PHOTO_ENCODED_STRING3=Base64.encodeToString(bytes,Base64.DEFAULT);
//
//            GlobalDeclarations.PDFBase64String3=PHOTO_ENCODED_STRING3;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("error", "onActivityResult: " + e.toString());
//        }
//
///*
//        pdfView3.fromUri(uri)
//                .defaultPage(pageNumber)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError(this)
//                .load();*/
//
//    }uriPdf1
//
//
//    public byte[] getBytesPDF1(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//    public byte[] getBytesPDF2(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//    public byte[] getBytesPDF3(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//    private void onCaptureImageResult(Intent data) {
//        if (data != null) {
//            capturePhotoBitmap = (Bitmap) data.getExtras().get("data");
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            capturePhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
//
//            if (pic_number == 1) {
//                //pdfView1.setVisibility(View.GONE);
//                iv_cam_one.setVisibility(View.VISIBLE);
//                iv_cam_one.setImageBitmap(capturePhotoBitmap);
//                Bitmap img1_bitmap = ((BitmapDrawable) iv_cam_one.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING1 = convertBitMap(img1_bitmap);
//
//            }
//            if (pic_number == 2) {
//               // pdfView2.setVisibility(View.GONE);
//                iv_cam_two.setVisibility(View.VISIBLE);
//                iv_cam_two.setImageBitmap(capturePhotoBitmap);
//                Bitmap img2_bitmap = ((BitmapDrawable) iv_cam_two.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING2 = convertBitMap(img2_bitmap);
//
//            }
//            if (pic_number == 3) {
//              //  pdfView3.setVisibility(View.GONE);
//                iv_cam_three.setVisibility(View.VISIBLE);
//                iv_cam_three.setImageBitmap(capturePhotoBitmap);
//                Bitmap img3_bitmap = ((BitmapDrawable) iv_cam_three.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING3 = convertBitMap(img3_bitmap);
//
//            }
//        }
//    }
//
//    private void onSelectFromGalleryResult(Intent data) {
//        if (data != null) {
//            try {
//                Uri imageUri = data.getData();
//
//                if (pic_number == 1) {
//                   // pdfView1.setVisibility(View.GONE);
//                    iv_cam_one.setVisibility(View.VISIBLE);
//                    galleryImageSetter(pic_number, imageUri);
//                }
//                if (pic_number == 2) {
//                   // pdfView2.setVisibility(View.GONE);
//                    iv_cam_two.setVisibility(View.VISIBLE);
//                    galleryImageSetter(pic_number, imageUri);
//
//                }
//                if (pic_number == 3) {
//                   // pdfView3.setVisibility(View.GONE);
//                    iv_cam_three.setVisibility(View.VISIBLE);
//                    galleryImageSetter(pic_number, imageUri);
//                }
//            } catch (Exception e) {
//            }
//
//
//        }
//    }
//
//    public String convertBitMap(Bitmap img_bitmap) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        img_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//        return encodedImage;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (GlobalDeclarations.locateonmap_long != 0.0 && GlobalDeclarations.locateonmap_lat != 0.0) {
//            latitudevalue_cpt = GlobalDeclarations.locateonmap_lat;
//            longitudevalue_cpt = GlobalDeclarations.locateonmap_long;
//        }
//
//
//    }
//
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;getResizedBitmap
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
//
//    public void galleryImageSetter(int no, Uri imageUri) {
//
//        try {
//            InputStream imageStream = getContentResolver().openInputStream(imageUri);
//            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//            selectedImage = getResizedBitmap(selectedImage, 200);// 400 is for example, replace with desired size
//            ImageView imageView;
//            Bitmap img_bitmap;
//            if (no == 1) {
//                imageView = (ImageView) findViewById(R.id.iv_cam_one);
//                imageView.setImageBitmap(selectedImage);
//                img_bitmap = ((BitmapDrawable) iv_cam_one.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING1 = convertBitMap(img_bitmap);
//            }
//            if (no == 2) {
//                imageView = (ImageView) findViewById(R.id.iv_cam_two);
//                imageView.setImageBitmap(selectedImage);
//                img_bitmap = ((BitmapDrawable) iv_cam_two.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING2 = convertBitMap(img_bitmap);
//            }
//            if (no == 3) {
//                imageView = (ImageView) findViewById(R.id.iv_cam_three);
//                imageView.setImageBitmap(selectedImage);
//                img_bitmap = ((BitmapDrawable) iv_cam_three.getDrawable()).getBitmap();
//                PHOTO_ENCODED_STRING3 = convertBitMap(img_bitmap);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private void turnOnLocation() {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
//        googleApiClient.connect();
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//                final Status status = result.getStatus();
//                switch (status.getStatusCode()) {
//                    case LocationSettingsStatusCodes.SUCCESS:
//
//                        getSubCategotyService();
//                        break;
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        try {
//                            status.startResolutionForResult(GrievanceComplaintActivity.this, REQUEST_LOCATION_TURN_ON);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Toast.makeText(GrievanceComplaintActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        //Toast.makeText(GrievanceComplaintActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                        break;
//                    default: {
//                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(i);
//                        break;
//                    }
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        PHOTO_ENCODED_STRING1 = null;
//        PHOTO_ENCODED_STRING2 = null;
//        PHOTO_ENCODED_STRING3 = null;
//      //  Intent intent = new Intent(GrievanceComplaintActivity.this, DynamicGrievanceActivity.class);
//       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        finish();
//     //   startActivity(intent);
//    }
//
//    private void checkUserisInsideHyd(double latitude, double longitude) {
//        try {
//            //progressbar.setVisibility(View.VISIBLE);
//            if(progressDialog != null)
//                progressDialog.showProgress(GrievanceComplaintActivity.this);
//
//            if (rg_placepicker.getCheckedRadioButtonId() == R.id.rb_currentlocation) {
//                gps = new GPSTracker(GrievanceComplaintActivity.this);
//                latitudevalue_cpt = gps.getLatitude();
//                longitudevalue_cpt = gps.getLongitude();
//            }
//
//
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            try {
//                JSONObject object = new JSONObject();
//                object.put("userid", "cgg@ghmc");
//                object.put("password", "ghmc@cgg@2018");
//                object.put("latitude", "" + latitudevalue_cpt);
//                object.put("longitude", "" + longitudevalue_cpt);
//                JsonParser jsonParser = new JsonParser();
//                gsonObject = (JsonObject) jsonParser.parse(object.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Call<WhereIAmBean> call = apiInterface.getWhereIAmDetails(gsonObject);
//            call.enqueue(new Callback<WhereIAmBean>() {
//                @Override
//                public void onResponse(Call<WhereIAmBean> call, final Response<WhereIAmBean> response) {
//                   // progressbar.setVisibility(View.GONE);
//                  // progressDialog.hideProgress();
//                    if (response.message().equalsIgnoreCase("OK")) {
//                        String circle = response.body().getCirclename();
//                        String ward = response.body().getWardName();
//                        String zone = response.body().getZone();
//
//                        try {
//                            if (!circle.equals("") && !ward.equals("") && !zone.equals("")) {
//                                if (Utils.checkInternetConnection(GrievanceComplaintActivity.this)) {
//                                    getInsertGrievanceDetails();
//                                } else {
//                                    Utils.showAlert(GrievanceComplaintActivity.this, "", getResources().getString(R.string.networkconnection_check), false);
//                                }
//                            } else {
//                                AlertDialog.Builder ad = new AlertDialog.Builder(GrievanceComplaintActivity.this);
//                                ad.setMessage("Cannot register grievance.unable to locate current location or it seems like your location is out of GHMC").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                                ad.show();
//                            }
//                        } catch (Resources.NotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } else {
//                        Toast.makeText(GrievanceComplaintActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<WhereIAmBean> call, Throwable t) {
//                    //progressbar.setVisibility(View.GONE);
//                    progressDialog.hideProgress();
//                    ShowRetroAlert.show(GrievanceComplaintActivity.this);
//
//                }
//            });
//        } catch (
//                Exception e)
//
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
