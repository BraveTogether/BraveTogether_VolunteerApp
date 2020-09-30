package com.example.bravetogether_volunteerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        return v;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }
}