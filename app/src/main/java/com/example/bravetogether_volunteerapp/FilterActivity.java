package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private static SharedPreferences mPreferences;
    static int radius, duration, nature;
    static Date from, until;
    //static boolean nature;
//    static DateFormat formatter = new SimpleDateFormat("HH:mm");
    static Intent i;
    public static List<JSONObject> activities;


    // get the user email from the file that holds the user info for the app.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        // configure the preference file
        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String UserUntil = mPreferences.getString("UserFilterAvailabilityEnd", null);
        String UserStart = mPreferences.getString("UserFilterAvailabilityStart", null);
        if (UserUntil !=null && UserStart!=null)
        {
            ((EditText)findViewById(R.id.until)).setText(UserUntil);
            ((EditText)findViewById(R.id.from)).setText(UserStart);
            switch (mPreferences.getString("UserFilterRadius", null))
            {
                case("10"):
                    radius(findViewById(R.id.km_option10));
                    break;
                case("15"):
                    radius(findViewById(R.id.km_option15));
                    break;
                case("20"):
                    radius(findViewById(R.id.km_option20));
                    break;
                default:
                    radius(findViewById(R.id.km_option_unlimited));
            }
            switch (mPreferences.getString("UserFilterDuration", null))
            {
                case("30"):
                    duration(findViewById(R.id.m30));
                    break;
                case("60"):
                    duration(findViewById(R.id.h1));
                    break;
                case("120"):
                    duration(findViewById(R.id.h2));
                    break;
                default:
                    duration(findViewById(R.id.unlimited_time));
            }
        }
        Toast.makeText(FilterActivity.this, UserStart, Toast.LENGTH_LONG).show();
    }

    public void radius(View view) {
        findViewById(R.id.km_option10).setSelected(false);
        findViewById(R.id.km_option15).setSelected(false);
        findViewById(R.id.km_option20).setSelected(false);
        findViewById(R.id.km_option_unlimited).setSelected(false);
        view.setSelected(true);
        radius = Integer.parseInt(view.getTag().toString());
    }
    public void duration(View view) {
        findViewById(R.id.m30).setSelected(false);
        findViewById(R.id.h1).setSelected(false);
        findViewById(R.id.h2).setSelected(false);
        findViewById(R.id.unlimited_time).setSelected(false);
        view.setSelected(true);
        duration = Integer.parseInt(view.getTag().toString());
    }
    public void nature(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected())
        {
            nature = Integer.parseInt(view.getTag().toString());
        }
        if (findViewById(R.id.online).isSelected() && findViewById(R.id.inplace).isSelected())
        {
            nature = -1;
        }
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

    public void Search(View view) throws ParseException {
        // get time and create date objects
        String FromTime="", UntilTime="";
        try {
            FromTime = ((EditText)findViewById(R.id.from)).getText().toString();
            UntilTime = ((EditText)findViewById(R.id.until)).getText().toString();

            DateFormat formatter = new SimpleDateFormat("HH:mm");
            from = formatter.parse(FromTime);
            until = formatter.parse(UntilTime);

            //String email = mPreferences.getString("UserEmail", "null");
            //get all the confirmed volunteers (1=true)
            String URL = getResources().getString(R.string.apiUrl) + "volunteers/confirmed/1";
            JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        JSONObject Activity;
                        @Override
                        public void onResponse(JSONArray response) {
                            activities = new ArrayList<JSONObject>();
                            // get the user address from the shared preference file
                            DateFormat formatter = new SimpleDateFormat("HH:mm");
                            //String UserAddress = mPreferences.getString("UserAddress", "null");
                            String UserAddress = "hatikva 27 ramat hasharon";
                            if (UserAddress == "null") {
                                String message = "לפני שתוכל לחפש עלייך להזין את כתובתך בדף ההגדרות";
                                Toast.makeText(FilterActivity.this, message, Toast.LENGTH_LONG).show();
                            } else {
                                boolean dur, distance, times, natureofactivity;
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        Activity = response.getJSONObject(i);
                                        int online=Activity.getInt("online"); // 1 equals true
                                        Date activity_start = formatter.parse(Activity.get("start_time").toString());
                                        natureofactivity = nature == online || nature ==-1; //-1 means user entered he wishes to volunteer both online or not
                                        float dist=0;

                                        // getTime returns epoch so 3600000 represents the milliseconds in one hour
                                        times = (activity_start.getTime() >= from.getTime()) && (activity_start.getTime() + 3600000 * duration <= until.getTime());
                                        if (natureofactivity && online==0 && times)
                                        {
                                            //calculate the distance between the user and the volunteer.
                                            dist = getDistance(Activity.get("address").toString(), UserAddress);
                                        }
                                        distance = dist < (radius*1000);
                                        dur = Activity.getInt("duration") <= duration;
                                        if (distance && dur && natureofactivity && times) {
                                            // add the distance from user to the volunteer data being forwarded to the next activity
                                            Activity.put("distance_from_user", String.valueOf(dist));
                                            activities.add((JSONObject)Activity);
                                            //Log.e("Activity", activities.toString());
                                        }
                                    } catch (JSONException | ParseException e) {
                                        e.printStackTrace();
                                    }
                                    //Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                                    //Bundle args = new Bundle();
                                    //args.putSerializable("myList", (Serializable) activities);
                                    //intent.putExtra("activitiesList", args);
                                    //startActivity(intent);
                                }
                                Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("message", "Failed with error msg:\t" + error.getMessage());
                            Log.e("stacktrace", "Error StackTrace: \t" + error.getStackTrace());
                            Log.e("Response", "there was an error in the filter activity!!! :(");
                        }
                    });
            // Access the RequestQueue through your singleton class.
            VolleySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);
        }
        catch(NullPointerException | ParseException exception)
        {
            if (FromTime == "" || UntilTime=="") {
                Toast.makeText(FilterActivity.this, "יש להכניס חלון זמנים", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(FilterActivity.this, "יש להכניס חלון זמנים במבנה כזה 12:00", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Save(View view) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        if (((CheckBox) view).isChecked()){
            preferencesEditor.putString("UserFilterRadius", String.valueOf(radius));
            preferencesEditor.putString("UserFilterDuration", String.valueOf(duration));
            preferencesEditor.putString("UserFilterAvailabilityStart", ((EditText)findViewById(R.id.from)).getText().toString());
            preferencesEditor.putString("UserFilterAvailabilityEnd", ((EditText)findViewById(R.id.until)).getText().toString());
            preferencesEditor.apply();
        }
        else{
            preferencesEditor.remove("UserFilterRadius");
            preferencesEditor.remove("UserFilterDuration");
            preferencesEditor.remove("UserFilterAvailabilityStart");
            preferencesEditor.remove("UserFilterAvailabilityEnd");
            preferencesEditor.apply();
        }
    }
}