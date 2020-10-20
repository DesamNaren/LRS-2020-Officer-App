package com.cgg.lrs2020officerapp.model.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class L1SubmitRequest {

    @SerializedName("P_IP_ADDRESS")
    @Expose
    private String pIPADDRESS;
    @SerializedName("P_OFFICER_TYPE")
    @Expose
    private String pOFFICERTYPE;
    @SerializedName("P_EMPLOYEE_ID")
    @Expose
    private String pEMPLOYEEID;
    @SerializedName("P_CREATED_BY")
    @Expose
    private String pCREATEDBY;
    @SerializedName("P_OTPNO")
    @Expose
    private String pOTPNO;
    @SerializedName("P_REMARKS")
    @Expose
    private String pREMARKS;
    @SerializedName("p_RECOMMENDED_FOR")
    @Expose
    private String pRECOMMENDEDFOR;
    @SerializedName("P_IMAGE4_PATH")
    @Expose
    private String pIMAGE4PATH;
    @SerializedName("P_IMAGE3_PATH")
    @Expose
    private String pIMAGE3PATH;
    @SerializedName("P_IMAGE2_PATH")
    @Expose
    private String pIMAGE2PATH;
    @SerializedName("P_IMAGE1_PATH")
    @Expose
    private String pIMAGE1PATH;
    @SerializedName("P_PLAN_PATH")
    @Expose
    private String pPLANPATH;
    @SerializedName("P_SRO_DOC_LINK")
    @Expose
    private String pSRODOCLINK;
    @SerializedName("P_EX_FILE_PATH")
    @Expose
    private String pEXFILEPATH;
    @SerializedName("P_LAND_USE_ID")
    @Expose
    private String pLANDUSEID;
    @SerializedName("P_PLOT_OBJECTON")
    @Expose
    private String pPLOTOBJECTON;
    @SerializedName("P_MVASONDATE")
    @Expose
    private String pMVASONDATE;
    @SerializedName("P_MARKET_VALUE")
    @Expose
    private String pMARKETVALUE;
    @SerializedName("P_ROAD_NORTH")
    @Expose
    private String pROADNORTH;
    @SerializedName("P_ROAD_SOUTH")
    @Expose
    private String pROADSOUTH;
    @SerializedName("P_ROAD_WEST")
    @Expose
    private String pROADWEST;
    @SerializedName("P_ROAD_EAST")
    @Expose
    private String pROADEAST;
    @SerializedName("P_ROAD_DETAILS")
    @Expose
    private String pROADDETAILS;
    @SerializedName("P_AREA_MATCH")
    @Expose
    private String pAREAMATCH;
    @SerializedName("P_NAME_MATCH")
    @Expose
    private String pNAMEMATCH;
    @SerializedName("P_PRIDate")
    @Expose
    private String pPRIDate;
    @SerializedName("P_APPLICANT_ID")
    @Expose
    private String pAPPLICANTID;

    public String getPIPADDRESS() {
        return pIPADDRESS;
    }

    public void setPIPADDRESS(String pIPADDRESS) {
        this.pIPADDRESS = pIPADDRESS;
    }

    public String getPOFFICERTYPE() {
        return pOFFICERTYPE;
    }

    public void setPOFFICERTYPE(String pOFFICERTYPE) {
        this.pOFFICERTYPE = pOFFICERTYPE;
    }

    public String getPEMPLOYEEID() {
        return pEMPLOYEEID;
    }

    public void setPEMPLOYEEID(String pEMPLOYEEID) {
        this.pEMPLOYEEID = pEMPLOYEEID;
    }

    public String getPCREATEDBY() {
        return pCREATEDBY;
    }

    public void setPCREATEDBY(String pCREATEDBY) {
        this.pCREATEDBY = pCREATEDBY;
    }

    public String getPOTPNO() {
        return pOTPNO;
    }

    public void setPOTPNO(String pOTPNO) {
        this.pOTPNO = pOTPNO;
    }

    public String getPREMARKS() {
        return pREMARKS;
    }

    public void setPREMARKS(String pREMARKS) {
        this.pREMARKS = pREMARKS;
    }

    public String getPRECOMMENDEDFOR() {
        return pRECOMMENDEDFOR;
    }

    public void setPRECOMMENDEDFOR(String pRECOMMENDEDFOR) {
        this.pRECOMMENDEDFOR = pRECOMMENDEDFOR;
    }

    public String getPIMAGE4PATH() {
        return pIMAGE4PATH;
    }

    public void setPIMAGE4PATH(String pIMAGE4PATH) {
        this.pIMAGE4PATH = pIMAGE4PATH;
    }

    public String getPIMAGE3PATH() {
        return pIMAGE3PATH;
    }

    public void setPIMAGE3PATH(String pIMAGE3PATH) {
        this.pIMAGE3PATH = pIMAGE3PATH;
    }

    public String getPIMAGE2PATH() {
        return pIMAGE2PATH;
    }

    public void setPIMAGE2PATH(String pIMAGE2PATH) {
        this.pIMAGE2PATH = pIMAGE2PATH;
    }

    public String getPIMAGE1PATH() {
        return pIMAGE1PATH;
    }

    public void setPIMAGE1PATH(String pIMAGE1PATH) {
        this.pIMAGE1PATH = pIMAGE1PATH;
    }

    public String getPPLANPATH() {
        return pPLANPATH;
    }

    public void setPPLANPATH(String pPLANPATH) {
        this.pPLANPATH = pPLANPATH;
    }

    public String getPSRODOCLINK() {
        return pSRODOCLINK;
    }

    public void setPSRODOCLINK(String pSRODOCLINK) {
        this.pSRODOCLINK = pSRODOCLINK;
    }

    public String getPEXFILEPATH() {
        return pEXFILEPATH;
    }

    public void setPEXFILEPATH(String pEXFILEPATH) {
        this.pEXFILEPATH = pEXFILEPATH;
    }

    public String getPLANDUSEID() {
        return pLANDUSEID;
    }

    public void setPLANDUSEID(String pLANDUSEID) {
        this.pLANDUSEID = pLANDUSEID;
    }

    public String getPPLOTOBJECTON() {
        return pPLOTOBJECTON;
    }

    public void setPPLOTOBJECTON(String pPLOTOBJECTON) {
        this.pPLOTOBJECTON = pPLOTOBJECTON;
    }

    public String getPMVASONDATE() {
        return pMVASONDATE;
    }

    public void setPMVASONDATE(String pMVASONDATE) {
        this.pMVASONDATE = pMVASONDATE;
    }

    public String getPMARKETVALUE() {
        return pMARKETVALUE;
    }

    public void setPMARKETVALUE(String pMARKETVALUE) {
        this.pMARKETVALUE = pMARKETVALUE;
    }

    public String getPROADNORTH() {
        return pROADNORTH;
    }

    public void setPROADNORTH(String pROADNORTH) {
        this.pROADNORTH = pROADNORTH;
    }

    public String getPROADSOUTH() {
        return pROADSOUTH;
    }

    public void setPROADSOUTH(String pROADSOUTH) {
        this.pROADSOUTH = pROADSOUTH;
    }

    public String getPROADWEST() {
        return pROADWEST;
    }

    public void setPROADWEST(String pROADWEST) {
        this.pROADWEST = pROADWEST;
    }

    public String getPROADEAST() {
        return pROADEAST;
    }

    public void setPROADEAST(String pROADEAST) {
        this.pROADEAST = pROADEAST;
    }

    public String getPROADDETAILS() {
        return pROADDETAILS;
    }

    public void setPROADDETAILS(String pROADDETAILS) {
        this.pROADDETAILS = pROADDETAILS;
    }

    public String getPAREAMATCH() {
        return pAREAMATCH;
    }

    public void setPAREAMATCH(String pAREAMATCH) {
        this.pAREAMATCH = pAREAMATCH;
    }

    public String getPNAMEMATCH() {
        return pNAMEMATCH;
    }

    public void setPNAMEMATCH(String pNAMEMATCH) {
        this.pNAMEMATCH = pNAMEMATCH;
    }

    public String getPPRIDate() {
        return pPRIDate;
    }

    public void setPPRIDate(String pPRIDate) {
        this.pPRIDate = pPRIDate;
    }

    public String getPAPPLICANTID() {
        return pAPPLICANTID;
    }

    public void setPAPPLICANTID(String pAPPLICANTID) {
        this.pAPPLICANTID = pAPPLICANTID;
    }

}
