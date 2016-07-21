package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgeit007 on 15/7/16.
 */

public class AttendaceIntoList {
    public List<AttendanceDataModel> getResult() {
        return result;
    }

    public void setResult(List<AttendanceDataModel> result) {
        this.result = result;
    }

    @SerializedName("result")
    @Expose
    private List<AttendanceDataModel> result = new ArrayList<AttendanceDataModel>();

}
