package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bravetogether_volunteerapp.adapters.ListOfVolunteerEventsAdapter;
import com.example.bravetogether_volunteerapp.adapters.MyItemAdapter;
import com.example.bravetogether_volunteerapp.adapters.VoluntteeirisStoryAdapter;
import com.example.bravetogether_volunteerapp.adapters.homePageListsGroupAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class home extends AppCompatActivity {

    private static SharedPreferences mPreferences;
    private final String urlData ="http://35.214.78.251:8080";
    //private String urlData;

    private RecyclerView my_recycler_view_nearBy;
    private RecyclerView Recycler_view_list_other;
    private RecyclerView Recycler_view_list_stories;
    private TextView userGreetings;

    private List<VoluntteeriesStory> storiesList;
    private List<VolunteerEvent> eventsList;
    private List<VolunteerEvent> eventsListNearBy;

    private int radius;

    //private homeGroupItem eventsNearByListItem;
    //private homeGroupItem eventsListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //urlData = getResources().getString(R.string.apiUrl);
        // configure the preference file
        //String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        //mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //set the text Greeting in the top of the page
       // UserGreetingText(getUserName());
        UserGreetingText("עומר");
        // List<homeGroupItem> listGroup = new ArrayList<>();

        //initialization of page list
        storiesList = new ArrayList<>();
        eventsList = new ArrayList<>();
        eventsListNearBy = new ArrayList<>();
        //eventsNearByListItem = new homeGroupItem();

        //initialization of BottomNavigation
        BottomNavigationView BottomNavigationViewHome = findViewById(R.id.BottomNavigationViewHome);
        BottomNavigationViewHome.setOnNavigationItemSelectedListener(navListener);

        volunteersStoriesLoadNew(this);

        //eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));
       // eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));
        //eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));


//        eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));
//        eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));
//        eventsList.add(new VolunteerEvent("עזרה בקניות", "13/14/15", "גיל הזהב", "120", "3", "300"));


        //eventsListItem.setListVolunteerEvent(eventsList);
        //eventsNearByListItem.setListVolunteerEvent(eventsList);

        //listGroup.add(eventsListItem);
        //listGroup.add(eventsNearByListItem);
        //homePageListsGroupAdapter adapter = (homePageListsGroupAdapter) new homePageListsGroupAdapter(this, listGroup);

//        my_recycler_view_nearBy = (RecyclerView) findViewById(R.id.Recycler_view_list_nearBy);
//        MyItemAdapter adapter_nearBy = (MyItemAdapter) new MyItemAdapter(this, eventsListNearBy);
//        my_recycler_view_nearBy.setAdapter(adapter_nearBy);
//        my_recycler_view_nearBy.setHasFixedSize(true);
//        my_recycler_view_nearBy.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
//
//
//        Recycler_view_list_other = (RecyclerView) findViewById(R.id.Recycler_view_list_other);
//        MyItemAdapter adapter_other = (MyItemAdapter) new MyItemAdapter(this, eventsList);
//        Recycler_view_list_other.setAdapter(adapter_other);
//        Recycler_view_list_other.setHasFixedSize(true);
//        Recycler_view_list_other.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
//
//        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
//        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
//        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
//        Recycler_view_list_stories = (RecyclerView) findViewById(R.id.Recycler_view_list_stories);
//        VoluntteeirisStoryAdapter adapterStoriesList = (VoluntteeirisStoryAdapter) new VoluntteeirisStoryAdapter(this,storiesList);
//        Recycler_view_list_stories.setAdapter(adapterStoriesList);
//        Recycler_view_list_stories.setHasFixedSize(true);
//        Recycler_view_list_stories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.homeFragment: {
                            selectedFragment = new HomeFragment();
                            break;
                        }
                        case R.id.settingFragment: {
                            selectedFragment = new SettingFragment();
                            break;
                        }
                        case R.id.profileFragment: {
                            //selectedFragment = new ProfileFragment();
                            break;

                        }
                        case R.id.searchFragment: {
                            selectedFragment = new searchFragment();
                            break;

                        }
                        case R.id.notificationFragment: {
                            selectedFragment = new NotificationFragment();
                            break;

                        }
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.home, selectedFragment).commit();
                    return true;
                }
            };

    protected  void volunteersStoriesLoadNew (final Context c) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlData + "/reviews/users", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.i("Error-Try", "we get it!");
                            createStoriesArray(response);
                            Recycler_view_list_stories = (RecyclerView) findViewById(R.id.Recycler_view_list_stories);
                            VoluntteeirisStoryAdapter adapterStoriesList = (VoluntteeirisStoryAdapter) new VoluntteeirisStoryAdapter(c,storiesList);
                            Recycler_view_list_stories.setAdapter(adapterStoriesList);
                            Recycler_view_list_stories.setHasFixedSize(true);
                            Recycler_view_list_stories.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.HORIZONTAL, false));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    protected void nearByVolunteerEventsLoad(final Context c) {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) Recycler_view_list_stories.findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            createEventsNearByArray(obj, "nameOfArry");

                            //creating custom adapter object
                            VoluntteeirisStoryAdapter adapterForStories = (VoluntteeirisStoryAdapter) new VoluntteeirisStoryAdapter(getApplicationContext(), storiesList);

                            //adding the adapter to recyclerView


                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void createStoriesArray(JSONObject jsonResponse) throws JSONException {
        //we have the array named name inside the object
        //so here we are getting that json array
        JSONArray storiesArray = jsonResponse.toJSONArray(jsonResponse.names());

        //now looping through all the elements of the json array
        for (int i = 0; i < storiesArray.length(); i++) {
            //getting the json object of the particular index inside the array
            JSONObject eventObject = storiesArray.getJSONObject(i);

            Log.i("lala",eventObject.toString());
            //creating a VoluntteeriesStory object and giving them the values from json object
            VoluntteeriesStory story = new VoluntteeriesStory(eventObject.getString("name"), eventObject.getString("address"), eventObject.getString("review_text"));

            //adding the story to storiesArray
            storiesList.add(story);
        }
    }

    public void createEventsNearByArray(JSONObject context, String strAddress) throws ParseException {

        String email = mPreferences.getString("UserEmail", "null");
        // send GET request for user information to get address
        String URL = getResources().getString(R.string.apiUrl) + "/user/" + email;
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;

                    @Override
                    public void onResponse(JSONArray response) {
                        // get the user address from the shared preference file
                        String UserAddress = mPreferences.getString("UserAddress", "null");
                        if (UserAddress == null) {
                            String message = "לפני שתוכל לחפש עלייך להזין את כתובתך בדף ההגדרות";
                            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                        } else {
                            boolean distance = false;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Activity = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    distance = getDistance(Activity.get("address").toString(), UserAddress) < radius;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (distance) {
                                    try {
                                        eventsListNearBy.add(new VolunteerEvent(Activity.get("name").toString(), Activity.get("date").toString(), Activity.get("about_volunteering").toString(), Activity.get("value_in_coins").toString(), Activity.get("duration").toString(), Activity.get("about_place").toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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




    //get the user name from shared preference
    protected String getUserName()  {
        String name = "";
        if(mPreferences.contains("first_name"))
        { name= "אורח"; }
        else { name=mPreferences.getString("first_name", null); }
        return name;
    }

    //set the text in the head of the page to good Morning etc..
    protected void UserGreetingText(String userName) {
        userGreetings = (TextView) findViewById(R.id.userGreetings);
        Calendar calendar = Calendar.getInstance();
        int timeofDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeofDay < 12) {
            userGreetings.setText("בוקר טוב," + userName);
        } else if (timeofDay < 16) {
            userGreetings.setText("צהריים טובים, " + userName);
        } else if (timeofDay < 17) {
            userGreetings.setText("אחר צהריים טובים, " + userName);
        } else if (timeofDay < 21) {
            userGreetings.setText("ערב טוב, " + userName);
        } else {
            userGreetings.setText("לילה טוב, " + userName);
        }
    }

    protected void radius(View view) {
        view.setSelected(!view.isSelected());
        radius = Integer.parseInt(view.getTag().toString());
    }

    protected float getDistance(String address1, String address2) {

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

    protected static LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng mylocation = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            mylocation = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mylocation;
    }

}



