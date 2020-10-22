package com.cgg.lrs2020officerapp.model.l1ScrutinyCheckList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L1ScrutinityResponse implements Serializable, Parcelable
{

    @SerializedName("Status_Code")
    @Expose
    private String statusCode;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("CheckList")
    @Expose
    private List<CheckList> checkList = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<L1ScrutinityResponse> CREATOR = new Creator<L1ScrutinityResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public L1ScrutinityResponse createFromParcel(Parcel in) {
            return new L1ScrutinityResponse(in);
        }

        public L1ScrutinityResponse[] newArray(int size) {
            return (new L1ScrutinityResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4755259964344181914L;

    protected L1ScrutinityResponse(Parcel in) {
        this.statusCode = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.checkList, (CheckList.class.getClassLoader()));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public L1ScrutinityResponse() {
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

    public List<CheckList> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckList> checkList) {
        this.checkList = checkList;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(statusCode);
        dest.writeValue(statusMessage);
        dest.writeList(checkList);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
