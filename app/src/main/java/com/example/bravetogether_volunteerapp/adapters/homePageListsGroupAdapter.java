package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bravetogether_volunteerapp.R;
import com.example.bravetogether_volunteerapp.VolunteerEventItemList;
import com.example.bravetogether_volunteerapp.homeGroupItem;

import java.util.List;

public class homePageListsGroupAdapter extends RecyclerView.Adapter<homePageListsGroupAdapter.MyViewHolder> {

    private Context context;
    private List<homeGroupItem> dataList;

    public homePageListsGroupAdapter(Context context, List<homeGroupItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = (View) LayoutInflater.from(context).inflate(R.layout.layout_group_home,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_title.setText(dataList.get(position).getHeadTitle());
        List<VolunteerEventItemList> item = dataList.get(position).getListItem();
        homeGroupItem event = dataList.get(position);
        VolunteerItemHomeAdapter adapter = new VolunteerItemHomeAdapter(context, item);
        holder.recycler_view_item_list.setHasFixedSize(true);
        holder.recycler_view_item_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false ));
        holder.recycler_view_item_list.setAdapter(adapter);
        holder.recycler_view_item_list.setNestedScrollingEnabled(false);

    }

    @Override
    public int getItemCount() {
        return (dataList != null ? dataList.size():0 );
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView item_title;
        RecyclerView recycler_view_item_list;
        Button btn_more;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = (TextView)itemView.findViewById(R.id.TextView1);
            btn_more = (Button)itemView.findViewById(R.id.btnMore);
            recycler_view_item_list = (RecyclerView)itemView.findViewById(R.id.Recycler_view_list);
        }
    }
}
