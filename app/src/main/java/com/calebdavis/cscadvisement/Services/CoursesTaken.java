package com.calebdavis.cscadvisement.Services;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.calebdavis.cscadvisement.DatabaseHelpers.CoursesDbAdapter;
import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteProject.Courses;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Caleb Davis on 4/27/15.
 */
public class CoursesTaken extends Activity {

    // Database Helper
    private CoursesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    String user_id;
    long user_row_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        dbHelper = new CoursesDbAdapter(this);
        dbHelper.open();


        //Clean all data
        dbHelper.deleteAllCourses();
        //dbHelper.deleteAllCompletedCourses();
        //Add some data
        dbHelper.insertInitialCourses();

        ParseUser currentUser = ParseUser.getCurrentUser();
        String student_id = currentUser.getUsername().toString();
        this.user_id = student_id;
        this.user_row_id = dbHelper.createStudent(student_id);


        //Generate ListView from SQLite Database
        displayListView();
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
            //Intent intent = new Intent(this, CoursesTakenActivity.class);
            //startActivity(intent);
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllCourses();
        List<Courses> courses =  dbHelper.getAllCoursesByStudent(user_id);
        Log.e("courses completed by student:", String.valueOf(courses.size()));

        // The desired columns to be bound
        String[] columns = new String[] {
                CoursesDbAdapter.KEY_COURSE_ID,
                CoursesDbAdapter.KEY_STATUS,
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.cid,
                R.id.status,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.courses_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.coursesListView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);



                // Get the state's capital from this row in the database.
                String course_id =
                        cursor.getString(cursor.getColumnIndexOrThrow("course_id"));

                long course_row_id = id;
                dbHelper.addCourseToStudentsSchedule(course_row_id, user_row_id);




                //Toast.makeText(getApplicationContext(),
                        //(int) completed_course_id, Toast.LENGTH_SHORT).show();


            }

        });

    }


}





