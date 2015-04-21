package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calebdavis.cscadvisement.DatabaseHelpers.LoginDatabaseAdapter;
import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.UserSessionManager;

public class LoginActivity extends Activity
{
    Button btnSignIn,btnSignUp;
    LoginDatabaseAdapter loginDataBaseAdapter;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDatabaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

        if(session.checkLogin())
            finish();



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  and Start The Activity
               signIn(v);
            }
        });
    }
    // Methods to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");

        // get the Refferences of views
        final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);

        final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {
                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent sessionLogin=new Intent(getApplicationContext(),SessionLoginActivity.class);
                    startActivity(sessionLogin);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}
