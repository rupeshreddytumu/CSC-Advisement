package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.calebdavis.cscadvisement.Services.CoursesDbAdapter;
import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Caleb Davis on 4/27/15.
 */
public class DegreeProgressActivity extends Activity {

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
        //dbHelper.deleteAllStudentCourses();
        //dbHelper.deleteAllCompletedCourses();
        //Add some data


        ParseUser currentUser = ParseUser.getCurrentUser();
        String student_id = currentUser.getUsername().toString();
        this.user_id = student_id;
        this.user_row_id = dbHelper.createStudent(student_id);
        try{
            List<StudentCourse> test_list = dbHelper.testCoursesForSpecificStudent(user_id);
            if ( test_list.size() == 0){
                dbHelper.insertStudentCourses(user_id);

            }

        }catch(Exception e){

        }



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
        if (id == R.id.profile) {
            return true;
        }

        if (id == R.id.logout){
            ParseUser.logOut();
            finish();
        }

        if (id == R.id.not_courses){
            // show courses taken
            Intent intent = new Intent(this, CoursesNotTakenListViewActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.courses_taken){
            // show courses taken
            Intent intent = new Intent(this, CoursesTakenListViewActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.fall_courses_not_taken){
            // show courses taken
            Intent intent = new Intent(this, FallCoursesNotTakenListViewActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.spring_courses_not_taken){
            // show courses taken
            Intent intent = new Intent(this, SpringCoursesNotTakenListViewActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayListView() {
        List<StudentCourse> test = dbHelper.testCoursesForSpecificStudent(user_id);
        Cursor cursor = dbHelper.fetchAllStudentCourses(user_id);
        List<StudentCourse> coursesNotTaken = dbHelper.getAllCoursesNotTakenByStudent(user_id, "false");

        String student_id_test = cursor.getString(cursor.getColumnIndexOrThrow("student_id"));
        String course_id_test = cursor.getString(cursor.getColumnIndexOrThrow("course_id"));
        final String status_test = cursor.getString(cursor.getColumnIndexOrThrow("status"));







        // The desired columns to be bound
        String[] columns = new String[] {
                CoursesDbAdapter.KEY_STUDENT_ID,
                CoursesDbAdapter.KEY_COURSE_ID,
                CoursesDbAdapter.KEY_STATUS,
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.sid,
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
                    String status =
                            cursor.getString(cursor.getColumnIndexOrThrow("status"));
                    long row_id = cursor.getLong(cursor.getColumnIndex("_id"));

                    String newStatus = status;





                    if (newStatus.equals("false")){

                        newStatus = "true";
                        dbHelper.updateItem(row_id, newStatus);
                        //statusTextView.setText(newStatus);


                    }
                    else if (newStatus.equals("true")){

                        newStatus = "false";
                        dbHelper.updateItem(row_id, newStatus)  ;
                        //dbHelper.updateItem(course_id, newStatus, user_id);
                        //statusTextView.setText(newStatus);

                    }



                }


        });

    }


}





