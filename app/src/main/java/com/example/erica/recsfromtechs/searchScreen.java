package com.example.erica.recsfromtechs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

    /**
     * Pulls all movie info from the API based on movie searched and displays the movies
     *
     * @param view The current layout with all the Android widgets
     */
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


    /**
     * Helps to populate the list view of the movies
     *
     * @param movieInfo the info of the movies to be displayed
     */
    private void populateListView(ArrayList<ArrayList> movieInfo) {

        ListView list;
        final String[] movieNames = new String[movieInfo.size()] ;
        final Bitmap[] images = new Bitmap[movieInfo.size()];

        int i = 0;
        for (ArrayList<String> e : movieInfo) {
            movieNames[i] = e.get(0);
            webImageGetter getter = new webImageGetter(e.get(3));
            images[i] =  getter.getBitmap();
            i++;
        }

            CustomList adapter = new
                    CustomList(searchScreen.this, movieNames, images);
            list= (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(searchScreen.this, "You Clicked at " + movieNames[+position], Toast.LENGTH_SHORT).show();

                }
            });
    }


}
