package com.example.erica.recsfromtechs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a movie object representing a single movie
 * Created by Courtney on 2/23/16.
 */
public class Movie {
    private String title;
    private String rating;
    private String year;


    public Movie(String title) {
        this.title = title;
        rating = "0";
    }
    public Movie(String title, String year, String rating) {
        this.title = title;
        this.rating = rating;
        this.year = year;
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

    public void setYear(String newYear) {
        year = newYear;
    }
    public String getYear() {
        return year;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }
}

