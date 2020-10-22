package com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CheckList implements Serializable, Parcelable
{

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
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<CheckList> CREATOR = new Creator<CheckList>() {



        public CheckList createFromParcel(Parcel in) {
            return new CheckList(in);
        }

        public CheckList[] newArray(int size) {
            return (new CheckList[size]);
        }

    }
            ;
    private final static long serialVersionUID = 5579050958648032245L;

    protected CheckList(Parcel in) {
        this.cLUSTERID = ((String) in.readValue((String.class.getClassLoader())));
        this.cLUSTERNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.mUNICIPALITYID = ((String) in.readValue((String.class.getClassLoader())));
        this.mUNICIPALITYNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.dISTRICTID = ((String) in.readValue((String.class.getClassLoader())));
        this.dISTRICTNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.cORPORATIONID = ((String) in.readValue((String.class.getClassLoader())));
        this.cORPORATIONNAME = ((String) in.readValue((String.class.getClassLoader())));
        this.vILLAGEID = ((String) in.readValue((String.class.getClassLoader())));
        this.vILLAGENAME = ((String) in.readValue((String.class.getClassLoader())));
        this.nOOFAPPLS = ((String) in.readValue((String.class.getClassLoader())));
        this.lOCALITY = ((String) in.readValue((String.class.getClassLoader())));
        this.vILLAGESYNO = ((String) in.readValue((String.class.getClassLoader())));
        this.lAYOUTEXTENT = ((String) in.readValue((String.class.getClassLoader())));
        this.pLOTNUMBER = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CheckList() {
    }

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


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cLUSTERID);
        dest.writeValue(cLUSTERNAME);
        dest.writeValue(mUNICIPALITYID);
        dest.writeValue(mUNICIPALITYNAME);
        dest.writeValue(dISTRICTID);
        dest.writeValue(dISTRICTNAME);
        dest.writeValue(cORPORATIONID);
        dest.writeValue(cORPORATIONNAME);
        dest.writeValue(vILLAGEID);
        dest.writeValue(vILLAGENAME);
        dest.writeValue(nOOFAPPLS);
        dest.writeValue(lOCALITY);
        dest.writeValue(vILLAGESYNO);
        dest.writeValue(lAYOUTEXTENT);
        dest.writeValue(pLOTNUMBER);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
