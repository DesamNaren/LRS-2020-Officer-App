package com.cgg.lrs2020officerapp.model.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class L1SubmitRequest implements Serializable, Parcelable
{

    @SerializedName("OBJECTIONABLE_LANDS")
    @Expose
    private String oBJECTIONABLELANDS;
    @SerializedName("PROHIBITORY_LANDS ")
    @Expose
    private String pROHIBITORYLANDS;
    @SerializedName("OPENSPACE_HTLINE")
    @Expose
    private String oPENSPACEHTLINE;
    @SerializedName("LOCALITY_AFFECTEDBY_MP ")
    @Expose
    private String lOCALITYAFFECTEDBYMP;
    @SerializedName("OPEN_SPACE_10PERCENT")
    @Expose
    private String oPENSPACE10PERCENT;
    @SerializedName("LAND_USE_ASPER_MAPLAN")
    @Expose
    private String lANDUSEASPERMAPLAN;
    @SerializedName("LEGAL_DISPUTES")
    @Expose
    private String lEGALDISPUTES;
    @SerializedName("APPLS_APPROVED ")
    @Expose
    private String aPPLSAPPROVED;
    @SerializedName("APPLS_SHORTFALL")
    @Expose
    private String aPPLSSHORTFALL;
    @SerializedName("APPLS_REJECTED ")
    @Expose
    private String aPPLSREJECTED;
    @SerializedName("IMAGE1_PATH")
    @Expose
    private String iMAGE1PATH;
    @SerializedName("IMAGE2_PATH ")
    @Expose
    private String iMAGE2PATH;
    @SerializedName("IMAGE3_PATH")
    @Expose
    private String iMAGE3PATH;
    @SerializedName("IMAGE4_PATH ")
    @Expose
    private String iMAGE4PATH;
    @SerializedName("IMAGE5_PATH")
    @Expose
    private String iMAGE5PATH;
    @SerializedName("REMARKS ")
    @Expose
    private String rEMARKS;
    @SerializedName("CREATED_BY")
    @Expose
    private String cREATEDBY;
    @SerializedName("TOKEN_ID")
    @Expose
    private String tOKENID;
    @SerializedName("OFFICER_TYPE")
    @Expose
    private String oFFICERTYPE;
    @SerializedName("IP_ADDRESS")
    @Expose
    private String iPADDRESS;
    
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<L1SubmitRequest> CREATOR = new Creator<L1SubmitRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public L1SubmitRequest createFromParcel(Parcel in) {
            return new L1SubmitRequest(in);
        }

        public L1SubmitRequest[] newArray(int size) {
            return (new L1SubmitRequest[size]);
        }

    }
            ;
    private final static long serialVersionUID = 2290643554466095207L;

    protected L1SubmitRequest(Parcel in) {
        this.oBJECTIONABLELANDS = ((String) in.readValue((String.class.getClassLoader())));
        this.pROHIBITORYLANDS = ((String) in.readValue((String.class.getClassLoader())));
        this.oPENSPACEHTLINE = ((String) in.readValue((String.class.getClassLoader())));
        this.lOCALITYAFFECTEDBYMP = ((String) in.readValue((String.class.getClassLoader())));
        this.oPENSPACE10PERCENT = ((String) in.readValue((String.class.getClassLoader())));
        this.lANDUSEASPERMAPLAN = ((String) in.readValue((String.class.getClassLoader())));
        this.lEGALDISPUTES = ((String) in.readValue((String.class.getClassLoader())));
        this.aPPLSAPPROVED = ((String) in.readValue((String.class.getClassLoader())));
        this.aPPLSSHORTFALL = ((String) in.readValue((String.class.getClassLoader())));
        this.aPPLSREJECTED = ((String) in.readValue((String.class.getClassLoader())));
        this.iMAGE1PATH = ((String) in.readValue((String.class.getClassLoader())));
        this.iMAGE2PATH = ((String) in.readValue((String.class.getClassLoader())));
        this.iMAGE3PATH = ((String) in.readValue((String.class.getClassLoader())));
        this.iMAGE4PATH = ((String) in.readValue((String.class.getClassLoader())));
        this.iMAGE5PATH = ((String) in.readValue((String.class.getClassLoader())));
        this.rEMARKS = ((String) in.readValue((String.class.getClassLoader())));
        this.cREATEDBY = ((String) in.readValue((String.class.getClassLoader())));
        this.tOKENID = ((String) in.readValue((String.class.getClassLoader())));
        this.oFFICERTYPE = ((String) in.readValue((String.class.getClassLoader())));
        this.iPADDRESS = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public L1SubmitRequest() {
    }

    @SerializedName("OBJECTIONABLE_LANDS")
    public String getOBJECTIONABLELANDS() {
        return oBJECTIONABLELANDS;
    }

    @SerializedName("OBJECTIONABLE_LANDS")
    public void setOBJECTIONABLELANDS(String oBJECTIONABLELANDS) {
        this.oBJECTIONABLELANDS = oBJECTIONABLELANDS;
    }

    @SerializedName("PROHIBITORY_LANDS ")
    public String getPROHIBITORYLANDS() {
        return pROHIBITORYLANDS;
    }

    @SerializedName("PROHIBITORY_LANDS ")
    public void setPROHIBITORYLANDS(String pROHIBITORYLANDS) {
        this.pROHIBITORYLANDS = pROHIBITORYLANDS;
    }

    @SerializedName("OPENSPACE_HTLINE")
    public String getOPENSPACEHTLINE() {
        return oPENSPACEHTLINE;
    }

    @SerializedName("OPENSPACE_HTLINE")
    public void setOPENSPACEHTLINE(String oPENSPACEHTLINE) {
        this.oPENSPACEHTLINE = oPENSPACEHTLINE;
    }

    @SerializedName("LOCALITY_AFFECTEDBY_MP ")
    public String getLOCALITYAFFECTEDBYMP() {
        return lOCALITYAFFECTEDBYMP;
    }

    @SerializedName("LOCALITY_AFFECTEDBY_MP ")
    public void setLOCALITYAFFECTEDBYMP(String lOCALITYAFFECTEDBYMP) {
        this.lOCALITYAFFECTEDBYMP = lOCALITYAFFECTEDBYMP;
    }

    @SerializedName("OPEN_SPACE_10PERCENT")
    public String getOPENSPACE10PERCENT() {
        return oPENSPACE10PERCENT;
    }

    @SerializedName("OPEN_SPACE_10PERCENT")
    public void setOPENSPACE10PERCENT(String oPENSPACE10PERCENT) {
        this.oPENSPACE10PERCENT = oPENSPACE10PERCENT;
    }

    @SerializedName("LAND_USE_ASPER_MAPLAN")
    public String getLANDUSEASPERMAPLAN() {
        return lANDUSEASPERMAPLAN;
    }

    @SerializedName("LAND_USE_ASPER_MAPLAN")
    public void setLANDUSEASPERMAPLAN(String lANDUSEASPERMAPLAN) {
        this.lANDUSEASPERMAPLAN = lANDUSEASPERMAPLAN;
    }

    @SerializedName("LEGAL_DISPUTES")
    public String getLEGALDISPUTES() {
        return lEGALDISPUTES;
    }

    @SerializedName("LEGAL_DISPUTES")
    public void setLEGALDISPUTES(String lEGALDISPUTES) {
        this.lEGALDISPUTES = lEGALDISPUTES;
    }

    @SerializedName("APPLS_APPROVED ")
    public String getAPPLSAPPROVED() {
        return aPPLSAPPROVED;
    }

    @SerializedName("APPLS_APPROVED ")
    public void setAPPLSAPPROVED(String aPPLSAPPROVED) {
        this.aPPLSAPPROVED = aPPLSAPPROVED;
    }

    @SerializedName("APPLS_SHORTFALL")
    public String getAPPLSSHORTFALL() {
        return aPPLSSHORTFALL;
    }

    @SerializedName("APPLS_SHORTFALL")
    public void setAPPLSSHORTFALL(String aPPLSSHORTFALL) {
        this.aPPLSSHORTFALL = aPPLSSHORTFALL;
    }

    @SerializedName("APPLS_REJECTED ")
    public String getAPPLSREJECTED() {
        return aPPLSREJECTED;
    }

    @SerializedName("APPLS_REJECTED ")
    public void setAPPLSREJECTED(String aPPLSREJECTED) {
        this.aPPLSREJECTED = aPPLSREJECTED;
    }

    @SerializedName("IMAGE1_PATH")
    public String getIMAGE1PATH() {
        return iMAGE1PATH;
    }

    @SerializedName("IMAGE1_PATH")
    public void setIMAGE1PATH(String iMAGE1PATH) {
        this.iMAGE1PATH = iMAGE1PATH;
    }

    @SerializedName("IMAGE2_PATH ")
    public String getIMAGE2PATH() {
        return iMAGE2PATH;
    }

    @SerializedName("IMAGE2_PATH ")
    public void setIMAGE2PATH(String iMAGE2PATH) {
        this.iMAGE2PATH = iMAGE2PATH;
    }

    @SerializedName("IMAGE3_PATH")
    public String getIMAGE3PATH() {
        return iMAGE3PATH;
    }

    @SerializedName("IMAGE3_PATH")
    public void setIMAGE3PATH(String iMAGE3PATH) {
        this.iMAGE3PATH = iMAGE3PATH;
    }

    @SerializedName("IMAGE4_PATH ")
    public String getIMAGE4PATH() {
        return iMAGE4PATH;
    }

    @SerializedName("IMAGE4_PATH ")
    public void setIMAGE4PATH(String iMAGE4PATH) {
        this.iMAGE4PATH = iMAGE4PATH;
    }

    @SerializedName("IMAGE5_PATH")
    public String getIMAGE5PATH() {
        return iMAGE5PATH;
    }

    @SerializedName("IMAGE5_PATH")
    public void setIMAGE5PATH(String iMAGE5PATH) {
        this.iMAGE5PATH = iMAGE5PATH;
    }

    @SerializedName("REMARKS ")
    public String getREMARKS() {
        return rEMARKS;
    }

    @SerializedName("REMARKS ")
    public void setREMARKS(String rEMARKS) {
        this.rEMARKS = rEMARKS;
    }

    @SerializedName("CREATED_BY")
    public String getCREATEDBY() {
        return cREATEDBY;
    }

    @SerializedName("CREATED_BY")
    public void setCREATEDBY(String cREATEDBY) {
        this.cREATEDBY = cREATEDBY;
    }

    @SerializedName("TOKEN_ID")
    public String getTOKENID() {
        return tOKENID;
    }

    @SerializedName("TOKEN_ID")
    public void setTOKENID(String tOKENID) {
        this.tOKENID = tOKENID;
    }

    @SerializedName("OFFICER_TYPE")
    public String getOFFICERTYPE() {
        return oFFICERTYPE;
    }

    @SerializedName("OFFICER_TYPE")
    public void setOFFICERTYPE(String oFFICERTYPE) {
        this.oFFICERTYPE = oFFICERTYPE;
    }

    @SerializedName("IP_ADDRESS")
    public String getIPADDRESS() {
        return iPADDRESS;
    }

    @SerializedName("IP_ADDRESS")
    public void setIPADDRESS(String iPADDRESS) {
        this.iPADDRESS = iPADDRESS;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(oBJECTIONABLELANDS);
        dest.writeValue(pROHIBITORYLANDS);
        dest.writeValue(oPENSPACEHTLINE);
        dest.writeValue(lOCALITYAFFECTEDBYMP);
        dest.writeValue(oPENSPACE10PERCENT);
        dest.writeValue(lANDUSEASPERMAPLAN);
        dest.writeValue(lEGALDISPUTES);
        dest.writeValue(aPPLSAPPROVED);
        dest.writeValue(aPPLSSHORTFALL);
        dest.writeValue(aPPLSREJECTED);
        dest.writeValue(iMAGE1PATH);
        dest.writeValue(iMAGE2PATH);
        dest.writeValue(iMAGE3PATH);
        dest.writeValue(iMAGE4PATH);
        dest.writeValue(iMAGE5PATH);
        dest.writeValue(rEMARKS);
        dest.writeValue(cREATEDBY);
        dest.writeValue(tOKENID);
        dest.writeValue(oFFICERTYPE);
        dest.writeValue(iPADDRESS);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}