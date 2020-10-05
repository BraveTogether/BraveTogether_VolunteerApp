package com.example.bravetogether_volunteerapp.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.bravetogether_volunteerapp.LoginFlow.CreateAccountFirstActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.IntroFirstTimeActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;
import com.example.bravetogether_volunteerapp.home;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class app extends Application {

    private SharedPreferences prefs;

    public app(){
        //No context
    }

    public static String getSha1Hex(String clearString)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes)
            {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        }
        catch (Exception ignored)
        {
            ignored.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(!prefs.contains("first_time")){
            Intent intent = new Intent(this, IntroFirstTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(prefs.contains("UID")){ //Checks if there is a user logged in
            Intent intent = new Intent(this, home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
