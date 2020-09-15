package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bravetogether_volunteerapp.R;

public class GeneralActivityListAdapter extends ArrayAdapter<String> {

    Context context;
    String rTitle[];
    String rDescription[];

    public GeneralActivityListAdapter(Context c,String title[],String description[]){
        super(c, R.layout.general_activity_list_item,title);
        this.context = c;
        this.rTitle = title;
        this.rDescription = description;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.general_activity_list_item,parent,false);
        TextView myTitle = row.findViewById(R.id.volunteerTitleTextView);
        TextView myDescription = row.findViewById(R.id.volunteerDescriptionTextView);

        myTitle.setText(rTitle[position]);
        myDescription.setText(rDescription[position]);

        return row;
    }
}
