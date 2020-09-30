package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class RegularProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_profile);

        final String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String UserType = mPreferences.getString("UserAccessType", "editor");

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
            case "VolunteerManager":
                ft.replace(R.id.body, new VolunteerManagerFragment());
            break;
        }
        ft.commit();
    }
}