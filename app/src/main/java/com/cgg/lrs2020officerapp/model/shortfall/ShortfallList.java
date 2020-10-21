package com.cgg.lrs2020officerapp.model.shortfall;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShortfallList implements Serializable, Parcelable
{

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("NAME")
    @Expose
    private String nAME;
    @SerializedName("Status")
    @Expose
    private String status;
    public final static Parcelable.Creator<ShortfallList> CREATOR = new Creator<ShortfallList>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ShortfallList createFromParcel(Parcel in) {
            return new ShortfallList(in);
        }

        public ShortfallList[] newArray(int size) {
            return (new ShortfallList[size]);
        }

    }
            ;
    private final static long serialVersionUID = 1795719923643468509L;

    protected ShortfallList(Parcel in) {
        this.iD = ((String) in.readValue((String.class.getClassLoader())));
        this.nAME = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ShortfallList() {
    }

    @SerializedName("ID")
    public String getID() {
        return iD;
    }

    @SerializedName("ID")
    public void setID(String iD) {
        this.iD = iD;
    }

    @SerializedName("NAME")
    public String getNAME() {
        return nAME;
    }

    @SerializedName("NAME")
    public void setNAME(String nAME) {
        this.nAME = nAME;
    }

    @SerializedName("Status")
    public String getStatus() {
        return status;
    }

    @SerializedName("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(nAME);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

}
