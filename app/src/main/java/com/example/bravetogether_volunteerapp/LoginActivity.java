package com.example.bravetogether_volunteerapp;

import androidx.annotation.NonNull;
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
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager callbackManager;
    private TextView username;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private LoginButton facebookLoginButton;
    private SignInButton googleSignInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN = 1; //Google request call


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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googleSignInButton = findViewById(R.id.googleSignInButton);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data); //Facebook callback manager
        if(requestCode == SIGN_IN){ //Google activity result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                Toast.makeText(this, "You have logged in", Toast.LENGTH_SHORT).show();
                finish();
                //TODO implement the backend for the google login
            }else{
                Toast.makeText(this, "Login has failed", Toast.LENGTH_SHORT).show();
            }

        }
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

        //TODO Implement sending the data to the database,confirming it's valid,creating a user and signing him in.
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { //A google API method

    }
}