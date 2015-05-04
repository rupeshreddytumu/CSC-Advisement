package com.calebdavis.cscadvisement.Services;

/**
 * Created by Caleb Davis on 4/21/15.
 */

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;



public class LoginSystem extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "uy1Wad4Tt3EwnuxovI3FXX5VhrohKyvBHSZyCyiP", "cOmnWAQ1VVPN3JFPyJ8IlM8MuZMsZjFBCPhx9hkY");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}