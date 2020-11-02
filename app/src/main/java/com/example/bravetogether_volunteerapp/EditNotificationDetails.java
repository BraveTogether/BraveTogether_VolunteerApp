package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class EditNotificationDetails extends AppCompatActivity {

    // variable that holds the details of the user volunteer,
    private int distanceRadius = 500;//radius preference
    private Integer timesOfVolunteer = 3;//1-morning, 2-noon, 3-evening, 4-no_limit
    private Integer typeOfVolunteer = 1;//1-online, 2-close, 3 anywhere
    private BigInteger uid;// id
    private BigInteger id;
    //url for server requests
    final String url = "http://35.214.78.251:8080/";

    //ArrayList the holds all the button for easy iteration.
    ArrayList<TextView> buttonsDistanceRadius = new ArrayList<>();
    ArrayList<TextView> buttonsTimesOfVolunteer = new ArrayList<>();
    ArrayList<TextView> buttonsTypeOfVolunteer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("EditNotificationDetails", "got to here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification_details);

        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        //TODO: check if all the mPreferences names are correct
        uid = new BigInteger(String.valueOf(mPreferences.getInt("id", 64)));
        //TODO: check if all the mPreferences names are correct

        //Add all buttons
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance10));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance15));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance20));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distanceUnlimited));

        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesMorning));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesNoon));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesEvening));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesUnlimited));

        buttonsTypeOfVolunteer.add((TextView)this.findViewById(R.id.typeOnline));
        buttonsTypeOfVolunteer.add((TextView)this.findViewById(R.id.typeClose));
        buttonsTypeOfVolunteer.add((TextView)this.findViewById(R.id.typeUnlimited));


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET,
                        url + "users_notifications/get_ids_and_preference/" + uid,
                        null, new Response.Listener<JSONArray>() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object = (JSONObject) response.get(0);
                            id = new BigInteger(String.valueOf(object.getInt("id")));
                            typeOfVolunteer = object.getInt("notification_location_pref_id");
                            distanceRadius = object.getInt("distance");
                            timesOfVolunteer = object.getInt("time_window_id");
                            setDistanceRadius(null);
                            setTimesOfVolunteer(null);
                            setTypeOfVolunteer(null);
                            } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", String.valueOf(error));
                    }
                });
        VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(jsonArrayRequest);
    }




    /**
     * Called when a view(distanceRadius) has been clicked.
     *Change the color of the button and set variable.
     * @param view The view that was clicked, if view is null, function called in onCreate to set
     *         the view of the page
     */
    public void setDistanceRadius(View view){
        int position = 0;
        Integer [] radius = {10, 15, 20, 500};
        if (view != null) {
            for (View v : buttonsDistanceRadius) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    distanceRadius = radius[position];
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (distanceRadius) {
                case 10:
                    buttonsDistanceRadius.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 15:
                    buttonsDistanceRadius.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 20:
                    buttonsDistanceRadius.get(2).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 500:
                    buttonsDistanceRadius.get(3).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }

    /**
     * Called when a view(setTimesVolunteer) has been clicked.
     *Change the color of the button and set variable for update in the DB.
     * @param view The view that was clicked, if view is null, function called in onCreate to set
     *         the view of the page
     */
    public void setTimesOfVolunteer(View view){
        int position = 1;
        if (view != null) {
            for (View v : buttonsTimesOfVolunteer) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    timesOfVolunteer = position;
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (timesOfVolunteer) {
                case 1:
                    buttonsTimesOfVolunteer.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 2:
                    buttonsTimesOfVolunteer.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 3:
                    buttonsTimesOfVolunteer.get(2).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 4:
                    buttonsTimesOfVolunteer.get(3).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }


    public void setTypeOfVolunteer(View view){
        int position = 1;
        if (view != null) {
            for (View v : buttonsTypeOfVolunteer) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    typeOfVolunteer = position;
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (typeOfVolunteer) {
                case 1:
                    buttonsTypeOfVolunteer.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 2:
                buttonsTypeOfVolunteer.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case 3:
                    buttonsTypeOfVolunteer.get(2).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }



    public void submitEdit(View view){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", id);
            jsonBody.put("time_window_id",timesOfVolunteer);
            jsonBody.put("location_pref_id", typeOfVolunteer);
            jsonBody.put("distance", distanceRadius);
            jsonBody.put("uid", uid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "users_notification/updates" ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("success_Response", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                })
        {
            @Override
            public byte[] getBody() throws AuthFailureError{
                try {
                    return requestBody == null? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee){
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s\", requestBody, \"utf-8\"");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            public String getBodyContentType(){
                return "application/json";
            }};
            VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(stringRequest);
            Intent intent = new Intent(this, RegularProfileActivity.class);
            startActivity(intent);
            finish();
    }
}