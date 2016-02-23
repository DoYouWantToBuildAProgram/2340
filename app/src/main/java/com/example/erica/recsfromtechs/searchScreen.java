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

import java.util.ArrayList;

public class searchScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public TextView mTextView;
    private String response;

    public void searchForMovie(View view) {
        mTextView = (TextView) findViewById(R.id.text);
        EditText text = (EditText)findViewById(R.id.editText);
        String value = text.getText().toString();
        System.out.println("You such searched for movie");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" + value + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        //printing first 500 chars of the response.  Only want to do this for debug
                        TextView view = (TextView) findViewById(R.id.listView);
                        view.setText(response.substring(0, 1000));

                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        //From that object, we extract the array of actual data labeled result
                        //JSONArray array = obj1.optJSONArray("result");
                        ArrayList<Movie> movies = new ArrayList<>();
                        String[] titles = response.split("title");
                        movies.add(new Movie(titles[0]));
                        view.setText(movies.toString().toCharArray(), 0, 20);



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

    }




    // Instantiate the RequestQueue.


    // Request a string response from the provided URL.

}
