package com.example.bravetogether_volunteerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager callbackManager;
    private String uid;
    private String Fname;
    private String Lname;
    private String email;
    private String imageURL;
    private TextView usernameView;
    private TextView emailView;
    private TextView password;
    private TextView confirmPassword;
    private LoginButton facebookLoginButton;
    private SignInButton googleSignInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN_GOOGLE = 1; //Google request call



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button); //referencing the facebook login button
        facebookLoginButton.setReadPermissions("email"); //For the facebook login to work

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { //A callback manager for our facebook button
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                Log.e("GraphRequest", "-------------" + response.toString());
                                try {
//                                    String obj = object.toString();                     //get complete JSON object refrence.
                                    String name = object.getString("name");                 //get particular JSON Object
                                    Log.d("name", "name: " + name);
                                    String[] FAndLname = name.split(" ", 2);
                                    Fname = FAndLname[0];
                                    Lname = FAndLname[1];
                                    email = object.getString("email");
                                    uid = object.getString("id");
                                    if (Profile.getCurrentProfile() != null) {                  //add this check because some people don't have profile picture
                                        imageURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
                                    }
                                    Log.d("before", "uid: "+ uid + ", email:  "+email);
                                    registerSocialUser(uid, Fname, Lname, email, imageURL);
                                    disconnectFromFacebook();
                                    //TODO: call to database and register information
                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name, last_name, email");  //set these parameter
                request.setParameters(parameters);
                request.executeAsync();
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
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        googleSignInButton = findViewById(R.id.googleSignInButton);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_GOOGLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_GOOGLE) { //Google activity result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("result",result.getStatus().toString());
            if (result.isSuccess()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(this, "You have logged in", Toast.LENGTH_SHORT).show();
                finish();
                //TODO implement the backend for the google login
            } else {
                Toast.makeText(this, "Login has failed", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data); //Facebook callback manager

        }
    }
    private void registerSocialUser (final String uid, final String Fname, final String Lname, final String email, final String imageURL) {
        Log.d("after", "uid: "+ uid + ", email:  "+email);
        String url = "https://mean-bat-31.loca.lt/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                params.put("uid", uid);
                params.put("firstname", Fname);
                params.put("lastname", Lname);
                params.put("email", email);
//                params.put("profile_picture", imageURL);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void signIn(View view) { //Sign in using email and password
        //TODO Implement sending the data to the database,confirming it's valid,creating a user and signing him in.
        //Check if username not exist && email not exists && passwords match



        //Getting Data From Localhost NodeJS Express Server --- Success!
        RequestQueue queue = Volley.newRequestQueue(this); //A queue for our requests
        String url = "http://localhost:3000/users"; //The url we are sending the get requests to

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() { //A single get request
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response.substring(1,response.length()-1));
                    Toast.makeText(LoginActivity.this, obj.get("vid").toString(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest); //Adding our request to the Volley Queue so it can run
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

}