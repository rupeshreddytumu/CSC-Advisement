package com.calebdavis.cscadvisement.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;

import java.util.ArrayList;

/**
 * Created by Caleb Davis on 5/4/15.
 */
public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<StudentCourse> searchArrayList;
    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<StudentCourse> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.courses_not_taken, null);
            holder = new ViewHolder();
            holder.cid = (TextView) convertView.findViewById(R.id.cid);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cid.setText(searchArrayList.get(position).getCourseId());



        return convertView;
    }

    static class ViewHolder {
        TextView cid;


    }
}