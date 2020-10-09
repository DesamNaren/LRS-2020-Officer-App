package com.cgg.lrs2020officerapp.model.applicantDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplicantDetailsRes {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("applicantList")
    @Expose
    private List<ApplicantDetailsListData> applicantList = null;
    @SerializedName("siteList")
    @Expose
    private List<ApplicantSiteList>  siteList = null;

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

    public List<ApplicantDetailsListData> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<ApplicantDetailsListData> applicantList) {
        this.applicantList = applicantList;
    }

    public List<ApplicantSiteList> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<ApplicantSiteList> siteList) {
        this.siteList = siteList;
    }

}
