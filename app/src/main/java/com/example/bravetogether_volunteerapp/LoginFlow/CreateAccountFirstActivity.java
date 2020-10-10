package com.example.bravetogether_volunteerapp.LoginFlow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.bravetogether_volunteerapp.MainActivity;
import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolleySingleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Date;

public class CreateAccountFirstActivity extends AppCompatActivity {

    // initialize variables
    EditText mTextUserPrivateName;
    EditText mTextUserFamilyName;
    EditText mTextUserEmail;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextPhoneNumber;
    EditText mTextAddress;
    EditText mTextAbout;
    Button mButtonLetsVolunteer;
    Button mButtonAddPicture;

    AwesomeValidation awesomeValidation;
    private String url= "http://35.214.78.251:8080";

    // firebase
    StorageReference mStorageRef;
    FirebaseStorage storage;
    Uri imgUri;
    String imgPath = null;
    ImageView img;
    private final int GALLERY_REQUEST_CODE = 1;
    private final int CAMERA_REQUEST_CODE = 2;
    public static final int CAMERA_PERM_CODE = 101;
    String mCurrentPhotoPath;

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
        mButtonAddPicture = (Button)findViewById(R.id.addImageButtonImageView);
        mButtonLetsVolunteer = (Button)findViewById(R.id.button_lets_volunteer);
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

        // Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mButtonAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });
    }

    // ------------------------------- Firebase ------------------------------- //

    private AlertDialog.Builder showPhotoSrcDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose whether to take a new picture or to choose one from gallery");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alert.setView(input);
        return alert;
    }

    private void askCameraPermissions() throws IOException {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        else getPhotoFromCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != CAMERA_PERM_CODE)
            Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                getPhotoFromCamera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Function for choosing the photo to be uploaded to firebase
    public void chooseImg(){
        AlertDialog.Builder alert = showPhotoSrcDialog();
        alert.setPositiveButton("Take a new picture", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    askCameraPermissions();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNegativeButton("choose a picture from gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
            }
        });
        alert.show();
    }

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
        else showToast("uploadImg imgUri == null");
    }

    private void showToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void getPhotoFromLibrary(){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            img.setImageBitmap(bitmap);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Uri setImageUri() {
        // Store image in dcim
        showToast("setImageUri");
        Uri uri = null;

        return uri;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_test";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void getPhotoFromCamera() throws IOException {
        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "a directory");
        Uri photoURI = FileProvider.getUriForFile(CreateAccountFirstActivity.this, CreateAccountFirstActivity.this.getApplicationContext().getPackageName() + ".provider", createImageFile());
        m_intent.setDataAndType(photoURI, "text/*");
        m_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // falls here since uri isnt good:
        Log.d("test", "before");
        startActivityForResult(m_intent, CAMERA_REQUEST_CODE);
        Log.d("test", "after");
    }

//    private void getPhotoFromCamera() {
//        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(Environment.getExternalStorageDirectory(), "a directory");
//        Uri uri = Uri.fromFile(file);
//        m_intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        // falls here since uri isnt good:
//        Log.d("test", "before");
//        startActivityForResult(m_intent, CAMERA_REQUEST_CODE);
//        Log.d("test", "after");
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK) showToast("onActivityResult.. resultCode != Activity.RESULT_OK");
        else showToast("onActivityResult.. resultCode == Activity.RESULT_OK");

        if(data != null && data.getData() != null) showToast("onActivityResult.. data != null && data.getData() != null");
        else if(data == null) showToast("onActivityResult.. data == null");
        else showToast("onActivityResult.. data.getData() == null");

        if(resultCode == Activity.RESULT_OK && data != null) {
            if(requestCode == GALLERY_REQUEST_CODE && data.getData() != null){
                imgUri = data.getData();
                showToast("GALLERY_REQUEST_CODE");
                getPhotoFromLibrary();
                uploadImg();
            }else if(requestCode == CAMERA_REQUEST_CODE){

                showToast("CAMERA_REQUEST_CODE");
                imgUri = Uri.parse(mCurrentPhotoPath);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(photo);
                uploadImg();
            }
        }
    }

    // ------------------------------- Data Base ------------------------------- //

    // Function for inserting all data from edit texts into our data base
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

}



