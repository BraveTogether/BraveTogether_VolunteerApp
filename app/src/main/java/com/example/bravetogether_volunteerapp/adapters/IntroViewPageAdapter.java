package com.example.bravetogether_volunteerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.R;

import java.util.ArrayList;
import java.util.List;

public class IntroViewPageAdapter extends RecyclerView.Adapter<IntroViewPageAdapter.ViewHolder> {

    List<Integer> list = new ArrayList<>();

    public IntroViewPageAdapter(List<Integer> list) {this.list = list;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        ImageView image,firstCircle,secondCircle,thirdCircle,fourthCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.descriptionTextView);
            image = itemView.findViewById(R.id.introImage);
            firstCircle = itemView.findViewById(R.id.first_dot);
            secondCircle = itemView.findViewById(R.id.second_dot);
            thirdCircle = itemView.findViewById(R.id.third_dot);
            fourthCircle = itemView.findViewById(R.id.fourth_dot);
        }
        void bind(Integer i){
            switch (i){
                case 0:
                    firstCircle.setImageResource(R.drawable.view_page2_full_dot);
                    secondCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    thirdCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    fourthCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    break;
                case 1:
                    firstCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    secondCircle.setImageResource(R.drawable.view_page2_full_dot);
                    thirdCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    fourthCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    break;
                case 2:
                    firstCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    secondCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    thirdCircle.setImageResource(R.drawable.view_page2_full_dot);
                    fourthCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    break;
                case 3  :
                    firstCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    secondCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    thirdCircle.setImageResource(R.drawable.view_page2_empty_dot);
                    fourthCircle.setImageResource(R.drawable.view_page2_full_dot);
                    break;
            }
        }
    }
}
