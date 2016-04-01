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

//        ListView list;
//        if(savedInstanceState == null) {
//;
//            Intent oldIntent = getIntent();
//            title = oldIntent.getStringExtra("title");
//            title = "" + title;
//
//
//
//        } else {
//            //Log.v("welcome", "saved instance state not null");
//            title = (String) savedInstanceState.getSerializable("title");
//
//
//        }



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
//        if( myMovie.getMajorRatings(currentMajor) != null) {
//
//
//            for (Float value : myMovie.getMajorRatings(currentMajor)) {
//                avg += value;
//
//                counter++;
//
//            }
//        }
//
//        avg = avg/counter;
//
//        majorRatingBar.setRating(avg);
        float currentRating = movieDbHandler.getUserRating(title, year);
        System.out.println("current rating =" + currentRating);
        int currentNum = movieDbHandler.getCurrentNum(title, year);
        System.out.println("current num=" + currentNum);
        float average = currentRating / currentNum;
        System.out.println("current average=" + average);
        majorRatingBar.setRating(average);



        Button submit = (Button) findViewById(R.id.submit);
        // perform click event on button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //prevRating is the rating the user already gave, only works if they havent left the page
                //if they already rated it that rating is removed and replaced with a new rating
                //new rating is added to the movie objects hashmap

//                if(prevRating != null){
//                    myMovie.getMajorRatings(currentMajor).remove(prevRating);
//                }
                 float newRating = majorRatingBar.getRating();
                String title = currentMovie.getString("title",null);
                String year = currentMovie.getString("year",null);
                float currentRating = movieDbHandler.getUserRating(title,year);
                int currentNum = movieDbHandler.getCurrentNum(title,year);
                movieDbHandler.updateUserRating(title,year,currentRating, newRating);
                myMovie.addMajorRating(currentMajor, newRating);
                movieDbHandler.incrementNumRating(title,year,currentNum);

                //Log.v("rating", rating.toString());
                //prevRating = rating;



            }
        });















    }
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }





}
