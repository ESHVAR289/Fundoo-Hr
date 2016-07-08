package com.bridgelabz.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bridgelabz.FundooHrLoginActivity;
import com.bridgelabz.model.MobileAndOtpModel;
import com.bridgelabz.model.MobileNoOtpGson;
import com.bridgelabz.restservice.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bridgeit007 on 6/7/16.
 */

public class HttpService extends IntentService {
    @Inject
    Retrofit retrofit;
    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            String mobile = intent.getStringExtra("mobile");
            verifyOtp(otp, mobile);
        }
    }

    private void verifyOtp(final String otp, final String mobile) {
        Call<MobileNoOtpGson> otpGson = retrofit.create(RestApi.class).getOtpStatus(new MobileAndOtpModel(mobile, otp));
        otpGson.enqueue(new Callback<MobileNoOtpGson>() {
            @Override
            public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.i(TAG, "onResponse: " + response.body().toString());
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        startActivity(new Intent(getApplicationContext(), FundooHrLoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
