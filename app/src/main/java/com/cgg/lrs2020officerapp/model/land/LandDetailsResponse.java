package com.cgg.lrs2020officerapp.model.land;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandDetailsResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("remarks")
    @Expose
    private List<LandListData> remarks = null;

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

    public List<LandListData> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<LandListData> remarks) {
        this.remarks = remarks;
    }
}
