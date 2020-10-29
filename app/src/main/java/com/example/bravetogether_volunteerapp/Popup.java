package com.example.bravetogether_volunteerapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Popup extends Activity {

    /**
     * @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * setContentView(R.layout.popup_registered_user);
     * <p>
     * DisplayMetrics dm = new DisplayMetrics();
     * getWindowManager().getDefaultDisplay().getMetrics(dm);
     * <p>
     * int width = dm.widthPixels;
     * int height = dm.heightPixels;
     * <p>
     * getWindow().setLayout((int)(width), (int)(height*0.8));
     * }
     */

    PopupWindow popUp;
    TextView relativeLayout = (TextView)findViewById(R.id.aboutPlaceOfVolunteerTextView);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_registered_user, null);



        if(relativeLayout.getParent() == null)
            Log.d("yes", "YES");

        popUp = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //popUp.showAtLocation(relativeLayout, Gravity.CENTER,0,0);
        popUp.dismiss();

    }
}
