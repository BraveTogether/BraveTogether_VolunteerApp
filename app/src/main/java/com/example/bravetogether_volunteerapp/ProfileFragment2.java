package com.example.bravetogether_volunteerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.adapters.profileTagAdapter;

import java.util.ArrayList;


public class ProfileFragment2 extends Fragment  {

    private ArrayList<String> mTagName = new ArrayList<>();
    private ArrayList<String> mTagDetails = new ArrayList<>();
    private ArrayList<Integer> mTagImage = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set listeners for the cilckables
        View v = inflater.inflate(R.layout.fragment_profile2, container, false);
        mTagName.add("ארת גבורה");
        mTagDetails.add("30 שעות התנדבות");
        mTagImage.add(getResources().getIdentifier("@drawable/component400",null,"com.example.bravetogether_volunteerapp" ));
        RecyclerView recyclerView = v.findViewById(R.id.my_recycler_view);
        profileTagAdapter profileTagAdapter = new profileTagAdapter(this.getContext(), mTagName, mTagDetails, mTagImage);
        recyclerView.setAdapter(profileTagAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return v;
    }

}