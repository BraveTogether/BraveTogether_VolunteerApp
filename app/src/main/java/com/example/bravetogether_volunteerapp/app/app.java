package com.example.bravetogether_volunteerapp.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.bravetogether_volunteerapp.LoginFlow.LoginActivity;
import com.example.bravetogether_volunteerapp.HomeActivity;

public class app extends Application {

    private SharedPreferences prefs;

    public app(){
        //No context
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences("user",MODE_PRIVATE);
//        checkForCurrentUserState();
        if(prefs.contains("UID")){ //Checks if there is a user logged in
            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
