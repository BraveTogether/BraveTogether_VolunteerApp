package com.example.bravetogether_volunteerapp.LoginFlow;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.CallToServer;
import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolleySingleton;
import com.example.bravetogether_volunteerapp.adapters.spinnerAdapter;
import com.example.bravetogether_volunteerapp.home;
import com.example.bravetogether_volunteerapp.ui.SlideAnimation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private final String apiKey = "AIzaSyA0hReShDEqNU3cdSm9eot1atb8-CKBy0Q";
    private String first_name,family_name,email,password,phone_number,home_address,about,user_desired_location,chosen_time,address,location,profilePictureUrl,User_desired_location;
    private Context mcontext = this;
    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private Button expandButton;
    private View expandedLocationBox,expandedTimeBox;
    private Activity activity = this;
    private GpsTracker gpsTracker;
    private Spinner spinner;
    private ToggleButton buttons[] = new ToggleButton[6];
    private boolean isButtonPres[] = new boolean[6];
    private ImageView linesAndRectViews[] = new ImageView[6];
    private boolean checkDays[] = new boolean[6];
    private ArrayList<String> time_windows_strings;
    private TextView time_window_text;
    double latitude,longitude;
    String switched;
    CallToServer cts;
    private Switch sw;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        cts = new CallToServer();

        for(int i=0;i<6;i++)
        {
            String buttonID = "day" + String.valueOf(i+1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (ToggleButton) findViewById(resID);
            if(i<5)
                buttonID = "line_2" + String.valueOf(i + 4);
            else
                buttonID = "timeTableRect";
            resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            linesAndRectViews[i] = (ImageView) findViewById(resID);
            isButtonPres[i] = false;
        }


        expandedLocationBox = (View) findViewById(R.id.expandedBox2);
        expandedTimeBox = (View) findViewById(R.id.expandedBox1);
        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_address);

        autocompleteFragment.setHint("הכנס מיקום ידני");
