package com.example.bravetogether_volunteerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ScrollView;

import com.example.bravetogether_volunteerapp.adapters.GeneralActivityReviewsAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/*
 * Currently takes too long to load. TODO: ASyncTask.
 */
public class GeneralActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        updateTextViewItems();

        //init map:
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.volunteerLocationMapFragment);
        mapFragment.getMapAsync(this);


        //TODO:get data from server. All the current values are placeholders.

        //init save volunteer to calendar intent and its listener
        final TextView save_to_cal = (TextView) findViewById(R.id.addToCalendarTextView);
        save_to_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2012, 2, 19, 7, 30);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2012, 2, 19, 8, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Volunteer")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "don't forget")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym");
                startActivity(intent);
            }
        });

        //init save volunteer to calendar intent and its listener
        final ImageView send_whatsapp = (ImageView) findViewById(R.id.whatsappContactImageView);
        send_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // see https://faq.whatsapp.com/general/chats/how-to-use-click-to-chat
                    Uri msg_uri = Uri.parse("https://wa.me/9720546776397"); //TODO: insert real number from server
                    Intent intent = new Intent(Intent.ACTION_VIEW, msg_uri)
                            .setPackage("com.whatsapp");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) // Whatsapp not installed. send sms:
                {
                    Uri msg_uri = Uri.parse("smsto:0546776397"); //TODO: insert real number from server
                    Intent intent = new Intent(Intent.ACTION_SENDTO, msg_uri);
                    startActivity(intent);
                }

            }
        });

        //enable scrolling and moving the map:
        final ScrollView generalActivityScrollView = (ScrollView) findViewById(R.id.generalActivityScrollView);
        final View transparentView = (View) findViewById(R.id.transparent_view);
        transparentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        generalActivityScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        generalActivityScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    default:
                        return true;
                }
            }
        });

        //TODO: change to load later.
        initReviews();

        final ImageView volunteerRegister = (ImageView) findViewById(R.id.volunteerRegister);
        volunteerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = getLayoutInflater().inflate(R.layout.popup_registered_user, null);
                PopupWindow popUp = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                popUp.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
                popUp.setOutsideTouchable(false);
                popUp.setFocusable(true);

                //
                popUp.showAtLocation(volunteerRegister, Gravity.CENTER, 0,0);

                View container = (View) popUp.getContentView().getParent();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                // add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                wm.updateViewLayout(container, p);



            }
        });



    }

    //Updates Textviews text to current volunteer.
    //TODO: fill with real values. currently only placeholders.
    private void updateTextViewItems()
    {
        final TextView titleOfActivity = (TextView)findViewById(R.id.titleOfActivityTextView);
        titleOfActivity.setText("כותרת התנדבות");
        final TextView locationOfActivity = (TextView)findViewById(R.id.locationOfActivityTextView);
        locationOfActivity.setText("מיקום ההתנדבות");
        final TextView dateOfActivity = (TextView)findViewById(R.id.dateOfActivityTextView);
        dateOfActivity.setText("10/11/12");
        final TextView addressOfActivity = (TextView)findViewById(R.id.addressOfActivityTextView);
        addressOfActivity.setText("אילת 14 חיפה");
        final TextView timeOfActivity = (TextView)findViewById(R.id.timeOfActivityTextView);
        timeOfActivity.setText("15:00");
        final TextView durationOfActivity = (TextView)findViewById(R.id.durationOfActivityTextView);
        durationOfActivity.setText("60 דקות");

        final TextView currencyAmount = (TextView)findViewById(R.id.currencyAmountTextview);
        currencyAmount.setText("110");

        final TextView descriptionOfVolunteer = (TextView)findViewById(R.id.descriptionOfVolunteerTextView);
        //titleOfActivity.setText();
        final TextView aboutPlaceOfVolunteer = (TextView)findViewById(R.id.aboutPlaceOfVolunteerTextView);
        //titleOfActivity.setText();

    }


    @Override
    /* sets marker location on new map and choose zoom level */
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
    }

    private void initReviews() {
        //TODO: get real review from server. create new class - review.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.reviewsOfVolunteerRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        List<String> text_input = new ArrayList<String>();
        text_input.add("הכי טוב בעולם");
        text_input.add("חוויה חובה");
        text_input.add("משמעותי ומעניין");

        GeneralActivityReviewsAdapter adapter = new GeneralActivityReviewsAdapter(this, text_input);
        recyclerView.setAdapter(adapter);
    }


}