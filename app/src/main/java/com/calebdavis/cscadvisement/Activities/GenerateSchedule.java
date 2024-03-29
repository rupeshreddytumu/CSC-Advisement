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
import android.widget.ListView;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;
import com.calebdavis.cscadvisement.Services.CoursesDbAdapter;
import com.calebdavis.cscadvisement.Services.MyCustomBaseAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Caleb Davis on 5/4/15.
 */
public class GenerateSchedule extends Activity {
    private CoursesDbAdapter dbHelper;
    String user_id;
    Button buttonSend;
    String to, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_schedule);
        dbHelper = new CoursesDbAdapter(this);
        dbHelper.open();

        buttonSend = (Button) findViewById(R.id.findSelected);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences prefs = GenerateSchedule.this.getSharedPreferences("ContactInfo", Context.MODE_PRIVATE);
                String user_email = prefs.getString("email", null);
                String advisor_email = prefs.getString("advisor_email", null);
                String name = prefs.getString("name", null);
                String advisor_name = prefs.getString("advisor", null);
                subject = name + "'s schedule for next semester.";
                to = advisor_email;


                ArrayList<StudentCourse> schedule = GetSearchResults();
                String schedule_string = "";

                for (StudentCourse s : schedule)
                {
                    schedule_string += s.getCourseId() + "\n";
                }

                message = "Dear " + advisor_name + ", \n" +
                        "I would like to take these courses next semester: \n" + schedule_string;



                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // we need setType to prompts only email clients.
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }});



        ArrayList<StudentCourse> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.coursesListView);
        lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));


        //dbHelper.close();


    }

    private ArrayList<StudentCourse> GetSearchResults(){
        ArrayList<StudentCourse> results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> new_results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> final_results = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> schedule = new ArrayList<StudentCourse>();
        ArrayList<StudentCourse> new_schedule = new ArrayList<StudentCourse>();


        ParseUser currentUser = ParseUser.getCurrentUser();
        user_id = currentUser.getUsername().toString();

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        if (month < 6){
            // spring semester - > generate schedule for fall semester
            results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(user_id, "false", "Fall");
            new_results = (ArrayList<StudentCourse>) dbHelper.getAllBothSemesterCoursesNotTakenByStudent(user_id, "false", "Both");
        }

        if (month > 7){
            // fall semester - > generate schedule for spring semester
            results = (ArrayList<StudentCourse>) dbHelper.getAllFallCoursesNotTakenByStudent(user_id, "false", "Spring");
            new_results = (ArrayList<StudentCourse>) dbHelper.getAllBothSemesterCoursesNotTakenByStudent(user_id, "false", "Both");
        }

        final_results.addAll(new_results);
        final_results.addAll(results);

        Collections.sort(final_results);

        schedule.addAll(final_results);
        new_schedule = checkRequirements(schedule);



        return new_schedule;
    }

    private boolean containsCourse(ArrayList<StudentCourse> list, int code) {
        for (StudentCourse object : list) {
            if (object.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    private void removeCourseFromSchedule(ArrayList<StudentCourse> schedule, int code){

        Iterator<StudentCourse> it = schedule.iterator();
        while (it.hasNext()) {
            StudentCourse course = it.next();
            if (course.getCode() == code) {
                it.remove();
            }
        }
    }

    private ArrayList<StudentCourse> checkRequirements(ArrayList<StudentCourse> schedule){
        ArrayList<StudentCourse> results = (ArrayList<StudentCourse>) dbHelper.getAllCoursesNotTakenByStudent(user_id, "false");

        Iterator<StudentCourse> it = schedule.iterator();
        while (it.hasNext()) {
            StudentCourse course = it.next();
            switch(course.getCode()){
                case 101:{}
                break;
                case 102:{
                    if (containsCourse(results, 101)){
                        it.remove();
                    }
                }
                break;
                case 203:{

                    if ((containsCourse(results, 101)) || (containsCourse(results, 102))){
                        it.remove();
                    }

                }
                break;
                case 204:{
                    if ((containsCourse(results, 101)) || (containsCourse(results, 102))){
                        it.remove();
                    }
                }
                break;
                case 300:{}
                break;
                case 306:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)){
                        it.remove();
                    }
                }
                break;
                case 307:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                }
                break;
                case 320:{}
                break;
                case 408:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                }
                break;
                case 411:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                }
                break;
                case 412:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                }
                break;
                case 413:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                }
                break;
                case 414:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                }
                break;
                case 415:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                    else if (containsCourse(schedule, 408)) {
                        it.remove();
                    }
                }
                break;
                case 424:{
                    if (containsCourse(schedule, 101)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 102)){
                        it.remove();
                    }
                    else if (containsCourse(schedule, 307)) {
                        it.remove();
                    }
                    else if (containsCourse(schedule, 414)) {
                        it.remove();
                    }
                }
                break;

            }


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
