package com.cgg.lrs2020officerapp.model.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class L2SubmitResponse {

    @SerializedName("Status Code")
    @Expose
    private String statusCode;
    @SerializedName("Status Message")
    @Expose
    private String statusMessage;

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

}
