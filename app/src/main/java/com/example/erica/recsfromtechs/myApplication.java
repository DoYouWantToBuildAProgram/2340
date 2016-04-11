package com.example.erica.recsfromtechs;

import android.app.Application;

import java.util.ArrayList;

/**
 * this is our singleton thing
 * it holds an array of movies and a user currentUser
 * currentUser isn't necessary but it makes our lives a lot easier
 * Created by juliaredston on 2/29/16.
 */
public class myApplication extends Application {

    //get access to this class in other classes using
    // appState = ((myApplication) this.getApplicationContext());
    private ArrayList<Movie> movies = new ArrayList<>();
    private User currentUser;

    /**
     * This method sets a specific user as the current user
     * @param user the user currently logged into the app
     */
    public void setCurrentUser(User user){ currentUser = user;}

    /**
     * Tells you who the user that is currently using the app is
     * @return the user currently logged into the app
     */
    public User getCurrentUser(){ return currentUser;}

    /**
     * Provides a list of movies
     * @return a list of movies
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * Sets a list of movies to given movies
     * @param newMovies the new movies to be added
     */
    public void setMovies(ArrayList<Movie> newMovies){
        movies = newMovies;
    }

    /**
     * Adds a movie to a list of movies
     * @param m the movie to be added
     */
    public void addMovie(Movie m){
        movies.add(m);
    }

    /**
     * Takes in a string with a movie title and searches for that movie
     * @param movieName The name of the movie we're searching for
     * @return the movie object representing that movie
     */
    public Movie getMovie(String movieName){
        for(Movie movie: movies){
            if(movie.getTitle().equals(movieName)){
                return movie;
            }
        }
        throw new java.util.NoSuchElementException();
    }
}
