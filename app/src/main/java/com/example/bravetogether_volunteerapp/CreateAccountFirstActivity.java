package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class CreateAccountFirstActivity extends AppCompatActivity {

    // initialize variables
    Button mButtonAddPicture;
    EditText mTextUserPrivateName;
    EditText mTextUserFamilyName;
    EditText mTextUserEmail;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    Button mButtonNext;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_first);

        // assign variables
        mTextUserPrivateName = (EditText)findViewById(R.id.privateNameEditText);
        mTextUserFamilyName = (EditText)findViewById(R.id.familyNameEditText);
        mTextUserEmail = (EditText)findViewById(R.id.emailEditText);
        mTextPassword = (EditText)findViewById(R.id.passwordEditText);
        mTextConfirmPassword = (EditText)findViewById(R.id.confirmPasswordEditText);
        mButtonNext = (Button)findViewById(R.id.button_next);

        // Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // TODO: decide which kind of validation we want: password, names ..

        // Validation for private name
        awesomeValidation.addValidation(this, R.id.privateNameEditText,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        // Validation for family name
        awesomeValidation.addValidation(this, R.id.familyNameEditText,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        // Validation for email
        awesomeValidation.addValidation(this, R.id.emailEditText,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        // Validation for password
        awesomeValidation.addValidation(this, R.id.passwordEditText,
                ".{6,}", R.string.invalid_password);
        // Validation for confirm password
        awesomeValidation.addValidation(this, R.id.confirmPasswordEditText,
                R.id.passwordEditText, R.string.invalid_confirm_passwords);

//        // Validation for mobile number
//        awesomeValidation.addValidation(this, R.id.familyNameEditText,
//                "05[0-9]{8}$", R.string.invalid_mobile);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check validation
                if(awesomeValidation.validate()){
                    // on success
                    Toast.makeText(getApplicationContext(),
                            "Form Validate Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Validation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //         volley - loginactivity - function()
}