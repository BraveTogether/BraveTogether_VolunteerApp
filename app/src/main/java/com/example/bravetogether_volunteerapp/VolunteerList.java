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
 * TODO: Replace all uses of this class before publishing your app.
 */
public class VolunteerList extends AppCompatActivity {

    static Context parentContext = ItemListActivity.parentContext;

    static JSONArray activities;
    /**
     * An array of sample (dummy) items.
     */
    public static final List<VolunteerItem> ITEMS = new ArrayList<VolunteerItem>();

    /**
     * A map of sample (Volunteer) items, by ID.
     */
    public static final Map<String, VolunteerItem> ITEM_MAP = new HashMap<String, VolunteerItem>();

    private static final int COUNT = 25;


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
        ArrayList<JSONObject> activitiesList = ItemListActivity.activitiesList;
        for (int i=0; i<activitiesList.size(); i++)
        {
            JSONObject volunteer = (JSONObject) activitiesList.get(i);
            // add volunteers to the list displayed
            try {
                String distance = volunteer.getString("distance_from_user");
                // parse the distance from user string to meters or kilometers.
                if (Float.parseFloat(distance)>1000) {
                    distance = String.valueOf(Float.parseFloat(distance)/1000) + " קמ";
                }
                else {
                    distance = String.valueOf(Float.parseFloat(distance)) + " מ'";

                }
                addItem(createVolunteerItem(String.valueOf(i) ,volunteer.getString("name"), volunteer.getString("description"), volunteer.getString("credits"), volunteer.getString("duration"), distance));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //statically add volunteers for testings.
        addItem(createVolunteerItem("1","יום הולדת", "אנחנו רוצים לחגוג יום הולדת לניצול שואה", "10", "30דק","500"));
        addItem(createVolunteerItem("2","צעדה", "אנחנו רוצים לעשות צעדה על מנת להעלות את המודעות", "20", "1 שעה", "300"));
        addItem(createVolunteerItem("3","פעילות אחרת", "תיאור של הפעילות תיאור תיאור תיאור", "20", "45דק","1500"));

    }

    private static void addItem(VolunteerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    // creates the header
    private static VolunteerItem createVolunteerItem(String id, String name, String description, String credits, String duration, String distance) {
        // create the volunteer object
        return new VolunteerItem(id, name, duration, distance, makeDetails(name, description, credits));
    }

    //creates the description section
    private static String makeDetails(String name, String description, String credits) {
        StringBuilder builder = new StringBuilder();
        builder.append("פרטים לגבי התנדבות: " + name +"\n");
        builder.append("תיאור: " + description +"\n");
        builder.append("קרדיטים: " + credits +"\n");
        return builder.toString();
    }

    /**
     * A Volunteer item representing a piece of content.
     */
    public static class VolunteerItem {
        public final String id;
        public final String content;
        public final String details;
        public final String duration;
        public final String distance;

        public VolunteerItem(String id, String content,String duration, String distance, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.duration = duration;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}