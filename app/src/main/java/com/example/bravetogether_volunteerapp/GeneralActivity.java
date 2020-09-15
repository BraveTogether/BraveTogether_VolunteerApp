package com.example.bravetogether_volunteerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GeneralActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //Refers to the navigation buttons of the list view
    ListView optionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        bottomNavigationView = findViewById(R.id.topNavigationDrawer);
        optionsListView = findViewById(R.id.volunteeringOptionsListView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.in_place){

                }else{

                }
                return false;
            }
        });
    }
}