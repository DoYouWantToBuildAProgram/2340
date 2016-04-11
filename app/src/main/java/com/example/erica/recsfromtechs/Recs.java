package com.example.erica.recsfromtechs;

/**
 * Recommendation class for objects to be added to our RecsDb
 * Created by Erica on 4/4/2016.
 */
public class Recs {

    private String major;
    private double rating;
    private String title;
    private String year;

    /**
     * Creates a recommendation
     * @param major The major of the user
     * @param rating The rating of the movie
     * @param title The title of the movie
     * @param year The year the movie was released
     */
    public Recs(String major, double rating,String title, String year){
        this.major = major;
        this.rating = rating;
        this.title = title;
        this.year = year;
    }

    /**
     * Returns the major associated with the users' rating of the movie
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Gives the title of the movie
     * @return The title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gives the year the movie was released
     * @return The year of release for the movie
     */
    public String getYear() {
        return year;
    }

    /**
     * Gives the rating of the movie
     * @return The movies rating out of five
     */
    public double getRating() {
        return rating;
    }
}
