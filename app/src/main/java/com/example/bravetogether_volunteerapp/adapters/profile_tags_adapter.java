package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.media.Image;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.R;

import java.util.ArrayList;


public class profile_tags_adapter extends  RecyclerView.Adapter<profile_tags_adapter.ViewHolder> {
    //For Debug
    private static final String TAG = "profile_tags_adapter";
    private ArrayList<String> mTagName;
    private ArrayList<String> mTagDetails;
    private ArrayList<Integer> mTagImage;
    private Context mContext;

    public profile_tags_adapter( Context context, ArrayList<String> tagName, ArrayList<String> tagDetails, ArrayList<Integer> tagImage)   {
                           mContext = context;
                           mTagDetails = tagDetails;
                           mTagImage = tagImage;
                           mTagName = tagName;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.tagImage.setImageResource(mTagImage.get(position));
        holder.tagName.setText(mTagName.get(position));
        holder.tagDetails.setText(mTagDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return mTagImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tagImage;
        TextView tagName;
        TextView tagDetails;
        RelativeLayout taglayout;
        LinearLayout tagLinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            tagImage = itemView.findViewById(R.id.tagImage);
            tagName = itemView.findViewById(R.id.tagName);
            tagDetails = itemView.findViewById(R.id.tagDetails);
            taglayout = itemView.findViewById(R.id.taglayout);
            tagLinearLayout = itemView.findViewById(R.id.tagLinearLayout);
        }
    }
}
