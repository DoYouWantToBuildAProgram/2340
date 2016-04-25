package com.example.erica.recsfromtechs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * This is the activity for the login screen
 * you need to enter a password and username
 * that will be checked in the database
 * to grant you further access to the app
 */
public class Login extends AppCompatActivity {


    private SharedPreferences.Editor editCurrentUser;

    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences currentUser;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        editCurrentUser.apply();
        dbHandler = new MyDBHandler(this);
    }

    /**
     * Allows the User to cancel their login and go back to the welcome page
     * @param view The current layout with all the Android widgets
     */
    public void cancel(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * When the user types in a correct username and password, it will take them to the Dashboard
     * If not, then it will allow the user to know that one of the two is incorrect
     * @param view The current layout with all the Android widgets
     */
    public void loginSuccessful(View view) throws Exception {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        if (dbHandler.authenticateUser(usernameText.getText().toString(), passwordText.getText().toString())) {
            editCurrentUser.putString("username", usernameText.getText().toString());
            editCurrentUser.commit();
            Intent intent = new Intent(this,dashboard.class);
            startActivity(intent);

        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence text = "Username/Password Incorrect or Account is Locked";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }

    }



    //this is the on click for the forgot password button
    //it makes a new password and creates an AlertDialog
    //where people enter their email, then either click ok or cancel

    public void resetPassword(View view){


        //this creates the new password, its 12 randomly generated characters
        String newPass = "";
        for(int i =0; i<12; i++){
            newPass += (char) (Math.random()*93 + 33);

        }


        //the AlertDialog can only take final variables, so we need to make our password final
        final String newPassFinal = newPass;

        //create our Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Email");
        builder.setCancelable(true);


        //create an edittext for our alert, this is where people enter their email
        //later on we are going to use this email to find the User associated with it
        //so that we can set their password to the new password we created
        //if its easier for you on the database end, instead we could have them enter their username
        //and from that we could set their password and find their email
        final EditText input = new EditText(this);

        input.setInputType(
                InputType.TYPE_CLASS_TEXT
        );
        builder.setView(input);

        //our alert has two options
        //"positive button" gets the email entered and calls sendEmail
        //"negative button" just cancels the whole thing
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString();

                sendEmail(email, newPassFinal);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder.create();
        alert11.show();



    }

    //this is where we send the email
    // inside this is where you should get the users information from the database
    //and set their password to the new password
    protected void sendEmail(String email, String password) {
        //here we need to find the user associated with this email
        //and change their password

        String[] TO = {email};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Forgotten Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your new password is "+password+". Please log in using this password and go to edit profile to set your new password");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Login.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
