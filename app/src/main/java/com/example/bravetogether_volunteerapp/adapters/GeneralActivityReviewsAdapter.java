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

import java.util.List;

public class GeneralActivityReviewsAdapter extends RecyclerView.Adapter<GeneralActivityReviewsAdapter.ViewHolder> {
    private Context context;
    private List<String> reviews;

    public GeneralActivityReviewsAdapter(Context context , List<String> reviews) {
        this.context = context;
        this.reviews = reviews;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review_title.setText(reviews.get(position));
        //TODO: fill actual review from server
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_picture;
        TextView user_name, review_title, review, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_picture = itemView.findViewById(R.id.reviewItemUserPictureImageView);
            user_name = itemView.findViewById(R.id.reviewItemUserNameTextView);
            review = itemView.findViewById(R.id.reviewItemReviewTextView);
            review_title = itemView.findViewById(R.id.reviewItemTitleTextView);
            location = itemView.findViewById(R.id.reviewItemLocationTextView);

        }
    }
}
