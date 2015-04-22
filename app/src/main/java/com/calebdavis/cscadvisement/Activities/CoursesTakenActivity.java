package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.calebdavis.cscadvisement.DatabaseHelpers.CourseCreater;
import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.Tables.Course;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb Davis on 4/22/15.
 */
public class CoursesTakenActivity extends Activity{


    MyCustomAdapter dataAdapter = null;
    CourseCreater courseCreater;
    List<Course> userCourseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        this.userCourseList = Course.listAll(Course.class);

        displayListView();
        checkButtonClick();
    }


    private void displayListView(){

        if (this.userCourseList.size()==0){
            courseCreater = new CourseCreater();
            this.userCourseList = courseCreater.createDegreePlan();

        } else {
            this.userCourseList = Course.listAll(Course.class);}


        // create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.courses_taken, this.userCourseList);
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        // Assign adapter to ListView
        coursesListView.setAdapter(dataAdapter);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when clicked, show a toast with the TextView text
                Course course = (Course) parent.getItemAtPosition(position);
                // come back to this toast
                //Toast.makeText(getApplicationContext(),"Clicked course: " + course.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Course> {
        private List<Course> courseList;

        public MyCustomAdapter(Context context, int textViewResourceId, List<Course> courseList){
            super(context, textViewResourceId, courseList);
            this.courseList = new ArrayList<Course>();
            this.courseList.addAll(courseList);
        }

        private class ViewHolder{
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            ViewHolder holder = null;
            Log.v("CovertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.courses_taken, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Course course = (Course) cb.getTag();

                        String code = course.getCode();
                        //List<Course> selectedCourses = Course.find(Course.class, "code = ?", "CSC 101");
                        List<Course> selectedCourses = Course.find(Course.class, "code = ?", code);
                        Course test = selectedCourses.get(0);


                        Log.d("test ID: ", String.valueOf(test));


                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        Course editCourse = Course.findById(Course.class, test.getId());
                        editCourse.setSelected(cb.isChecked());
                        editCourse.save();
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();

            }

            Course course = courseList.get(position);
            holder.code.setText(" (" + course.getCode() + ")");
            holder.name.setText(course.getName());
            holder.name.setChecked(course.isSelected());
            holder.name.setTag(course);

            return convertView;
        }
    }

    private void checkButtonClick(){
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<Course> courseList = dataAdapter.courseList;
                for (int i = 0; i < courseList.size(); i++){
                    Course course = courseList.get(i);
                    course.setSelected(false);
                    course.save();
                }
                Toast.makeText(getApplicationContext(),
                        "Refreshed CheckBoxes", Toast.LENGTH_LONG).show();

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.logout){
            ParseUser.logOut();
            finish();

        }

        if (id == R.id.courses){
            // show courses taken
            Intent intent = new Intent(this, CoursesTakenActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
