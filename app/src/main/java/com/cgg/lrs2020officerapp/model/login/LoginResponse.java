package com.cgg.lrs2020officerapp.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Status Code")
    @Expose
    private String statusCode;
    @SerializedName("Status Message")
    @Expose
    private String statusMessage;
    @SerializedName("USER_ID")
    @Expose
    private String uSERID;
    @SerializedName("AUTHORITY_ID")
    @Expose
    private String aUTHORITYID;
    @SerializedName("ROLE_ID")
    @Expose
    private String rOLEID;
    @SerializedName("OFFICE_ID")
    @Expose
    private String oFFICEID;
    @SerializedName("SRO_DOC_LINK")
    @Expose
    private String sRO_DOC_LINK;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("DESIGNATION")
    @Expose
    private String dESIGNATION;

    @SerializedName("STATUS_ID")
    @Expose
    private String sTATUS_ID;

    @SerializedName("TOKEN_ID")
    @Expose
    private String tOKEN_ID;

    public String getsTATUS_ID() {
        return sTATUS_ID;
    }

    public void setsTATUS_ID(String sTATUS_ID) {
        this.sTATUS_ID = sTATUS_ID;
    }

    public String gettOKEN_ID() {
        return tOKEN_ID;
    }

    public void settOKEN_ID(String tOKEN_ID) {
        this.tOKEN_ID = tOKEN_ID;
    }

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

    public String getUSERID() {
        return uSERID;
    }

    public void setUSERID(String uSERID) {
        this.uSERID = uSERID;
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

    public String getOFFICEID() {
        return oFFICEID;
    }

    public void setOFFICEID(String oFFICEID) {
        this.oFFICEID = oFFICEID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getsRO_DOC_LINK() {
        return sRO_DOC_LINK;
    }

    public void setsRO_DOC_LINK(String sRO_DOC_LINK) {
        this.sRO_DOC_LINK = sRO_DOC_LINK;
    }

    public String getdESIGNATION() {
        return dESIGNATION;
    }

    public void setdESIGNATION(String dESIGNATION) {
        this.dESIGNATION = dESIGNATION;
    }
}
