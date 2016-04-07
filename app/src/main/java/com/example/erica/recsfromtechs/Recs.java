package com.example.erica.recsfromtechs;

/**
 * Created by Erica on 4/4/2016.
 */
public class Recs {

    private String major;
    private double rating;
    private String title;
    private String year;

    public Recs(String major, double rating,String title, String year){
        this.major = major;
        this.rating = rating;
        this.title = title;
        this.year = year;
    }

    public String getMajor() {
        return major;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }
}
