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
import android.widget.Toast;

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
import java.util.Arrays;

/**
 * Created by Courtney on 2/25/16.
 */
public class DVD extends AppCompatActivity {
    RequestQueue queue;
    RequestQueue queue2;
    SharedPreferences currentMovie;
    SharedPreferences.Editor editCurrentMovie;
    MovieDB movieDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);
        movieDbHandler = new MovieDB(this, null, null, 1);
        currentMovie = getSharedPreferences("CurrentMovie", MODE_PRIVATE);
        editCurrentMovie = currentMovie.edit();
        showDVDReleases(findViewById(R.id.list3));
    }

    /**
     * Pulls the DVD movie releases info from the API
     * It then converts it to a JSON object and parses it
     * once it has all the information it calls the
     * @method populateListView
     *
     * @param view The current layout with all the Android widgets
     */
    public void showDVDReleases(View view) {

        final ArrayList<ArrayList> movieInfo = new ArrayList<>();

        String url ="http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=16&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<ArrayList> boxOfficeInfo = new ArrayList<>();
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
     * Populates the view of the list of movies.
     * The method also adds the movie to the database
     * once they are clicked
     *
     * @param movieInfo The info to be displayed
     */
    private void populateListView(ArrayList<ArrayList> movieInfo) {

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
                CustomList(DVD.this, movieNames, movieYears, ratings, images);
        list= (ListView) findViewById(R.id.list3);
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
                Intent intent = new Intent(DVD.this, MovieActivity.class);
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
