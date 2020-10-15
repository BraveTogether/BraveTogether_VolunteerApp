package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

import java.util.ArrayList;

public class EditNotificationDetails extends AppCompatActivity {

    // variable that holds the details of the user volunteer,
    //in the onCreate function the variable updated from the shared preferences file
    //TODO check what type of variable the DB holds
    private String distanceRadius;
    private String timesOfVolunteer;
    private String typeOfVolunteer;

    //ArrayList the holds all the button for easy iteration.
    ArrayList<TextView> buttonsDistanceRadius = new ArrayList<>();
    ArrayList<TextView> buttonsTimesOfVolunteer = new ArrayList<>();
    ArrayList<TextView> buttonsTypeOfVolunteer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notification_details);

        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
       SharedPreferences mPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        //TODO: check if all the mPreferences names are correct
        distanceRadius = mPreferences.getString("notification_address", "10");
        timesOfVolunteer = mPreferences.getString("times", "morning");
        typeOfVolunteer = mPreferences.getString("type", "online");
        //TODO: check if all the mPreferences names are correct

        //Add all buttons
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance10));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance15));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distance20));
        buttonsDistanceRadius.add((TextView)this.findViewById(R.id.distanceUnlimited));


        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesMorning));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesNoon));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesEvening));
        buttonsTimesOfVolunteer.add((TextView)this.findViewById(R.id.timesUnlimited));

        buttonsTypeOfVolunteer.add((TextView)this.findViewById(R.id.typeOnline));
        buttonsTypeOfVolunteer.add((TextView)this.findViewById(R.id.typePhysical));

        setDistanceRadius(null);
        setTimesOfVolunteer(null);
        setTypeOfVolunteer(null);
    }


    /**
     * Called when a view(distanceRadius) has been clicked.
     *Change the color of the button and set variable.
     * @param view The view that was clicked, if view is null, function called in onCreate to set
     *         the view of the page
     */
    public void setDistanceRadius(View view){
        int position = 0;
        String [] s = {"10", "15", "20", "unlimited"};
        if (view != null) {
            for (View v : buttonsDistanceRadius) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    distanceRadius = s[position];
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (distanceRadius) {
                case "10":
                    buttonsDistanceRadius.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "15":
                    buttonsDistanceRadius.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "20":
                    buttonsDistanceRadius.get(2).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "Unlimited":
                    buttonsDistanceRadius.get(3).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }


    public void setTimesOfVolunteer(View view){
        int position = 0;
        String [] s = {"morning", "noon", "evening" ,  "unlimited"};
        if (view != null) {
            for (View v : buttonsTimesOfVolunteer) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    timesOfVolunteer = s[position];
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (timesOfVolunteer) {
                case "morning":
                    buttonsTimesOfVolunteer.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "noon":
                    buttonsTimesOfVolunteer.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "evening":
                    buttonsTimesOfVolunteer.get(2).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "Unlimited":
                    buttonsTimesOfVolunteer.get(3).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }


    public void setTypeOfVolunteer(View view){
        int position = 0;
        String [] s = {"physical" , "online"};
        if (view != null) {
            for (View v : buttonsTypeOfVolunteer) {
                if (v.getId() == view.getId()) {
                    v.setBackgroundResource(R.drawable.buttonblueinside);
                    typeOfVolunteer = s[position];
                }
                else
                    v.setBackgroundResource(R.drawable.textfield_d_buttons);
                position++;
            }
        }
        else {
            switch (typeOfVolunteer) {
                case "physical":
                    buttonsTypeOfVolunteer.get(0).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
                case "online":
                    buttonsTypeOfVolunteer.get(1).setBackgroundResource(R.drawable.buttonblueinside);
                    break;
            }
        }
    }


    //TODO need to edit the profile on the DB
    public void submitEdit(View view){
        Intent intent = new Intent(this, RegularProfileActivity.class);
        startActivity(intent);
    }

}