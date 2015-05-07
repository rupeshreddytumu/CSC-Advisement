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

/**
 * Created by Caleb Davis on 5/4/15.
 */
public class FallCoursesNotTakenListViewActivity extends Activity {

    private CoursesDbAdapter dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
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


        ParseUser currentUser = ParseUser.getCurrentUser();
        String student_id = currentUser.getUsername().toString();

        //results = (ArrayList<StudentCourse>) dbHelper.getAllCoursesNotTakenByStudent(student_id, "false");
        results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "Fall");
        new_results = (ArrayList<StudentCourse>) dbHelper.getAllBothSemesterCoursesNotTakenByStudent(student_id, "false", "Both");
        //results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "spring");

        final_results.addAll(new_results);
        final_results.addAll(results);

        return final_results;

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
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
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


        if (id == R.id.get_advised){
            // show courses taken
            Intent intent = new Intent(this, GenerateSchedule.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
