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
     * @param title the movie title
     * @param year the release year
     * @param rating the rating that is being added to
     *               the movie
     */
    public Movie(String title, String year, String rating) {
        this.title = title;
        this.rating = rating;
        this.year = year;
    }


    /**
     * returns the movie title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the rating of the string
     * @param newRating the new rating
     */
    public void setRating(String newRating) {
        rating = newRating;
    }

    /**
     * Returns the rating of the movie
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets the year of release
     * @param newYear the new release year
     */
    public void setyear(String newYear) {
        year = newYear;
    }

    /**
     * Returns the year the movie was released
     * @return year of release
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the title of the movie
     * @param newTitle the new title
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }
}

