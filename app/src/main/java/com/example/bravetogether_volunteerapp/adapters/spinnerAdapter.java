package com.example.bravetogether_volunteerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bravetogether_volunteerapp.R;

import java.util.ArrayList;

public class spinnerAdapter extends BaseAdapter
{
    ArrayList<Integer> drawables;
    ArrayList<String> strings;
    Context context;

    public spinnerAdapter(Context context)
    {
        this.context=context;
        drawables  = new ArrayList<>();
        strings = new ArrayList<>();
        drawables.add(R.drawable.sun_dropdown);
        drawables.add(R.drawable.noon_dropdown);
        drawables.add(R.drawable.moon_dropdown);
        String retrieve []= context.getResources().getStringArray(R.array.time_windows);
        for(String re:retrieve)
        {
            strings.add(re);
        }

    }
    @Override
    public int getCount()
    {
        return drawables.size();
    }
    @Override
    public Object getItem(int arg0)
    {
        return drawables.get(arg0);
    }
    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.time_window_drop_down_item, null);
        TextView txv=(TextView)view.findViewById(android.R.id.text1);
//        txv.setBackgroundColor(drawables.get(pos));
        txv.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawables.get(pos), 0);
        txv.setText(strings.get(pos));
//        txv.setText("Text  "+pos);
        return view;
    }

}