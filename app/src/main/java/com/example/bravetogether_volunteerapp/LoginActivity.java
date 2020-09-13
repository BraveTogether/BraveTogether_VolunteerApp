package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView username = findViewById(R.id.usernameEditText);
    TextView email = findViewById(R.id.emailTextView);
    TextView password = findViewById(R.id.passwordEditText);
    TextView confirmPassword = findViewById(R.id.confirmPasswordEditText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signIn(View view) {
        String mUsername = username.getText().toString();
        String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        String mConfirmPassword = confirmPassword.getText().toString();
    }
}