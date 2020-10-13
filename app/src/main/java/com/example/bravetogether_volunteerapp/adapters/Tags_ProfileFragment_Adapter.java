package com.example.bravetogether_volunteerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class Tags_ProfileFragment_Adapter extends RecyclerView.Adapter<Tags_ProfileFragment_Adapter.TagViewHolder> {
    // ----------- Variables -------------
    private ArrayList<String> tagsNames;
    private ArrayList<String> tagsDetails;
    //private ArrayList<Integer> tagsImages; //The Integer represent the Img resource.Checked but waits for DB
    private Context context;
    // ----------- For Debug -------------
    private static final String TAG = "Tags_ProfileFragment_Adapter";

    public Tags_ProfileFragment_Adapter (Context context, ArrayList<String> names, ArrayList<String> details){
        this.context = context;
        tagsDetails = details;
        //tagsImages = imgs; --> waits for DB
        tagsNames = names;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag_item,parent,false);
        TagViewHolder holder = new TagViewHolder(view);
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.tagDetails.setText(tagsDetails.get(position));
        holder.tagName.setText(tagsNames.get(position));
        //holder.tagImage.setImageResource(tagsImages.get(position));--> waits for DB
    }

    @Override
    public int getItemCount() {
        return tagsNames.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder{

        ImageView tagImage;
        TextView tagName,tagDetails;
        RelativeLayout parentlayout ;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
           // tagImage = (ImageView) itemView.findViewById(R.id.tagImage); --> waits for DB
            tagName = (TextView) itemView.findViewById(R.id.tagName);
            tagDetails = (TextView) itemView.findViewById(R.id.tagDetails);
            parentlayout = (RelativeLayout) itemView.findViewById(R.id.taglayout); // check if needed -> just in case ov clicking the item
        }
    }
}
