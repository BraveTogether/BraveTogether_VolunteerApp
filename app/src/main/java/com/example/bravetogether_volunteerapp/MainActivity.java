package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
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
