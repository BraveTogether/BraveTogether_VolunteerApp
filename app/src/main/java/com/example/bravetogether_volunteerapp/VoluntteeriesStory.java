package com.example.bravetogether_volunteerapp;

import android.graphics.Bitmap;

public class VoluntteeriesStory {
    String title;
    String date;
    String eventDescription;
    String profilePic;

    public String getProfilePic() {
        return profilePic;
    }

    public VoluntteeriesStory(String title, String Location, String eventDescription, String profilePic) {
        this.title = title;
        this.date = Location;
        this.eventDescription = eventDescription;
        this.profilePic = profilePic;
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


