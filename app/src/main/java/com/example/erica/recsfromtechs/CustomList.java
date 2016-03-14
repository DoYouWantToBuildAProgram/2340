package com.example.erica.recsfromtechs;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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



        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(name[position]);

        System.out.println("passing in +++" + imageId[position]);
        new webImageGetter(imageView).execute(imageId[position]);

        return rowView;
    }
}