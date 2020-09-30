package com.example.bravetogether_volunteerapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bravetogether_volunteerapp.R;

public class SettingsFragment extends Fragment {

    private static SharedPreferences mPreferences;

    private SettingsViewModel settingsViewModel;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        String sharedPrefFile =
                "com.example.android.BraveTogether_VolunteerApp";
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        String phone, address, firstname, lastname;

        // get the user info
        address = mPreferences.getString("UserAddress", null);
        firstname = mPreferences.getString("UserFirstName", null);
        lastname = mPreferences.getString("UserLastName", null);
        phone = mPreferences.getString("UserPhoneNumber", null);

        EditText firstnametext = (EditText) view.findViewById(R.id.FirstNameText);
        EditText lastnametext = (EditText) view.findViewById(R.id.LastNameText);
        EditText addresstext = (EditText) view.findViewById(R.id.AddressText);
        EditText phonetext = (EditText) view.findViewById(R.id.PhoneText);

        if (firstname != null) { firstnametext.setText(firstname); }
        if (lastname != null) { lastnametext.setText(lastname); }
        if (address != null){ addresstext.setText(address); }
        if (phone != null){ phonetext.setText(phone); }
        return view;
    }

}


