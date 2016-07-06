package com.bridgelabz.model;

/**
 * Created by bridgeit007 on 1/7/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileNoOtpGson {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     * @return The data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

}