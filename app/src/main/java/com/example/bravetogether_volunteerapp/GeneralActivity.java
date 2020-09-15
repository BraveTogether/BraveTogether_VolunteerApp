package com.example.bravetogether_volunteerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bravetogether_volunteerapp.adapters.GeneralActivityListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GeneralActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //Refers to the navigation buttons of the list view
    ListView optionsListView;
    GeneralActivityListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        String title[] = {"one","two","three"}; //Placeholders untill we have real data
        String description[] = {"one","two","three"};

        bottomNavigationView = findViewById(R.id.topNavigationDrawer);
        optionsListView = findViewById(R.id.volunteeringOptionsListView);
        adapter = new GeneralActivityListAdapter(this,title,description);
        optionsListView.setAdapter(adapter);
        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GeneralActivity.this, "נרשמתה  להתנדבות!", Toast.LENGTH_SHORT).show();
                //TODO לרשום את המשתמש להתנדבות שהוא בחר בה
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.in_place){
                    //TODO להוציא נתונים של התנדבוית במקום עצמו ולשים בליסט ויו
                }else{
                    //TODO להוציא נתונים של התנדבויות אונליין ולשים בליסט ויו
                }
                return false;
            }
        });
    }
}