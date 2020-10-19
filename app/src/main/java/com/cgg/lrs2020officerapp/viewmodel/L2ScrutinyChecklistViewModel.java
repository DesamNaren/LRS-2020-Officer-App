package com.cgg.lrs2020officerapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.lrs2020officerapp.R;
import com.cgg.lrs2020officerapp.error_handler.ErrorHandlerInterface;
import com.cgg.lrs2020officerapp.model.land.LandDetailsResponse;
import com.cgg.lrs2020officerapp.model.recommend.RecommendDetailsResponse;
import com.cgg.lrs2020officerapp.model.road.RoadDetailsResponse;
import com.cgg.lrs2020officerapp.network.LRSService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class L2ScrutinyChecklistViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<RoadDetailsResponse> roadDetailsResponseMutableLiveData;
    private MutableLiveData<LandDetailsResponse> landDetailsResponseMutableLiveData;
    private MutableLiveData<RecommendDetailsResponse> recommendDetailsResponseMutableLiveData;
    private ErrorHandlerInterface errorHandlerInterface;
    public L2ScrutinyChecklistViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        errorHandlerInterface = (ErrorHandlerInterface) context;
        roadDetailsResponseMutableLiveData=new MutableLiveData<>();
        landDetailsResponseMutableLiveData=new MutableLiveData<>();
        recommendDetailsResponseMutableLiveData=new MutableLiveData<>();

    }


    public LiveData<RoadDetailsResponse> getRoadDetailsResponse() {
        if (roadDetailsResponseMutableLiveData != null) {
            getRoadDetailsResponseCall();
        }
        return roadDetailsResponseMutableLiveData;
    }
    private void getRoadDetailsResponseCall() {
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getRoadDetails().enqueue(new Callback<RoadDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RoadDetailsResponse> call, @NonNull Response<RoadDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roadDetailsResponseMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RoadDetailsResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<LandDetailsResponse> getLandDetailsResponse() {
        if (landDetailsResponseMutableLiveData != null) {
            getLandDetailsResponseCall();
        }
        return landDetailsResponseMutableLiveData;
    }
    private void getLandDetailsResponseCall() {
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getLandDetails().enqueue(new Callback<LandDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<LandDetailsResponse> call, @NonNull Response<LandDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    landDetailsResponseMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LandDetailsResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


    public LiveData<RecommendDetailsResponse> getRecommendDetailsResponse() {
        if (recommendDetailsResponseMutableLiveData != null) {
            getRecommendDetailsResponseCall();
        }
        return recommendDetailsResponseMutableLiveData;
    }
    private void getRecommendDetailsResponseCall() {
        LRSService lrsService = LRSService.Factory.create();
        lrsService.getRecommendationMaster().enqueue(new Callback<RecommendDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecommendDetailsResponse> call, @NonNull Response<RecommendDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recommendDetailsResponseMutableLiveData.setValue(response.body());
                }else {
                    errorHandlerInterface.handleError(context.getString(R.string.server_not), context);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecommendDetailsResponse> call, @NonNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
