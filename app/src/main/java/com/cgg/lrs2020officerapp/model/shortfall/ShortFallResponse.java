package com.cgg.lrs2020officerapp.model.shortfall;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShortFallResponse implements Serializable, Parcelable
{

    @SerializedName("Status_Code")
    @Expose
    private String statusCode;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("Shortfall_List")
    @Expose
    private List<ShortfallList> shortfallList = null;
    public final static Parcelable.Creator<ShortFallResponse> CREATOR = new Creator<ShortFallResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ShortFallResponse createFromParcel(Parcel in) {
            return new ShortFallResponse(in);
        }

        public ShortFallResponse[] newArray(int size) {
            return (new ShortFallResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6498177270454102666L;

    protected ShortFallResponse(Parcel in) {
        this.statusCode = ((String) in.readValue((String.class.getClassLoader())));
        this.statusMessage = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.shortfallList, (ShortfallList.class.getClassLoader()));
    }

    public ShortFallResponse() {
    }

    @SerializedName("Status_Code")
    public String getStatusCode() {
        return statusCode;
    }

    @SerializedName("Status_Code")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @SerializedName("Status_Message")
    public String getStatusMessage() {
        return statusMessage;
    }

    @SerializedName("Status_Message")
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @SerializedName("Shortfall_List")
    public List<ShortfallList> getShortfallList() {
        return shortfallList;
    }

    @SerializedName("Shortfall_List")
    public void setShortfallList(List<ShortfallList> shortfallList) {
        this.shortfallList = shortfallList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(statusCode);
        dest.writeValue(statusMessage);
        dest.writeList(shortfallList);
    }

    public int describeContents() {
        return 0;
    }

}

