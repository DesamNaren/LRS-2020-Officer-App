package com.cgg.lrs2020officerapp.network;


import com.cgg.lrs2020officerapp.model.applicationList.ApplicationReq;
import com.cgg.lrs2020officerapp.model.applicationList.ApplicationRes;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.login.LoginRequest;
import com.cgg.lrs2020officerapp.model.login.LoginResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("Scrutiny/GET_APPLICATIONS_LIST")
    Call<ApplicationRes> getApplicationListResponse(@Body ApplicationReq applicationReq);

    @GET("Scrutiny/Abutting_Road_Data")
    Call<RoadDetailsResponse> getRoadDetails();


    @GET("Scrutiny/GetLandUsageType")
    Call<LandDetailsResponse> getLandDetails();

    @GET("Scrutiny/GetRecommendMaster")
    Call<RecommendDetailsResponse> getRecommendationMaster();

}



