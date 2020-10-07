package com.cgg.lrs2020officerapp.model.applicationList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicationListData {

    @SerializedName("APPLICATION_ID")
    @Expose
    private String aPPLICATIONID;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("FATHER_HUSBAND_NAME")
    @Expose
    private String fATHERHUSBANDNAME;
    @SerializedName("APPLICANT_MOBILE_NO")
    @Expose
    private String aPPLICANTMOBILENO;
    @SerializedName("PLOT_NUMBER")
    @Expose
    private String pLOTNUMBER;
    @SerializedName("LOCALITY")
    @Expose
    private Object lOCALITY;
    @SerializedName("UPDATEDON")
    @Expose
    private Object uPDATEDON;

    public String getAPPLICATIONID() {
        return aPPLICATIONID;
    }

    public void setAPPLICATIONID(String aPPLICATIONID) {
        this.aPPLICATIONID = aPPLICATIONID;
    }

    public String getNAME() {
        return nAME;
    }

    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    public String getFATHERHUSBANDNAME() {
        return fATHERHUSBANDNAME;
    }

    public void setFATHERHUSBANDNAME(String fATHERHUSBANDNAME) {
        this.fATHERHUSBANDNAME = fATHERHUSBANDNAME;
    }

    public String getAPPLICANTMOBILENO() {
        return aPPLICANTMOBILENO;
    }

    public void setAPPLICANTMOBILENO(String aPPLICANTMOBILENO) {
        this.aPPLICANTMOBILENO = aPPLICANTMOBILENO;
    }

    public String getPLOTNUMBER() {
        return pLOTNUMBER;
    }

    public void setPLOTNUMBER(String pLOTNUMBER) {
        this.pLOTNUMBER = pLOTNUMBER;
    }

    public Object getLOCALITY() {
        return lOCALITY;
    }

    public void setLOCALITY(Object lOCALITY) {
        this.lOCALITY = lOCALITY;
    }

    public Object getUPDATEDON() {
        return uPDATEDON;
    }

    public void setUPDATEDON(Object uPDATEDON) {
        this.uPDATEDON = uPDATEDON;
    }
}
