package com.example.bravetogether_volunteerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.w3c.dom.Text;

import java.util.Arrays;


@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateVolunteerActivity extends AppCompatActivity {

    private String apiKey = "AIzaSyA0hReShDEqNU3cdSm9eot1atb8-CKBy0Q";
    private String address;
//    private DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker);
    String strDate;
    String strTime;
    Context mcontext = this;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_volunteer);

        EditText nameTextView = (EditText) findViewById(R.id.volunteerName);
        EditText pdescriptEditText = (EditText) findViewById(R.id.placeDescrition);
        EditText tdescriptEditText = (EditText) findViewById(R.id.todoeDescrition);
        String Name = nameTextView.getText().toString();
        String placeDescrition = pdescriptEditText.getText().toString(); // about the place
        String todoDesctiptiontodoDesctiption = tdescriptEditText.getText().toString(); // about the volunteer itself
        Button selectDate = findViewById(R.id.btnDate);
        Button selectTIme = findViewById(R.id.btnTime);
        TextView addPicView = findViewById(R.id.addPicText);

        addPicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add pictures
            }
        });



        //AutoComplete Place text

        Places.initialize(getApplicationContext(), apiKey); //Initialize SDK
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_address);

        autocompleteFragment.setHint("כתובת");
        //TODO: change to rtl text direction
        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(29.4533796, 34.2674994),
                new LatLng(33.3356317, 35.8950234)));

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                address = place.getAddress();
                Log.i("place", "Place: " + place.getAddress());
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place.error", "An error occurred: " + status);
            }
        });


        //Date Picker

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int cday = c.get(Calendar.DAY_OF_MONTH);
                int cmonth = c.get(Calendar.MONTH);
                int cyear = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(year, month, day);

                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        strDate = format.format(calendar.getTime()); // Date for database
                                        Log.d("date", strDate);
                            }
                        }, cyear, cmonth, cday);
                datePickerDialog.show();
            }
        });

        //Time Picker
        selectTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                strTime = new StringBuilder().append(hourOfDay).append(":").append(minute).append(":")
                                        .append("00").toString();
//                                 = String.valueOf(hourOfDay) + String.valueOf(hourOfDay);
                                Log.d("time", strTime);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

//                DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                final Calendar calendar = Calendar.getInstance();
//                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                                int minute = calendar.get(Calendar.MINUTE);
//
//                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                                strDate = format.format(calendar.getTime()); // Date for database
//                                Log.d("date", strDate);
//                            }
//                        }, 0, 0, 0);
//                datePickerDialog.show();
            }
        });

    }

}

