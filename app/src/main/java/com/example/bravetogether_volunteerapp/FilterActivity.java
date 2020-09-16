package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class FilterActivity extends AppCompatActivity {

    static Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        /// make sure to change MainActivity to the activiry you fish to forward the data to.
        i = new Intent(FilterActivity.this, MainActivity.class);
    }

    public void radius(View view) {
        view.setSelected(!view.isSelected());
        String radius = view.getTag().toString();
        i.putExtra("radius", radius);
    }
    public void duration(View view) {
        String duration = view.getTag().toString();
        i.putExtra("duration", duration);
    }

    public void Search(View view) {
    }

    public void Save(View view) {

    }
}