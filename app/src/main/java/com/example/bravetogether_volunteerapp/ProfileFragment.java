package com.example.bravetogether_volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set listeners for the cilckables
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ((ImageView)v.findViewById(R.id.contact)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.contactText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.add_friends)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.inviteText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.about)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.aboutText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.logout)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.logoutText)).setOnClickListener(this);
        return v;
    }

    public void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "פניה בנושא האפליקציה");
        i.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(i, "Send mail..."));
    }

    public void goToAboutPage(){
    }

    public void logout(){
    //    Intent intent = new Intent(getContext(), RegisterActivity.class);
    //    String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
    //    SharedPreferences preferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void addFriends(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "It is fun!";
        String shareSub = "Go to Volunteer";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contactText:
            case R.id.contact:
                sendEmail();
                break;
            case R.id.aboutText:
            case R.id.about:
                goToAboutPage();
                break;
            case R.id.logout:
            case R.id.logoutText:
                logout();
                break;
            case R.id.add_friends:
                addFriends();
                break;
        }
    }
}