package com.example.bravetogether_volunteerapp.LoginFlow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.home;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    SharedPreferences prefs;
    //TODO add facebook and google log in buttons and functionality

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logIn(View view) {
        email = findViewById(R.id.editTextEmailAddress);
        pass = findViewById(R.id.editTextPassword);
        prefs = getSharedPreferences("user",MODE_PRIVATE);
        if(email.getText() == null || pass.getText() == null){
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }else{
            //TODO Go to server with email and pass and check for user
        }
    }

    public void skipToHomePage(View view) {
        startActivity(new Intent(LoginActivity.this,home.class));
    }

    public void forgotPassword(View view) {
        //TODO check if there is an email,if there is send the password reset link to the email
        Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
    }
}