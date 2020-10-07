package com.cgg.lrs2020officerapp.model.applicantDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicantDetailsReq {

    @SerializedName("P_APPLICATION_ID")
    @Expose
    private String pAPPLICATIONID;
    @SerializedName("P_ROLE_ID")
    @Expose
    private String pROLEID;

    public String getPAPPLICATIONID() {
        return pAPPLICATIONID;
    }

    public void setPAPPLICATIONID(String pAPPLICATIONID) {
        this.pAPPLICATIONID = pAPPLICATIONID;
    }

    public String getPROLEID() {
        return pROLEID;
    }

    public void setPROLEID(String pROLEID) {
        this.pROLEID = pROLEID;
    }

}
