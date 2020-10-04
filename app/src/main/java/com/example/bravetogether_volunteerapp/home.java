package com.example.bravetogether_volunteerapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bravetogether_volunteerapp.adapters.VoluntteeirisStoryAdapter;
import com.example.bravetogether_volunteerapp.adapters.homePageListsGroupAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class home extends AppCompatActivity {

    RecyclerView my_recycler_view;
    TextView userGreetings;
    RecyclerView my_recycler_view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        timeSetText("עומר");
        List<homeGroupItem> listGroup = new ArrayList<>();
        List <VolunteerEvent> eventsList = new ArrayList<>();
        BottomNavigationView BottomNavigationViewHome =findViewById(R.id.BottomNavigationViewHome);
        BottomNavigationViewHome.setOnNavigationItemSelectedListener(navListener);

        eventsList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        List <VolunteerEvent> eventsList2 = new ArrayList<>();
        eventsList2.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList2.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList2.add(new VolunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        homeGroupItem Item1 = new homeGroupItem();
        Item1.setHeadTitle("התנדביות מותאמות לך");
        Item1.setListVolunteerEvent(eventsList);
        homeGroupItem Item2 = new homeGroupItem();
        Item2.setListVolunteerEvent(eventsList2);
        Item2.setHeadTitle("התנדביות מסורתיות");
        listGroup.add(Item1);
        listGroup.add(Item2);
        homePageListsGroupAdapter adapter = (homePageListsGroupAdapter)new homePageListsGroupAdapter(this,listGroup);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setAdapter(adapter);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        List <VoluntteeriesStory> storiesList = new ArrayList<>();
        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
        storiesList.add(new VoluntteeriesStory("עזרה בקניות","13/14/15","גיל הזהב"));
        VoluntteeirisStoryAdapter adapter2 = (VoluntteeirisStoryAdapter) new VoluntteeirisStoryAdapter(this,storiesList);
        my_recycler_view2 = (RecyclerView) findViewById(R.id.Recycler_view_list3);
        my_recycler_view2.setAdapter(adapter2);
        my_recycler_view2.setHasFixedSize(true);
        my_recycler_view2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

    }

    protected void timeSetText(String username) {
        userGreetings = (TextView)  findViewById(R.id.userGreetings);
        Calendar calendar = Calendar.getInstance();
        int timeofDay = calendar.get(Calendar.HOUR_OF_DAY);
        if(timeofDay >= 0 && timeofDay <12){
            userGreetings.setText("בוקר טוב," + username);
        }
        else if(timeofDay >=12 && timeofDay <16){
            userGreetings.setText("צהריים טובים, " + username);
        }
        else if(timeofDay >=16 && timeofDay <21){
            userGreetings.setText("ערב טוב, " + username);
        }
        else if(timeofDay >=21 && timeofDay <24){
            userGreetings.setText("לילה טוב, " + username);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.homeFragment:{
                            selectedFragment = new HomeFragment();
                            break;
                        }
                        case R.id.settingFragment: {
                            selectedFragment = new SettingFragment();
                            break;
                        }
                        case R.id.profileFragment: {
                            //selectedFragment = new ProfileFragment();
                            break;

                        }
                        case R.id.searchFragment: {
                            selectedFragment = new searchFragment();
                            break;

                        }
                        case R.id.notificationFragment: {
                            selectedFragment = new NotificationFragment();
                            break;

                        }
                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.home, selectedFragment).commit();
                    return true;
                }
            };
}






