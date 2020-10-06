package com.example.bravetogether_volunteerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bravetogether_volunteerapp.LoginFlow.RegisterActivity;

public class ManagerFragment extends Fragment implements View.OnClickListener {

    public ManagerFragment() {
        // Required empty public constructor
    }

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Set listeners for the cilckables
        View v = inflater.inflate(R.layout.fragment_manager, container, false);

        ((ImageView) v.findViewById(R.id.approve)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.approveText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.permissions)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.permissionsText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.add_new_volunteer)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.addText)).setOnClickListener(this);
        ((ImageView) v.findViewById(R.id.alert)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.alertText)).setOnClickListener(this);
        return v;
    }

    public void approve(){
        Intent intent = new Intent(getContext(), VolunteerApprovalActivity.class);
        startActivity(intent);
    }
    public void addVolunteer(){
        Intent intent = new Intent(getContext(), CreateVolunteerActivity.class);
        startActivity(intent);
    }
    public void alert(){

    }
    public void permissions(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.approveText:
            case R.id.approve:
                approve();
                break;
            case R.id.permissionsText:
            case R.id.permissions:
                permissions();
                break;
            case R.id.addText:
            case R.id.add_new_volunteer:
                addVolunteer();
                break;
            case R.id.alertText:
            case R.id.alert:
                alert();
                break;
        }

}
}