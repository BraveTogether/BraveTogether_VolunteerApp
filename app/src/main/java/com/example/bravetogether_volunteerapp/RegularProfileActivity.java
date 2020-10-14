package com.example.bravetogether_volunteerapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class RegularProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_profile);

        final String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String UserType = mPreferences.getString("UserAccessType", "user");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch(UserType){
            case "user":
                ft.replace(R.id.body, new RegularUserFragment());
            break;
            case "verifier":
                ft.replace(R.id.body, new VerifierFragment());
            break;
            case "manager":
                ft.replace(R.id.body, new ManagerFragment());
            break;
            case "editor":
                ft.replace(R.id.body, new EditorFragment());
            break;
            case "volunteerManager":
                ft.replace(R.id.body, new VolunteerManagerFragment());
            break;
        }
        ft.commit();
    }
}