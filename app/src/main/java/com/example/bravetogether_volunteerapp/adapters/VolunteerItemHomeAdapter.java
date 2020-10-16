package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolunteerEventItemList;
import com.example.bravetogether_volunteerapp.interfaces.ItemClickListener;

import java.util.List;

public class VolunteerItemHomeAdapter extends RecyclerView.Adapter<VolunteerItemHomeAdapter.MyViewHolder> {

    Context context;
    int resource;
    List<VolunteerEventItemList> eventList;

    public VolunteerItemHomeAdapter(Context context, List<VolunteerEventItemList> item) {
        this.context = context;
        this.eventList = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return (new MyViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VolunteerEventItemList event = eventList.get(position);

        //holder.eventNameOfOneList.setText(event.getEventName());
        holder.eventDescriptionOfOneList.setText(event.getEventDescription());
        holder.eventLocationOfOneList.setText(event.getLocation());
        holder.eventDateOfOneList.setText(event.getDate());
        holder.eventDurationOfOneList.setText(event.getDuration());
        holder.eventCoinsOfOneList.setText(event.getNumberCredits());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (eventList != null ? eventList.size():0 );
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView eventNameOfOneList;
        TextView eventDescriptionOfOneList;
        TextView eventLocationOfOneList;
        TextView eventDateOfOneList;
        TextView eventDurationOfOneList;
        TextView eventCoinsOfOneList;
        ImageView img_item;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener ItemClickListener) {
            this.itemClickListener = ItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameOfOneList = (TextView)itemView.findViewById(R.id.textViewEvent);
            eventDescriptionOfOneList = (TextView)itemView.findViewById(R.id.textViewDescription);
            eventLocationOfOneList = (TextView)itemView.findViewById(R.id.textViewLocation);
            eventDateOfOneList = (TextView)itemView.findViewById(R.id.textViewDate);
            eventDurationOfOneList = (TextView)itemView.findViewById(R.id.textViewTime);
            eventCoinsOfOneList = (TextView)itemView.findViewById(R.id.textViewCoinsNum);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClickListener(v, getAdapterPosition());
        }
    }
}