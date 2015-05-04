package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;
import com.calebdavis.cscadvisement.Services.CoursesDbAdapter;
import com.calebdavis.cscadvisement.Services.MyCustomBaseAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by user on 5/4/15.
 */
public class GenerateSchedule extends Activity {
    private CoursesDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        dbHelper = new CoursesDbAdapter(this);
        dbHelper.open();

        ArrayList<StudentCourse> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.coursesListView);
        lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));


    }

    private ArrayList<StudentCourse> GetSearchResults(){
        ArrayList<StudentCourse> results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> new_results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> final_results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> schedule = new ArrayList<StudentCourse>();


        ParseUser currentUser = ParseUser.getCurrentUser();
        String student_id = currentUser.getUsername().toString();

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        if (month < 6){
            // spring semester - > generate schedule for fall semester
            results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "fall");
            new_results = (ArrayList<StudentCourse>) dbHelper.getAllBothSemesterCoursesNotTakenByStudent(student_id, "false", "both");
        }

        if (month > 7){
            // fall semester - > generate schedule for spring semester
            results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "spring");
            new_results = (ArrayList<StudentCourse>) dbHelper.getAllBothSemesterCoursesNotTakenByStudent(student_id, "false", "both");
        }

        final_results.addAll(new_results);
        final_results.addAll(results);

        Collections.sort(final_results);

        for (int i =0; i < 5; i++){
            schedule.add(final_results.get(i));
        }

        return schedule;
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


        if (id == R.id.courses){
            // show courses taken
            Intent intent = new Intent(this, DegreeProgressActivity.class);
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

        if (id == R.id.get_advised){
            // show courses taken
            Intent intent = new Intent(this, GenerateSchedule.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
