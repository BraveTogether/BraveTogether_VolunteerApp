package com.example.bravetogether_volunteerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VolunteerManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VolunteerManagerFragment extends Fragment {

    public VolunteerManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VolunteerManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VolunteerManagerFragment newInstance(String param1, String param2) {
        VolunteerManagerFragment fragment = new VolunteerManagerFragment();
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
        return inflater.inflate(R.layout.fragment_volunteer_manager, container, false);
    }
}