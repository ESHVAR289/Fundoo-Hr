package com.bridgelabz.restservice;

import com.bridgelabz.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by eshvar289 on 3/7/16.
 */

public interface RestApi {
    @GET("/face_rest.json")
    Call<List<Post>> getPosts();
}
