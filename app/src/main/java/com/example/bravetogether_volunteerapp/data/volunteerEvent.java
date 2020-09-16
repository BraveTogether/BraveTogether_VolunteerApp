package com.example.bravetogether_volunteerapp.data;

public class volunteerEvent {
    //for each picture in res->drawable there is a id
    int imageIdClock, imageIdLocation, imageIdDate, imageIdCredit;
    String eventName;
    String date;
    String eventDescription;
    String numberCredits, duration, location;

    public volunteerEvent(String eventName, String date, String eventDescription, String numberCredits, String duration, String location) {
        this.eventName = eventName;
        this.date = date;
        this.eventDescription = eventDescription;
        this.numberCredits = numberCredits;
        this.duration = duration;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public int getImageIdLocation() {
        return imageIdLocation;
    }

    public int getImageIdDate() {
        return imageIdDate;
    }

    public int getImageIdCredit() {
        return imageIdCredit;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getNumberCredits() {
        return numberCredits;
    }

    public String getDuration() {
        return duration;
    }

    public int getImageIdClock() {
        return imageIdClock;
    }
}
