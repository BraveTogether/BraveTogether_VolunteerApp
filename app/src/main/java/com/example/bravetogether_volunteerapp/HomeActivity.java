package com.example.bravetogether_volunteerapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bravetogether_volunteerapp.ImageRetrieving.ImageRetriever;
import com.example.bravetogether_volunteerapp.adapters.VolunteerItemHomeAdapter;
import com.example.bravetogether_volunteerapp.adapters.VoluntteeirisStoryAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static SharedPreferences mPreferences;
    private final String urlData ="http://35.214.78.251:8080";
    //private String urlData;

    private RecyclerView my_recycler_view_nearBy;
    private RecyclerView Recycler_view_list_Central;
    private RecyclerView Recycler_view_list_stories;
    private TextView userGreetings;

    private List<VoluntteeriesStory> storiesList;
    private List<VolunteerEventItemList> volunteerEventItemLists;
    private List<VolunteerEventItemList> eventsListNearBy;
    private int radius;


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
        radius= 1000;
        UserGreetingText("עומר");
        // List<homeGroupItem> listGroup = new ArrayList<>();

        //initialization of page list
        storiesList = new ArrayList<>();
        volunteerEventItemLists = new ArrayList<>();
        eventsListNearBy = new ArrayList<>();
        //eventsNearByListItem = new homeGroupItem();

        //initialization of BottomNavigation
        BottomNavigationView BottomNavigationViewHome = findViewById(R.id.BottomNavigationViewHome);
        BottomNavigationViewHome.setOnNavigationItemSelectedListener(navListener);


        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            String rationale = "Please provide storage permission so that you can load your profile picture";
            Permissions.Options options = new Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning");

            Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
    @Override
    public void onGranted() {
        storiesList(HomeActivity.this);
       // try {
            //createEventsNearByArray(HomeActivity.this);
            //createCentralEventsNearByArray(HomeActivity.this);
        //} catch (ParseException e) {
           // e.printStackTrace();
       // }
            }
    @Override
    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
            Toast.makeText(HomeActivity.this, "Please allow the the app to access your storage in order to show your profile picture", Toast.LENGTH_SHORT).show();
            }
            });


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


    protected void storiesList(final Context c) {
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, urlData + "/reviews/users", null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Error-Try", "we get it!");
                        try {
                            //now looping through all the elements of the json array
                            for (int i = 0; i < response.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject eventObject = response.getJSONObject(i);
                                //creating a VoluntteeriesStory object and giving them the values from json object
                                final ImageRetriever imageRetriever = new ImageRetriever();
                                //Uri imageURI = Uri.parse(eventObject.getString("profile_picture"));
                                Uri imageURI = Uri.parse("gs://bravetogethervolunteerapp.appspot.com/images/02f6bcae-a2e4-4ced-9759-4afd44b5c514");
                                String filePath = imageRetriever.retrieveImg(imageURI, HomeActivity.this, "Home - Pic").getPath();

                                VoluntteeriesStory story = new VoluntteeriesStory(eventObject.getString("name"), eventObject.getString("address"), eventObject.getString("review_text"), filePath);
                                //adding the story to storiesArray
                                storiesList.add(story);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Recycler_view_list_stories = (RecyclerView) findViewById(R.id.Recycler_view_list_stories);
                        VoluntteeirisStoryAdapter adapterStoriesList = (VoluntteeirisStoryAdapter) new VoluntteeirisStoryAdapter(c,storiesList);
                        Recycler_view_list_stories.setAdapter(adapterStoriesList);
                        Recycler_view_list_stories.setHasFixedSize(true);
                        Recycler_view_list_stories.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.HORIZONTAL, false));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", String.valueOf(error));
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(JsonArrayRequest);
    }

    public void createEventsNearByArray(final Context c) throws ParseException {
        //String email = mPreferences.getString("UserEmail", "null");
        // send GET request for user information to get address
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, urlData + "/events", null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;

                    @Override
                    public void onResponse(JSONArray response) {
                        // get the user address from the shared preference file
                        //String UserAddress = mPreferences.getString("UserAddress", "null");
                        String UserAddress = "hatikva 27 ramat hasharon";
                            boolean distance = false;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Activity = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    float dist = getDistance(Activity.get("address").toString(), UserAddress);
                                    distance = dist < (radius*1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (distance) {
                                    try {
                                        eventsListNearBy.add(new VolunteerEventItemList(Activity.get("name").toString(), Activity.get("date").toString(), Activity.get("about_volunteering").toString(), Activity.get("value_in_coins").toString(), Activity.get("duration").toString(), Activity.get("about_place").toString(), Activity.get("picture").toString()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            my_recycler_view_nearBy = (RecyclerView) findViewById(R.id.Recycler_view_list_nearBy);
                            VolunteerItemHomeAdapter adapter_nearBy = (VolunteerItemHomeAdapter) new VolunteerItemHomeAdapter(c, eventsListNearBy);
                            my_recycler_view_nearBy.setAdapter(adapter_nearBy);
                            my_recycler_view_nearBy.setHasFixedSize(true);
                            my_recycler_view_nearBy.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.HORIZONTAL, false));
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

    public void createCentralEventsNearByArray(final Context c) throws ParseException {
        //String email = mPreferences.getString("UserEmail", "null");
        // send GET request for user information to get address
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, urlData + "/events", null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;

                    @Override
                    public void onResponse(JSONArray response) {
                        // get the user address from the shared preference file
                        //String UserAddress = mPreferences.getString("UserAddress", "null");
                        String UserAddress = "hatikva 27 ramat hasharon";
                        boolean distance = false;
                        int central = 0;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Activity = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                float dist = getDistance(Activity.get("address").toString(), UserAddress);
                                distance = dist < (radius*1000);
                                String c = Activity.get("main").toString();
                                if(c != "null")
                                central = (Integer.parseInt(c));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (central==1 && distance) {
                                try {
                                    volunteerEventItemLists.add(new VolunteerEventItemList(Activity.get("name").toString(), Activity.get("date").toString(), Activity.get("about_volunteering").toString(), Activity.get("value_in_coins").toString(), Activity.get("duration").toString(), Activity.get("about_place").toString(), Activity.get("picture").toString()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Recycler_view_list_Central = (RecyclerView) findViewById(R.id.Recycler_view_list_Central);
                        VolunteerItemHomeAdapter adapter_list_Central = (VolunteerItemHomeAdapter) new VolunteerItemHomeAdapter(c, eventsListNearBy);
                        Recycler_view_list_Central.setAdapter(adapter_list_Central);
                        Recycler_view_list_Central.setHasFixedSize(true);
                        Recycler_view_list_Central.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.HORIZONTAL, false));

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
    // הופך כתובת לאורך ורוחב
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



