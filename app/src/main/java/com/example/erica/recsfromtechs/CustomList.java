package com.example.erica.recsfromtechs;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a helper class that creates a custom list
 * where every time has an image on the left side
 * and a text to the left with a subtext under it
 *
 * This particular list is for movies
 *
 * The method uses an Async task to get the image with
 * the class webImageGetter
 */
public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] name;
    private final String[] year;
    private final String[] rating;
    private final String[] imageId;
    public CustomList(Activity context,
                      String[] name, String[] year, String[] rating, String[] imageId) {
        super(context, R.layout.list_single, name);

        this.year = year;
        this.context = context;
        this.name = name;
        this.rating = rating;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView txtRating = (TextView) rowView.findViewById(R.id.txt2);


        //Set the view
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(name[position]);

        //Format the rating string
        String ratingString;
        if (rating[position].equals ("-1")) {
            ratingString = "This movie has no rating";
        } else {
            ratingString = "Critics Rating: " +  rating[position];
        }


        txtRating.setText(ratingString);

        new webImageGetter(imageView).execute(imageId[position]);

        return rowView;
    }
}