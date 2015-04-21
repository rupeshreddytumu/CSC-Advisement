package com.calebdavis.cscadvisement.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.calebdavis.cscadvisement.DatabaseHelpers.LoginDatabaseAdapter;
import com.calebdavis.cscadvisement.R;
import com.calebdavis.cscadvisement.Tables.Account;
import com.calebdavis.cscadvisement.Tables.Student;
import com.calebdavis.cscadvisement.UserSessionManager;


public class SignupActivity extends Activity
{
    EditText editTextUserName,editTextPassword,editTextConfirmPassword, editTextEmail, editTextAdvisor, editTextStudentID;
    Button btnCreateAccount;

    Account account;
    Student student;

    UserSessionManager session;

    LoginDatabaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        session = new UserSessionManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDatabaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
        editTextEmail=(EditText)findViewById(R.id.editTextStudentEmail);
        editTextStudentID=(EditText)findViewById(R.id.editTextStudentID);


        editTextAdvisor=(EditText)findViewById(R.id.editTextStudentAdvisor);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                String email=editTextEmail.getText().toString();
                String studentID=editTextStudentID.getText().toString();
                String advisor=editTextAdvisor.getText().toString();

                session.createUserLoginSession(userName, password, studentID, email, advisor);






                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, password);
                    // create student table with email, id, avisor, etc.

                    Toast.makeText(getApplicationContext(), "Account Successfully Created", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
