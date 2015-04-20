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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //Course.deleteAll(Course.class);
        //Course course = new Course("CSC 101","Intro to C++",false);
        //course.save();

        try {
            List<Course> courseList = Course.listAll(Course.class);
            if (courseList.size() == 0){

            Course.deleteAll(Course.class);
            Course course = new Course(1L, "CSC 101", "Intro to C++", false);
            course.save();
            List<Course> selectedCourses = Course.find(Course.class, "code = ?", "CSC 101");
            Course test = selectedCourses.get(0);
            long testLong = test.ID;
            course.setID(testLong);}
            else {
                List<Course> selectedCourses = Course.find(Course.class, "code = ?", "CSC 101");
                Course test = selectedCourses.get(0);
                long testLong = test.ID;
                Course look = Course.findById(Course.class, testLong);
                look.selected = look.isSelected();
                look.save();
                Log.d("look: ", String.valueOf(look));




            }
        } catch (Exception e) {

            Log.d("sugar", "failed to add a course");
            Log.d("sugar", e.toString());
        }



        /*Course course1 = new Course("2","CSC 102","Advanced C++",false);
        course1.save();


        Course course3 = new Course("3","CSC 307","Data Structures",false);
        course3.save();


        Course course4 = new Course("4","CSC 317","Object Oriented",false);
        course4.save();


        Course course5 = new Course("5","CSC 413","Intro to Algorithms",false);
        course5.save();

        Course course6 = new Course("6","CSC 414","Software Engineering I",false);
        course6.save();

        Course course7 = new Course("7","CSC 424","Software Engineering II",false);
        course7.save();*/

        List<Course> courseList = Course.listAll(Course.class);


        //Course.find(Course.class, "course_id = 1");


        //Toast.makeText(getApplicationContext(),"QUERY: " +  Course.find(Course.class, "code = CSC 101"), Toast.LENGTH_LONG).show();
        //Course.find(Course.class, "code = CSC 102");
        //Toast.makeText(getApplicationContext(),"book: " +  book, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"book1: " +  book1, Toast.LENGTH_LONG).show();



        // create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.courses_taken, courseList);
        ListView coursesListView = (ListView) findViewById(R.id.coursesListView);
        // Assign adapter to ListView
        coursesListView.setAdapter(dataAdapter);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // when clicked, show a toast with the TextView text
                Course course = (Course) parent.getItemAtPosition(position);
                // come back to this toast
                Toast.makeText(getApplicationContext(),"Clicked course: " + course.getName(), Toast.LENGTH_LONG).show();
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


                        List<Course> selectedCourses = Course.find(Course.class, "code = ?", "CSC 101");
                        //List<Course> selectedCourses = Course.find(Course.class, "code = ?", course.getCode());
                        Course test = selectedCourses.get(0);
                        Long idk = test.getId();

                        Log.d("test ID: ", String.valueOf(test));


                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        Course editCourse = Course.findById(Course.class, test.getId());
                        //course.setSelected(cb.isChecked());
                        if (editCourse.selected)
                        {
                            editCourse.setSelected(false);
                            editCourse.save();
                        }
                        else
                        {
                            editCourse.setSelected(true);
                            editCourse.save();
                        }



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

