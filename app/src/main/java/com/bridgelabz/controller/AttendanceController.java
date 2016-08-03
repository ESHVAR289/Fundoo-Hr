package com.bridgelabz.controller;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bridgelabz.callback.ResponseCallbackListener;
import com.bridgelabz.dagger.AppController;
import com.bridgelabz.model.TimeEntryResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

import static com.bridgelabz.util.Constants.MESSAGE_CONFIRMATION_URL;
import static com.bridgelabz.util.Constants.MESSAGE_RESPONSE_URL;
import static com.bridgelabz.util.Constants.TAG_REQ;

public class AttendanceController {

    private static final String TAG = AttendanceController.class.getSimpleName();
    private Retrofit mRetrofit;
    private TimeEntryResponse mTimeEntryResponseData;
    private ResponseCallbackListener mResponseCallbackListener;

    public AttendanceController(ResponseCallbackListener callbackListener, Retrofit retrofit) {
        this.mResponseCallbackListener = callbackListener;
        this.mRetrofit = retrofit;
    }

    public void getResponseForMessage(final String mobile_no, final String editTextMessage) {
        //rest call implementation using volley
        StringRequest messageRequest = new StringRequest(Request.Method.POST, MESSAGE_RESPONSE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (new JSONObject(jsonObject.getString("data")).getString("type").equals("attendance")) {
                        mTimeEntryResponseData = new Gson().fromJson(response, TimeEntryResponse.class);
                        mResponseCallbackListener.messageResponse(mTimeEntryResponseData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        if (jsonObject.getString("err").equals("You have already entered intime")) {
                            mResponseCallbackListener.attendanceErrorResponse(jsonObject.getString("err"));
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        try {
                            if (jsonObject.getString("data").equals("please try again...")) {
                                mResponseCallbackListener.attendanceErrorResponse(jsonObject.getString("data"));
                            }
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseCallbackListener.attendanceErrorResponse(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> loginParams = new HashMap<>();
                loginParams.put("mobile", mobile_no);
                loginParams.put("message", editTextMessage);
                Log.i(TAG, "getParams: " + mobile_no + " " + editTextMessage);
                return loginParams;
            }
        };

        //Adding the request to the request que
        AppController.getInstance().addToRequestQueue(messageRequest, TAG_REQ);
    }

    public void sendResponseConfirmation(final String userId, final String date, final String inTime, final String outTime, final String totalTime, final boolean updateStatus) {
        JSONObject postRequestData = createPostJson(userId, inTime, outTime, totalTime, updateStatus);
        //rest call implementation using volley
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST,
                        MESSAGE_CONFIRMATION_URL,
                        postRequestData,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("data").equals("update")) {
                                        mResponseCallbackListener.confirmationResponse(response.getString("data"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    try {
                                        if (response.getString("err").equals("You are not check")) {
                                            mResponseCallbackListener.attendanceErrorResponse(response.getString("err"));
                                        }
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseCallbackListener.attendanceErrorResponse(error.toString());
                    }
                });

        //Adding the request to the request que
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, TAG_REQ);

    }

    private JSONObject createPostJson(String userId, String inTime, String outTime, String totalTime, boolean updateStatus) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("inTime", inTime);
            jsonObject.put("outTime", outTime);
            jsonObject.put("totalTime", totalTime);
            jsonObject.put("check", updateStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
