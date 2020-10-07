package com.cgg.lrs2020officerapp.model.road;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoadListData {
    @SerializedName("ABBUTING_ID")
    @Expose
    private String aBBUTINGID;
    @SerializedName("ABBUTING_NAME")
    @Expose
    private String aBBUTINGNAME;

    public String getABBUTINGID() {
        return aBBUTINGID;
    }

    public void setABBUTINGID(String aBBUTINGID) {
        this.aBBUTINGID = aBBUTINGID;
    }

    public String getABBUTINGNAME() {
        return aBBUTINGNAME;
    }

    public void setABBUTINGNAME(String aBBUTINGNAME) {
        this.aBBUTINGNAME = aBBUTINGNAME;
    }
}
