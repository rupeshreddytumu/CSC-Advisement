package com.calebdavis.cscadvisement;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    CourseCreater courseCreater;
    List<Course> userCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userCourseList = Course.listAll(Course.class);

        displayListView();
        checkButtonClick();
        /*
        FileReader fr = new FileReader();
        ArrayList<String> s = fr.line_reader(this);

        for(int i = 0; i<s.size();i++){
            Toast.makeText(this,s.get(i),Toast.LENGTH_SHORT).show();
        }

        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, s);

        coursesListView.setAdapter(adapter);*/

    }

    private void displayListView(){
        /*FileReader fr = new FileReader();
        ArrayList<String> courseList = fr.line_reader(this);
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, courseList);*/


        if (this.userCourseList.size()==0){
            courseCreater = new CourseCreater();
            this.userCourseList = courseCreater.createDegreePlan();

        } else {
            this.userCourseList = Course.listAll(Course.class);}

        /*try {



        } catch (Exception e) {

            Log.d("sugar", "failed to add a course");
            Log.d("sugar", e.toString());
        }*/

        //List<Course> courseList = Course.listAll(Course.class);

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

    private class MyCustomAdapter extends  ArrayAdapter<Course>{
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
                        Long idk = test.getId();

                        Log.d("test ID: ", String.valueOf(test));


                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        Course editCourse = Course.findById(Course.class, test.getId());
                        editCourse.setSelected(cb.isChecked());
                        editCourse.save();
                        /*
                        if (editCourse.selected)
                        {
                            editCourse.setSelected(false);
                            //editCourse.save();
                        }
                        else
                        {
                            editCourse.setSelected(true);

                        }*/
                        //editCourse.save();



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
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                List<Course> courseList = dataAdapter.courseList;
                for (int i = 0; i < courseList.size(); i++){
                    Course course = courseList.get(i);
                    if (course.isSelected()){
                        responseText.append("\n" + course.getName());
                    }
                }
                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

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

        return super.onOptionsItemSelected(item);
    }

}

