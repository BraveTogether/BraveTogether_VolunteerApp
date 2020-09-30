package com.example.bravetogether_volunteerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountFirstActivity extends AppCompatActivity {

    // initialize variables
    Button mButtonAddPicture;
    EditText mTextUserPrivateName;
    EditText mTextUserFamilyName;
    EditText mTextUserEmail;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextPhoneNumber;
    EditText mTextAddress;
    EditText mTextAbout;
    Button mButtonLetsVolunteer;
    AwesomeValidation awesomeValidation;
    private String url= "http://35.214.78.251:8080";

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
        mTextPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        mTextAddress = (EditText)findViewById(R.id.Address);
        mTextAbout = (EditText)findViewById(R.id.About);
        mButtonLetsVolunteer = (Button)findViewById(R.id.button_lets_volunteer);

        // Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validation for private name
        awesomeValidation.addValidation(this, R.id.privateNameEditText,
                RegexTemplate.NOT_EMPTY, R.string.invalid_private_name);
        // Validation for family name
        awesomeValidation.addValidation(this, R.id.familyNameEditText,
                RegexTemplate.NOT_EMPTY, R.string.invalid_family_name);
        // Validation for email
        awesomeValidation.addValidation(this, R.id.emailEditText,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        // Validation for password:
        // 1. At least one upper case English letter, (?=.*?[A-Z])
        // 2. At least one lower case English letter, (?=.*?[a-z])
        // 3. At least one digit, (?=.*?[0-9])
        // 4. At least one special character, (?=.*?[#?!@$%^&*-])
        // 5. Minimum eight in length .{8,}
        awesomeValidation.addValidation(this, R.id.passwordEditText,
                "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", R.string.invalid_password);
        // Validation for confirm password
        awesomeValidation.addValidation(this, R.id.confirmPasswordEditText,
                R.id.passwordEditText, R.string.invalid_confirm_passwords);
        // Validation for phone number
        awesomeValidation.addValidation(this, R.id.PhoneNumber,
                "05[0-9]{8}$", R.string.invalid_mobile);
        // Validation for address //TODO: connect to google maps
        awesomeValidation.addValidation(this, R.id.Address,
                RegexTemplate.NOT_EMPTY, R.string.invalid_address);

        /**
         * function for calling our REQUEST function
         * the call will occur only if the validation for all fields is good
         */
        mButtonLetsVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check validation
                if(awesomeValidation.validate()){
//                    // on success
//                    Toast.makeText(getApplicationContext(),
//                            "Form Validate Successfully", Toast.LENGTH_SHORT).show();
                    registerUser(mTextUserPrivateName.toString(), mTextUserFamilyName.toString(),
                            mTextUserEmail.toString(), mTextPassword.toString(), mTextPhoneNumber.toString(),
                            mTextAddress.toString(), mTextAbout.toString());
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Validation Failed - Please fill all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * function for inserting all data from edit texts into our data base
     */
    private void registerUser (final String firstName, final String lastName, final String email,
                                     final String password, final String phoneNumber, final String address, final String about) {
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
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                params.put("email", email);
                params.put("password", password);
                params.put("phonenumber", phoneNumber);
                params.put("address", address);
                params.put("about", about);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void addPicture(View view) {

    }
}