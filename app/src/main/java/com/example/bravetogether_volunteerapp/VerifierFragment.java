package com.example.bravetogether_volunteerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bravetogether_volunteerapp.RegularProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerifierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifierFragment extends Fragment {

    public VerifierFragment() {
        // Required empty public constructor
    }

    public static VerifierFragment newInstance(String param1, String param2) {
        VerifierFragment fragment = new VerifierFragment();
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
        return inflater.inflate(R.layout.fragment_verifier, container, false);
    }
}