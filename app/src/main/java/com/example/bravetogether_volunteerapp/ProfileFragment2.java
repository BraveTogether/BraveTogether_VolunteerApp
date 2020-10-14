package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.adapters.ProfileFragmentEventAdapter;
import com.example.bravetogether_volunteerapp.adapters.Tags_ProfileFragment_Adapter;

import java.util.ArrayList;

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
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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
        ArrayList<ProfileEventObject> list = createDummyEventList(); // need to change to correct list.
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