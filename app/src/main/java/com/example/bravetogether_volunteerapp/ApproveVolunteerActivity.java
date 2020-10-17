package com.example.bravetogether_volunteerapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.ImageRetrieving.ImageRetriever;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class ApproveVolunteerActivity extends AppCompatActivity {

    private String vid = "13"; //TODO: replace hardcoded id!!!
    private String picture;
    private int online;
    private String duration;
    private String min_volunteer;
    private String max_volunteers;
    private String start_time;
    private String strDate;
    ImageView imageView;
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
    private Context mcontext=this;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_volunteer);
        final String url = getString(R.string.apiUrl);
        final int[] manager = new int[1];



        imageView = (ImageView) findViewById(R.id.uploaded_image_approve_activity);
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


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "volunteers/events/get_data/" + vid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("success_response", response.toString());
                        try {
                            final JSONObject record = response.getJSONObject(0);
                            picture = record.getString("picture");
                            Log.d("storage_uri_from_record", picture);


                            final ImageRetriever imageRetriever = new ImageRetriever();

                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            String rationale = "Please provide storage permission so that you can load your profile picture";
                            Permissions.Options options = new Permissions.Options()
                            .setRationaleDialogTitle("Info")
                            .setSettingsDialogTitle("Warning");

                            Permissions.check(mcontext, permissions, rationale, options, new PermissionHandler() {
                                @Override
                                public void onGranted() {
                                        Log.d("storage_uri", picture);
                                        Uri imageURI = Uri.parse(picture);
//                                        String filePath = imageRetriever.retrieveImg(imageURI, ApproveVolunteerActivity.this, "volunteer_image_approve").getPath();
                                        }
                                @Override
                                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                                        Toast.makeText(ApproveVolunteerActivity.this, "Please allow the the app to access your storage in order to show your profile picture", Toast.LENGTH_SHORT).show();
                                        }
                                        });
                            manager[0] = record.optInt("manager");
                            nameEditTextView.setText(record.getString("name"));
                            addressView.setText(record.getString("address"));
                            pdescriptTextView.setText(record.getString("about_place"));
                            tdescriptTextView.setText(record.getString("about_volunteering"));
                            online = record.getInt("online");
                            switch (online) {
                                case 0:
                                    onlineView.setText("לא מקוון");
                                    break;
                                case 1:
                                    onlineView.setText("מקוון");
                                    break;
                            }
                            dateView.setText(record.getString("date"));
                            startHourView.setText(record.getString("start_time"));
                            durationView.setText(new StringBuilder().append(record.getString("duration")).append("שעות"));
                            minVolView.setText(new StringBuilder().append(record.getString("min_volunteer")).append("מינימום"));
                            maxVolView.setText(new StringBuilder().append(record.getString("max_volunteers")).append("מקסימום"));

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


        approve = (Button) findViewById(R.id.Approve);
        approve.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           JSONObject jsonBody = new JSONObject();
                                           try {
                                               jsonBody.put("vid", vid);
                                           } catch (JSONException e) {

                                           }

                                           final String requestBody = jsonBody.toString();

                                           StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "volunteers/events/confirm",
                                                   new Response.Listener<String>() {
                                                       @Override
                                                       public void onResponse(String response) {
                                                           Log.d("success_Response", response);
                                                       }
                                                   },
                                                   new Response.ErrorListener() {
                                                       @Override
                                                       public void onErrorResponse(VolleyError error) {
                                                           try {
                                                               Log.d("error.response", Objects.requireNonNull(error.getMessage()));
                                                           } catch (NullPointerException e) {
                                                               Log.d("error.null", "error is null");
                                                           }
                                                       }
                                                   }) {
                                               @Override
                                               public byte[] getBody() throws AuthFailureError {
                                                   try {
                                                       return requestBody == null ? null : requestBody.getBytes("utf-8");
                                                   } catch (UnsupportedEncodingException uee) {
                                                       VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                                       return null;
                                                   }
                                               }

                                               @Override
                                               public String getBodyContentType() {
                                                   return "application/json";
                                               }
                                           };
                                           VolleySingleton.getInstance(ApproveVolunteerActivity.this).addToRequestQueue(stringRequest);
                                       }
                                   });
        messageManager = (Button) findViewById(R.id.messageManager);
        messageManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "volunteer/manager/phone/" + manager[0], null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("success_response", response.toString());
                                JSONObject record = null;
                                String phone = null;
                                try {
                                    record = response.getJSONObject(0);
                                    phone = record.getString("phone_number");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    // see https://faq.whatsapp.com/general/chats/how-to-use-click-to-chat
                                    Uri msg_uri = Uri.parse("https://wa.me/972"+ phone); //TODO: insert real number from server
                                    Intent intent = new Intent(Intent.ACTION_VIEW, msg_uri)
                                            .setPackage("com.whatsapp");
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) // Whatsapp not installed. send sms:
                                {
                                    Uri msg_uri = Uri.parse("smsto:" + phone); //TODO: insert real number from server
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, msg_uri);
                                    startActivity(intent);
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
            }
        });
    }
}