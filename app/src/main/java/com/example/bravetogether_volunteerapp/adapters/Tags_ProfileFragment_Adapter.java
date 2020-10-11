package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private ArrayList<String> tagsImages; //need to check how to set those pictures for now using url.
    private Context context;

    public Tags_ProfileFragment_Adapter (Context context, ArrayList<String> names, ArrayList<String> details, ArrayList<String> imgs){
        this.context = context;
        tagsDetails = details;
        tagsImages = imgs;
        tagsNames = names;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag_item,parent,false);
        TagViewHolder holder = new TagViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
         //image handler by using url
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TagViewHolder extends RecyclerView.ViewHolder{

        ImageView tagImage;
        TextView tagName,tagDetails;
        RelativeLayout parentlayout ;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagImage = (ImageView) itemView.findViewById(R.id.tagImage);
            tagName = (TextView) itemView.findViewById(R.id.tagName);
            tagDetails = (TextView) itemView.findViewById(R.id.tagDetails);
            parentlayout = (RelativeLayout) itemView.findViewById(R.id.taglayout); // check if needed -> just in case ov clicking the item

        }
    }
}
