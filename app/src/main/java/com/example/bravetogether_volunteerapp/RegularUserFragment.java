package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.concurrent.Semaphore;


public class RegularUserFragment extends Fragment implements View.OnClickListener {
    private TextView typeText , distanceText, hoursText;
    private boolean notifyOnDay;
    private Switch notifyOnDaySwitch;
    private boolean c = true;
    private Semaphore semaphore = new Semaphore(0);
    private int distanceRadius = 500;//radius preference
    private Integer timesOfVolunteer = 3;//1-morning, 2-noon, 3-evening, 4-no_limit
    private Integer typeOfVolunteer = 1;//1-online, 2-close, 3 anywhere
    private BigInteger uid;
    final String url = "http://35.214.78.251:8080/";

    public RegularUserFragment() {
        // Required empty public constructor
    }

    public static RegularUserFragment newInstance(String param1, String param2) {
        RegularUserFragment fragment = new RegularUserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        uid =BigInteger.valueOf(mPreferences.getInt("uid", 64));
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
                            typeOfVolunteer = object.getInt("notification_location_pref_id");
                            distanceRadius = object.getInt("distance");
                            timesOfVolunteer = object.getInt("time_window_id");
                            notifyOnDay = object.getInt("notify_on_day")==1?true:false;
                            notifyOnDaySwitch.setChecked(notifyOnDay);
                            distanceText.setText(distanceRadius + " קמ");
                            switch (typeOfVolunteer) {
                                case 1:
                                    typeText.setText("התנבות און ליין'");
                                    break;
                                case 2:
                                    typeText.setText("התנדבות פיזית");
                                    break;
                                case 3:
                                    typeText.setText("ללא הגבלת מרחק");
                            }
                            switch (timesOfVolunteer){
                                case 1:
                                    hoursText.setText("בוקר");
                                    break;
                                case 2:
                                    hoursText.setText("צהריים");
                                    break;
                                case 3:
                                    hoursText.setText("ערב");
                                    break;
                                case 4:
                                    hoursText.setText("ללא הגבלת שעה");
                                    break;
                            }
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
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_regular_user, container, false);
        //Set the Values form SharedPref to the text
        distanceText = (TextView)(v.findViewById(R.id.distance));
        typeText = (TextView)(v.findViewById(R.id.type));
        hoursText = (TextView)(v.findViewById(R.id.hours));
        //Checks if the switch is on and change the text color
        notifyOnDaySwitch = v.findViewById(R.id.switch_button);
        v.findViewById(R.id.editNotification).setOnClickListener(this);
        notifyOnDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                notifyOnDay = true;
                    typeText.setTextColor(Color.parseColor("#001925"));
                    distanceText.setTextColor(Color.parseColor("#001925"));
                    hoursText.setTextColor(Color.parseColor("#001925"));}
                else {
                    notifyOnDay = false;
                    typeText.setTextColor(Color.parseColor("#8C96A2"));
                    distanceText.setTextColor(Color.parseColor("#8C96A2"));
                    hoursText.setTextColor(Color.parseColor("#8C96A2")); }

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("uid", uid);
                    jsonBody.put("notifyOnDay", notifyOnDay?1:0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "users_notifications/notifyOnDay" ,
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
                    public byte[] getBody() throws AuthFailureError {
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
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            }
        });
        return v;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Log.d("RegularUserFragment" , "clicked");
        getActivity().finish();
        Intent intent = new Intent(getContext(), EditNotificationDetails.class);
        startActivity(intent);
    }
}
