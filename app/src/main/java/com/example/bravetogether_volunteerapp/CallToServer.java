package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CallToServer {

    private final String url = "http://35.214.78.251:8080";
    private String userDetails;

    public String getUserDetails (final String email,Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/users/" + "email",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error.response", error.getMessage());
                    }
                });
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        queue.add(stringRequest);
        queue.start();
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                userDetails = request.toString();
                Log.i("lalala",userDetails);
            }
        });
        int tries = 0;
        while (tries < 5) {
            if (userDetails != null) {
                return userDetails;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tries++;
            }
        }
        //TODO Implement logic if server is down or every other error
        return "Server is probably Down";
    }
}
