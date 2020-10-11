package com.example.bravetogether_volunteerapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bravetogether_volunteerapp.LoginFlow.CreateAccountFirstActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.IntroFirstTimeActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.NotificationActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

/**
 *
 *     // get the shared preferences file
 *     private final String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
 *     private SharedPreferences mPreferences;
 *     mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
 *
 *     //change or create the value of UserEmail
 *     SharedPreferences.Editor preferencesEditor = mPreferences.edit();
 *     preferencesEditor.putString("UserEmail", email);
 *
 *     // get the value of UserEmail when the default value (if there is no value is noy@mail.gmail)
 *     String email = mPreferences.getString("UserEmail", "noy@mail.gmail");
 */

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Catch the intent with the UID from the app class.
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(MainActivity.this,RegularProfileActivity.class);
        startActivity(intent);
    }

    public void goToFirstTime(View view){
        Intent intent = new Intent(MainActivity.this, IntroFirstTimeActivity.class);
        startActivity(intent);
    }

    public void goToGeneralActivity(View view){
        Intent intent = new Intent(MainActivity.this,GeneralActivity.class);
        startActivity(intent);
    }

    public void goToFilterPage(View view) {
        Intent intent = new Intent(MainActivity.this,FilterActivity.class);
        startActivity(intent);
    }

    public void goToNormalRegistration(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAccountFirstActivity.class);
        startActivity(intent);
    }

    public void goToNotification(View view) {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        startActivity(intent);
    }

    public void scanner(View view) {
        Intent intent = new Intent(MainActivity.this, Scanner.class);
        startActivity(intent);
    }

    public void goToCreateVolunteer(View view) {
        Intent intent = new Intent(MainActivity.this, CreateVolunteerActivity.class);
        startActivity(intent);
    }

    public void goProfileFragment(View view){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_test,new ProfileFragment2(),null).commit();
    }
}
