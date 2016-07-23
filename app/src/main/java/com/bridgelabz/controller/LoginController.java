package com.bridgelabz.controller;

import com.bridgelabz.callback.LoginCallbackListener;
import com.bridgelabz.model.MobileNoOtpResponse;
import com.bridgelabz.model.MobileOtpPostDataModel;
import com.bridgelabz.restservice.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginController {
    private LoginCallbackListener mLoginCallbackListener;
    private Retrofit mRetrofit;

    public LoginController(LoginCallbackListener loginCallbackListener, Retrofit retrofit) {
        this.mLoginCallbackListener = loginCallbackListener;
        this.mRetrofit = retrofit;
    }

    public void getTheOtp(String mo_number) {
        Call<MobileNoOtpResponse> mobileNoOtpGson = mRetrofit.create(RestApi.class).getMobileNoStatus(new MobileOtpPostDataModel(mo_number));
        mobileNoOtpGson.enqueue(new Callback<MobileNoOtpResponse>() {
            @Override
            public void onResponse(Call<MobileNoOtpResponse> call, Response<MobileNoOtpResponse> response) {
                if (response.body().getStatus()) {
                    mLoginCallbackListener.mobileNoResponse(response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<MobileNoOtpResponse> call, Throwable t) {
                mLoginCallbackListener.onFailureMobileNoResponse();
            }
        });
    }

    public void getOtpConfirmation(String mo_number, String otp) {
        Call<MobileNoOtpResponse> otpGson = mRetrofit.create(RestApi.class).getOtpStatus(new MobileOtpPostDataModel(mo_number, otp));
        otpGson.enqueue(new Callback<MobileNoOtpResponse>() {
            @Override
            public void onResponse(Call<MobileNoOtpResponse> call, Response<MobileNoOtpResponse> response) {
                mLoginCallbackListener.checkForOtpConfirmation(response.body().getStatus());
            }

            @Override
            public void onFailure(Call<MobileNoOtpResponse> call, Throwable t) {
                mLoginCallbackListener.onFailureOtpResponse();
            }
        });
    }
}
