package com.bridgelabz.controller;

import android.util.Log;
import android.widget.Toast;

import com.bridgelabz.callback.ResponseCallbackListener;
import com.bridgelabz.model.ConfirmationResponse;
import com.bridgelabz.model.TimeEntryPostMessageModel;
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.model.TimeEntryResponseDataModel;
import com.bridgelabz.restservice.RestApi;
import com.bridgelabz.util.App;
import com.bridgelabz.view.FundooHrToolbarSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bridgeit007 on 21/7/16.
 */

public class AttendanceController {

    Retrofit mRetrofit;
    private TimeEntryResponse mTimeEntryResponseData;
    private ResponseCallbackListener mResponseCallbackListener;
    private static final String TAG = AttendanceController.class.getSimpleName();
    private App mAppInstance;
    public AttendanceController(ResponseCallbackListener callbackListener, Retrofit retrofit){
        this.mResponseCallbackListener = callbackListener;
        this.mRetrofit = retrofit;
    }

    public void getResponseForMessage(String mobile_no,String editTextMessage){
       // mAppInstance.getmNetComponent().inject(this);
        String str="Eshvar";
        Call<TimeEntryResponse> timeEntryResponse=mRetrofit.create(RestApi.class).
                                                    getTimeEntryMsg(new TimeEntryPostMessageModel(mobile_no,editTextMessage));
        timeEntryResponse.enqueue(new Callback<TimeEntryResponse>() {
            @Override
            public void onResponse(Call<TimeEntryResponse> call, Response<TimeEntryResponse> response) {
                mTimeEntryResponseData = response.body();
                mResponseCallbackListener.messageResponse(mTimeEntryResponseData);
            }

            @Override
            public void onFailure(Call<TimeEntryResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.toString());
            }
        });
    }
    public void sendResponseConfirmation(String userId, String date, String inTime, String outTime, String totalTime,boolean updateStatus){
        Call<ConfirmationResponse> timeEntryConfirmation = mRetrofit.create(RestApi.class)
                .sentTimeEntryConfirmation(
                        new TimeEntryResponse(
                                new TimeEntryResponseDataModel(userId,date,inTime,outTime,totalTime),
                                updateStatus));
        timeEntryConfirmation.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {
                mResponseCallbackListener.confirmationResponse(response.body());
            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });
    }
}
