package com.bridgelabz.model;

/**
 * Created by bridgeit007 on 8/7/16.
 */

public class TimeEntryMessage {
    String mobile;
    String message;

    public TimeEntryMessage(String mobile, String message) {
        this.mobile = mobile;
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
