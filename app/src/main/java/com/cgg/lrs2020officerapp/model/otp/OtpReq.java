package com.cgg.lrs2020officerapp.model.otp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpReq {

    @SerializedName("TOKEN_ID")
    @Expose
    private String tOKENID;
    @SerializedName("APPLICATION_ID")
    @Expose
    private String aPPLICATIONID;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;

    public String getTOKENID() {
        return tOKENID;
    }

    public void setTOKENID(String tOKENID) {
        this.tOKENID = tOKENID;
    }

    public String getAPPLICATIONID() {
        return aPPLICATIONID;
    }

    public void setAPPLICATIONID(String aPPLICATIONID) {
        this.aPPLICATIONID = aPPLICATIONID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
