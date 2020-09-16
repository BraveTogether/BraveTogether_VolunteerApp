package com.example.bravetogether_volunteerapp.data;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.adapters.ListOfVolunteerEventsAdapter;

import java.util.ArrayList;
import java.util.List;

public class VolunteeriesListActivity extends AppCompatActivity {

    ListView listview;
    List<volunteerEvent> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteeries_list);

        eventsList = new ArrayList<>();
        eventsList.add(new volunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList.add(new volunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        eventsList.add(new volunteerEvent("עזרה בקניות","13/14/15","גיל הזהב", "120" ,"3" , "300"));
        listview = (ListView)findViewById(R.id.ListViewEvents);
        ListOfVolunteerEventsAdapter adapter = new ListOfVolunteerEventsAdapter(this, R.layout.volunteer_list_item, eventsList);
        listview.setAdapter(adapter);
    }
}