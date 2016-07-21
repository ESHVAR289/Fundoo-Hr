package com.bridgelabz.restservice;

import com.bridgelabz.model.MessageData;
import com.bridgelabz.model.MessageGson;
import com.bridgelabz.model.MobileOtpPostDataModel;
import com.bridgelabz.model.MobileNoOtpResponse;
import com.bridgelabz.model.TimeEntryResponse;
import com.bridgelabz.model.TimeEntryPostMessageModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by eshvar289 on 3/7/16.
 */

public interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("sendmsg/")
    Call<MessageGson> getMessage(@Body MessageGson messageGson);

    @Headers("Content-Type: application/json")
    @POST("sms/otp/")
    Call<MobileNoOtpResponse> getMobileNoStatus(@Body MobileOtpPostDataModel mobileOtpPostDataModel);

    @Headers("Content-Type: application/json")
    @POST("sms/verify/")
    Call<MobileNoOtpResponse> getOtpStatus(@Body MobileOtpPostDataModel mobileOtpPostDataModel);

    @Headers("Content-Type: application/json")
    @POST("message/")
    Call<TimeEntryResponse> getTimeEntryMsg(@Body TimeEntryPostMessageModel timeEntryPostMessageModel);

    @Headers("Content-Type: application/json")
    @POST("message/timeEntryConform/")
    Call<MessageData> sentTimeEntryConfirmation(@Body TimeEntryResponse timeEntryResponse);

    @GET("dummy_attendance.json")
    Call<String> getAttendanceData();
}
