package com.example.erica.recsfromtechs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class searchScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public TextView mTextView;

    public void searchForMovie(View view) {
        mTextView = (TextView) findViewById(R.id.text);
        System.out.println("You such searched for movie");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=kill+bill&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
        /*JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        String answer = resp.toString();
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ resp.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });*/
        //queue.add(jsObjRequest);
    }




    // Instantiate the RequestQueue.


    // Request a string response from the provided URL.

}
