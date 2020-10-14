package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;


public class ProfileHeaderFragment extends Fragment implements View.OnClickListener {

    public ProfileHeaderFragment() {
        // Required empty public constructor
    }

    public static ProfileHeaderFragment newInstance() {
        ProfileHeaderFragment fragment = new ProfileHeaderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_header, container, false);
        ((ImageView)v.findViewById(R.id.change_details_image)).setOnClickListener(this);
        // get preferences file
        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        //TODO: check if all the mPreferences names are correct
        String email = mPreferences.getString("email", "user@mymail.com");
        String fullName = mPreferences.getString("first_name", "ישראל") + " " + mPreferences.getString("last_name", "ישראלי");
        String phoneNum = mPreferences.getString("UserPhoneNumber", "0547777777");
        String bio = mPreferences.getString("UserPhoneNumber", "כאן המשתמש כותב את הפרטים על עצמו");
        String userType = mPreferences.getString("UserType", "סוג פרופיל");
        //TODO: check if all the mPreferences names are correct
        ((TextView)v.findViewById(R.id.FullNameText)).setText(fullName);
        ((TextView)v.findViewById(R.id.EmailText)).setText(email);
        ((TextView)v.findViewById(R.id.PhoneNumber)).setText(phoneNum);
        ((TextView)v.findViewById(R.id.UserBio)).setText(bio);
        ((TextView)v.findViewById(R.id.UserType)).setText(userType);
        return v;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }
}