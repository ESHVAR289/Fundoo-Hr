package com.bridgelabz.model;

/**
 * Created by bridgeit007 on 1/7/16.
 */

public class MobileAndOtpModel {
    String mobile;
    String otp;
    String inTime;
    String outTime;
    Boolean status;

    public MobileAndOtpModel(String mobile) {
        this.mobile = mobile;
    }

    public MobileAndOtpModel(String mobile, String otp) {
        this.mobile = mobile;
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
