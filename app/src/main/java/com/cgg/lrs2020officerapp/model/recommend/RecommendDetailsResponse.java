package com.cgg.lrs2020officerapp.model.recommend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendDetailsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("recommendMasterList")
    @Expose
    private List<RecommendListData> recommendMasterList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecommendListData> getRecommendMasterList() {
        return recommendMasterList;
    }

    public void setRecommendMasterList(List<RecommendListData> recommendMasterList) {
        this.recommendMasterList = recommendMasterList;
    }

}
