package com.example.bravetogether_volunteerapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.adapters.ProfileFragmentEventAdapter;
import com.example.bravetogether_volunteerapp.adapters.profileTagAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment2 extends Fragment {

    // Variables:
    private CircleImageView mProfileImage;
    private TextView mUserName, mVolunteerHours, mCoins, mProcess;
    private View ProfileView;
    private RecyclerView rcTags,rcNearVolunteers,rcVolunteerHistory;
    private Context context;
    private ArrayList<String> mTagName = new ArrayList<>();
    private ArrayList<String> mTagDetails = new ArrayList<>();
    public ProfileFragment2(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("ProfileFragment2", "onBindViewHolder: called");
        ProfileView = inflater.inflate(R.layout.fragment_profile2, container, false);
        setUserDate();
        setNearVoluteer();
        setVolunteerHistory();
        profileTagAdapter p = new profileTagAdapter(context, mTagName, mTagDetails);
        rcTags = ProfileView.findViewById(R.id.rcTags);
        setRecyclerViewSetting(rcTags,p);
        return ProfileView;
    }



    // Access to DB to get user data
    private void setUserDate(){
        // init variables
        mProfileImage = (CircleImageView) ProfileView.findViewById(R.id.imageViewProfilePic);
        mUserName = (TextView) ProfileView.findViewById(R.id.textViewUserName);
        mVolunteerHours = (TextView) ProfileView.findViewById(R.id.txtHours);
        mCoins = (TextView) ProfileView.findViewById(R.id.txtCoins);
        mProcess = (TextView) ProfileView.findViewById(R.id.txtProcess);
        // TODO:: poll user data from server and set variables
    }

    private void setVolunteerHistory(){
        // TODO:: poll volunteer history from server.
        ArrayList<VolunteerEvent> list = createDummyList(); // need to change to correct list.
        rcVolunteerHistory = ProfileView.findViewById(R.id.rcVolunteerHistory);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcVolunteerHistory,adapter);

    }

    private void setNearVoluteer(){
        // TODO:: check user location and define near location (what is the radius)
        // TODO:: poll near volunteer from server.
        ArrayList<VolunteerEvent> list = createDummyList(); // need to change to correct list.
        rcNearVolunteers = ProfileView.findViewById(R.id.rcNearVolunteer);
        ProfileFragmentEventAdapter adapter = new ProfileFragmentEventAdapter(context,list);
        setRecyclerViewSetting(rcNearVolunteers,adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // Dummy function for test ** need to be deleted **
    private ArrayList<VolunteerEvent> createDummyList(){
        ArrayList<VolunteerEvent> dummyList =  new ArrayList<>();
        mTagDetails.add("30 שעות התנדבות");
        mTagName.add("אות גבורה");
        dummyList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        dummyList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        dummyList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        return dummyList;
    }


    private void setRecyclerViewSetting(RecyclerView view, RecyclerView.Adapter adapter){
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false ));
        view.setAdapter(adapter);
        view.setNestedScrollingEnabled(false);
    }

}