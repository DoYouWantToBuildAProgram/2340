package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class searchScreen extends AppCompatActivity {

    RequestQueue queue;
    RequestQueue queue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);


    }
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }
    public void searchForMovie(View view) {

        final ArrayList<ArrayList> movieInfo = new ArrayList<>();

        System.out.println("You just searched for movie");
        EditText temp   = (EditText)findViewById(R.id.editText);
        String strTemp = temp.getText().toString().trim();
        strTemp.replace(" ", "+");
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

                            // add each movie's title to an array
                            String[] movieTitles = new String[movies.length()];
                            for (int i = 0; i < movies.length(); i++) {
                                ArrayList<String> thisMovieArray = new ArrayList<>();
                                JSONObject movie = movies.getJSONObject(i);
                                thisMovieArray.add(movie.getString("title"));
                                thisMovieArray.add(movie.getString("year"));

                                // thisMovieArray.add(movie.getString("userRating"));

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


    private void populateListView(ArrayList<ArrayList> movieInfo) {

        ListView list;
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
                    Bundle bundle = new Bundle();
                    bundle.putString("title", movieNames[position]);
                    bundle.putString("year", movieYears[position]);
                    bundle.putString("rating", ratings[position]);
                    bundle.putString("image", images[position]);
                    Intent intent = new Intent(searchScreen.this, MovieActivity.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
                   // Toast.makeText(searchScreen.this, "You Clicked at " + movieNames[+position], Toast.LENGTH_SHORT).show();

                }
            });


    }


}
