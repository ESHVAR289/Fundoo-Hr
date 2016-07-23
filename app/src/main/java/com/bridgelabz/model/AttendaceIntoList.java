package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AttendaceIntoList {
    @SerializedName("result")
    @Expose
    private List<AttendanceDataModel> result = new ArrayList<AttendanceDataModel>();

    public List<AttendanceDataModel> getResult() {
        return result;
    }

    public void setResult(List<AttendanceDataModel> result) {
        this.result = result;
    }

}
