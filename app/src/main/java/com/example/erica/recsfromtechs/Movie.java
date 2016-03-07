package com.example.erica.recsfromtechs;

/**
 * Created by Courtney on 2/23/16.
 */
public class Movie {
    private String title;
    private int rating;

    public Movie(String title) {
        this.title = title;
        rating = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setRating(int newRating) {
        rating = newRating;
    }
    public int getRating() {
        return rating;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }
}