//package com.example.bravetogether_volunteerapp.LoginFlow;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.content.FileProvider;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.ActivityNotFoundException;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.Image;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.text.InputType;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.EditText;
//
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.basgeekball.awesomevalidation.AwesomeValidation;
//import com.basgeekball.awesomevalidation.ValidationStyle;
//import com.basgeekball.awesomevalidation.utility.RegexTemplate;
//import com.example.bravetogether_volunteerapp.MainActivity;
//import com.example.bravetogether_volunteerapp.R;
//import com.example.bravetogether_volunteerapp.VolleySingleton;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
////import com.google.firebase.storage.StorageReference;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.Date;
//
//public class CreateAccountFirstActivity extends AppCompatActivity {
//
//    // initialize variables
//    EditText mTextUserPrivateName;
//    EditText mTextUserFamilyName;
//    EditText mTextUserEmail;
//    EditText mTextPassword;
//    EditText mTextConfirmPassword;
//    EditText mTextPhoneNumber;
//    EditText mTextAddress;
//    EditText mTextAbout;
//    Button mButtonLetsVolunteer;
//    Button mButtonAddPicture;
//
//    AwesomeValidation awesomeValidation;
//    private String url= "http://35.214.78.251:8080";
//
//    // firebase
//    StorageReference mStorageRef;
//    FirebaseStorage storage;
//    Uri imgUri;
//    String imgPath = null;
//    ImageView img;
//    private final int GALLERY_REQUEST_CODE = 1;
//    private final int CAMERA_REQUEST_CODE = 2;
//    public static final int CAMERA_PERM_CODE = 101;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account_first);
//
//        // assign variables
//        mTextUserPrivateName = (EditText)findViewById(R.id.privateNameEditText);
//        mTextUserFamilyName = (EditText)findViewById(R.id.familyNameEditText);
//        mTextUserEmail = (EditText)findViewById(R.id.emailEditText);
//        mTextPassword = (EditText)findViewById(R.id.passwordEditText);
//        mTextConfirmPassword = (EditText)findViewById(R.id.confirmPasswordEditText);
//        mTextPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
//        mTextAddress = (EditText)findViewById(R.id.Address);
//        mTextAbout = (EditText)findViewById(R.id.About);
//        mButtonAddPicture = (Button)findViewById(R.id.addImageButtonImageView);
//        mButtonLetsVolunteer = (Button)findViewById(R.id.button_lets_volunteer);
//        img = (ImageView) findViewById(R.id.profile_image_2);
//
//        // Initialize Validation Style
//        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//
//        // Validation for private name
//        awesomeValidation.addValidation(this, R.id.privateNameEditText,
//                RegexTemplate.NOT_EMPTY, R.string.invalid_private_name);
//        // Validation for family name
//        awesomeValidation.addValidation(this, R.id.familyNameEditText,
//                RegexTemplate.NOT_EMPTY, R.string.invalid_family_name);
//        // Validation for email
//        awesomeValidation.addValidation(this, R.id.emailEditText,
//                Patterns.EMAIL_ADDRESS, R.string.invalid_email);
//        // Validation for password:
//        // 1. At least one upper case English letter, (?=.*?[A-Z])
//        // 2. At least one lower case English letter, (?=.*?[a-z])
//        // 3. At least one digit, (?=.*?[0-9])
//        // 4. At least one special character, (?=.*?[#?!@$%^&*-])
//        // 5. Minimum eight in length .{8,}
//        awesomeValidation.addValidation(this, R.id.passwordEditText,
//                "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", R.string.invalid_password);
//        // Validation for confirm password
//        awesomeValidation.addValidation(this, R.id.confirmPasswordEditText,
//                R.id.passwordEditText, R.string.invalid_confirm_passwords);
//        // Validation for phone number
//        awesomeValidation.addValidation(this, R.id.PhoneNumber,
//                "05[0-9]{8}$", R.string.invalid_mobile);
//        // Validation for address //TODO: connect to google maps
//        awesomeValidation.addValidation(this, R.id.Address,
//                RegexTemplate.NOT_EMPTY, R.string.invalid_address);
//
//        // Function for calling our REQUEST function
//        // The call will occur only if the validation for all fields is good
//        mButtonLetsVolunteer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // check validation
//                if(awesomeValidation.validate()){
////                    // on success
////                    Toast.makeText(getApplicationContext(),
////                            "Form Validate Successfully", Toast.LENGTH_SHORT).show();
//                    registerUser(mTextUserPrivateName.toString(), mTextUserFamilyName.toString(),
//                            mTextUserEmail.toString(), mTextPassword.toString(), mTextPhoneNumber.toString(),
//                            mTextAddress.toString(), mTextAbout.toString());
//                }else{
//                    Toast.makeText(getApplicationContext(),
//                            "Validation Failed - Please fill all fields correctly", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Firebase
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//        mButtonAddPicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImg();
//            }
//        });
//    }
//
//    // ------------------------------- Firebase ------------------------------- //
//
//    private AlertDialog.Builder showPhotoSrcDialog(){
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Choose whether to take a new picture or to choose one from gallery");
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        alert.setView(input);
//        return alert;
//    }
//
//    private void askCameraPermissions() {
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
//        else getPhotoFromCamera();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode != CAMERA_PERM_CODE)
//                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
//        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) getPhotoFromCamera();
//    }
//
//    // Function for choosing the photo to be uploaded to firebase
//    public void chooseImg(){
//        AlertDialog.Builder alert = showPhotoSrcDialog();
//        alert.setPositiveButton("Take a new picture", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                askCameraPermissions();
//            }
//        });
//        alert.setNegativeButton("choose a picture from gallery", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
//            }
//        });
//        alert.show();
//    }
//
//    private void uploadImg() {
//        if(imgUri != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = mStorageRef.child("images/"+ UUID.randomUUID().toString());
//            ref.putFile(imgUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(CreateAccountFirstActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(CreateAccountFirstActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
//        }
//        else         showToast("uploadImg imgUri == null");
//
//    }
//
//    private void showToast(CharSequence text){
//        Context context = getApplicationContext();
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//    }
//
//    private void getPhotoFromLibrary(){
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
//            img.setImageBitmap(bitmap);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String imageFileName = "JPEG_test_";
////        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        showToast(storageDir.getPath());
//        // Save a file: path for use with ACTION_VIEW intents
////        currentPhotoPath = image.getAbsolutePath();
//        return File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//    }
//
////    private void getPhotoFromCamera() {
////        showToast("getPhotoFromCamera");
////        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////        if(cameraIntent.resolveActivity(getPackageManager()) != null){
////            showToast("cameraIntent.resolveActivity(getPackageManager()) != null");
////            File photoFile = null;
////            try { photoFile = createImageFile();} catch (IOException e){showToast("catch"); }
////            if (photoFile != null) {
////                Uri photoURI = FileProvider.getUriForFile(this, "net.smallacademy.android.fileprovider", photoFile);
////                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
////                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
////            }
////
////        }
////    }
//
//    public Uri setImageUri() {
//        // Store image in dcim
//        showToast("setImageUri");
////        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
//        Uri uri = null;
////        uri = Uri.fromFile(file);
////        this.imgPath = file.getAbsolutePath();
//        return uri;
//    }
//
//    private void getPhotoFromCamera() {
//        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        m_intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
////        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
////        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
////        m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(m_intent, CAMERA_REQUEST_CODE);
//    }
//
////    public Uri getImageUri(Context inContext, Bitmap inImage) {
////        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
////        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
////        return Uri.parse(path);
////    }
////
////    public String getRealPathFromURI(Uri uri) {
////        String path = "";
////        if (getContentResolver() != null) {
////            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
////            if (cursor != null) {
////                cursor.moveToFirst();
////                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
////                path = cursor.getString(idx);
////                cursor.close();
////            }
////        }
////        return path;
////    }
//
//    public String getImagePath() {
//        return imgPath;
//    }
//
//
//    public Bitmap decodeFile(String path) {
//        try {
//            // Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(path, o);
//            // The new size we want to scale to
//            final int REQUIRED_SIZE = 70;
//
//            // Find the correct scale value. It should be the power of 2.
//            int scale = 1;
//            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
//                scale *= 2;
//
//            // Decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            return BitmapFactory.decodeFile(path, o2);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode != Activity.RESULT_OK) showToast("onActivityResult.. resultCode != Activity.RESULT_OK");
//        else showToast("onActivityResult.. resultCode == Activity.RESULT_OK");
//
//        if(data != null && data.getData() != null) showToast("onActivityResult.. data != null && data.getData() != null");
//        else if(data == null) showToast("onActivityResult.. data == null");
//        else showToast("onActivityResult.. data.getData() == null");
//
//        if(resultCode == Activity.RESULT_OK && data != null) {
//            if(requestCode == GALLERY_REQUEST_CODE && data.getData() != null){
//                imgUri = data.getData();
//                showToast("GALLERY_REQUEST_CODE");
//                getPhotoFromLibrary();
//                uploadImg();
//            }else if(requestCode == CAMERA_REQUEST_CODE){
////                //File object of camera image
////                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
////
////                //Uri of camera image
////                imgUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
//                showToast("CAMERA_REQUEST_CODE");
//
////                String selectedImagePath = getImagePath();
////                img.setImageBitmap(decodeFile(selectedImagePath));
//
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                img.setImageBitmap(photo);
////                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
////                Uri tempUri = getImageUri(getApplicationContext(), photo);
////
////                // CALL THIS METHOD TO GET THE ACTUAL PATH
////                File finalFile = new File(getRealPathFromURI(tempUri));
//
////                Uri imageUri = data.getData();
////                uploadImg();
//            }
//        }
//    }
//
//    // ------------------------------- Data Base ------------------------------- //
//
//    // Function for inserting all data from edit texts into our data base
//    private void registerUser (final String firstName, final String lastName, final String email,
//                                     final String password, final String phoneNumber, final String address, final String about) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("error.response", error.getMessage());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<>();
//                params.put("firstname", firstName);
//                params.put("lastname", lastName);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("phonenumber", phoneNumber);
//                params.put("address", address);
//                params.put("about", about);
//                return params;
//            }
//        };
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }
//
//}
