package com.example.bravetogether_volunteerapp.LoginFlow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolleySingleton;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateAccountFirstActivity extends AppCompatActivity {

    // initialize variables
    EditText mTextUserPrivateName;
    EditText mTextUserFamilyName;
    EditText mTextUserEmail;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextPhoneNumber;
    EditText mTextAbout;
    Button mButtonLetsVolunteer;
    Button mButtonAddPicture;
    AwesomeValidation awesomeValidation;
    private String url;
    String address = null;
    AutocompleteSupportFragment autocompleteFragment;

    // Firebase
    StorageReference mStorageRef;
    Uri imgUri;
    ImageView img;
    private final int PICK_IMAGE_REQUEST = 71;

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
        mTextAbout = (EditText)findViewById(R.id.About);
        mButtonAddPicture = (Button)findViewById(R.id.addImageButtonImageView);
        mButtonLetsVolunteer = (Button)findViewById(R.id.button_lets_volunteer);
        url = getString(R.string.apiUrl);
        // Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();
        img = (ImageView) findViewById(R.id.profile_image_2);

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

        // Function for calling our REQUEST function
        // The call will occur only if the validation for all fields is good
        mButtonLetsVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check validation
                if(awesomeValidation.validate()){
                    registerUser(mTextUserPrivateName.toString(), mTextUserFamilyName.toString(),
                            mTextUserEmail.toString(), mTextPassword.toString(), mTextPhoneNumber.toString(),
                             mTextAbout.toString());
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Validation Failed - Please fill all fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });

        // Address AutoFill 2
//        // Initialize the AutocompleteSupportFragment.
//        autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.Address);
//
//        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
//
//        if (!Places.isInitialized()) {
//            String apiKey = "AIzaSyA0hReShDEqNU3cdSm9eot1atb8-CKBy0Q";
//            Places.initialize(getApplicationContext(), apiKey);
//        }
//        PlacesClient placesClient = Places.createClient(this);
//
//        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
//                new LatLng(29.4533796, 34.2674994),
//                new LatLng(33.3356317, 35.8950234)));
//        autocompleteFragment.setCountries("IN");
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(@NotNull Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(@NotNull Status status) {
//                // TODO: Handle the error.
//                Log.i("TAG", "An error occurred: " + status);
//            }
//        });

//        // Address AutoFill
//        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.Address);
//
//        Places.initialize(getApplicationContext(), apiKey); //Initialize SDK
//        PlacesClient placesClient = Places.createClient(this);
//
//        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
//                new LatLng(29.4533796, 34.2674994),
//                new LatLng(33.3356317, 35.8950234)));
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS));
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                address = place.getAddress();
//                Log.i("place", "Place: " + place.getAddress());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("place.error", "An error occurred: " + status);
//            }
//        });
    }

    // ------------------------------- Photo Uploading ------------------------------- //

    // Function for creating the intent that choose the photo to be uploaded to firebase
    void chooseImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

//    // ----------------------------------- Retrieve Image ----------------------------------- //
//
//    final File rootPath = new File(Environment.getExternalStorageDirectory(), "Brave-Together");
//
//    private ProgressDialog showProgress(){
//        ProgressDialog  pd = new ProgressDialog(this);
//        pd.setMessage("Downloading... Please Wait");
//        pd.setIndeterminate(true);
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();
//        return pd;
//    }
//
//    private void retrieveImg(Uri uri){
//        final ProgressDialog  pd = showProgress();
//        if (!rootPath.exists()) {
//            rootPath.mkdirs();
//        }
//        final File localFile = new File(rootPath, "braveTogether.jpg");
//
//        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
//        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener <FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//                if (localFile.canRead()){
//                    pd.dismiss();
//                }
//                Toast.makeText(getApplicationContext(), "Download Completed", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
//                Toast.makeText(getApplicationContext(), "Download Incompleted", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    // uploading the image to firebase
    private void uploadImg() {
        if(imgUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateAccountFirstActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateAccountFirstActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    // setting the chosen picture inside the imageView and calling uploadImg method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                img.setImageBitmap(bitmap);
                uploadImg();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------------------- Data Base ------------------------------- //

    // Function for inserting all data from edit texts into our data base
    private void registerUser (final String firstName, final String lastName, final String email,
                               final String password, final String phoneNumber, final String about) {
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
}