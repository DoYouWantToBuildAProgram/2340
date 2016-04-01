package com.example.erica.recsfromtechs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Courtney on 2/23/16.
 */
public class Movie {
    private String title;
    private String rating;
    private String year;
    private HashMap<String, List<Float>> majorRatings;

    public Movie(String title) {
        this.title = title;
        rating = "0";
        majorRatings = new HashMap<>();
    }
    public Movie(String title, String year, String rating) {
        this.title = title;
        this.rating = rating;
        this.year = year;
        majorRatings = new HashMap<>();
    }

    public HashMap<String,List<Float>> getMajorRatings(){
       return majorRatings;
    }

    public List<Float> getMajorRatings(String major){
        return majorRatings.get(major);
    }

    public void addMajorRating(String major, Float value){
        List<Float> ratings;
        if(majorRatings.get(major) != null){
            ratings = majorRatings.get(major);
            ratings.add(value);
        } else {
            ratings = new ArrayList<Float>();
            ratings.add(value);
        }
        majorRatings.put(major,ratings);
    }

    public String getTitle() {
        return title;
    }
    public void setRating(String newRating) {
        rating = newRating;
    }
    public String getRating() {
        return rating;
    }

    public void setyear(String newYear) {
        year = newYear;
    }
    public String getYear() {
        return year;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }
}

