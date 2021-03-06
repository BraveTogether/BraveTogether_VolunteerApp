package com.example.bravetogether_volunteerapp.LoginFlow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolleySingleton;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final String sharedPrefFile = "com.example.android.BraveTogether_VolunteerApp";
    private CallbackManager callbackManager;
    private String uid,firstname,lastname,email,imageURL;
    private LoginButton facebookLoginButton;
    private SignInButton googleSignInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN_GOOGLE = 1; //Google request call

    private SharedPreferences mPreferences;
    private Button signInWithEmailButton;
    private TextView skipNow,gotAnAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signInWithEmailButton = findViewById(R.id.connectWithEmail); //The sign in with email button functionality
        signInWithEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,CreateAccountFirstActivity.class));
            }
        });

        skipNow = findViewById(R.id.skipNow); //Underline the text at the bottom
        skipNow.setPaintFlags(skipNow.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        gotAnAccount = findViewById(R.id.gotAnAccountTextView); //Underline the skip text
        gotAnAccount.setPaintFlags(gotAnAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        callbackManager = CallbackManager.Factory.create();
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

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
                                    firstname = FAndLname[0];
                                    lastname = FAndLname[1];
                                    email = object.getString("email");
                                    uid = object.getString("id");
                                    if (Profile.getCurrentProfile() != null) {                  //add this check because some people don't have profile picture
                                        imageURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
                                    }
                                    Intent intent = new Intent(RegisterActivity.this, CreateAccountFirstActivity.class);
                                    intent.putExtra("uid",uid);
                                    intent.putExtra("first_name",firstname);
                                    intent.putExtra("last_name",lastname);
                                    intent.putExtra("email",email);
//                                    disconnectFromFacebook();
                                    startActivity(intent);
                                    finish();
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
                Toast.makeText(RegisterActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
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
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if (acct != null) {
                    firstname = acct.getGivenName(); //Only the first name
                    lastname = acct.getFamilyName(); //Only the family name
                    email = acct.getEmail(); //google email
                    uid = acct.getId(); //The user ID --- Use this to identify the user on the database
                    Uri personPhoto = acct.getPhotoUrl(); //A profile picture from google
                }
                Intent intent = new Intent(RegisterActivity.this, CreateAccountFirstActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("first_name",firstname);
                intent.putExtra("last_name",lastname);
                intent.putExtra("email",email);
                startActivity(intent);
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

    public void skipToHomePage(View view) {
        startActivity(new Intent(RegisterActivity.this, RegisterWhereActivity.class));
    }

    @Override
    protected void onPause(){
        super.onPause();
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

    public void goToLoginActivity(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}