//        autocompleteFragment.getView().setTextDirection(View.TEXT_DIRECTION_ANY_RTL);

        expandedTimeBox.setVisibility(View.GONE);
        expandedLocationBox.setVisibility(View.VISIBLE);
        mConstraintLayout = findViewById(R.id.constraint_layout);
        expandButton = (Button) findViewById(R.id.expandButton);

        spinner = (Spinner) findViewById(R.id.Timespinner);
        spinner.setAdapter(new spinnerAdapter(mcontext));
        time_window_text = (TextView) findViewById(R.id.time_windows_text);

        String retrieve []= mcontext.getResources().getStringArray(R.array.time_windows_hours);
        time_windows_strings = new ArrayList<>();
        for(String re:retrieve)
        {
            time_windows_strings.add(re);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { //Get the days from array "time_windows_hours" with position
                chosen_time = time_windows_strings.get(position);
                time_window_text.setText(chosen_time);
                time_window_text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // Place AutoFill

        Places.initialize(getApplicationContext(), apiKey); //Initialize SDK
        PlacesClient placesClient = Places.createClient(this);


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
                address = place.getAddress(); //Get the ktovet iadanit
                Log.i("place", "Place: " + place.getAddress());
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place.error", "An error occurred: " + status);
            }
        });


        //expanding location box

        sw = findViewById(R.id.switch_button);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                expandedLocationBox.setVisibility(expandedLocationBox.isShown() ? View.GONE
                        : View.VISIBLE);
                if (isChecked) {
                    SlideAnimation.slide_up(mcontext, expandedLocationBox);
                    expandedLocationBox.setVisibility(View.GONE);
                    try {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
                        {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        }
                        getLocation(); // getting the location
                    } catch (Exception e){
                        e.printStackTrace();
                    }

//                    autocompleteFragment.getView().setVisibility(View.GONE);
                } else {
                    expandedLocationBox.setVisibility(View.VISIBLE);
                    SlideAnimation.slide_down(mcontext, expandedLocationBox);
//                    autocompleteFragment.getView().setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void expandTimeBox(View view) {
        // If the button is already gone then you don't need to apply any constraints
        if (expandButton.getVisibility() == View.GONE) {
            return;
        }
    /* Otherwise:
    - Make the button2 gone;
    - Insert the actual constraints in the ConstraintSet
    - Define a new constraint between button3 and button2 (TopToBottomOf)
    - Apply it to the ConstraintLayout */
        expandedTimeBox.setVisibility(View.VISIBLE);
        SlideAnimation.slide_down(mcontext, expandedTimeBox);
        expandButton.setVisibility(View.GONE);
        mConstraintSet.clone(mConstraintLayout);
        mConstraintSet.connect(R.id.expandBox2, ConstraintSet.TOP,
                R.id.expandedBox1, ConstraintSet.BOTTOM);
        mConstraintSet.connect(R.id.expandedBox2, ConstraintSet.TOP,
                R.id.expandedBox1, ConstraintSet.BOTTOM);
        mConstraintSet.applyTo(mConstraintLayout);
        /*
         * make every other element visible*/
        for(int i=0;i<5;i++)
        {
            buttons[i].setVisibility(View.VISIBLE);
            linesAndRectViews[i].setVisibility(View.VISIBLE);
        }
        linesAndRectViews[5].setVisibility(Button.VISIBLE);
        buttons[5].setVisibility(Button.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(mcontext);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.d("latitude", "latitude: " + String.valueOf(latitude));
            Log.d("longitude", "longitude: " + String.valueOf(longitude));

        }else{
            gpsTracker.showSettingsAlert();
        }
    }


    public void weekDayCheck(View view) {  //Get the days from the array
        boolean pressed = ((ToggleButton) view).isChecked();
        if (pressed)
        {
            view.setBackgroundResource(R.drawable.ic_ellipse_73);
        }
        else
        {
            view.setBackgroundResource(R.color.transparent);
        }
        switch (view.getId()) {
            case R.id.day1:
                checkDays[0] = !checkDays[0];
                break;
            case R.id.day2:
                checkDays[1] = !checkDays[1];
                break;
            case R.id.day3:
                checkDays[2] = !checkDays[2];
                break;
            case R.id.day4:
                checkDays[3] = !checkDays[3];
                break;
            case R.id.day5:
                checkDays[4] = !checkDays[4];
                break;
            case R.id.day6:
                checkDays[5] = !checkDays[5];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void letsVolunteer(View view) {
        Intent intent = new Intent(NotificationActivity.this, home.class);
        Intent getIntent = getIntent();
        {
            first_name = getIntent.getStringExtra("first_name");
            family_name = getIntent.getStringExtra("family_name");
            email = getIntent.getStringExtra("email");
            password = getIntent.getStringExtra("password");
        }
        { //Write to user profile
            phone_number = getIntent.getStringExtra("phone_number"); //Phone number
            home_address = getIntent.getStringExtra("address"); // address
            about = getIntent.getStringExtra("about"); //About
            profilePictureUrl = getIntent.getStringExtra("image"); // Get the profile picture URL from intent
        }
        {
            if(String.valueOf(latitude).equals("")){
                location = "";
            }else{
                location = Double.toString(latitude) + Double.toString(longitude);
            }
            switched = sw.isChecked() ? "0" : "1";
        }

        user_desired_location = getIntent.getStringExtra("user_desired_location"); //notification_location_pref_id

        cts.registerUser(this,email,password,first_name,family_name,phone_number,home_address,about,"4",profilePictureUrl,
                            location,switched,user_desired_location);

        //**Get the true values from check days
        if(chosen_time == null){
            //Write null to database
        }else{
            //write chosen_time to database
        }


        if(String.valueOf(latitude).equals("")){
            //need to get the user input location
        }else{
            String location = Double.toString(latitude) + Double.toString(longitude);
        }
    }

}