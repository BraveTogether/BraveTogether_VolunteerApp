package com.example.bravetogether_volunteerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.bravetogether_volunteerapp.LoginFlow.IntroFirstTimeActivity;
import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

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
        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
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
        Intent intent = new Intent(MainActivity.this,CreateAccountFirstActivity.class);
        startActivity(intent);
    }
}
