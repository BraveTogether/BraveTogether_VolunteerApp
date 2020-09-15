package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VolunteerApprovalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_approval);
    }

    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I just signed up for this ___ volunteering event. join me now!.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void goToIntroPage(View view) {
        Intent i = new Intent(VolunteerApprovalActivity.this, IntroFirstTimeActivity.class);
        startActivity(i);
    }
}