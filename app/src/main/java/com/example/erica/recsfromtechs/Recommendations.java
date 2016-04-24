package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * This shows what movies are recommended for a user
 * Created by juliaredston on 3/9/16.
 */
public class Recommendations extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String item;
    private RecsDB recsDbHandler;
    private double thresholdRating = 3.5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        //Spinner spinner;

        recsDbHandler = new RecsDB(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.majors,R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(Recommendations.this);




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        LinkedList<Recs> majorRecs;
        HashMap<String,LinkedList<Double>> movies = new HashMap<>();
        String text = "";

        majorRecs = (LinkedList<Recs>) recsDbHandler.listOfRecs(item);
        for(Recs rec:majorRecs){
            System.out.println(rec.getTitle() + " and " + rec.getMajor());
            if(movies.containsKey(rec.getTitle()+ " " + rec.getYear())) {
                movies.get(rec.getTitle()+" " + rec.getYear()).add(rec.getRating());
            } else {
                movies.put(rec.getTitle() + " " + rec.getYear(), new LinkedList<Double>());
                movies.get(rec.getTitle() + " " + rec.getYear()).add(rec.getRating());
            }
        }
        for(String key: movies.keySet()) {
            System.out.println(key);
            int counter = 0;
            double sum = 0;
            for(double rating:movies.get(key)) {
                sum += rating;
                counter++;
            }
            double average = sum / counter;

            if (average > thresholdRating) {
                text += key + " Rating: " + average + "\n";
            }
        }
        if (text.equals("")) {
            text = "Sorry no recommendations could be given";
        }
        TextView recView;
        recView =(TextView)findViewById(R.id.recommendationList);
        recView.setText(text);

        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Switches the screen to the Dashboard page
     * @param view The view we're looking at
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }
}