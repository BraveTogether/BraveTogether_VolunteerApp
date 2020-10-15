package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bravetogether_volunteerapp.adapters.ProfileFragmentEventAdapter;
import com.example.bravetogether_volunteerapp.adapters.Tags_ProfileFragment_Adapter;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment2 extends Fragment {

    // Variables:
   private CircleImageView mProfileImage;
   private TextView mUserName, mVolunteerHours, mCoins, mProcess;
   private  View ProfileView;
   private RecyclerView rcTags,rcNearVolunteers, rcMyVolunteer;
   private Context context;


   // DB and sharedPrefFile:
   //private final String url = getResources().getString(R.string.apiUrl);
   //private final String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
   private SharedPreferences mPreferences;


    public ProfileFragment2(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProfileView = inflater.inflate(R.layout.fragment_profile2, container, false);
        setUserDate();
        setNearVolunteer();
        setMyVolunteers();
        setTags();
        return ProfileView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Access to DB / SharedFile to get user data
    private void setUserDate(){
        // init variables
        mProfileImage = (CircleImageView) ProfileView.findViewById(R.id.imageViewProfilePic);
        mUserName = (TextView) ProfileView.findViewById(R.id.textViewUserName);
        mVolunteerHours = (TextView) ProfileView.findViewById(R.id.txtHours);
        mCoins = (TextView) ProfileView.findViewById(R.id.txtCoins);
        mProcess = (TextView) ProfileView.findViewById(R.id.txtProcess);
        //
        //  TODO:: check keys and types correctness.
        //
        // Set user name, if username is not in the shared file user name is פלוני אלמוני
        String username = mPreferences.getString("first_name", "פלוני") +
                          " " + mPreferences.getString("last_name","אלמוני");
        mUserName.setText(username);
        //  Set volunteer hours if not in shared file display -1.
        mVolunteerHours.setText(mPreferences.getInt("volunteer_hours",-1)+"");
        //  Set Coins if not in shared file display -1.
        mCoins.setText(mPreferences.getInt("credits",-1)+"");
        //  Set process if not in shared file display -1%.
        mProcess.setText(mPreferences.getInt("process",-1)+"%");

        // TODO:: handle profile image.
//        Picasso.with(Profile.this)
//                .load(Image)
//                .placeholder(R.drawable.icon_user)
//                .into(ProfileImage);

    }


    // RecyclerView handlers -- Horizontal scrolling --
    private void setMyVolunteers(){
        // TODO:: poll volunteer history from server.
        ArrayList<ProfileEventObject> list = createDummyEventList(); // need to change to correct list.
        rcMyVolunteer = ProfileView.findViewById(R.id.rcMyVolunteers);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcMyVolunteer,adapter);
    }

    private void setNearVolunteer(){
        // TODO:: check user location and define near location (what is the radius)
        // TODO:: poll near volunteer from server.
        ArrayList<ProfileEventObject> list = setNearEvents(100); // need to change to correct list.
        System.out.println("************* "+list.size()+" **********");

        rcNearVolunteers = ProfileView.findViewById(R.id.rcNearVolunteer);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcNearVolunteers,adapter);
    }

    private void setTags(){
        ArrayList<TagObject> list = createDummyTagList(); //need to change to correct list
        rcTags = ProfileView.findViewById(R.id.rcTags);
        Tags_ProfileFragment_Adapter adapter = new Tags_ProfileFragment_Adapter(context, list);
        setRecyclerViewSetting(rcTags,adapter);
    }

    private void setRecyclerViewSetting(RecyclerView view, RecyclerView.Adapter adapter){
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false ));
        view.setAdapter(adapter);
        view.setNestedScrollingEnabled(false);
    }



    // copied from FillterActivity if function become static we can delete this.
    public float getDistance(String address1, String address2) {

        LatLng place1 = FilterActivity.getLocationFromAddress(context, address1);
        LatLng place2 = FilterActivity.getLocationFromAddress(context, address2);
        Location locationA = new Location("location");

        locationA.setLatitude(place1.latitude);
        locationA.setLongitude(place1.longitude);

        Location locationB = new Location("location");

        locationB.setLatitude(place2.latitude);
        locationB.setLongitude(place2.longitude);


        return locationA.distanceTo(locationB);
    }

    // modified Search method from FillterActivity -  set dbNearEvent list with all the near event.
    private ArrayList<ProfileEventObject> setNearEvents(final int radius){
        String url =  getResources().getString(R.string.apiUrl) + "volunteers/confirmed/1";
//        final String userAddress = mPreferences.getString("userAddress",
//                "Remez 8, Tel Aviv, Israel");  // default address may need to change.
        final String userAddress = "hatikva 27 ramat hasharon";
        final ArrayList<ProfileEventObject> result = new ArrayList<>();
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    JSONObject jsonEvent;

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("PF2_setNearEvent","OnResponse_Start");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonEvent = response.getJSONObject(i);
                                System.out.println("***"+jsonEvent.getString("name")+"***");
                                int online = jsonEvent.getInt("online"); // 1-> event is online, 0-> frontal event.
                                float dist = (online == 0)? getDistance(jsonEvent.get("address").toString(), userAddress) : 0;
                                if(dist < radius*1000){
                                    System.out.println(dist);
                                    result.add(new ProfileEventObject(jsonEvent));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PF2_setNearEvent","Error::"+error.getMessage());
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(JsonArrayRequest);
        return result;
    }

    // classes for adapters
    public  class ProfileEventObject{
        private String headline;
        private String date;
        private String start_time;
        private String location;
        private String imgUrl;
        private String credits;
        private long uid;       // need to check how uid is saved.

        public ProfileEventObject(long uid,String headline, String date, String start_time, String location,String credits, String imgUrl) {
            this.headline = headline;
            this.date = date;
            this.start_time = start_time;
            this.location = location;
            this.imgUrl = imgUrl;
            this.uid = uid;
            this.credits=credits;
        }

        public ProfileEventObject(JSONObject activity){
            try {
                this.headline = activity.getString("name");
                this.start_time = activity.get("start_time").toString(); // TODO:: check format.
                this.location = activity.getString("address");
                this.imgUrl = activity.getString("picture");
                this.uid = activity.getLong("id");
               // this.credits = activity.getInt("value_in_coins")+"";
                this.date = "dummydate"; // TODO:: get correct date.
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public String getCredits() {
            return credits;
        }

        public String getHeadline() {
            return headline;
        }

        public String getDate() {
            return date;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getLocation() {
            return location;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public long getUid() {
            return uid;
        }
    }

    public class TagObject{
        private String tagName;
        private String tagDetails;
        private String tagImgUrl;

        public  TagObject(String name, String details, String imgUrl){
            this.tagName = name;
            this.tagDetails = details;
            this.tagImgUrl = imgUrl;
        }

        public String getTagDetails() {
            return tagDetails;
        }

        public String getTagImgUrl() {
            return tagImgUrl;
        }

        public String getTagName() {
            return tagName;
        }
    }

    // TODO:: Delete dummy functios
    // Dummy function for test ** need to be deleted **
    private ArrayList<ProfileEventObject> createDummyEventList(){
        ArrayList<ProfileEventObject> dummyList =  new ArrayList<>();
        dummyList.add(new ProfileEventObject(1,"עזרה בקניות","16.6.2020","17:30",
                                            "הורקנוס 7, תל אביב","300","" ));
        dummyList.add(new ProfileEventObject(2,"עזרה בקניות","16.6.2020","17:30",
                                    "הורקנוס 7, תל אביב","300","" ));
        dummyList.add(new ProfileEventObject(3,"עזרה בקניות","16.6.2020","17:30",
                                             "הורקנוס 7, תל אביב","300","" ));
        return dummyList;
    }

    private  ArrayList<TagObject> createDummyTagList(){
        ArrayList<TagObject> list = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            list.add(new TagObject("אות הגבורה","30 שעות התנדבות", ""));
        }
        return list;
    }

}