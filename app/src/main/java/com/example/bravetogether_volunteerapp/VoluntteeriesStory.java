package com.example.bravetogether_volunteerapp;

public class VoluntteeriesStory {
    String title;
    String date;
    String eventDescription;

    public VoluntteeriesStory(String title, String Location, String eventDescription) {
        this.title = title;
        this.date = Location;
        this.eventDescription = eventDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return date;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}


