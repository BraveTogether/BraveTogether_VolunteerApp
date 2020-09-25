package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class IntroFirstTimeActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList list;
    TextView alreadyHaveAnAccountTextView;
    Button letsStartButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_first_time);

        viewPager2 = findViewById(R.id.viewPager2);

        list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        alreadyHaveAnAccountTextView = findViewById(R.id.alreadyHaveAnAccountTextView);
        alreadyHaveAnAccountTextView.setPaintFlags(alreadyHaveAnAccountTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        alreadyHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroFirstTimeActivity.this,LoginActivity.class));
                finish();
            }
        });

        letsStartButton = findViewById(R.id.letsBegin);
        letsStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroFirstTimeActivity.this,LoginActivity.class));
            }
        });
    }

}
