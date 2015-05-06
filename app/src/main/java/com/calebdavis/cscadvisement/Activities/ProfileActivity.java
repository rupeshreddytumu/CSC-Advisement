package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.calebdavis.cscadvisement.R;
import com.parse.ParseUser;

/**
 * Created by Caleb Davis on 5/5/15.
 */

public class ProfileActivity extends Activity {
    // Declare Variable
    Button editAccount, completeCourses, incompleteCourses, fallCourses, springCourses, update_courses_taken;
    TextView name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.profile_activity);
        SharedPreferences prefs = ProfileActivity.this.getSharedPreferences("ContactInfo", Context.MODE_PRIVATE);
        String user_name = prefs.getString("name", null);



        // Locate Button in welcome.xml
        editAccount = (Button) findViewById(R.id.edit_account);
        completeCourses = (Button) findViewById(R.id.completed_courses);
        incompleteCourses = (Button) findViewById(R.id.courses_not_taken);
        fallCourses = (Button) findViewById(R.id.fall_courses_not_taken);
        springCourses = (Button) findViewById(R.id.spring_courses_not_taken);
        update_courses_taken = (Button) findViewById(R.id.update_courses_taken);

        name = (TextView) findViewById(R.id.student_name);
        name.setText(user_name);

        // Edit Account Button Click Listener
        editAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();


            }
        });

        update_courses_taken.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ProfileActivity.this, UpdateDegreeProgressActivity.class);
                startActivity(intent);
                finish();


            }
        });

        // Complete Courses Button Click Listener
        completeCourses.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Show Completed Courses Activity
                Intent intent = new Intent(ProfileActivity.this, CoursesTakenListViewActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Incomplete Courses Button Click Listener
        incompleteCourses.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Show incompleted Courses Activity
                Intent intent = new Intent(ProfileActivity.this, CoursesNotTakenListViewActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Fall Courses Not Taken Button Click Listener
        fallCourses.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Show fall courses not taken Courses Activity
                Intent intent = new Intent(ProfileActivity.this, FallCoursesNotTakenListViewActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Spring Courses Not Taken Button Click Listener
        springCourses.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Show spring courses not taken Courses Activity
                Intent intent = new Intent(ProfileActivity.this, SpringCoursesNotTakenListViewActivity.class);
                startActivity(intent);
                finish();

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
