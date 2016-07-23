package com.bridgelabz.model;

public class TimeEntryPostMessageModel {
    String mobile;
    String message;

    public TimeEntryPostMessageModel(String mobile, String message) {
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
