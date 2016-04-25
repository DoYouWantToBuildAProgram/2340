package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieActivity extends AppCompatActivity {
    public myApplication appState;
    SharedPreferences movieInfo;
    SharedPreferences.Editor editMovieInfo;
    SharedPreferences currentUser;
    SharedPreferences.Editor editCurrentUser;
    Float rating;
    Float prevRating;
    MyDBHandler dbHandler;
    SharedPreferences currentMovie;
    SharedPreferences.Editor editCurrentMovie;
    MovieDB movieDbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        appState = ((myApplication) this.getApplicationContext());
        dbHandler = new MyDBHandler(this, null, null, 1);
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        movieDbHandler = new MovieDB(this, null, null, 1);
        currentMovie = getSharedPreferences("CurrentMovie",MODE_PRIVATE);
        editCurrentMovie = currentMovie.edit();



        TextView titleTextView =(TextView)findViewById(R.id.title);
        String title = currentMovie.getString("title",null);
        String year = currentMovie.getString("year",null);
        String rating = currentMovie.getString("rating",null);
        titleTextView.setText(title);




        //here im getting a movie object from our singleton appState
        //setting the year and rating TextViews to what was stored in the movie object
        final Movie myMovie = appState.getMovie(title);
        //rating =  myMovie.getRating();
        //TextView yearView =new TextView(this);
        TextView yearView =(TextView)findViewById(R.id.year);
        yearView.setText(year);



        //TextView ratingView =new TextView(this);
        TextView ratingView =(TextView)findViewById(R.id.rating);
        ratingView.setText(rating);

        //majorRatingBar is our star bar, currently it shows what the average rating is for the users major
        //which is being calculated below
        //Major ratings are stored in a HashMap in Movie Objects
        //the string of the major is the key, the values are floats of the ratings

        final RatingBar majorRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
        //float avg =0;
        //int counter = 0;
        final String currentMajor = dbHandler.getMajor(currentUser.getString("username",null));

        float currentRating = movieDbHandler.getUserRating(title, year);
       // System.out.println("current rating =" + currentRating);
        int currentNum = movieDbHandler.getCurrentNum(title, year);
       // System.out.println("current num=" + currentNum);
        float average = currentRating / currentNum;
       // System.out.println("current average=" + average);
        majorRatingBar.setRating(average);



        Button submit = (Button) findViewById(R.id.submit);
        // perform click event on button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 float newRating = majorRatingBar.getRating();
                String title = currentMovie.getString("title",null);
                String year = currentMovie.getString("year",null);
                float currentRating = movieDbHandler.getUserRating(title,year);
                int currentNum = movieDbHandler.getCurrentNum(title,year);
                movieDbHandler.updateUserRating(title,year,currentRating, newRating);
                myMovie.addMajorRating(currentMajor, newRating);
                movieDbHandler.incrementNumRating(title,year,currentNum);



            }
        });















    }
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }





}
