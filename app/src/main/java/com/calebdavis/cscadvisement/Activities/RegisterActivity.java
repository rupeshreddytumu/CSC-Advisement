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

import com.calebdavis.cscadvisement.R;
import com.parse.ParseUser;

/**
 * Created by Caleb Davis on 4/22/15.
 */
public class RegisterActivity extends Activity {

    Button buttonCreateAccount;
    EditText editTextUserName, editTextStudentAdvisor, editTextStudentEmail, editTextAdvisorEmail;
    public static final String PREFS_NAME = "ContactInfo";
    String name, email, advisor, advisor_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            return true;
        }

        if (id == R.id.get_advised){
            // show courses taken
            Intent intent = new Intent(this, GenerateSchedule.class);
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