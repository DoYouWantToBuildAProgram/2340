package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This method is the Box Office Releases page, which
 * displays movies recently put out in theaters
 * Created by Courtney on 2/25/16.
 */
public class BoxOffice extends AppCompatActivity{
    private RequestQueue queue;
    //private RequestQueue queue2;
    RequestQueue queue;
    RequestQueue queue2;
    SharedPreferences currentMovie;
    SharedPreferences.Editor editCurrentMovie;
    MovieDB movieDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);
        movieDbHandler = new MovieDB(this, null);
        currentMovie = getSharedPreferences("CurrentMovie", MODE_PRIVATE);
        editCurrentMovie = currentMovie.edit();
        showBoxOfficeMovies(findViewById(R.id.list2));
    }

    /**
     * Pulls the box office movie info from the API
     *
     * @param view The current layout with all the Android widgets
     */
    private void showBoxOfficeMovies(View view) {

        final ArrayList<ArrayList<String>> movieInfo = new ArrayList<>();

        String url ="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=16&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<ArrayList<String>> boxOfficeInfo = new ArrayList<>();
                //handle a valid response coming back.  Getting this string mainly for debug
                //printing first 500 chars of the response.  Only want to do this for debug
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    // fetch the array of movies in the response
                    JSONArray movies = jsonResponse.getJSONArray("movies");

                    // add each movie's title to an array
                    String[] movieTitles = new String[movies.length()];
                    for (int i = 0; i < movies.length(); i++) {
                        ArrayList<String> thisMovieArray = new ArrayList<>();
                        JSONObject movie = movies.getJSONObject(i);
                        thisMovieArray.add(movie.getString("title"));
                        thisMovieArray.add(movie.getString("year"));

                        JSONObject rating = movie.getJSONObject("ratings");
                        thisMovieArray.add(rating.getString("critics_score"));

                        JSONObject posters = movie.getJSONObject("posters");
                        thisMovieArray.add(posters.getString("thumbnail"));
                        boxOfficeInfo.add(thisMovieArray);

                    }
                    populateListView(boxOfficeInfo);

                } catch (JSONException e) {
                    System.out.println("error parsing JSON " + e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //show error on phone

            }
        });
        //this actually queues up the async response with Volley

        queue.add(stringRequest);
    }

    /**
     * Helps to populate the view of the list of movies
     *
     * @param movieInfo The info to be displayed
     */
    private void populateListView(ArrayList<ArrayList<String>> movieInfo) {

        ListView list;
        final String[] movieNames = new String[movieInfo.size()] ;
        final String[] movieYears = new String[movieInfo.size()] ;
        final String[] ratings = new String[movieInfo.size()] ;
        final String[] images = new String[movieInfo.size()];

        int i = 0;
        for (ArrayList<String> e : movieInfo) {
            movieNames[i] = e.get(0);
            images[i] =  e.get(3);
            ratings[i] = e.get(2);
            movieYears[i] = e.get(1);
            i++;
        }

        CustomList adapter = new
                CustomList(BoxOffice.this, movieNames, movieYears, ratings, images);
        list= (ListView) findViewById(R.id.list2);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                editCurrentMovie.putString("title", movieNames[position]);
                editCurrentMovie.commit();
                editCurrentMovie.putString("year", movieYears[position]);
                editCurrentMovie.commit();
                editCurrentMovie.putString("rating", ratings[position]);
                movieDbHandler.addMovie(new Movie(movieNames[position], movieYears[position],ratings[position]));
                Intent intent = new Intent(BoxOffice.this, MovieActivity.class);

                startActivity(intent);
            }
        });
    }

    /**
     * Allows the user to go back to the dashboard
     * @param view The current layout with all the Android widgets
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }


}
