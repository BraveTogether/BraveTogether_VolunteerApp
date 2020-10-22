package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment2 extends Fragment {

    // Variables:
   private CircleImageView mProfileImage;
   private TextView mUserName, mVolunteerHours, mCoins, mProcess;
   private  View ProfileView;
   private RecyclerView rcTags,rcNearVolunteers, rcMyVolunteer;
   private Context context;
   private SeekBar sbRadius;
   private TextView txtRadius;
   private int radius;
   private final int MYVOLUNTEER = 0;
   private final int NEAREVENT = 1;


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
        radius = 10;    // init search radius to be 10KM
        setUserDate();
        setTags(new ArrayList<TagObject>());                     // TODO:: delete when setUserTag completed
       // setMyVolunteers(new ArrayList<ProfileEventObject>());    // TODO:: delete when setUserTag completed
        setNearEvents();
        setUserEvents();  //    TODO:: activate when complete
//        setUserTags();  //    TODO:: activate when complete

        return ProfileView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // set user data from sharedfile and DB to user info bar
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

    //  get a list from sever query ( see setUserEvents func ) start build RecyclerView for side scrolling
    private void setMyVolunteers( ArrayList<ProfileEventObject> list ){
        stopLoading(MYVOLUNTEER);
        //list = createDummyEventList(); //TODO:: need be deleted when setUserEvent completed, for now dummy list.
        if(list.size() == 0){
            eventsTryAgain(MYVOLUNTEER);
            return;
        }
        rcMyVolunteer = ProfileView.findViewById(R.id.rcMyVolunteers);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcMyVolunteer,adapter);
    }

    //  get a list from sever query ( see setNearEvents func ) start build RecyclerView for side scrolling
    private void setNearVolunteer(ArrayList<ProfileEventObject> list){
        stopLoading(NEAREVENT);
        if( list.size() == 0){
            eventsTryAgain(NEAREVENT);
            return;
        }
        rcNearVolunteers = ProfileView.findViewById(R.id.rcNearVolunteer);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcNearVolunteers,adapter);
    }

    //  get a list from sever query ( see setUserTags func ) start build RecyclerView for side scrolling
    private void setTags (ArrayList<TagObject> list){
        list = createDummyTagList();    //TODO:: need be deleted when setUserTags completed, for now dummy list.
        rcTags = ProfileView.findViewById(R.id.rcTags);
        Tags_ProfileFragment_Adapter adapter = new Tags_ProfileFragment_Adapter(context, list);
        setRecyclerViewSetting(rcTags,adapter);
    }

    // RecyclerView setting -- Horizontal scrolling --
    private void setRecyclerViewSetting(RecyclerView view, RecyclerView.Adapter adapter){
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false ));
        view.setAdapter(adapter);
        view.setNestedScrollingEnabled(false);
    }

    //  When server returns an empty list try againg massge will show up.
    //  layoutid param will indicate Near Events location (NEAREVENT) or My Events location (MYVOLUNTEER)
    private void eventsTryAgain(int layoutid){
        final LinearLayout layout = (LinearLayout) ProfileView.findViewById((layoutid == NEAREVENT)?R.id.LLnotFound:R.id.LLnotFoundME);
        layout.setVisibility(View.VISIBLE);
        ImageView tryAgian = (ImageView) ProfileView.findViewById((layoutid == NEAREVENT)?R.id.btnTryAgain:R.id.btnTryAgainME);
        if(layoutid == NEAREVENT){
        sbRadius = (SeekBar) ProfileView.findViewById(R.id.chooseRadius);
        sbRadius.setProgress(radius);
        txtRadius = (TextView) ProfileView.findViewById(R.id.txtRadius);
        txtRadius.setText(sbRadius.getProgress() + " קילומטר " );
        sbRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    radius = progress;
                    txtRadius.setText(seekBar.getProgress() + " קילומטר " );

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                    return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtRadius.setText(seekBar.getProgress() + " קילומטר " );
                radius = seekBar.getProgress();
            }
        });
       tryAgian.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               layout.setVisibility(View.INVISIBLE);
               setNearEvents();
           }
       });}
        else {
            tryAgian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.setVisibility(View.INVISIBLE);
                    setUserEvents();

                }
            });
        }

    }

    //  Show loading progress bar.
    //  id param will indicate Near Events location (NEAREVENT) or My Events location (MYVOLUNTEER).
    private void loadingdata(int id){
        id = (id == NEAREVENT)? R.id.NE_Pbar: R.id.ME_Pbar;
        ProgressBar bar =(ProgressBar) ProfileView.findViewById(id);
        bar.setVisibility(View.VISIBLE);
    }

    //  Stop showing loading progress bar.
    //  id param will indicate Near Events location (NEAREVENT) or My Events location (MYVOLUNTEER).
    private void stopLoading(int id){
        id = (id == NEAREVENT)? R.id.NE_Pbar: R.id.ME_Pbar;
        ProgressBar bar =(ProgressBar) ProfileView.findViewById(id);
        bar.setVisibility(View.INVISIBLE);
    }

    // copied from FillterActivity if function become static we can delete this.
    // gets 2 address and returns the distance between them.
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

    // gets from server list of near events.
    private void setNearEvents(){
        String url =  getResources().getString(R.string.apiUrl) + "volunteer_events/future";
        final String userAddress = mPreferences.getString("userAddress",
                "Remez 8, Tel Aviv, Israel");  // default address may need to change.
        final ArrayList<ProfileEventObject> result = new ArrayList<>();
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    JSONObject jsonEvent;

                    @Override
                    public void onResponse(JSONArray response) {
                        loadingdata(NEAREVENT);         // TODO:: add Progress bar element and set function.
                        Log.d("PF2_setNearEvent","OnResponse_Start");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonEvent = response.getJSONObject(i);
                                int online = jsonEvent.getInt("online"); // 1-> event is online, 0-> frontal event.
                                float dist = (online == 0)? getDistance(jsonEvent.get("address").toString(), userAddress) : 0;
                                if(dist < radius*1000){
                                    result.add(new ProfileEventObject(jsonEvent));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setNearVolunteer(result);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PF2_setNearEvent","Error::"+error.getMessage());
                        setNearVolunteer(result);
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(JsonArrayRequest);
    }

    // gets from server list of user events. TODO:: need to check properly
    private void setUserEvents(){
        final ArrayList<ProfileEventObject> result = new ArrayList<>();
        final int user_id = mPreferences.getInt("id",-1);
        if (user_id == -1){
            eventsTryAgain(MYVOLUNTEER);
            return;
        }
        String url =  getResources().getString(R.string.apiUrl) + "volunteer_events/future/"+user_id;  // TODO:: change query to get user event info
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    JSONObject jsonEvent;

                    @Override
                    public void onResponse(JSONArray response) {
                        loadingdata(MYVOLUNTEER);
                        Log.d("PF2_setUserEvent","OnResponse_Start");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonEvent = response.getJSONObject(i);
                                result.add(new ProfileEventObject(jsonEvent));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setMyVolunteers(result);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PF2_setUserEvent","Error::"+error.getMessage());
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(JsonArrayRequest);
    }

    // gets from server list of user events. TODO:: change query to get correct information.
    private void setUserTags(){
        String url =  getResources().getString(R.string.apiUrl) + "volunteers/confirmed/1";  // TODO:: change query to get user tags
        final ArrayList<TagObject> result = new ArrayList<>();
        final int user_id = mPreferences.getInt("id",-1);
        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    JSONObject jsonEvent;

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("PF2_setUserTags","OnResponse_Start");
                        for(int i = 0; i < response.length(); i++){
                            try {
                                jsonEvent = response.getJSONObject(i);
                                result.add(new TagObject(jsonEvent));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setTags(result);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PF2_setUserTags","Error::"+error.getMessage());
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(JsonArrayRequest);
    }

    // Classes for adapters

    // Holds events data - mostly used in ProfileFragmentEventAdapter.
    public  class ProfileEventObject{
        private String headline;
        private String date;
        private String start_time;
        private String location;
        private String imgUrl;
        private String credits;
        private long uid;       // TODO:: need to check how uid is saved (int / long).

        // TODO:: created for dummy events creation may be deleted
        public ProfileEventObject(long uid,String headline, String date, String start_time, String location,String credits, String imgUrl) {
            this.headline = headline;
            this.date = date;
            this.start_time = start_time;
            this.location = location;
            this.imgUrl = imgUrl;
            this.uid = uid;
            this.credits=credits;
        }

        //  Constructor which using JSONObject ( see setNearEvents or setUserEvents )
        public ProfileEventObject(JSONObject activity){
            try {
                this.headline = activity.getString("name");
                this.start_time = activity.getString("start_time");// TODO:: check format.
                this.location = activity.getString("address");
                this.imgUrl = activity.getString("picture");
                this.uid = activity.getLong("id");
                this.credits = activity.getString("value_in_coins")+"";
                this.date = activity.getString("date");//"dummydate"; // TODO:: format date

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // Getters for each field

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

        // TODO:: created for dummy tags creation may be deleted
        public  TagObject(String name, String details, String imgUrl){
            this.tagName = name;
            this.tagDetails = details;
            this.tagImgUrl = imgUrl;
        }

        //  Constructor which using JSONObject ( see setUserTags )
        public TagObject(JSONObject activity){
            try {
                this.tagName = activity.getString("name");          // TODO:: check format.
                this.tagDetails = activity.getString("details");    // TODO:: check format.
                this.tagImgUrl = activity.getString("picture");     // TODO:: check format.
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // Getters for each field:
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
        dummyList.add(new ProfileEventObject(11,"עזרה בקניות","16.6.2020","17:30",
                                            "הורקנוס 7, תל אביב","300","" ));
        dummyList.add(new ProfileEventObject(2,"עזרה בקניות","16.6.2020","17:30",
                                    "הורקנוס 7, תל אביב","300","" ));
        dummyList.add(new ProfileEventObject(3,"עזרה בקניות","16.6.2020","17:30",
                                             "הורקנוס 7, תל אביב","300","" ));
        return dummyList;
    }

    // Dummy function for test ** need to be deleted **
    private  ArrayList<TagObject> createDummyTagList(){
        ArrayList<TagObject> list = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            list.add(new TagObject("אות הגבורה","30 שעות התנדבות", ""));
        }
        return list;
    }

}