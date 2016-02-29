package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        String title = "";
        if(savedInstanceState == null) {
            //the user name has been passed to the dashboard as an "extra"
            //works like a map, "user" is our key
            //Log.v("welcome", "saved instance state null");
            Intent oldIntent = getIntent();
             title = oldIntent.getStringExtra("title");
             title = "" + title;
        } else {
            //Log.v("welcome", "saved instance state not null");
            title = (String) savedInstanceState.getSerializable("title");
        }

        TextView titleTextView =new TextView(this);
        titleTextView =(TextView)findViewById(R.id.title);
        titleTextView.setText(title);


    }

}
