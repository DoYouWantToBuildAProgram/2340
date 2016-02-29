package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        String title = "";
        //String[] movieImage = new String[1];
        ListView list;
        if(savedInstanceState == null) {
            //the user name has been passed to the dashboard as an "extra"
            //works like a map, "user" is our key
            //Log.v("welcome", "saved instance state null");
            Intent oldIntent = getIntent();
             title = oldIntent.getStringExtra("title");
             title = "" + title;
            //movieImage[0] = oldIntent.getStringExtra("image");
            //movieImage[0] = "" + movieImage;
        } else {
            //Log.v("welcome", "saved instance state not null");
            title = (String) savedInstanceState.getSerializable("title");
            //movieImage[0] = (String) savedInstanceState.getSerializable("image");
        }
        /*
        CustomList adapter = new
                CustomList(MovieActivity.this, title, movieImage);
        list= (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        */


        TextView titleTextView =new TextView(this);
        titleTextView =(TextView)findViewById(R.id.title);
        titleTextView.setText(title);



    }

}
