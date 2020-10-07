package com.cgg.lrs2020officerapp.model.land;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandListData {

    @SerializedName("USAGE_TYPE_ID")
    @Expose
    private String uSAGETYPEID;
    @SerializedName("USAGE_TYPE")
    @Expose
    private String uSAGETYPE;

    public String getUSAGETYPEID() {
        return uSAGETYPEID;
    }

    public void setUSAGETYPEID(String uSAGETYPEID) {
        this.uSAGETYPEID = uSAGETYPEID;
    }

    public String getUSAGETYPE() {
        return uSAGETYPE;
    }

    public void setUSAGETYPE(String uSAGETYPE) {
        this.uSAGETYPE = uSAGETYPE;
    }
}
