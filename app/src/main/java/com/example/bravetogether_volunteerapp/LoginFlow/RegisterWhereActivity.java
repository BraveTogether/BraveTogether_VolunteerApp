package com.example.bravetogether_volunteerapp.LoginFlow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.preference.PreferenceManager;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.HomeActivity;

public class RegisterWhereActivity extends AppCompatActivity {

    private Intent intent,getIntent;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_where);
    }

    public void choose(View view) {
        ConstraintSet c = new ConstraintSet();
        c.clone(this, R.layout.activity_register_where);
        switch ((String)view.getTag()) {
            case "home":
                c.setVerticalBias(R.id.checker, (float)0.14);
                location = "1";
                break;
            case "near":
                c.setVerticalBias(R.id.checker, (float)0.5);
                location = "2";
                break;
            default:
                c.setVerticalBias(R.id.checker, (float)0.86);
                location = "3";
        }
    }

    public void goToNotification(View view) {
        intent = new Intent(this, NotificationActivity.class);
        getIntent = getIntent();
        intent.putExtra("first_name",getIntent.getStringExtra("private_name"));
        intent.putExtra("family_name",getIntent.getStringExtra("family_name"));
        intent.putExtra("email",getIntent.getStringExtra("email"));
        intent.putExtra("password",getIntent.getStringExtra("password"));
        intent.putExtra("phone_number",getIntent.getStringExtra("phone_number"));
        intent.putExtra("address",getIntent.getStringExtra("address"));
        intent.putExtra("about",getIntent.getStringExtra("about"));
        intent.putExtra("image",getIntent.getStringExtra("image"));
        if(location == null) {location = "3";}
        intent.putExtra("user_desired_location",location);
        startActivity(intent);
    }

    public void skip(View view) {
        startActivity(new Intent(RegisterWhereActivity.this, HomeActivity.class));
    }
}