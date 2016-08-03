
package com.bridgelabz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TimeEntryResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private TimeEntryResponseDataModel timeEntryResponseDataModel;

    private boolean status;

    public TimeEntryResponse(TimeEntryResponseDataModel timeEntryResponseDataModel, boolean status) {
        this.timeEntryResponseDataModel = timeEntryResponseDataModel;
        this.status = status;
    }
    public TimeEntryResponseDataModel getTimeEntryResponseDataModel() {
        return timeEntryResponseDataModel;
    }

    public void setTimeEntryResponseDataModel(TimeEntryResponseDataModel timeEntryResponseDataModel) {
        this.timeEntryResponseDataModel = timeEntryResponseDataModel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
