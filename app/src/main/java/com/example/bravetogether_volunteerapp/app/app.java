package com.example.bravetogether_volunteerapp.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.bravetogether_volunteerapp.LoginFlow.IntroFirstTimeActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.example.bravetogether_volunteerapp.MainActivity;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class app extends Application {

    private String uid;
    AccessToken accessToken;
    GoogleSignInAccount acct;
    SharedPreferences prefs;

    public app(){
        //No context

    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkForCurrentUserState();
    }

    public void checkForCurrentUserState(){
        acct = GoogleSignIn.getLastSignedInAccount(this);
        accessToken = AccessToken.getCurrentAccessToken();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("first_time", false)) {
            Intent intent = new Intent(app.this, IntroFirstTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (acct != null) {
                Intent intent = new Intent(app.this, MainActivity.class);
                uid = acct.getId();
                //Implement call to server to check if user signed up or not
                intent.putExtra("uid", uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (accessToken != null) {
                uid = accessToken.getUserId();
                //Implement call to server to check if user signed up or not
                Intent intent = new Intent(app.this, MainActivity.class);
                intent.putExtra("uid", uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (prefs.getString("uid", null) != null) {
                Intent intent = new Intent(app.this, MainActivity.class);
                uid = prefs.getString("uid", null);
                //Implement call to server to get all the user data
                intent.putExtra("uid", uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(app.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
