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

import java.util.List;

/**
 * Created by juliaredston on 3/9/16.
 */
public class Recommendations extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String item;
    public myApplication appState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

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
        String text = "";

        for(Movie m: appState.getMovies()){
            List<Float> list = m.getMajorRatings(item);
            double avg =0;
            int counter =0;
            if(list != null) {
                for (Float value : list) {
                    avg += value;
                    //Log.v("rating", "adding " + value);
                    counter++;

                }
                avg = avg / counter;

                if (avg >= 3.5) {
                    text += m.getTitle() + " Rating: " + avg + "\n";

                }
            }
        }
        if(text.equals("")){
            text = "Sorry no recommendations could be given";
        }
        TextView recView =new TextView(this);
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