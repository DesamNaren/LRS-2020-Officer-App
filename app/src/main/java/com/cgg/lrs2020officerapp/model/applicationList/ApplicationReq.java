package com.cgg.lrs2020officerapp.model.applicationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicationReq {
    @SerializedName("USER_ID")
    @Expose
    private String uSERID;
    @SerializedName("OFFICE_ID")
    @Expose
    private String oFFICEID;
    @SerializedName("AUTHORITY_ID")
    @Expose
    private String aUTHORITYID;
    @SerializedName("ROLE_ID")
    @Expose
    private String rOLEID;
    @SerializedName("STATUS_ID")
    @Expose
    private String sTATUSID;

    public String getUSERID() {
        return uSERID;
    }

    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
    }

    public String getOFFICEID() {
        return oFFICEID;
    }

    public void setOFFICEID(String oFFICEID) {
        this.oFFICEID = oFFICEID;
    }

    public String getAUTHORITYID() {
        return aUTHORITYID;
    }

    public void setAUTHORITYID(String aUTHORITYID) {
        this.aUTHORITYID = aUTHORITYID;
    }

    public String getROLEID() {
        return rOLEID;
    }

    public void setROLEID(String rOLEID) {
        this.rOLEID = rOLEID;
    }

    public String getSTATUSID() {
        return sTATUSID;
    }

    public void setSTATUSID(String sTATUSID) {
        this.sTATUSID = sTATUSID;
    }
}
