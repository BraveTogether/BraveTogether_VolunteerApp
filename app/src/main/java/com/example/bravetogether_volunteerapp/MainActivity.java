package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName(); //Google Name
            Toast.makeText(this, personName, Toast.LENGTH_SHORT).show();
            String personGivenName = acct.getGivenName(); //Only the first name
            String personFamilyName = acct.getFamilyName(); //Only the family name
            String personEmail = acct.getEmail(); //google email
            String personId = acct.getId(); //The user ID --- Use this to identify the user on the database
            Uri personPhoto = acct.getPhotoUrl(); //A profile picture from google
        }
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    public void goToFirstTime(View view){
        Intent intent = new Intent(MainActivity.this,IntroFirstTimeActivity.class);
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
}
