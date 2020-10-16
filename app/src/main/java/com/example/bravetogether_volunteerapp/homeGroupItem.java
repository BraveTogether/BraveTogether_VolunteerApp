package com.example.bravetogether_volunteerapp;

import java.util.List;

public class homeGroupItem {
    private String headTitle;
    private List<VolunteerEventItemList> listVolunteerEventItemList;

    public void setListVolunteerEventItemList(List<VolunteerEventItemList> listVolunteerEventItemList) {
        this.listVolunteerEventItemList = listVolunteerEventItemList;
    }

    public homeGroupItem() {
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public homeGroupItem(String headTitle, List<VolunteerEventItemList> listVolunteerEventItemList) {
        this.headTitle = headTitle;
        this.listVolunteerEventItemList = listVolunteerEventItemList;
    }

    public List<VolunteerEventItemList> getListItem() {
        return listVolunteerEventItemList;
    }
}
