package com.bridgelabz.model;

/**
 * Created by bridgeit007 on 14/7/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AttendanceDataModel {

    @SerializedName("Sr_No")
    @Expose
    private String srNo;
    @SerializedName("InTime")
    @Expose
    private String inTime;
    @SerializedName("OutTime")
    @Expose
    private String outTime;
    @SerializedName("TotalTime")
    @Expose
    private String totalTime;
    @SerializedName("Date")
    @Expose
    private String date;

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

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