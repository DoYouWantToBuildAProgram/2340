package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * This is the activity class for the movie
 *
 * This page displayes information about the movie
 * The user can also rate the movie on the page ranging from 0
 * to 5 stars going in increments of .5 stars
 * If the user returns to this page after leaving it
 * the rating will still be there
 */
public class MovieActivity extends AppCompatActivity {
    SharedPreferences currentUser;
    SharedPreferences.Editor editCurrentUser;
    MyDBHandler dbHandler;
    SharedPreferences currentMovie;
    SharedPreferences.Editor editCurrentMovie;
    MovieDB movieDbHandler;
    RecsDB recsDbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        dbHandler = new MyDBHandler(this, null);
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        movieDbHandler = new MovieDB(this, null);
        recsDbHandler = new RecsDB(this, null, null, 1);
        currentMovie = getSharedPreferences("CurrentMovie",MODE_PRIVATE);
        editCurrentMovie = currentMovie.edit();

        TextView titleTextView =(TextView)findViewById(R.id.title);
        String title = currentMovie.getString("title",null);
        String year = currentMovie.getString("year",null);
        String rating = currentMovie.getString("rating",null);
        titleTextView.setText(title);




        //here im getting a movie object from our singleton appState
        //setting the year and rating TextViews to what was stored in the movie object
        //final Movie myMovie = appState.getMovie(title);
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
        float currentRating = movieDbHandler.getUserRating(title, year);
        int currentNum = movieDbHandler.getCurrentNum(title, year);
        float average = currentRating / currentNum;
        majorRatingBar.setRating(average);



        Button submit = (Button) findViewById(R.id.submit);
        // perform click event on button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //prevRating is the rating the user already gave, only works if they havent left the page
                //if they already rated it that rating is removed and replaced with a new rating
                //new rating is added to the movie objects hashmap

                float newRating = majorRatingBar.getRating();
                String title = currentMovie.getString("title",null);
                String year = currentMovie.getString("year",null);
                recsDbHandler.addRec(new Recs(dbHandler.getMajor(currentUser.getString("username",null)),newRating,title,year));
                float currentRating = movieDbHandler.getUserRating(title,year);
                int currentNum = movieDbHandler.getCurrentNum(title,year);
                movieDbHandler.updateUserRating(title,year,currentRating, newRating);
                movieDbHandler.incrementNumRating(title,year,currentNum);



            }
        });


    }

    /**
     * this method takes you back to the dashboard
     * @param view
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }





}