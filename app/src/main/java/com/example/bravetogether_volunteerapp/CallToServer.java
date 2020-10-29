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


    public void registerUser(final Context context, final String email, final String password, final String first_name, final String last_name, final String phone, final String address, final String about,
                             final String user_type, final String profile_pic, final String location, final String switched, final String userDesiredLocation){
        final StringRequest sr = new StringRequest(Request.Method.POST, url + "/user/signup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("huhuhu",response);
                getUserId(context,email,phone,address,about,user_type,profile_pic,location,switched,userDesiredLocation);
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

    public void getUserId (final Context context, final String email, final String phone, final String address, final String about,
                           final String user_type, final String profile_pic, final String location, final String switched, final String userDesiredLocation) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/user/" + email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        JsonParser parser = new JsonParser();
                        JsonElement json = parser.parse(response);
                        String id = String.valueOf(json.getAsJsonObject().get("id"));
                        Log.d("Response",id);
                        registerUserProfile(context,id,phone,address,about,user_type,profile_pic,location,switched,userDesiredLocation);
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

    public void registerUserProfile(final Context context, final String UID, final String phone, final String address, final String about,
                                    final String user_type, final String profile_pic, final String location, final String switched, final String userDesiredLocation){
        final StringRequest sr = new StringRequest(Request.Method.POST, url + "/user/signup_user_profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                registerUserNotification(context,UID,phone,address,about,user_type,profile_pic,location,switched,userDesiredLocation);
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
                params.put("uid", UID);
                params.put("phone_number", phone);
                params.put("home_address", address);
                params.put("about", about);
                params.put("user_type",user_type);
                params.put("profile_picture",profile_pic);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(sr);
    }

    public void registerUserNotification(final Context context, final String UID, final String phone, final String address, final String about,
                                         final String user_type, final String profile_pic, final String location, final String switched, final String userDesiredLocation){
        final StringRequest sr = new StringRequest(Request.Method.POST, url + "/users_notifications/new_user", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                getUsersNotificationId(context,UID);
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
                params.put("uid",UID);
                params.put("notification_address", location);
                params.put("notify_on_day", switched);
                params.put("notification_location_id",userDesiredLocation);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(sr);
    }

    public void getUsersNotificationId (final Context context,final String UID) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/users_notifications/get_id",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);

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
                params.put("uid",UID);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    };


}
