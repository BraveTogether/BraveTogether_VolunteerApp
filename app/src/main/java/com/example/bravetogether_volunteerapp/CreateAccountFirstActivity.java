package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountFirstActivity extends AppCompatActivity {

    EditText firstNameEditText,lastNameEditText,emailEditText,passwordEditText,confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_first);

        getSocialMediaDetails();
    }

    public void getSocialMediaDetails(){
        Intent intent = getIntent();
        if(intent.getStringExtra("uid") != null) {
            String uid = intent.getStringExtra("uid"); //Use this UID to register.
            String firstName = intent.getStringExtra("first_name");
            String lastName = intent.getStringExtra("last_name");
            String email = intent.getStringExtra("email");

            firstNameEditText = findViewById(R.id.nameEditText);
            lastNameEditText = findViewById(R.id.familyNameEditText);
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

            firstNameEditText.setText(firstName); //Setting the parameters from facebook or google
            lastNameEditText.setText(lastName);
            emailEditText.setText(email);

            passwordEditText.setAlpha(0);
            confirmPasswordEditText.setAlpha(0);
        }
    }
}