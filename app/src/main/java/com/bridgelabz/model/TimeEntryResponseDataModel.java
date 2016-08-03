
package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TimeEntryResponseDataModel implements Serializable {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("inTime")
    @Expose
    private String inTime;
    @SerializedName("outTime")
    @Expose
    private String outTime;
    @SerializedName("totalTime")
    @Expose
    private String totalTime;

    @SerializedName("type")
    @Expose
    private String attendance;

    public TimeEntryResponseDataModel(String userId, String inTime, String outTime, String totalTime, String attendance) {
        this.userId = userId;
        this.inTime = inTime;
        this.outTime = outTime;
        this.totalTime = totalTime;
        this.attendance = attendance;
    }

    public TimeEntryResponseDataModel(String userId, String inTime, String outTime, String totalTime) {

    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

}
