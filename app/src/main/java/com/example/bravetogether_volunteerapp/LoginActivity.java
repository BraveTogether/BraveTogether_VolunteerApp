package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager = CallbackManager.Factory.create();

    private TextView username;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private LoginButton facebookLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
//        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

//         Callback registration
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
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
}