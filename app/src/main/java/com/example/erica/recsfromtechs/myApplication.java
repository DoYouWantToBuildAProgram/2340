package com.example.erica.recsfromtechs;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by juliaredston on 2/29/16.
 */
public class myApplication extends Application
{

    //this is our singleton thing
    //it holds an array of movies and a user currentUser
    //currentUser isn't necessary but it makes our lives a lot easier


    //get access to this class in other classes using
    // appState = ((myApplication) this.getApplicationContext());
    public ArrayList<Movie> movies = new ArrayList<Movie>();
    private User currentUser;

    public void setCurrentUser(User user){ currentUser = user;}
    public User getCurrentUser(){ return currentUser;}

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
