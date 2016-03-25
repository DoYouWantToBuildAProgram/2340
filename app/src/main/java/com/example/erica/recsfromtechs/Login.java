package com.example.erica.recsfromtechs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    SharedPreferences passwords;
    SharedPreferences.Editor editPasswords;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        passwords  = getSharedPreferences("MyPref", MODE_PRIVATE);
        editPasswords = passwords.edit();
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
            Intent intent = new Intent(this,dashboard.class);
            //this sends the user name to the dashboard
            //called "extra", it is essentially a map
            //"user" is our key
            /* Not sure what any of this does, so I commented it out for now
            Intent oldIntent = getIntent();
            String userName = oldIntent.getStringExtra("userName");
            String userEmail = oldIntent.getStringExtra("userEmail");
            String userMajor = oldIntent.getStringExtra("userMajor");
            Bundle bundle = new Bundle();
            bundle.putString("userName",userName);
            bundle.putString("userEmail", userEmail);
            bundle.putString("userMajor", userMajor);
            bundle.putString("user", usernameText.getText().toString());
            intent.putExtras(bundle);
            Log.v("welcome", "input" + usernameText.getText());
            */
            startActivity(intent);

        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence text = "Username or Password Incorrect";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }

    }

}
