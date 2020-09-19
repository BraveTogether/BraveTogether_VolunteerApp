package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.logging.Filter;

public class FilterActivity extends AppCompatActivity {

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
        String radius = view.getTag().toString();
        i.putExtra("radius", radius);
    }
    public void duration(View view) {
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

    public float getDistance(LatLng place1, LatLng place2) {
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
        //change addressUser for query to our db for the user address
        //change addressFoundation for query to our db for the user address
        String addressUser = "התקוה 23 רמת השרון";
        String addressFoundation = "דוד בן גוריון 22 הרצליה";

        Context cont = getApplicationContext();
        LatLng user = getLocationFromAddress(cont, addressUser);
        LatLng foundation = getLocationFromAddress(cont, addressFoundation);

        float distance = getDistance(foundation, user);
    }

    public void Save(View view) {

    }
}