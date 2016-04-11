package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class searchScreen extends AppCompatActivity {

    private RequestQueue queue;
    //private RequestQueue queue2;
    private SharedPreferences.Editor editCurrentMovie;

    private MovieDB movieDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences currentMovie;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        //queue2 = Volley.newRequestQueue(this);
        movieDbHandler = new MovieDB(this);
        currentMovie = getSharedPreferences("CurrentMovie",MODE_PRIVATE);
        editCurrentMovie = currentMovie.edit();



    }

    /**
     * Switches screens to the dashboard page
     * @param view The view we're looking at
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }

    /**
     * Connects to the Rotten Tomatoes API to get the list of movies corresponding
     * to the text entered
     * @param view The view we're currently look at
     */
    public void searchForMovie(View view) {



        final ArrayList<ArrayList<String>> movieInfo = new ArrayList<>();
        EditText temp   = (EditText)findViewById(R.id.editText);
        String strTemp = temp.getText().toString().trim();
        strTemp = strTemp.replace(" ", "+");
        String url ="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" + strTemp + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                                //printing first 500 chars of the response.  Only want to do this for debug
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // fetch the array of movies in the response
                            JSONArray movies = jsonResponse.getJSONArray("movies");

                            for (int i = 0; i < movies.length(); i++) {
                                ArrayList<String> thisMovieArray = new ArrayList<>();
                                JSONObject movie = movies.getJSONObject(i);
                                thisMovieArray.add(movie.getString("title"));
                                thisMovieArray.add(movie.getString("year"));

                                JSONObject rating = movie.getJSONObject("ratings");
                                thisMovieArray.add(rating.getString("critics_score"));

                                JSONObject posters = movie.getJSONObject("posters");
                                thisMovieArray.add(posters.getString("thumbnail"));
                                movieInfo.add(thisMovieArray);

                            }
                            populateListView(movieInfo);

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




    // Instantiate the RequestQueue.


    // Request a string response from the provided URL.

    /**
     * Provides a UI display to see the list of movies searched for
     * @param movieInfo All of the movie info to be included
     */
    private void populateListView(ArrayList<ArrayList<String>> movieInfo) {        ListView list;
        final String[] movieNames = new String[movieInfo.size()] ;
        final String[] movieYears = new String[movieInfo.size()] ;
        final String[] ratings = new String[movieInfo.size()] ;
        final String[] images = new String[movieInfo.size()];

        int i = 0;
        for (ArrayList<String> e : movieInfo) {
            movieNames[i] = e.get(0);
            movieYears[i] = e.get(1);
            ratings[i] = e.get(2);
            images[i] = e.get(3);
            i++;
        }

            CustomList adapter = new
                    CustomList(searchScreen.this, movieNames, movieYears, ratings, images);
            list= (ListView) findViewById(R.id.list);
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
                    Intent intent = new Intent(searchScreen.this, MovieActivity.class);
                    startActivity(intent);

                }
            });
    }

}
