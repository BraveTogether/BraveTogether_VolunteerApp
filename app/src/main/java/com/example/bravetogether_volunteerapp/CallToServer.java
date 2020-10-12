package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallToServer {

    private final String url = "http://35.214.78.251:8080";


    public void registerUser(final Context context,final String email, final String password, final String first_name, final String last_name, final String phone, final String address, final String about,
                             final String user_type,final String profile_pic){
        final StringRequest sr = new StringRequest(Request.Method.POST, url + "/user/signup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                getUserId(context,email,phone,address,about,user_type,profile_pic);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(sr);
    }

    public void getUserId (final Context context,final String email, final String phone, final String address, final String about,
                           final String user_type,final String profile_pic) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/user/" + email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        JsonParser parser = new JsonParser();
                        JsonElement json = parser.parse(response);
                        String id = String.valueOf(json.getAsJsonObject().get("id"));
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putString("UID",id);
                        edit.apply();

                        registerUserProfile(context,id,phone,address,about,user_type,profile_pic);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error.response", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    };

    public void registerUserProfile(final Context context,final String UID, final String phone, final String address, final String about,
                                    final String user_type,final String profile_pic){
        final StringRequest sr = new StringRequest(Request.Method.POST, url + "/user/signup_user_profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", UID);
                params.put("phone", phone);
                params.put("address", address);
                params.put("about", about);
                params.put("user_type",user_type);
                params.put("profile_pic",profile_pic);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(sr);
    }


}
