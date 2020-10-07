package com.cgg.lrs2020officerapp.model.road;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoadDetailsResponse {
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<RoadListData> data = null;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<RoadListData> getData() {
        return data;
    }

    public void setData(List<RoadListData> data) {
        this.data = data;
    }
}
