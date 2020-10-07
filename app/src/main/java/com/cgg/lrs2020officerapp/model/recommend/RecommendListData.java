package com.cgg.lrs2020officerapp.model.recommend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendListData {

    @SerializedName("REC_ID")
    @Expose
    private String rECID;
    @SerializedName("REC_NAME")
    @Expose
    private String rECNAME;

    public String getRECID() {
        return rECID;
    }

    public void setRECID(String rECID) {
        this.rECID = rECID;
    }

    public String getRECNAME() {
        return rECNAME;
    }

    public void setRECNAME(String rECNAME) {
        this.rECNAME = rECNAME;
    }

}
