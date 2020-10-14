package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class RegularUserFragment extends Fragment {
    public RegularUserFragment() {
        // Required empty public constructor
    }

    public static RegularUserFragment newInstance(String param1, String param2) {
        RegularUserFragment fragment = new RegularUserFragment();
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
        View v = inflater.inflate(R.layout.fragment_regular_user, container, false);

        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        //Set the Values form SharedPref to the text
        String distance, duration, type, hours;
        distance = mPreferences.getString("UserDistance", "10");
        duration = mPreferences.getString("UserDuration", "2");
        hours = mPreferences.getString("hours", "צהריים");
        type = mPreferences.getString("UserType", "כל הסוגים");
        final TextView typeText, durationText, distanceText, hoursText;
        distanceText = (TextView)(v.findViewById(R.id.distance));
        durationText = (TextView)(v.findViewById(R.id.duration));
        typeText = (TextView)(v.findViewById(R.id.type));
        hoursText = (TextView)(v.findViewById(R.id.hours));
        distanceText.setText(distance + " קמ");
        durationText.setText(duration + " שעה");
        typeText.setText(type);
        hoursText.setText(hours);

        //Checks if the switch is on and change the text color
        Switch s = v.findViewById(R.id.switch_button);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                typeText.setTextColor(Color.parseColor("#001925"));
                distanceText.setTextColor(Color.parseColor("#001925"));
                hoursText.setTextColor(Color.parseColor("#001925"));
                durationText.setTextColor(Color.parseColor("#001925"));}

                else {
                    typeText.setTextColor(Color.parseColor("#8C96A2"));
                    distanceText.setTextColor(Color.parseColor("#8C96A2"));
                    hoursText.setTextColor(Color.parseColor("#8C96A2"));
                    durationText.setTextColor(Color.parseColor("#8C96A2"));
                }

            }
        });
        return v;
    }
}