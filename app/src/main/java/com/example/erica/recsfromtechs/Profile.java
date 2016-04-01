package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Niklas on 3/14/2016.
 */



public class Profile extends AppCompatActivity {
    SharedPreferences userInfo;
    SharedPreferences.Editor editUserInfo;
    MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userInfo = getSharedPreferences("AnotherPref", MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);
        Intent oldIntent = getIntent();
        String user = oldIntent.getStringExtra("user");

        // make method if user is admin make button clickable'
        Button adminButton = (Button) findViewById(R.id.goToAdminPagee);

        if (true) { //true if user is admin THIS NEEDS TO BE FIXED
            adminButton.setEnabled(true);
        } else {
            adminButton.setEnabled(false);
        }

        //placeholder
        //get the user from the database here
        User thisUser = new User("sad","happy","other","another","yes",1,1);

        TextView usernameText = (TextView) findViewById(R.id.username);
        System.out.println(usernameText == null);
        usernameText.setText(thisUser.getUsername());
        TextView passwordText = (TextView) findViewById(R.id.password);
        passwordText.setText(thisUser.getPassword());
        TextView majorText = (TextView) findViewById(R.id.major);
        majorText.setText(thisUser.getMajor());
        TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText(thisUser.getEmail());

    }

    public void goToEditProfile(View view) {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }

    public void gotToAdminPage(View view) {
        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);
    }
}