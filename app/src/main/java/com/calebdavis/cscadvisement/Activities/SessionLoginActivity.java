package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.UserSessionManager;

import java.util.HashMap;



public class SessionLoginActivity extends Activity {

    Button buttonSignIn, buttonSignUp;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.editTextUserNameToLogin);
        txtPassword = (EditText) findViewById(R.id.editTextPasswordToLogin);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        SharedPreferences settings = getSharedPreferences("CSCAdvisement", 0);

        if (settings.getBoolean("my_first_time", true)) {

            Intent signup=new Intent(getApplicationContext(),SignupActivity.class);
            // Add new Flag to start new Activity
            signup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signup);

            // first time task

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }


        // User Login button
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        // User Register button
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        // Set OnClick Listener on SignUp button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  and Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intentSignUP);
            }
        });

        // Login button click event


        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent login=new Intent(getApplicationContext(),MainActivity.class);
                // Add new Flag to start new Activity
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                finish();
            }});


        // Login button click event
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Validate if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    // For testing puspose username, password is checked with static data
                    // username = admin
                    // password = admin

                    // get user data from session
                    HashMap<String, String> user = session.getUserDetails();

                    // get name
                    String name = user.get(UserSessionManager.KEY_NAME);

                    // get password
                    String user_password = user.get(UserSessionManager.KEY_PASSWORD);

                    // get email
                    String email = user.get(UserSessionManager.KEY_EMAIL);

                    // get ID
                    String studentID = user.get(UserSessionManager.STUDENT_ID);

                    // get advisor
                    String advisor = user.get(UserSessionManager.STUDENT_ADVISOR);

                    if(username.equals(name) && password.equals(user_password)){

                        // Creating user login session
                        // Statically storing name="Android Example"
                        // and email="androidexample84@gmail.com"
                        session.createUserLoginSession(name, password, email, studentID, advisor);

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    }else{

                        // username / password doesn't match
                        Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();

                    }
                }else{

                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}