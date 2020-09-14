package com.example.bravetogether_volunteerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView username;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private LoginButton facebookLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button); //referencing the facebook login button
        facebookLoginButton.setReadPermissions("email", "public_profile", "user_friends"); //For the facebook login to work

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { //A callback manager for our facebook button
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                username.setText(loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Error", exception.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void signIn(View view) { //Sign in using email and password

        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailTextView);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);


        String mUsername = username.getText().toString();
        String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();
        String mConfirmPassword = confirmPassword.getText().toString();

        //TODO
        //Implement sending the data to the database,confirming it's valid,creating a user and signing him in.

    }

    public void registerWithFacebook(View view){

    }
}