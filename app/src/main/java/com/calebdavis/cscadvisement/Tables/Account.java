package com.calebdavis.cscadvisement.Tables;

import com.orm.SugarRecord;

/**
 * Created by Caleb Davis on 4/20/15.
 */
public class Account extends SugarRecord<Account> {
    String userName;
    String password;
    boolean signedIn = false;


    public Account(){}

    public Account(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.signedIn = false;


    }

    public boolean getSignInStatus(){
        return this.signedIn;
    }
    public void setUserSignedInStatus(boolean status){
        this.signedIn = status;
    }
    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

}
