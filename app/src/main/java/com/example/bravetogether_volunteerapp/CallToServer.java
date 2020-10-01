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
    private String[] userDetails = new String[5];

    public String[] getUserDetails (final String email,Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/users/" + "email",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
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
        return new String[0];
    }
}
