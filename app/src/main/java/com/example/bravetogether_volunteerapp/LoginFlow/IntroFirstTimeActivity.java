package com.example.bravetogether_volunteerapp.LoginFlow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.adapters.IntroViewPageAdapter;

import java.util.ArrayList;


public class IntroFirstTimeActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList list;
    TextView alreadyHaveAnAccountTextView;
    Button letsStartButton;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_first_time);

        viewPager2 = findViewById(R.id.viewPager2);

        list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        IntroViewPageAdapter introViewPageAdapter = new IntroViewPageAdapter(list);
        viewPager2.setAdapter(introViewPageAdapter);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        alreadyHaveAnAccountTextView = findViewById(R.id.alreadyHaveAnAccountTextView);
        alreadyHaveAnAccountTextView.setPaintFlags(alreadyHaveAnAccountTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        alreadyHaveAnAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = prefs.edit();
                editor.putBoolean("first_time",true);
                editor.apply();
                startActivity(new Intent(IntroFirstTimeActivity.this, LoginActivity.class));
            }
        });

        letsStartButton = findViewById(R.id.letsBegin);
        letsStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = prefs.edit();
                editor.putBoolean("first_time",true);
                editor.apply();
                startActivity(new Intent(IntroFirstTimeActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

}
