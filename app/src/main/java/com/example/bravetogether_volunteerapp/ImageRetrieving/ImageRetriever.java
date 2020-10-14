package com.example.bravetogether_volunteerapp.ImageRetrieving;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nabinbhandari.android.permissions.PermissionHandler;
import java.io.File;

/*
Put the following code at the beginning of your onCreate function
Notice that you have to replace imageViewName with your image view variable
 */

//            final ImageRetriever imageRetriever = new ImageRetriever();
//
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            String rationale = "Please provide storage permission so that you can load your profile picture";
//            Permissions.Options options = new Permissions.Options()
//            .setRationaleDialogTitle("Info")
//            .setSettingsDialogTitle("Warning");
//
//            Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
//    @Override
//    public void onGranted() {
//            Uri imageURI = Uri.parse("gs://bravetogethervolunteerapp.appspot.com/images/8bd91a65-85d9-412f-a299-26a4cd633194");
//            filePath = imageRetriever.retrieveImg(imageURI, TestRetrieveImgFromFirebase.this).getPath();
//            }
//    @Override
//    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//            Toast.makeText(TestRetrieveImgFromFirebase.this, "Please allow the the app to access your storage in order to show your profile picture", Toast.LENGTH_SHORT).show();
//            }
//            });
//
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            imageViewName.setImageBitmap(bitmap);

// ------------------------------------ ImageRetriever Class (Don't change it) ------------------------------------ //

public class ImageRetriever {
    private ProgressDialog showProgress(Activity activity){
        ProgressDialog  pd = new ProgressDialog(activity);
        pd.setMessage("Downloading... Please Wait");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        return pd;
    }

    final File rootPath = new File(Environment.getExternalStorageDirectory(), "Brave-Together");

    public File retrieveImg(Uri uri, final Activity activity){
        final ProgressDialog pd = showProgress(activity);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        //TODO: change img name if we don't want to override previous pictures
        final File localFile = new File(rootPath, "braveTogether.jpg");

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                if (localFile.canRead()){
                    pd.dismiss();
                }
                Toast.makeText(activity, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                Toast.makeText(activity, "Download Incompleted", Toast.LENGTH_LONG).show();
            }
        });
        return localFile;
    }
}
