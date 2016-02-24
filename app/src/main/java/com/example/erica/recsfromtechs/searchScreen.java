package com.example.erica.recsfromtechs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class searchScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView view = (TextView) findViewById(R.id.text);
        view.setText("Text goes here");

    }

    public TextView mTextView;
    private String response;

    public void searchForMovie(View view) {
        System.out.println("You just searched for movie");
        String url ="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=kill+bill&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        //printing first 500 chars of the response.  Only want to do this for debug
                try {
                    TextView view = (TextView) findViewById(R.id.text);
                    view.setText(response);
                    JSONObject jsonResponse = new JSONObject(response);

                    // fetch the array of movies in the response
                    JSONArray movies = jsonResponse.getJSONArray("movies");

                    // add each movie's title to an array
                    String[] movieTitles = new String[movies.length()];
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        movieTitles[i] = movie.getString("title");
                    }
                    view.setText(Arrays.toString(movieTitles));
                } catch (JSONException e) {
                    System.out.println("error parsing JSON " + e.toString());
                }
                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        //From that object, we extract the array of actual data labeled result
                        //JSONArray array = obj1.optJSONArray("result");
//                        ArrayList<Movie> movies = new ArrayList<>();
//                        String[] titles = response.split("title");
//                        movies.add(new Movie(titles[0]));
//                        view.setText(movies.toString().toCharArray(), 0, 20);

                        //once we have all data, then go to list screen
                        //changeView(states);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        //show error on phone
                        TextView view = (TextView) findViewById(R.id.text);
                        view.setText(response);
                    }
                });
        //this actually queues up the async response with Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }




    // Instantiate the RequestQueue.


    // Request a string response from the provided URL.

}
