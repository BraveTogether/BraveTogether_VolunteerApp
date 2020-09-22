package com.example.bravetogether_volunteerapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FilterActivity extends AppCompatActivity {

    private static SharedPreferences mPreferences;
    static int radius, duration, time;
    static boolean nature;
    static Intent i;

    // get the user email from the file that holds the user info for the app.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        // configure the preference file
        String sharedPrefFile =
                "com.example.android.BraveTogether_VolunteerApp";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        /// make sure to change MainActivity to the activity you wish to forward the data to.
        i = new Intent(FilterActivity.this, MainActivity.class);
    }

    public void radius(View view) {
        view.setSelected(!view.isSelected());
        radius = Integer.parseInt(view.getTag().toString());
    }
    public void duration(View view) {
        view.setSelected(!view.isSelected());
        duration = Integer.parseInt(view.getTag().toString());
    }
    public void time(View view) {
        view.setSelected(!view.isSelected());
        time = Integer.parseInt(view.getTag().toString());
    }
    public void nature(View view) {
        view.setSelected(!view.isSelected());
        nature = Boolean.parseBoolean(view.getTag().toString());
    }

    public static LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng mylocation = null;
        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            mylocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mylocation;
    }

    public float getDistance(String address1, String address2) {

        Context cont = getApplicationContext();
        LatLng place1 = getLocationFromAddress(cont, address1);
        LatLng place2 = getLocationFromAddress(cont, address2);
        Location locationA = new Location("location");

        locationA.setLatitude(place1.latitude);
        locationA.setLongitude(place1.longitude);

        Location locationB = new Location("location");

        locationB.setLatitude(place2.latitude);
        locationB.setLongitude(place2.longitude);


        return locationA.distanceTo(locationB);
    }

    public void Search(View view) {
        String URL = getString(R.string.apiUrl);
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;
                    @Override
                    public void onResponse(JSONArray response) {
                        // get the user address from the shared preference file
                        String UserAddress = mPreferences.getString("UserAddress", "null");

                        if (UserAddress == null) {
                            // redirect the user to the activity in which he fills the required info.
                        }
                        else {
                            ArrayList<JSONObject> activities = new ArrayList<JSONObject>();
                            boolean dur, distance, times, natureofactivity;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Activity = response.getJSONObject(i);
                                    // havent yet calculated the time because i dont know if we will use time ranges entered by
                                    // user or time frames as like in the current layout
                                    distance = getDistance(Activity.get("address").toString(), UserAddress) < radius;
                                    dur = Activity.getInt("duration") <= duration;
                                    natureofactivity = (nature == (Activity.getBoolean("online")));
                                    if (distance && dur && natureofactivity) {
                                        activities.add(response.getJSONObject(i));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", "there was an error!!! :(");
                    }
                });
// Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);
    }

    public void Save(View view) {

    }

}
















