package com.example.bravetogether_volunteerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        String distance, duration, type;
        distance = mPreferences.getString("UserDistance", "10");
        duration = mPreferences.getString("UserDuration", "2");
        type = mPreferences.getString("UserType", "כל הסוגים");
        TextView typeText, durationText, distanceText;
        distanceText = (TextView)(v.findViewById(R.id.distance));
        durationText = (TextView)(v.findViewById(R.id.duration));
        typeText = (TextView)(v.findViewById(R.id.hours));
        distanceText.setText(distance + " קמ");
        durationText.setText(duration + " שעה");
        typeText.setText(type);
        return v;
    }
}