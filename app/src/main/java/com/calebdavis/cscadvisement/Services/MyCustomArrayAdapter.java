package com.calebdavis.cscadvisement.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;

import java.util.ArrayList;

/**
 * Created by user on 5/4/15.
 */
public class MyCustomArrayAdapter extends ArrayAdapter<StudentCourse> {
    private CoursesDbAdapter dbHelper;
    private static ArrayList<StudentCourse> studentCourseArrayList;
    private LayoutInflater mInflater;

    public MyCustomArrayAdapter(Context context, int resource, ArrayList<StudentCourse> courseList) {
        super(context, resource, courseList);
        studentCourseArrayList = courseList;
        mInflater = LayoutInflater.from(context);
        dbHelper = new CoursesDbAdapter(context);
        dbHelper.open();

    }

    public int getCount() {
        return studentCourseArrayList.size();
    }

    public StudentCourse getItem(int position) {
        return studentCourseArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.select_courses, null);
            holder = new ViewHolder();

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    StudentCourse course = (StudentCourse) cb.getTag();

                    if (cb.isChecked()){
                        course.setTaken("true");
                        dbHelper.updateItem(course.getId(), "true");
                    }
                    if (!cb.isChecked()){
                        course.setTaken("false");
                        dbHelper.updateItem(course.getId(), "false");
                    }

                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        StudentCourse course = studentCourseArrayList.get(position);
        holder.checkBox.setText(" (" +  course.getCode() + ")");
        holder.checkBox.setText(course.getCourseId());
        holder.checkBox.setChecked(Boolean.parseBoolean(course.getTaken()));
        holder.checkBox.setTag(course);

        return convertView;
    }

    private class ViewHolder {

        CheckBox checkBox;
    }
}

