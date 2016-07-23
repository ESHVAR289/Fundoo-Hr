package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AttendaceDetailsData {
    @SerializedName("inTime")
    @Expose
    private String inTime;
    @SerializedName("outTime")
    @Expose
    private String outTime;
    @SerializedName("totalTime")
    @Expose
    private String totalTime;
    @SerializedName("date")
    @Expose
    private String date;

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
