package com.cgg.lrs2020officerapp.network;


import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsReq;
import com.cgg.lrs2020officerapp.model.applicantDetails.ApplicantDetailsRes;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.login.LoginRequest;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitRequest;
import com.cgg.lrs2020officerapp.model.submit.L1SubmitResponse;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitRequest;
import com.cgg.lrs2020officerapp.model.submit.L2SubmitResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LRSService {
    class Factory {
        public static LRSService create() {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LRSURL.LRS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(LRSService.class);
        }
    }

    @POST("Scrutiny/OfficerLogin")
    Call<List<LoginResponse>> getLoginResponse(@Body LoginRequest loginRequest);


    @POST("Scrutiny/AddScrutinyDetails")
    Call<List<L1SubmitResponse>> getSubmitResponse(@Body L1SubmitRequest request);

    @POST("Scrutiny/AddScrutinyDetails")
    Call<List<L2SubmitResponse>> getSubmitResponse(@Body L2SubmitRequest request);

    @POST("Scrutiny/GET_APPLICATIONS_LIST")
    Call<ApplicationRes> getApplicationListResponse(@Body ApplicationReq applicationReq);


    @POST("Scrutiny/GetApplicantSiteDetails")
    Call<ApplicantDetailsRes> getApplicantDetailsResponse(@Body ApplicantDetailsReq applicationReq);

    @GET("Scrutiny/Abutting_Road_Data")
    Call<RoadDetailsResponse> getRoadDetails();


    @GET("Scrutiny/GetLandUsageType")
    Call<LandDetailsResponse> getLandDetails();

    @GET("Scrutiny/GetRecommendMaster")
    Call<RecommendDetailsResponse> getRecommendationMaster();

}



