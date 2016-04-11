package com.example.erica.recsfromtechs;

/**
 * Created by Courtney on 2/23/16.
 * This is the object class for a movie. it has a variety
 * of useful methods that help the app use OOP
 */
public class Movie {
    private String title;
    private String rating;
    private String year;

    /**
     * creates a movie that is being rated
     * @param title
     * @param year
     * @param rating the rating that is being added to
     *               the movie
     */
    public Movie(String title, String year, String rating) {
        this.title = title;
        this.rating = rating;
        this.year = year;
    }


    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }


    /**
     * @return rating
     */
    public String getRating() {
        return rating;
    }


    /**
     * @return year of release
     */
    public String getYear() {
        return year;
    }

}

