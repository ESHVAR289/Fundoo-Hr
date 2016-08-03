package com.bridgelabz.controller;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bridgelabz.callback.LoginCallbackListener;
import com.bridgelabz.dagger.AppController;
import com.bridgelabz.model.MobileNoOtpResponse;
import com.bridgelabz.model.MobileOtpPostDataModel;
import com.bridgelabz.restservice.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.bridgelabz.util.Constants.LOGIN_URL;
import static com.bridgelabz.util.Constants.TAG_REQ;

public class LoginController {
    private static String TAG = LoginController.class.getSimpleName();
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

    public void checkLoginDetails(final String mo_number, final String loginPassword) {

        StringRequest loginRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response" + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("data").equals("Successfully Login")) {
                        mLoginCallbackListener.loginResponse();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        mLoginCallbackListener.loginErrorResponse(jsonObject.getString("err"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Registration Error" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> loginParams = new HashMap<>();
                loginParams.put("mobile", mo_number);
                loginParams.put("password", loginPassword);
                Log.i(TAG, "getParams: " + mo_number + " " + loginPassword);
                return loginParams;
            }
        };

        //Adding the request to the request que
        AppController.getInstance().addToRequestQueue(loginRequest, TAG_REQ);
    }
}
