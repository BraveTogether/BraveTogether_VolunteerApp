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
//        checkForCurrentUserState();
        if(prefs.getString("user_details",null) == null){
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void checkForCurrentUserState(){

        { //Getting the saved account if it exists.
            acct = GoogleSignIn.getLastSignedInAccount(this);
            accessToken = AccessToken.getCurrentAccessToken();
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
        }

        if (!prefs.getBoolean("first_time", false)) {
            Intent intent = new Intent(app.this, IntroFirstTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (acct != null) {
                uid = acct.getId(); //Make a sheilta that takes ID brings UserDetails
                goToHomeActivity();
            } else if (accessToken != null) {
                uid = accessToken.getUserId();
                goToHomeActivity();
            } else if (prefs.getString("uid", null) != null) {
                uid = prefs.getString("uid", null);
                goToHomeActivity();
            } else {
                Intent intent = new Intent(app.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    public void goToHomeActivity(){
        Intent intent = new Intent(app.this, RegisterActivity.class);
        CallToServer getAccountDetails = new CallToServer();
        userDetails = getAccountDetails.getUserDetails(uid,this);
        Log.i("lalala",userDetails);
        intent.putExtra("user_details",userDetails);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
