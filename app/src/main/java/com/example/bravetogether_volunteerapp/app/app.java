package com.example.bravetogether_volunteerapp.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.bravetogether_volunteerapp.LoginFlow.LoginActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.example.bravetogether_volunteerapp.MainActivity;
import com.facebook.AccessToken;
import com.facebook.login.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class app extends Application {

    private String uid;

    public app(){
        //No context

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Context
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(acct != null){
            Intent intent = new Intent(app.this, MainActivity.class);
            uid = acct.getId();
            intent.putExtra("uid",uid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //Get the UID to load up user
        }else if(accessToken != null){
            uid = accessToken.getUserId();
            Intent intent = new Intent(app.this,MainActivity.class);
            intent.putExtra("uid",uid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //Get the UID to load up user
        }else if(prefs.getString("uid",null) != null){
            //Send the UID to the next activity
            Intent intent = new Intent(app.this,MainActivity.class);
            uid = prefs.getString("uid",null);
            intent.putExtra("uid",uid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(app.this, RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
