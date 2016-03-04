package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.RatingBar;

public class MovieActivity extends AppCompatActivity {
    SharedPreferences movieInfo;
    SharedPreferences.Editor editMovieInfo;

    /*
    public void editRating(View view) {
        TextView rateText = (TextView)findViewById(R.id.rating);
        EditText changedRating = (EditText)findViewById(R.id.newRating);
        nameText.setText(changedName.getText().toString());
        editUserInfo.putString("name", changedName.getText().toString());
        editUserInfo.commit();
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        String title = "";
        String year = "";

        String rating = "";
        //String[] movieImage = new String[1];
        ListView list;
        if(savedInstanceState == null) {
            //the user name has been passed to the dashboard as an "extra"
            //works like a map, "user" is our key
            //Log.v("welcome", "saved instance state null");
            Intent oldIntent = getIntent();
            title = oldIntent.getStringExtra("title");
            title = "" + title;

            Intent intentYear = getIntent();
            year = intentYear.getStringExtra("year");
            year = "" + year;


            Intent intentRating = getIntent();
            rating = intentRating.getStringExtra("rating");
            rating = "" + rating;



            //movieImage[0] = oldIntent.getStringExtra("image");
            //movieImage[0] = "" + movieImage;
        } else {
            //Log.v("welcome", "saved instance state not null");
            title = (String) savedInstanceState.getSerializable("title");
            year = (String) savedInstanceState.getSerializable("year");
            rating = (String) savedInstanceState.getSerializable("rating");

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
        titleTextView =(TextView)findViewById(R.id.year);
        titleTextView.setText(year);
        titleTextView =(TextView)findViewById(R.id.rating);
        titleTextView.setText(rating);



        //String movie = oldIntent.getStringExtra("user");

        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submit = (Button) findViewById(R.id.submit);
        // perform click event on button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values and then displayed in a toast
                //String totalStars = "Total Stars:: " + simpleRatingBar.getNumStars();
                String rating = "Rating: " + simpleRatingBar.getRating();
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            }
        });
        /*

        movieInfo = getSharedPreferences("Movie Ratings User", MODE_PRIVATE);
        editMovieInfo = movieInfo.edit();
        Intent someIntent = getIntent();
        String movie = someIntent.getStringExtra("movie");
        String uRating = movieInfo.getString(movie + "rating", null);
        TextView uRatingTxt = (TextView)findViewById(R.id.userRating);
        uRatingTxt.setText(uRating);
        /*



        */










    }
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }





}
