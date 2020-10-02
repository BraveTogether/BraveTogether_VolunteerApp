package com.example.bravetogether_volunteerapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Scanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
    static SharedPreferences mPreferences;
    static String lastScanDay, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,scannView);
        resultData = findViewById(R.id.resultsOfQr);


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        lastScanDay = mPreferences.getString("lastScanDay", "1.1.2019");
        email = mPreferences.getString("UserEmail", "null");

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String results, date,credits, URL;
                        results = result.getText();
                        String[] fields = results.split(" ");
                        credits = fields[0];
                        date = fields[1];
                        Date LastScanned=null, QRDate=null;
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            LastScanned = formatter.parse(lastScanDay);
                            QRDate = formatter.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if ((LastScanned.before(QRDate))){
                            updateCredits(credits);
                            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                            preferencesEditor.putString("lastScanDay", date);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You have already scanned today", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }

    public void updateCredits(final String credits){
        String URL = getString(R.string.apiUrl) + "/user/" + email;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error.response", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("credits", credits);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Scanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }


}
