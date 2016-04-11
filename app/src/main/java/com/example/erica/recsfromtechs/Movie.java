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
     * creates a movie that is not being rated
     * @param title
     */
    public Movie(String title) {
        this.title = title;
        rating = "0";
    }


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
     * sets the rating
     * @param newRating
     */
    public void setRating(String newRating) {
        rating = newRating;
    }

    /**
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * sets the year of release
     * @param newYear
     */
    public void setyear(String newYear) {
        year = newYear;
    }

    /**
     * @return year of release
     */
    public String getYear() {
        return year;
    }

    /**
     * sets the title of the movie
     * @param newTitle
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }
}

