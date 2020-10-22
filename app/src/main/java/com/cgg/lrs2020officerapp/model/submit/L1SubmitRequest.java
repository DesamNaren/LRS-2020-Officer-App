package com.cgg.lrs2020officerapp.model.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class L1SubmitRequest {

    @SerializedName("OBJECTIONABLE_LANDS")
    @Expose
    private String oBJECTIONABLELANDS;
    @SerializedName("PROHIBITORY_LANDS")
    @Expose
    private String pROHIBITORYLANDS;
    @SerializedName("OPENSPACE_HTLINE")
    @Expose
    private String oPENSPACEHTLINE;
    @SerializedName("LOCALITY_AFFECTEDBY_MP")
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
    @SerializedName("APPLS_APPROVED")
    @Expose
    private String aPPLSAPPROVED;
    @SerializedName("APPLS_SHORTFALL")
    @Expose
    private String aPPLSSHORTFALL;
    @SerializedName("APPLS_REJECTED")
    @Expose
    private String aPPLSREJECTED;
    @SerializedName("IMAGE1_PATH")
    @Expose
    private String iMAGE1PATH;
    @SerializedName("IMAGE2_PATH")
    @Expose
    private String iMAGE2PATH;
    @SerializedName("IMAGE3_PATH")
    @Expose
    private String iMAGE3PATH;
    @SerializedName("IMAGE4_PATH")
    @Expose
    private String iMAGE4PATH;
    @SerializedName("IMAGE5_PATH")
    @Expose
    private String iMAGE5PATH;
    @SerializedName("REMARKS")
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
    @SerializedName("IPADDRESS")
    @Expose
    private String iPADDRESS;

    @SerializedName("OPEN_SPACE_10PERCENT_NO_TEXT")
    @Expose
    private String OPEN_SPACE_10PERCENT_NO_TEXT;

    @SerializedName("LOCALITY_AFFECTEDBY_MP_OPTIONS")
    @Expose
    private String LOCALITY_AFFECTEDBY_MP_OPTIONS;

    @SerializedName("LRS_PERMIT")
    @Expose
    private String LRS_PERMIT;


    public String getOBJECTIONABLELANDS() {
        return oBJECTIONABLELANDS;
    }

    public void setOBJECTIONABLELANDS(String oBJECTIONABLELANDS) {
        this.oBJECTIONABLELANDS = oBJECTIONABLELANDS;
    }

    public String getPROHIBITORYLANDS() {
        return pROHIBITORYLANDS;
    }

    public void setPROHIBITORYLANDS(String pROHIBITORYLANDS) {
        this.pROHIBITORYLANDS = pROHIBITORYLANDS;
    }

    public String getOPENSPACEHTLINE() {
        return oPENSPACEHTLINE;
    }

    public void setOPENSPACEHTLINE(String oPENSPACEHTLINE) {
        this.oPENSPACEHTLINE = oPENSPACEHTLINE;
    }

    public String getLOCALITYAFFECTEDBYMP() {
        return lOCALITYAFFECTEDBYMP;
    }

    public void setLOCALITYAFFECTEDBYMP(String lOCALITYAFFECTEDBYMP) {
        this.lOCALITYAFFECTEDBYMP = lOCALITYAFFECTEDBYMP;
    }

    public String getOPENSPACE10PERCENT() {
        return oPENSPACE10PERCENT;
    }

    public void setOPENSPACE10PERCENT(String oPENSPACE10PERCENT) {
        this.oPENSPACE10PERCENT = oPENSPACE10PERCENT;
    }

    public String getLANDUSEASPERMAPLAN() {
        return lANDUSEASPERMAPLAN;
    }

    public void setLANDUSEASPERMAPLAN(String lANDUSEASPERMAPLAN) {
        this.lANDUSEASPERMAPLAN = lANDUSEASPERMAPLAN;
    }

    public String getLEGALDISPUTES() {
        return lEGALDISPUTES;
    }

    public void setLEGALDISPUTES(String lEGALDISPUTES) {
        this.lEGALDISPUTES = lEGALDISPUTES;
    }

    public String getAPPLSAPPROVED() {
        return aPPLSAPPROVED;
    }

    public void setAPPLSAPPROVED(String aPPLSAPPROVED) {
        this.aPPLSAPPROVED = aPPLSAPPROVED;
    }

    public String getAPPLSSHORTFALL() {
        return aPPLSSHORTFALL;
    }

    public void setAPPLSSHORTFALL(String aPPLSSHORTFALL) {
        this.aPPLSSHORTFALL = aPPLSSHORTFALL;
    }

    public String getAPPLSREJECTED() {
        return aPPLSREJECTED;
    }

    public void setAPPLSREJECTED(String aPPLSREJECTED) {
        this.aPPLSREJECTED = aPPLSREJECTED;
    }

    public String getIMAGE1PATH() {
        return iMAGE1PATH;
    }

    public void setIMAGE1PATH(String iMAGE1PATH) {
        this.iMAGE1PATH = iMAGE1PATH;
    }

    public String getIMAGE2PATH() {
        return iMAGE2PATH;
    }

    public void setIMAGE2PATH(String iMAGE2PATH) {
        this.iMAGE2PATH = iMAGE2PATH;
    }

    public String getIMAGE3PATH() {
        return iMAGE3PATH;
    }

    public void setIMAGE3PATH(String iMAGE3PATH) {
        this.iMAGE3PATH = iMAGE3PATH;
    }

    public String getIMAGE4PATH() {
        return iMAGE4PATH;
    }

    public void setIMAGE4PATH(String iMAGE4PATH) {
        this.iMAGE4PATH = iMAGE4PATH;
    }

    public String getIMAGE5PATH() {
        return iMAGE5PATH;
    }

    public void setIMAGE5PATH(String iMAGE5PATH) {
        this.iMAGE5PATH = iMAGE5PATH;
    }

    public String getREMARKS() {
        return rEMARKS;
    }

    public void setREMARKS(String rEMARKS) {
        this.rEMARKS = rEMARKS;
    }

    public String getCREATEDBY() {
        return cREATEDBY;
    }

    public void setCREATEDBY(String cREATEDBY) {
        this.cREATEDBY = cREATEDBY;
    }

    public String getTOKENID() {
        return tOKENID;
    }

    public void setTOKENID(String tOKENID) {
        this.tOKENID = tOKENID;
    }

    public String getOFFICERTYPE() {
        return oFFICERTYPE;
    }

    public void setOFFICERTYPE(String oFFICERTYPE) {
        this.oFFICERTYPE = oFFICERTYPE;
    }

    public String getIPADDRESS() {
        return iPADDRESS;
    }

    public void setIPADDRESS(String iPADDRESS) {
        this.iPADDRESS = iPADDRESS;
    }

    public String getLRS_PERMIT() {
        return LRS_PERMIT;
    }

    public void setLRS_PERMIT(String LRS_PERMIT) {
        this.LRS_PERMIT = LRS_PERMIT;
    }

    public String getLOCALITY_AFFECTEDBY_MP_OPTIONS() {
        return LOCALITY_AFFECTEDBY_MP_OPTIONS;
    }

    public void setLOCALITY_AFFECTEDBY_MP_OPTIONS(String LOCALITY_AFFECTEDBY_MP_OPTIONS) {
        this.LOCALITY_AFFECTEDBY_MP_OPTIONS = LOCALITY_AFFECTEDBY_MP_OPTIONS;
    }

    public String getOPEN_SPACE_10PERCENT_NO_TEXT() {
        return OPEN_SPACE_10PERCENT_NO_TEXT;
    }

    public void setOPEN_SPACE_10PERCENT_NO_TEXT(String OPEN_SPACE_10PERCENT_NO_TEXT) {
        this.OPEN_SPACE_10PERCENT_NO_TEXT = OPEN_SPACE_10PERCENT_NO_TEXT;
    }
}