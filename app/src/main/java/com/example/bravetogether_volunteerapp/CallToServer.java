package com.example.bravetogether_volunteerapp;

import android.content.Context;
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

    private void getUserDetails (final String email,Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/users/" + "email",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error.response", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        VolleySingleton.getInstance(context).getRequestQueue().addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
            }
        });
    }
}
