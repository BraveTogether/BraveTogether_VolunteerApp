package com.example.bravetogether_volunteerapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApproveVolunteerActivity extends AppCompatActivity {

    private String vid = "11"; //TODO: replace hardcoded id!!!
    private int manager;
    private String picture;
    private int online;
    private String duration;
    private String min_volunteer;
    private String max_volunteers;
    private String start_time;
    private String strDate;
    TextView nameEditTextView;
    TextView addressView;
    TextView pdescriptTextView;
    TextView tdescriptTextView;
    TextView onlineView;
    TextView dateView;
    TextView startHourView;
    TextView durationView;
    TextView minVolView;
    TextView maxVolView;
    private Button approve;
    private Button messageManager;


    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_volunteer);
        url = getString(R.string.apiUrl) + "volunteer/get_data_test";
        String uri = String.format(getString(R.string.apiUrl) + "volunteers/events/get_data/" + vid);
        nameEditTextView = (TextView) findViewById(R.id.volunteerNameApprove);
        addressView = (TextView) findViewById(R.id.addressApprove);
        pdescriptTextView = (TextView) findViewById(R.id.placeDescritionApprove);
        tdescriptTextView = (TextView) findViewById(R.id.todoeDescritionApprove);
        onlineView = (TextView) findViewById(R.id.onlineApprove);
        dateView = (TextView) findViewById(R.id.dateView);
        startHourView = (TextView) findViewById(R.id.hourView);
        durationView = (TextView) findViewById(R.id.durationView);
        minVolView = (TextView) findViewById(R.id.minVolViewApprove);
        maxVolView = (TextView) findViewById(R.id.maxVolViewApprove);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("success_response", response.toString());
                        try {
                            JSONObject record = response.getJSONObject(0);
                            nameEditTextView.setText(record.getString("name"));
                            addressView.setText(record.getString("address"));
                            pdescriptTextView.setText(record.getString("about_place"));
                            tdescriptTextView.setText(record.getString("about_volunteering"));
                            online = record.getInt("online");
                            switch (online){
                                case 0:
                                    onlineView.setText("לא מקוון");
                                    break;
                                case 1:
                                    onlineView.setText("מקוון");
                                    break;
                            }
                            dateView.setText(record.getString("date"));
                            startHourView.setText(record.getString("start_time"));
                            durationView.setText(record.getString("duration"));
                            minVolView.setText(record.getString("min_volunteer"));
                            maxVolView.setText(record.getString("max_volunteers"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error_response", error.toString());

                    }
                });


        VolleySingleton.getInstance(ApproveVolunteerActivity.this).addToRequestQueue(jsonArrayRequest);

//
//        approve = (Button) findViewById(R.id.Approve);
//        messageManager = (Button) findViewById(R.id.messageManager);
    }
}