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



public class Login extends AppCompatActivity {

    SharedPreferences passwords;
    SharedPreferences.Editor editPasswords;
    SharedPreferences currentUser;
    SharedPreferences.Editor editCurrentUser;


    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        passwords = getSharedPreferences("MyPref", MODE_PRIVATE);
        editPasswords = passwords.edit();
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        dbHandler = new MyDBHandler(this, null, null, 1);

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
     * When the user types in a correct username and password, it will take them to the dashboard
     * If not, then it will allow the user to know that one of the two is incorrect
     * @param view The current layout with all the Android widgets
     */
    public void loginSuccessful(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        if (dbHandler.authenticateUser(usernameText.getText().toString(), passwordText.getText().toString())) {
            editCurrentUser.putString("username", usernameText.getText().toString());
            editCurrentUser.commit();
            // Configure sign-in to request the user's ID, email address, and basic profile. ID and


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

    public void resetPassword(View view){

        String newPass = "";
        for(int i =0; i<12; i++){
            newPass += (char) Math.random()%93 + 33;

        }
        final String newPassFinal = newPass;


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Email");


        final EditText input = new EditText(this);

        input.setInputType(
                InputType.TYPE_CLASS_TEXT
        );
        builder.setView(input);


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


    }

    protected void sendEmail(String email, String password) {
        //

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
