package com.example.bravetogether_volunteerapp.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telecom.Call;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.bravetogether_volunteerapp.CallToServer;
import com.example.bravetogether_volunteerapp.LoginFlow.IntroFirstTimeActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.LoginActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.example.bravetogether_volunteerapp.MainActivity;
import com.example.bravetogether_volunteerapp.home;
import com.facebook.AccessToken;
import com.facebook.login.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class app extends Application {

    private String uid;
    private AccessToken accessToken;
    private GoogleSignInAccount acct;
    private SharedPreferences prefs;
    String userDetails;

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
            Intent intent = new Intent(this, home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
