package com.bridgelabz.restservice;

import com.bridgelabz.Post;
import com.bridgelabz.model.MessageGson;
import com.bridgelabz.model.MobileAndOtpModel;
import com.bridgelabz.model.MobileNoOtpGson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by eshvar289 on 3/7/16.
 */

public interface RestApi {
    @GET("/face_rest.json")
    Call<List<Post>> getPosts();

    @Headers("Content-Type: application/json")
    @POST("sendmsg/")
    Call<MessageGson> getMessage(@Body MessageGson messageGson);

    @Headers("Content-Type: application/json")
    @POST("otp/")
    Call<MobileNoOtpGson> getMobileNoStatus(@Body MobileAndOtpModel mobileAndOtpModel);

    @Headers("Content-Type: application/json")
    @POST("verify/")
    Call<MobileNoOtpGson> getOtpStatus(@Body MobileAndOtpModel mobileAndOtpModel);

}
