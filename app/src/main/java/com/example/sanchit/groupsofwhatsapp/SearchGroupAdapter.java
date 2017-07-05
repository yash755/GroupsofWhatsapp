package com.example.sanchit.groupsofwhatsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;

public class SearchGroupAdapter extends BaseAdapter implements Filterable{

    public Context context;
    public ArrayList<SearchGroup> employeeArrayList;
    public ArrayList<SearchGroup> orig;

    public SearchGroupAdapter(Context context, ArrayList<SearchGroup> employeeArrayList) {
        super();
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }


    public class SearchGroupHolder
    {
        TextView name;
        ImageView imageView;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new Filter.FilterResults();
                final ArrayList<SearchGroup> results = new ArrayList<SearchGroup>();
                if (orig == null)
                    orig = employeeArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final SearchGroup g : orig) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                employeeArrayList = (ArrayList<SearchGroup>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return employeeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchGroupHolder holder;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.custom_searchgroups, parent, false);
            holder=new SearchGroupHolder();
            holder.name=(TextView) convertView.findViewById(R.id.textView3);
            holder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            //holder.age=(TextView) convertView.findViewById(R.id.txtAge);
            convertView.setTag(holder);
        }
        else
        {
            holder=(SearchGroupHolder) convertView.getTag();
        }
        //new DownloadImageTask(holder.imageView).execute(employeeArrayList.get(position).getIcon());

        holder.name.setText(employeeArrayList.get(position).getName());

        //holder.age.setText(String.valueOf(employeeArrayList.get(position).getAge()));
        return convertView;
    }
}