package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;
import com.calebdavis.cscadvisement.Services.CoursesDbAdapter;
import com.calebdavis.cscadvisement.Services.MyCustomBaseAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Caleb Davis on 5/4/15.
 */
public class CoursesNotTakenListViewActivity extends Activity {

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

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                StudentCourse fullObject = (StudentCourse)o;
                Toast.makeText(CoursesNotTakenListViewActivity.this, "You have chosen: " + " " + fullObject.getCourseId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<StudentCourse> GetSearchResults(){
        ArrayList<StudentCourse> results = new ArrayList<StudentCourse>();

        ParseUser currentUser = ParseUser.getCurrentUser();
        String student_id = currentUser.getUsername().toString();

        results = (ArrayList<StudentCourse>) dbHelper.getAllCoursesNotTakenByStudent(student_id, "false");
        //results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "fall");
        //results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(student_id, "false", "spring");

        Collections.sort(results);

        return results;
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



        if (id == R.id.get_advised){
            // show courses taken
            Intent intent = new Intent(this, GenerateSchedule.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}