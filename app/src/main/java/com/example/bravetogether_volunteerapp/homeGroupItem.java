package com.example.bravetogether_volunteerapp;

import java.util.List;

public class homeGroupItem {
    private String headTitle;
    private List<VolunteerEvent> listVolunteerEvent;

    public void setListVolunteerEvent(List<VolunteerEvent> listVolunteerEvent) {
        this.listVolunteerEvent = listVolunteerEvent;
    }

    public homeGroupItem() {
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public homeGroupItem(String headTitle, List<VolunteerEvent> listVolunteerEvent) {
        this.headTitle = headTitle;
        this.listVolunteerEvent = listVolunteerEvent;
    }

    public List<VolunteerEvent> getListItem() {
        return  listVolunteerEvent;
    }
}
