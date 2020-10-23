package com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckList {

    @SerializedName("CLUSTER_ID")
    @Expose
    private String cLUSTERID;
    @SerializedName("CLUSTER_NAME")
    @Expose
    private String cLUSTERNAME;
    @SerializedName("MUNICIPALITY_ID")
    @Expose
    private String mUNICIPALITYID;
    @SerializedName("MUNICIPALITY_NAME")
    @Expose
    private String mUNICIPALITYNAME;
    @SerializedName("DISTRICT_ID")
    @Expose
    private String dISTRICTID;
    @SerializedName("DISTRICT_NAME")
    @Expose
    private String dISTRICTNAME;
    @SerializedName("CORPORATION_ID")
    @Expose
    private String cORPORATIONID;
    @SerializedName("CORPORATION_NAME")
    @Expose
    private String cORPORATIONNAME;
    @SerializedName("VILLAGE_ID")
    @Expose
    private String vILLAGEID;
    @SerializedName("VILLAGE_NAME")
    @Expose
    private String vILLAGENAME;
    @SerializedName("NO_OF_APPLS")
    @Expose
    private String nOOFAPPLS;
    @SerializedName("LOCALITY")
    @Expose
    private String lOCALITY;
    @SerializedName("VILLAGE_SYNO")
    @Expose
    private String vILLAGESYNO;
    @SerializedName("LAYOUT_EXTENT")
    @Expose
    private String lAYOUTEXTENT;
    @SerializedName("PLOT_NUMBER")
    @Expose
    private String pLOTNUMBER;

    public String getCLUSTERID() {
        return cLUSTERID;
    }

    public void setCLUSTERID(String cLUSTERID) {
        this.cLUSTERID = cLUSTERID;
    }

    public String getCLUSTERNAME() {
        return cLUSTERNAME;
    }

    public void setCLUSTERNAME(String cLUSTERNAME) {
        this.cLUSTERNAME = cLUSTERNAME;
    }

    public String getMUNICIPALITYID() {
        return mUNICIPALITYID;
    }

    public void setMUNICIPALITYID(String mUNICIPALITYID) {
        this.mUNICIPALITYID = mUNICIPALITYID;
    }

    public String getMUNICIPALITYNAME() {
        return mUNICIPALITYNAME;
    }

    public void setMUNICIPALITYNAME(String mUNICIPALITYNAME) {
        this.mUNICIPALITYNAME = mUNICIPALITYNAME;
    }

    public String getDISTRICTID() {
        return dISTRICTID;
    }

    public void setDISTRICTID(String dISTRICTID) {
        this.dISTRICTID = dISTRICTID;
    }

    public String getDISTRICTNAME() {
        return dISTRICTNAME;
    }

    public void setDISTRICTNAME(String dISTRICTNAME) {
        this.dISTRICTNAME = dISTRICTNAME;
    }

    public String getCORPORATIONID() {
        return cORPORATIONID;
    }

    public void setCORPORATIONID(String cORPORATIONID) {
        this.cORPORATIONID = cORPORATIONID;
    }

    public String getCORPORATIONNAME() {
        return cORPORATIONNAME;
    }

    public void setCORPORATIONNAME(String cORPORATIONNAME) {
        this.cORPORATIONNAME = cORPORATIONNAME;
    }

    public String getVILLAGEID() {
        return vILLAGEID;
    }

    public void setVILLAGEID(String vILLAGEID) {
        this.vILLAGEID = vILLAGEID;
    }

    public String getVILLAGENAME() {
        return vILLAGENAME;
    }

    public void setVILLAGENAME(String vILLAGENAME) {
        this.vILLAGENAME = vILLAGENAME;
    }

    public String getNOOFAPPLS() {
        return nOOFAPPLS;
    }

    public void setNOOFAPPLS(String nOOFAPPLS) {
        this.nOOFAPPLS = nOOFAPPLS;
    }

    public String getLOCALITY() {
        return lOCALITY;
    }

    public void setLOCALITY(String lOCALITY) {
        this.lOCALITY = lOCALITY;
    }

    public String getVILLAGESYNO() {
        return vILLAGESYNO;
    }

    public void setVILLAGESYNO(String vILLAGESYNO) {
        this.vILLAGESYNO = vILLAGESYNO;
    }

    public String getLAYOUTEXTENT() {
        return lAYOUTEXTENT;
    }

    public void setLAYOUTEXTENT(String lAYOUTEXTENT) {
        this.lAYOUTEXTENT = lAYOUTEXTENT;
    }

    public String getPLOTNUMBER() {
        return pLOTNUMBER;
    }

    public void setPLOTNUMBER(String pLOTNUMBER) {
        this.pLOTNUMBER = pLOTNUMBER;
    }

}

