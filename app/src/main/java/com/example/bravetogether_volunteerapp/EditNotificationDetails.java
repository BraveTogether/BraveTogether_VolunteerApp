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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;

public class EditNotificationDetails extends AppCompatActivity {

    // variable that holds the details of the user volunteer,
    private Integer distanceRadius = 500;//radius preference
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

    static boolean c = true;
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





        //TODO check how to use specific query
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url + "users_notifications/get_ids_and_preference/"+ uid , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("success_response", response.toString());
                        try {
                            id = BigInteger.valueOf(response.getInt("id"));
                            typeOfVolunteer = response.getInt("notification_location_pref_id");
                            distanceRadius = response.getInt("distance");
                            c = false;
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new  Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
        });
        VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(jsonObjectRequest);

        //request the profile ID form DB don't exit while loop if response from server not got back
        while (c){};

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                (Request.Method.GET, url + "users_notifications/get_times/"+ id , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("success_response", response.toString());
                        try {
                         timesOfVolunteer = response.getInt("users_notifications_id");
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new  Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(jsonObjectRequest2);

        setDistanceRadius(null);
        setTimesOfVolunteer(null);
        setTypeOfVolunteer(null);
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
        int position = 0;
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


    //TODO need to edit the profile on the DB
    public void submitEdit(View view){
        JSONObject distancePref = new JSONObject();
        JSONObject locationPref = new JSONObject();
        JSONObject timePref = new JSONObject();
        try {
            distancePref.put("distance", distanceRadius);
            locationPref.put("location_pref_id", typeOfVolunteer);
            timePref.put("time_window", timesOfVolunteer);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBodyLocation = locationPref.toString();
        final String requestBodyTime = timePref.toString();
        StringRequest stringRequestLocation = new StringRequest(Request.Method.POST, url + "user_notification/updates" + uid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("success_Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        StringRequest stringRequestTime = new StringRequest(Request.Method.POST, url + "user_notification_dates/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("success_Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(stringRequestLocation);
        VolleySingleton.getInstance(EditNotificationDetails.this).addToRequestQueue(stringRequestTime);
        Intent intent = new Intent(this, RegularProfileActivity.class);
        startActivity(intent);
    }

}