
package com.bridgelabz.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Data {

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

    public Data(String userId, String inTime, String outTime, String totalTime) {
        this.userId = userId;
        this.inTime = inTime;
        this.outTime = outTime;
        this.totalTime = totalTime;
    }

    /**
     *
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     *     The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     *     The inTime
     */
    public String getInTime() {
        return inTime;
    }

    /**
     *
     * @param inTime
     *     The inTime
     */
    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    /**
     *
     * @return
     *     The outTime
     */
    public String getOutTime() {
        return outTime;
    }

    /**
     *
     * @param outTime
     *     The outTime
     */
    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    /**
     *
     * @return
     *     The totalTime
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     *
     * @param totalTime
     *     The totalTime
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

}
