package com.example.sanchit.groupsofwhatsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter{


    private int icons[];
    private String names[];
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context,int icons[], String names[]){
        this.context = context;
        this.icons = icons;
        this.names = names;

    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     View gridView = convertView;
        if(convertView==null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_gridview,null);

        }

        ImageView icon = (ImageView)gridView.findViewById(R.id.icons);
        TextView textView = (TextView)gridView.findViewById(R.id.name);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),icons[position]);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        dr.setCornerRadius(25);

        icon.setImageDrawable(dr);
        textView.setText(names[position]);

        return gridView;
    }
}
