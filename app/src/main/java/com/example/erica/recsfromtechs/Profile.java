package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Niklas on 3/14/2016.
 */



public class Profile extends AppCompatActivity {
    SharedPreferences userInfo;
    SharedPreferences.Editor editUserInfo;
    SharedPreferences currentUser;
    SharedPreferences.Editor editCurrentUser;
    MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();

        userInfo = getSharedPreferences("AnotherPref", MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);
        Intent oldIntent = getIntent();
        String user = oldIntent.getStringExtra("user");

        // make method if user is admin make button clickable'
        Button adminButton = (Button) findViewById(R.id.goToAdminPage);

        if (dbHandler.getIsAdmin(currentUser.getString("username",null)) == 1) { //true if user is admin THIS NEEDS TO BE FIXED
            adminButton.setEnabled(true);
        } else {
            adminButton.setEnabled(false);
        }

        //placeholder
        //get the user from the database here

        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(dbHandler.getName(currentUser.getString("username",null)));
        TextView usernameText = (TextView) findViewById(R.id.username);
        usernameText.setText(currentUser.getString("username",null));
        TextView passwordText = (TextView) findViewById(R.id.password);
        passwordText.setText(dbHandler.getPassword(currentUser.getString("username",null)));
        TextView majorText = (TextView) findViewById(R.id.major);
        majorText.setText(dbHandler.getMajor(currentUser.getString("username",null)));
        TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText(dbHandler.getEmail(currentUser.getString("username",null)));

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