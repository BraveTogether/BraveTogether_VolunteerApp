package com.example.bravetogether_volunteerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.bravetogether_volunteerapp.LoginFlow.NotificationActivity;

public class RegisterWhereActivity extends AppCompatActivity {

    private static SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_where);
        String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    public void choose(View view) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        ConstraintSet c = new ConstraintSet();
        c.clone(this, R.layout.activity_register_where);
        switch ((String)view.getTag()) {
            case "home":
                c.setVerticalBias(R.id.checker, (float)0.14);
                preferencesEditor.putString("UserDesiredLocation", "online");
                break;
            case "near":
                c.setVerticalBias(R.id.checker, (float)0.5);
                preferencesEditor.putString("UserDesiredLocation", "NotOnline");
                break;
            default:
                c.setVerticalBias(R.id.checker, (float)0.86);
                preferencesEditor.putString("UserDesiredLocation", "both");
        }
    }

    public void goToNotification(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void skip(View view) {
    }
}