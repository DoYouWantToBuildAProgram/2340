package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by juliaredston on 3/9/16.
 */
public class Recommendations extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String item;
    public myApplication appState;
    RecsDB recsDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        recsDbHandler = new RecsDB(this, null, null, 1);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.majors,R.layout.spinner_item);
        spinner.setAdapter(adapter);
        appState = ((myApplication) this.getApplicationContext());
        spinner.setOnItemSelectedListener(Recommendations.this);
        //spinner.setOnItemSelectedListener(this);



    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        LinkedList<Recs> majorRecs;
        HashMap<String,LinkedList<Double>> movies = new HashMap<>();
        String text = "";

//        for(Movie m: appState.getMovies()){
//            List<Float> list = m.getMajorRatings(item);
//            double avg =0;
//            int counter =0;
//            if(list != null) {
//                for (Float value : list) {
//                    avg += value;
//                    //Log.v("rating", "adding " + value);
//                    counter++;
//
//                }
//                avg = avg / counter;
//
//                if (avg >= 3.5) {
//                    text += m.getTitle() + " Rating: " + avg + "\n";
//
//                }
//            }
//        }
        majorRecs = recsDbHandler.listOfRecs(item);
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
            if(average > 3.5) {
                text += key + " Rating: " + average + "\n";
            }
        }
        if(text == ""){
            text = "Sorry no recommendations could be given";
        }
        TextView recView;
        recView =(TextView)findViewById(R.id.recommendationList);
        recView.setText(text);

        //Toast.makeText(this,"You Selected: " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }
}