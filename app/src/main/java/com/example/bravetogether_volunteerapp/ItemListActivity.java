package com.example.bravetogether_volunteerapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.LoginFlow.TestRetrieveImgFromFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static Context parentContext;
    public static List<JSONObject> activitiesList;
    static ContentResolver content;
    static String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ContentResolver content = getContentResolver();

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String rationale = "Please provide storage permission so that you can load your profile picture";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                Uri imageURI = Uri.parse("gs://bravetogethervolunteerapp.appspot.com/images/8bd91a65-85d9-412f-a299-26a4cd633194");
                filePath = retrieveImg(imageURI).getPath();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Toast.makeText(ItemListActivity.this, "Please allow the the app to access your storage in order to show your profile picture", Toast.LENGTH_SHORT).show();
            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("תוצאות חיפוש");


        //Bundle args = getIntent().getBundleExtra("activitiesList");
        //activitiesList = (List<JSONObject>) args.getSerializable("myList");
        activitiesList = FilterActivity.activities;

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private ProgressDialog showProgress(){
        ProgressDialog  pd = new ProgressDialog(this);
        pd.setMessage("Downloading... Please Wait");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        return pd;
    }

    final File rootPath = new File(Environment.getExternalStorageDirectory(), "Brave-Together");
    private File retrieveImg(Uri uri){
        final ProgressDialog  pd = showProgress();
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }
        final File localFile = new File(rootPath, "braveTogether.jpg");

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                if (localFile.canRead()){
                    pd.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                Toast.makeText(getApplicationContext(), "Download Incompleted", Toast.LENGTH_LONG).show();
            }
        });
        return localFile;
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        parentContext = this;
        //update the list
        VolunteerList.update();
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, VolunteerList.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<VolunteerList.VolunteerItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolunteerList.VolunteerItem item = (VolunteerList.VolunteerItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<VolunteerList.VolunteerItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);
            holder.mDuration.setText(mValues.get(position).duration);
            holder.mDistance.setText(mValues.get(position).distance);
            //Uri imageURI = Uri.parse(mValues.get(position).URI);
            //    final File rootPath = new File(Environment.getExternalStorageDirectory(), "Brave-Together");

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            holder.mPhoto.setImageBitmap(bitmap);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            holder.mPhoto.setImageBitmap(bitmap);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            //final TextView mIdView;
            final TextView mContentView;
            final TextView mDuration;
            final TextView mDistance;
            final ImageView mPhoto;

            ViewHolder(View view) {
                super(view);
                //mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
                mDuration = (TextView) view.findViewById(R.id.duration);
                mDistance = (TextView) view.findViewById(R.id.distance);
                mPhoto = (ImageView) view.findViewById(R.id.image);
            }
        }
    }
}