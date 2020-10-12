package com.example.bravetogether_volunteerapp.LoginFlow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bravetogether_volunteerapp.CallToServer;
import com.example.bravetogether_volunteerapp.R;

import java.security.MessageDigest;

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
            Toast.makeText(this, "Please enter an email and a password", Toast.LENGTH_SHORT).show();
        }else{
            String sha1Password = getSha1Hex(pass.getText().toString());
            //TODO check if password from user details is the same as password in sha1Password
        }
    }

    public static String getSha1Hex(String clearString) //The code that hashes the password to SHA-1
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes)
            {
                buffer.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        }
        catch (Exception ignored)
        {
            ignored.printStackTrace();
            return null;
        }
    }

    public void skipToHomePage(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterWhereActivity.class));
    }

    public void forgotPassword(View view) {
        //TODO check if there is an email,if there is send the password reset link to the email
        Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
    }
}