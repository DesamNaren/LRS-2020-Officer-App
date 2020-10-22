package com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class L1ScrutinyChecklistRequest {

    @SerializedName("CLUSTER_ID")
    @Expose
    private String CLUSTER_ID;
    @SerializedName("AUTHORITY_ID")
    @Expose
    private String AUTHORITY_ID;
    @SerializedName("APP_LIST")
    @Expose
    private String APP_LIST;

    public String getCLUSTER_ID() {
        return CLUSTER_ID;
    }

    public void setCLUSTER_ID(String CLUSTER_ID) {
        this.CLUSTER_ID = CLUSTER_ID;
    }

    public String getAUTHORITY_ID() {
        return AUTHORITY_ID;
    }

    public void setAUTHORITY_ID(String AUTHORITY_ID) {
        this.AUTHORITY_ID = AUTHORITY_ID;
    }

    public String getAPP_LIST() {
        return APP_LIST;
    }

    public void setAPP_LIST(String APP_LIST) {
        this.APP_LIST = APP_LIST;
    }
}
