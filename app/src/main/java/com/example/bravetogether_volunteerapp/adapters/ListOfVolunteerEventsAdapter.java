package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolunteerEvent;

import java.util.List;

public class ListOfVolunteerEventsAdapter extends ArrayAdapter<VolunteerEvent> {

    Context context;
    int resource;
    List<VolunteerEvent> eventList;

    //Builder of thr adapter
    public ListOfVolunteerEventsAdapter(@NonNull Context context, int resource, @NonNull List<VolunteerEvent> eventList) {
        super(context, resource, eventList);
        this.context = context;
        this.resource = resource;
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rowInTheTable = layoutInflater.inflate(R.layout.layout_item, parent,false);

        TextView eventNameOfOneList = rowInTheTable.findViewById(R.id.textViewEvent);
        TextView eventDescriptionOfOneList = rowInTheTable.findViewById(R.id.textViewDescription);
        TextView eventLocationOfOneList = rowInTheTable.findViewById(R.id.textViewLocation);
        TextView eventDateOfOneList = rowInTheTable.findViewById(R.id.textViewDate);
        TextView eventDurationOfOneList = rowInTheTable.findViewById(R.id.textViewTime);
        TextView eventCoinsOfOneList = rowInTheTable.findViewById(R.id.textViewCoinsNum);

        VolunteerEvent event = eventList.get(position);

        eventNameOfOneList.setText(event.getEventName());
        eventDescriptionOfOneList.setText(event.getEventDescription());
        eventLocationOfOneList.setText(event.getLocation());
        eventDateOfOneList.setText(event.getDate());
        eventDurationOfOneList.setText(event.getDuration());
        eventCoinsOfOneList.setText(event.getNumberCredits());

        return rowInTheTable;
    }



}
