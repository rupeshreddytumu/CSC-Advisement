package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calebdavis.cscadvisement.R;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    Button buttonCreateAccount;
    EditText editTextUserName, editTextStudentAdvisor, editTextStudentEmail, editTextAdvisorEmail;
    public static final String PREFS_NAME = "ContactInfo";
    String name, email, advisor, advisor_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);

        // student full name
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        // advisor full name
        editTextStudentAdvisor = (EditText) findViewById(R.id.editTextStudentAdvisor);
        // student usm email
        editTextStudentEmail = (EditText) findViewById(R.id.editTextStudentEmail);
        // advisor usm email
        editTextAdvisorEmail = (EditText) findViewById(R.id.editTextAdvisorEmail);
        // submit button
        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Logout current user
                name = editTextUserName.getText().toString();
                advisor =  editTextStudentAdvisor.getText().toString();
                email =  editTextStudentEmail.getText().toString();
                advisor_email = editTextAdvisorEmail.getText().toString();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                // Writing data to SharedPreferences
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", name);
                editor.putString("advisor", advisor);
                editor.putString("student_email", email);
                editor.putString("advisor_email", advisor_email);
                editor.commit();

                Intent intent = new Intent(MainActivity.this,
                        ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });

        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class

            Intent intent = new Intent(this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                try {
                    if (checkIfRegistrationNeeded()){
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                    }
                } catch (IOException e) {
                    try {
                        writeFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }

            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }




    }

    public boolean checkIfRegistrationNeeded() throws IOException {


        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();
        FileInputStream fIn = openFileInput(struser +"registration.txt");
        InputStreamReader isr = new InputStreamReader(fIn);

        /* Prepare a char-Array that will
         * hold the chars we read back in. */
        //List<Course> selectedCourses = Course.find(Course.class, "selected = ?", "true");
        String test = new String("Hello Android");
         char[] inputBuffer = new char[test.length()];

        // Fill the Buffer with data from the file
        isr.read(inputBuffer);

        // Transform the chars to a String
        String readString = new String(inputBuffer);


        if (readString.isEmpty()){
            Toast.makeText(
                    getApplicationContext(),
                    "First time launching app",
                    Toast.LENGTH_LONG).show();
            writeFile();
            return true;
        }

        Toast.makeText(
                getApplicationContext(),
                "INPUT: " + readString,
                Toast.LENGTH_LONG).show();

        return false;
    }

    public void writeFile() throws IOException {
        Toast.makeText(
                getApplicationContext(),
                "Creating txt file!",
                Toast.LENGTH_LONG).show();

        //List<Course> coursesTaken = Course.find(Course.class, "selected = ?", "true");
        ParseUser currentUser = ParseUser.getCurrentUser();
        String studentId = currentUser.getUsername().toString();
        //if (!coursesTaken.isEmpty()){


        final String test = new String("Hello Android");

        // Convert currentUser into String



        FileOutputStream fOut = openFileOutput(studentId + "registration.txt",
                MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);

        // Write the string to the file
        osw.write(test);
        osw.flush();
        osw.close();

    }//}


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


