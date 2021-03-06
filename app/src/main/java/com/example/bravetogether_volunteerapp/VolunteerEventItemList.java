package com.example.bravetogether_volunteerapp;

public class VolunteerEventItemList {
    //for each picture in res->drawable there is a id
    int imageIdClock, imageIdLocation, imageIdDate, imageIdCredit;
    String eventName;
    String date;
    String eventDescription;
    String numberCredits, duration, location;
    String picture;

    public VolunteerEventItemList(String eventName, String date, String eventDescription, String numberCredits, String duration, String location, String pic) {
        this.eventName = eventName;
        this.date = date;
        this.eventDescription = eventDescription;
        this.numberCredits = numberCredits;
        this.duration = duration;
        this.location = location;
        this.picture = pic;
    }

    public VolunteerEventItemList() {

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
