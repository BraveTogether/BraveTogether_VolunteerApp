package com.example.bravetogether_volunteerapp.LoginFlow;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.ui.SlideAnimation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class NotificationActivity extends AppCompatActivity {

    private String apiKey = "AIzaSyA0hReShDEqNU3cdSm9eot1atb8-CKBy0Q";
    private String address;
    private Context mcontext = this;
    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet = new ConstraintSet();
    private Button expandButton;
    private View expandedLocationBox;
    private View expandedTimeBox;
    private Activity activity = this;
    private GpsTracker gpsTracker;
    private Button buttons[] = new Button[6];
    private ImageView linesViews[] = new ImageView[5];

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        for(int i=0;i<6;i++)
        {
            String buttonID = "day" + String.valueOf(i+1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resID);
        }

        for(int i=0;i<5;i++)
        {
            String buttonID = "line_2" + String.valueOf(i+4);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            linesViews[i] = (ImageView) findViewById(resID);
        }

        expandedLocationBox = (View) findViewById(R.id.expandedBox2);
        expandedTimeBox = (View) findViewById(R.id.expandedBox1);
        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_address);


        expandedTimeBox.setVisibility(View.GONE);
        expandedLocationBox.setVisibility(View.GONE);
        mConstraintLayout = findViewById(R.id.constraint_layout);
        expandButton = (Button) findViewById(R.id.expandButton);


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
                address = place.getAddress();
                Log.i("place", "Place: " + place.getAddress());
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place.error", "An error occurred: " + status);
            }
        });


        //expanding location box

        Switch sw = findViewById(R.id.switch_button);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                expandedLocationBox.setVisibility(expandedLocationBox.isShown() ? View.GONE
                        : View.VISIBLE);
                if (isChecked) {
                    SlideAnimation.slide_up(mcontext, expandedLocationBox);
                    expandedLocationBox.setVisibility(View.GONE);
                    try {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
                        {
                            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        }
                        getLocation();
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
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(mcontext);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Log.d("latitude", "latitude: " + String.valueOf(latitude));
            Log.d("longitude", "longitude: " + String.valueOf(longitude));

        }else{
            gpsTracker.showSettingsAlert();
        }
    }


    public void weekDayCheck(View view) {
    }
}