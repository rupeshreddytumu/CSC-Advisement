package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
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
public class RegisterActivity extends Activity{

    EditText editTextUserName, editTextStudentAdvisor, editTextStudentEmail, editTextAdvisorEmail;
    Button buttonCreateAccount;

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

        // Set OnClick Listener on SignUp button
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // get studentID from login



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

}