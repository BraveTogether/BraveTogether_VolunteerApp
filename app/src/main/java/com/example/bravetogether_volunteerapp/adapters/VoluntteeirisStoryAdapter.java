package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;
import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VoluntteeriesStory;
import java.util.List;


public class VoluntteeirisStoryAdapter extends RecyclerView.Adapter<VoluntteeirisStoryAdapter.MyViewHolder> {
    Context context;
    List<VoluntteeriesStory> eventList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        TextView title;
        TextView location;
        TextView description;


        public MyViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView)v.findViewById(R.id.textViewDescription3);
            location = (TextView)v.findViewById(R.id.textViewLocation);
            description = (TextView)v.findViewById(R.id.textViewDescription);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VoluntteeirisStoryAdapter(Context context, List<VoluntteeriesStory> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VoluntteeirisStoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item2,parent,false);
        return (new MyViewHolder(itemView));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        VoluntteeriesStory event = eventList.get(position);

       holder.title.setText(event.getTitle());
       holder.location.setText(event.getLocation());
       holder.description.setText(event.getEventDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       return (eventList != null ? eventList.size():0 );
    }
}





