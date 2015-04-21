package com.calebdavis.cscadvisement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.calebdavis.cscadvisement.Activities.SessionLoginActivity;

import java.util.HashMap;

/**
 * Created by Caleb Davis on 4/20/15.
 */
public class UserSessionManager {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    // SharedPreff file name
    private static final String PREFER_NAME = "CSCAdvisement";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "admin";

    // User password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "admin";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // student ID
    public static final String STUDENT_ID = "wID";

    // student adivsor
    public static final String STUDENT_ADVISOR = "advisor";



    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String password, String wID, String email, String advisor){

        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);

        editor.putString(STUDENT_ID, wID);

        editor.putString(STUDENT_ADVISOR, advisor);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SessionLoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user id
        user.put(STUDENT_ID, pref.getString(STUDENT_ID, null));

        // user advisor
        user.put(STUDENT_ADVISOR, pref.getString(STUDENT_ADVISOR, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, SessionLoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}

