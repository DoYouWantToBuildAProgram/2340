package com.example.erica.recsfromtechs;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by juliaredston on 2/29/16.
 */
public class myApplication extends Application
{
    public ArrayList<Movie> movies = new ArrayList<Movie>();

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> newMovies){
        movies = newMovies;
    }

    public void addMovie(Movie m){
        movies.add(m);
    }

    public Movie getMovie(String movieName){
        for(Movie movie: movies){
            if(movie.getTitle().equals(movieName)){
                return movie;
            }
        }

        throw new java.util.NoSuchElementException();
    }
}
