package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.RatingBar;

public class MovieActivity extends AppCompatActivity {
    public myApplication appState;
    SharedPreferences movieInfo;
    SharedPreferences.Editor editMovieInfo;
    Float rating;
    Float prevRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        appState = ((myApplication) this.getApplicationContext());
        String title = "";

        ListView list;
        if(savedInstanceState == null) {
;
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



        //here im getting a movie object from our singleton appState
        //setting the year and rating TextViews to what was stored in the movie object
        final Movie myMovie = appState.getMovie(title);
        rating =  myMovie.getRating();
        TextView yearView =new TextView(this);
        yearView =(TextView)findViewById(R.id.year);
        yearView.setText(myMovie.getYear());



        TextView ratingView =new TextView(this);
        ratingView =(TextView)findViewById(R.id.rating);
        ratingView.setText(new Float(myMovie.getRating()).toString());

        //majorRatingBar is our star bar, currently it shows what the average rating is for the users major
        //which is being calculated below
        //Major ratings are stored in a HashMap in Movie Objects
        //the string of the major is the key, the values are floats of the ratings

        final RatingBar majorRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
        float avg =0;
        int counter = 0;
        if( myMovie.getMajorRatings(appState.getCurrentUser().getMajor()) != null) {


            for (Float value : myMovie.getMajorRatings(appState.getCurrentUser().getMajor())) {
                avg += value;

                counter++;

            }
        }

        avg = avg/counter;

        majorRatingBar.setRating(avg);




        Button submit = (Button) findViewById(R.id.submit);
        // perform click event on button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //prevRating is the rating the user already gave, only works if they havent left the page
                //if they already rated it that rating is removed and replaced with a new rating
                //new rating is added to the movie objects hashmap

                if(prevRating != null){
                    myMovie.getMajorRatings(appState.getCurrentUser().getMajor()).remove(prevRating);
                }
                 rating = majorRatingBar.getRating();
                Log.v("user", appState.getCurrentUser().toString());
                myMovie.addMajorRating(appState.getCurrentUser().getMajor(), rating);

                Log.v("rating", rating.toString());
                prevRating = rating;



            }
        });















    }
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }





}
