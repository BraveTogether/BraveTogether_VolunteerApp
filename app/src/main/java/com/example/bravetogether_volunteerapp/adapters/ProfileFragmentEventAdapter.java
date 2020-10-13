package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolunteerEvent;

import java.util.List;

public class ProfileFragmentEventAdapter extends RecyclerView.Adapter<ProfileFragmentEventAdapter.EventHolder> {
    Context context;
    List<VolunteerEvent> eventList;
    public ProfileFragmentEventAdapter(Context context, List<VolunteerEvent> list){
        this.context = context;
        this.eventList = list;
    }


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_near_volunteer_item,parent,false);
        return (new EventHolder((itemView)));
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, final int position) {
        VolunteerEvent event = eventList.get(position);
        // TODO:: need to make sure that list holds start time and address
        String details = event.getLocation()+"|"+event.getDate()+"|"+event.getDuration();
        //set textviews:
        holder.credits.setText(event.getNumberCredits());
        holder.details.setText(details);
        holder.headline.setText(event.getEventName());
        //  TODO:: need to understand how to add images

        //  TODO:: finish share button action
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareUrl = "http://www.WE_NEED_EVENT_URL.org/"+position; // TODO:: check what are we sharing.
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareUrl);
                context.startActivity(Intent.createChooser(shareIntent,"UNKNOWN FOR NOW"));
            }
        });

        //  TODO:: handle onclick for view.
    }

    @Override
    public int getItemCount() {
        return (eventList != null)?eventList.size():0;
    }

    public class EventHolder extends  RecyclerView.ViewHolder {
        TextView details,credits,headline;
        ImageView Vpic,Cpic,share;
        RelativeLayout parent;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);
            details = (TextView) itemView.findViewById(R.id.mDetails);
            credits = (TextView) itemView.findViewById(R.id.mCoins);
            headline = (TextView) itemView.findViewById(R.id.mHeadLine);
            Vpic = (ImageView) itemView.findViewById(R.id.mVolnteerPic);
            Cpic = (ImageView) itemView.findViewById(R.id.mCoinPic);
            share = (ImageView) itemView.findViewById(R.id.mShare);

        }
    }

}
