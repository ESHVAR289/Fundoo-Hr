package com.bridgelabz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bridgelabz.model.MobileAndOtpModel;
import com.bridgelabz.model.MobileNoOtpGson;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FundooHrOtpActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    private static final String TAG = FundooHrOtpActivity.class.getSimpleName();
    EditText etOtp;
    Button btnSubmit;
    String mobile_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundoo_hr_otp);
        etOtp = (EditText) findViewById(R.id.inputOtp);
        btnSubmit = (Button) findViewById(R.id.btn_verify_otp);
        ((App) getApplication()).getmNetComponent().inject(this);
        mobile_no = getIntent().getStringExtra("mobile");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*verifyOtp(etOtp.getText().toString().trim(),mobile_no);*/
                Call<MobileNoOtpGson> otpGson = retrofit.create(RestApi.class).getOtpStatus(new MobileAndOtpModel(mobile_no, etOtp.getText().toString().trim()));
                otpGson.enqueue(new Callback<MobileNoOtpGson>() {
                    @Override
                    public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {
                /*try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.i(TAG, "onResponse: " + response.body().toString());
                    boolean status = jsonObject.getBoolean("status");
                    if (response.body().getStatus()) {
                        startActivity(new Intent(getApplicationContext(), FundooHrLoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                        if (response.body().getStatus()) {
                            startActivity(new Intent(getApplicationContext(), FundooHrLoginActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp(String otp) {


        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("otp", otp);
            grapprIntent.putExtra("mobile",mobile_no);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyOtp(final String otp, final String mobile) {
        Call<MobileNoOtpGson> otpGson = retrofit.create(RestApi.class).getOtpStatus(new MobileAndOtpModel(mobile, otp));
        otpGson.enqueue(new Callback<MobileNoOtpGson>() {
            @Override
            public void onResponse(Call<MobileNoOtpGson> call, Response<MobileNoOtpGson> response) {
                /*try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.i(TAG, "onResponse: " + response.body().toString());
                    boolean status = jsonObject.getBoolean("status");
                    if (response.body().getStatus()) {
                        startActivity(new Intent(getApplicationContext(), FundooHrLoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                if (response.body().getStatus()) {
                    startActivity(new Intent(getApplicationContext(), FundooHrLoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<MobileNoOtpGson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

}
