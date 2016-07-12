package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bridgeit007 on 12/7/16.
 */

public class MessageData {
    @SerializedName("data")
    @Expose
    boolean data;

    public MessageData(boolean data) {
        this.data = data;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
