package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
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

    static int radius;
    static Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        /// make sure to change MainActivity to the activiry you fish to forward the data to.
        i = new Intent(FilterActivity.this, MainActivity.class);
    }

    public void radius(View view) {
        view.setSelected(!view.isSelected());
        radius = Integer.parseInt(view.getTag().toString());
    }
    public void duration(View view) {
        view.setSelected(!view.isSelected());
        String duration = view.getTag().toString();
        i.putExtra("duration", duration);
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
        Location locationA = new Location("התקוה 23 רמת השרון");

        locationA.setLatitude(place1.latitude);
        locationA.setLongitude(place1.longitude);

        Location locationB = new Location("הרצל 22 הרצליה");

        locationB.setLatitude(place2.latitude);
        locationB.setLongitude(place2.longitude);

        float distance = locationA.distanceTo(locationB);
        return distance;
    }

    public void Search(View view) {
    }

    public void display(View view) {

        String URL = getString(R.string.apiUrl);
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    String ActivityAddress, UserAddress;
                    @Override
                    public void onResponse(JSONArray response) {
                        UserAddress = "test";
                        for (int i=0; i<response.length(); i++){
                            try {
                                ActivityAddress=(response.getJSONObject(0).get("address")).toString();
                                if (getDistance(ActivityAddress,UserAddress) < radius) {

                                }
                            }
                            catch (JSONException e) { e.printStackTrace(); }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Response", "there was an error!!! :(");
                    }
                });
// Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);

    }


    public void Save(View view) {

    }
}
















