package com.cgg.lrs2020officerapp.model.applicationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicationReq {
    @SerializedName("TOKEN_ID")
    @Expose
    private String tOKENID;
    @SerializedName("office_id")
    @Expose
    private String oFFICEID;
    @SerializedName("authority_id")
    @Expose
    private String aUTHORITYID;
    @SerializedName("role_id")
    @Expose
    private String rOLEID;
    @SerializedName("status_id")
    @Expose
    private String sTATUSID;

    public String getTOKENID() {
        return tOKENID;
    }

    public void setTOKENID(String uSERID) {
        this.tOKENID = uSERID;
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
