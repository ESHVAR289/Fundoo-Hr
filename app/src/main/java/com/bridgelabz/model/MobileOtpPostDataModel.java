package com.bridgelabz.model;

public class MobileOtpPostDataModel {
    String mobile;
    String otp;

    public MobileOtpPostDataModel(String mobile) {
        this.mobile = mobile;
    }

    public MobileOtpPostDataModel(String mobile, String otp) {
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
