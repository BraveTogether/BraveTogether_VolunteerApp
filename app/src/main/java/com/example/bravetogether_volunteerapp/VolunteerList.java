package com.example.bravetogether_volunteerapp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class VolunteerList extends AppCompatActivity {

    static Context parentContext = ItemListActivity.parentContext;

    static JSONArray activities;

    // this and the map were final
    public static List<VolunteerItem> ITEMS = new ArrayList<VolunteerItem>();
    public static Map<String, VolunteerItem> ITEM_MAP = new HashMap<String, VolunteerItem>();

    static {
        /*  Get the unpublished volunteers from the database
        String URL = parentContext.getResources().getString(R.string.apiUrl)+"/volunteers/published/false";
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    JSONObject Activity;
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Activity = response.getJSONObject(i);
                                addItem(createVolunteerItem(Activity.getString("uid"),Activity.getString("name"), Activity.getString("about_volunteering"), Activity.getString("credits"),Activity.getString("duration")));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", "there was an error!!! :(");
                    }
                });
        VolleySingleton.getInstance(parentContext).addToRequestQueue(JsonArrayRequest);
         */

        //get the list of relevant activities.
        //List<JSONObject> activitiesList = ItemListActivity.activitiesList;
        List<JSONObject> activitiesList = FilterActivity.activities;
        for (int i=0; i<activitiesList.size(); i++)
        {
            JSONObject volunteer = (JSONObject) activitiesList.get(i);
            // add volunteers to the list displayed
            try {
                String distance = volunteer.getString("distance_from_user");
                int dist = (int)Float.parseFloat(distance);
                // parse the distance from user string to meters or kilometers.
                if (Float.parseFloat(distance)>1000) {
                    dist = dist/1000;
                    distance = String.valueOf(dist) + " קמ";
                }
                else {
                    distance =  dist + " מ'";
                }
                String duration = volunteer.getString("duration") + " דק";
                addItem(createVolunteerItem(String.valueOf(i) ,volunteer.getString("name"), volunteer.getString("about_volunteering"), volunteer.getString("value_in_coins"), duration, distance, volunteer.getString("picture"), volunteer.getString("address")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(){
        ITEMS = new ArrayList<VolunteerItem>();
        List<JSONObject> activitiesList = FilterActivity.activities;
        for (int i=0; i<activitiesList.size(); i++)
        {
            JSONObject volunteer = (JSONObject) activitiesList.get(i);
            // add volunteers to the list displayed
            try {
                String distance = volunteer.getString("distance_from_user");
                int dist = (int)Float.parseFloat(distance);
                // parse the distance from user string to meters or kilometers.
                if (Float.parseFloat(distance)>1000) {
                    dist = dist/1000;
                    distance = String.valueOf(dist) + " קמ";
                }
                else {
                    distance =  dist + " מ'";
                }
                String duration = volunteer.getString("duration") + " דק";
                addItem(createVolunteerItem(String.valueOf(i) ,volunteer.getString("name"), volunteer.getString("about_volunteering"), volunteer.getString("value_in_coins"), duration, distance, volunteer.getString("picture"), volunteer.getString("address")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
    //                img.setImageBitmap(bitmap);

    private static void addItem(VolunteerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    // creates the header
    private static VolunteerItem createVolunteerItem(String id, String name, String description, String credits, String duration, String distance, String URI ,String address) {
        // create the volunteer object
        return new VolunteerItem(id, name, duration, distance, URI, makeDetails(description, credits, duration, address));
    }

    //creates the description section
    private static String makeDetails(String description, String credits, String duration, String address) {
        StringBuilder builder = new StringBuilder();
        builder.append(description +"\n\n");
        builder.append("כתובת: " + address +"\n");
        builder.append("קרדיטים: " + credits +"\n");
        builder.append("משך ההתנדבות: " + duration +"\n");
        return builder.toString();
    }

    /**
     * A Volunteer item representing a piece of content.
     */
    public static class VolunteerItem {
        public String id;
        public String content;
        public String details;
        public String duration;
        public String distance;
        public String URI;

        public VolunteerItem(String id, String content,String duration, String distance, String URI ,String details) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.duration = duration;
            this.distance = distance;
            this.URI = URI;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}