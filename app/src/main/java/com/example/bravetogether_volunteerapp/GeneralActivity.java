package com.example.bravetogether_volunteerapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.adapters.GeneralActivityReviewsAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

TODO: change picture to load picture from web.
TODO: get real vid,uid.

 */


public class GeneralActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String title, address, time, duration, description, about_place, date_str, coins, manager_uid = "8", manager_phone = "000";
    private String url = "http://35.214.78.251:8080/"; //todo: change to R.string...
    private String vid = "13" , uid = "8"; //todo: chage to real values
    private Date date;
    private ProgressDialog progressDialog;
    private RelativeLayout hide_all_while_loading;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private ImageView volunteerRegister;
    private TextView save_to_cal_activity;
    private Boolean map_ready = false, load_data_ready = false, volley_ready = false;
    private Boolean is_registered_to_volunteer = true;

    private final int REGISTERED_SUCCESS = 1, UNREGISTERED_SUCCESS = 2, ERROR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        hide_all_while_loading = (RelativeLayout)findViewById(R.id.generalActivityHideAll);
        volunteerRegister = (ImageView) findViewById(R.id.volunteerRegister);
        load_data data = new load_data();
        data.execute();

        //init map:
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.volunteerLocationMapFragment);
        mapFragment.getMapAsync(this);


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

    }


    //Updates Textviews text to data retrieved from server.
    private void updateTextViewItems() {
        final TextView titleOfActivity = (TextView) findViewById(R.id.titleOfActivityTextView);
        final TextView locationOfActivity = (TextView) findViewById(R.id.locationOfActivityTextView);
        final TextView dateOfActivity = (TextView) findViewById(R.id.dateOfActivityTextView);
        final TextView addressOfActivity = (TextView) findViewById(R.id.addressOfActivityTextView);
        final TextView timeOfActivity = (TextView) findViewById(R.id.timeOfActivityTextView);
        final TextView durationOfActivity = (TextView) findViewById(R.id.durationOfActivityTextView);
        final TextView currencyAmount = (TextView) findViewById(R.id.currencyAmountTextview);
        final TextView descriptionOfVolunteer = (TextView) findViewById(R.id.descriptionOfVolunteerTextView);
        final TextView aboutPlaceOfVolunteer = (TextView) findViewById(R.id.aboutPlaceOfVolunteerTextView);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    url + "volunteers/events/get_data/" + vid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("success_response", response.toString());
                        try {
                            final JSONObject values = response.getJSONObject(0);
                            title = values.getString("name");
                            address = values.getString("address");
                            time = values.getString("start_time");
                            duration = values.getString("duration");
                            coins = values.getString("value_in_coins");
                            description = values.getString("about_volunteering");
                            about_place = values.getString("about_place");
                            manager_uid = values.getString("manager");

                            titleOfActivity.setText(title);
                            currencyAmount.setText(coins);
                            addressOfActivity.setText(address);
                            timeOfActivity.setText(time);
                            durationOfActivity.setText(duration);
                            descriptionOfVolunteer.setText(description);
                            aboutPlaceOfVolunteer.setText(about_place);
                            dateOfActivity.setText(convertDate(values.getString("date")));

                            updateManagerPhoneNumber(manager_uid); //gets phone number from server
                            volley_ready = true;
                            allFinished();

                            updateMapPin(address); //geocoding

                        } catch (JSONException e) {
                            Log.d("JSONObject", "onResponse: ");
                            error_handler();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_response get", error.toString());
                        error_handler();
                    }
                });
        VolleySingleton.getInstance(GeneralActivity.this).addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap; //save for later actions
        map_ready = true;
        allFinished();
    }

    private void initReviews() {
        //TODO: get real review from server. currently request kills the server.
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

    //gets phone number based on uid of manager_uid.
    // updates 'manager_phone' value.
    private void updateManagerPhoneNumber(String manager_uid) {
        if (manager_uid == null)
            Log.d("gal", "null");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "volunteer/manager/phone/" + manager_uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("success_response", response.toString());
                        try {
                            final JSONObject values = response.getJSONObject(0);
                            manager_phone = values.getString("phone_number");
                        } catch (JSONException e) {
                            Log.d("JSONException", e.toString());
                            error_handler();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response.Error", error.toString());
                        error_handler();
                    }
                });
        VolleySingleton.getInstance(GeneralActivity.this).addToRequestQueue(jsonArrayRequest);

    }

    //returns OnClickListener with Event intent to be started on click.
    private View.OnClickListener getSavetoCalendarOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(date);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, title)
                        .putExtra(CalendarContract.Events.DESCRIPTION, description)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, address);
                startActivity(intent);
            }
        };
    }


    // the get request from the server returns date in iso8601 format (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
    // this function gets string representing date from the server and converts it to dd/mm/yy
    // format to be printed to user. The converted string is stored in 'date_str', and the
    // Java.Date object representing the same date is stored in 'date'.
    private String convertDate(String iso8601_date) {

        SimpleDateFormat iso8601_to_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            date = iso8601_to_date.parse(iso8601_date);
        } catch (ParseException e) {
            Log.d("convertDate", e.toString());
            error_handler();
        }

        try {
            Log.d("date", date.toString());
            SimpleDateFormat date_to_print = new SimpleDateFormat("dd/MM/yy");
            date_str = date_to_print.format(date);
        } catch (Exception e) {
            Log.d("convertDate", e.toString());
            error_handler();
        }

        return date_str;
    }

    // sets OnClickListener intent with managers phone number.
    // tries to open whatsapp to contact, or sms to contact if fails.
    private void setTalkToManager() {
        ImageView send_whatsapp = (ImageView) findViewById(R.id.whatsappContactImageView);
        send_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // see https://faq.whatsapp.com/general/chats/how-to-use-click-to-chat
                    Uri msg_uri = Uri.parse("https://wa.me/972" + manager_phone);
                    Intent intent = new Intent(Intent.ACTION_VIEW, msg_uri)
                            .setPackage("com.whatsapp");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) // Whatsapp not installed. send sms:
                {
                    Uri msg_uri = Uri.parse("smsto:" + manager_phone);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, msg_uri);
                    startActivity(intent);
                }

            }
        });
    }

    /*
    all actions for click on 'register' button - register (if not registered) or unregister if registered,
    shows progressDialog, sends register request and pops relevant popup to the response / error.
     */
    private void sendRegisterRequest()
    {
        progressDialog = new ProgressDialog(GeneralActivity.this);
        progressDialog.show();

        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("vid", vid);
            jsonBody.put("uid", uid);
        } catch (JSONException e) {
            error_handler();
        }

        // IMPORTANT: if user is already registered, unregister him. add "unregister" to request.
        String unregister = is_registered_to_volunteer ? "unregister" : "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url
                                        + "users_volunteerings_events/" + unregister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(is_registered_to_volunteer) //already registered --> unregister.
                            popUp(R.layout.popup_unregistered,volunteerRegister, UNREGISTERED_SUCCESS);
                        else
                            popUp(R.layout.popup_registered_user,volunteerRegister, REGISTERED_SUCCESS);
                        is_registered_to_volunteer = !is_registered_to_volunteer;
                        updateRegisterImageView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        popUp(R.layout.popup_error,volunteerRegister, ERROR);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    Log.d("body", jsonBody.toString());
                    return jsonBody.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    /*
    extends AsyncTask.
    loads all data from server, updates the relevant textviews and listeners.
     */
    private class load_data extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            updateTextViewItems();
            save_to_cal_activity = (TextView)findViewById(R.id.addToCalendarTextView) ;
            save_to_cal_activity.setOnClickListener(getSavetoCalendarOnClickListener());
            setTalkToManager();
            initReviews();
            setIsRegistered();

            volunteerRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRegisterRequest();
                }
            });
            return null;
        }

        protected void onPostExecute(String result) {
            load_data_ready = true;
            allFinished();
        }
        @Override
        protected void onPreExecute() {
        }
    }


    /*
    go-to function when error occurs. sets activity_error on user's screen
     */
    private void error_handler()
    {
        setContentView(R.layout.activity_error);
    }


    /*
    Checks if all long functions (server gets) finished, and when all done
    hides the 'hide_all_while_loading' View
     */
    private void allFinished()
    {
        if(volley_ready && map_ready && load_data_ready)
            hide_all_while_loading.setVisibility(View.GONE);
    }



    //simple geocoding - address to LatLng (form map pin, map camera)
    public static LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng latLngLocation = null;
        try
        {
            address = coder.getFromLocationName(strAddress, 1);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d("killer", "getLocationFromAddress: " + latLngLocation.toString());
        }
        catch (Exception e)
        {
            Log.d("killer", "null");
            Log.d("killer", e.getMessage());
            return null;
        }
        return latLngLocation;
    }

    private void updateMapPin(String strAddress)
    {
        LatLng pin = getLocationFromAddress(getApplicationContext(), strAddress);
        Log.d("updateMapPin", "address: " + strAddress);
        if(pin != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pin, 13));
            googleMap.addMarker(new MarkerOptions().position(pin));
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
        }
        else
        {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.0461, 34.8516), 10));
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.getUiSettings().setZoomControlsEnabled(false);
        }
    }


    //pops layout_to_pop and execute additional code according to 'flag'.
    //param someImageView = some already initialized ImageView, can be any ImageView.
    private void popUp(int layout_to_pop, ImageView someImageView, int flag)
    {
        View popupView = getLayoutInflater().inflate(layout_to_pop, null);
        final PopupWindow popUp = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popUp.setAnimationStyle(R.style.Animation_Design_BottomSheetDialog);
        popUp.setOutsideTouchable(false);
        popUp.setFocusable(true);

        progressDialog.dismiss();
        popUp.showAtLocation(someImageView, Gravity.CENTER, 0, 0);

        View container = (View) popUp.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);

        switch (flag)  //extra code execute, determined by 'flag'.
        {
            case REGISTERED_SUCCESS: //user successfully registered to volunteer
            {
                final ImageView save_to_cal_popup = (ImageView) container.findViewById(R.id.popupAddToCalendarImageView);
                save_to_cal_popup.setOnClickListener(getSavetoCalendarOnClickListener());
                final ImageView go_back = (ImageView) container.findViewById(R.id.popupBackImageView);
                go_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                break;
            }
            case UNREGISTERED_SUCCESS:
            {
                final ImageView go_back = (ImageView) container.findViewById(R.id.unrigisteredPopoupBackImageView);
                go_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                break;
            }

            case ERROR:
            {
                final ImageView go_back = (ImageView) container.findViewById(R.id.errorPopoupBackImageView);
                go_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                break;
            }
            default:{ break;}
        }


    }


    //updates the register / unregister ImageView based on is_registered_to_volunteer
    // will show cancel registration if register.
    // will show register if not registered.
    private void updateRegisterImageView()
    {
        Log.d("updateRegisterImageView", "volunteerRegister: " + is_registered_to_volunteer.toString());
        if(is_registered_to_volunteer)
            volunteerRegister.setImageResource(R.drawable.ic_cencal_registration);
        else
            volunteerRegister.setImageResource(R.drawable.volunteer_register_icon);

    }




    //send request and checks if user is registered to current volunteer.
    //updates is_registered_to_volunteer and changes register button accordingly.
    private void setIsRegistered()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                    url + "users_volunteering_events/get_count/8/13", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("success setIsRegisteer", response.toString());
                        try {
                            final JSONObject values = response.getJSONObject(0);
                            if (values.getInt("COUNT (*)") == 0) // TODO: not hardcoded.
                                is_registered_to_volunteer = false;
                            else
                                is_registered_to_volunteer = true;

                            updateRegisterImageView();

                        } catch (JSONException e) {
                            Log.d("JSONObject", "onResponse: ");
                            error_handler();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_response get", error.toString());
                        error_handler();
                    }
                });
        VolleySingleton.getInstance(GeneralActivity.this).addToRequestQueue(jsonArrayRequest);
        }
}


