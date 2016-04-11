package com.example.erica.recsfromtechs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Erica on 3/31/2016.
 * This is the class that handels communication with SQLite
 * databas that stores information about movies. It saves information
 * about their name title and the average rating a user gave them.
 */
public class MovieDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";
    private static final String TABLE_MOVIES = "movies";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_URATING = "userRating";
    private static final String COLUMN_MRATING = "movieRating";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_NUMRATING = "numRating";


    public MovieDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MOVIES + " ("
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_URATING + " FLOAT, "
                + COLUMN_NUMRATING + " INTEGER, "
                + COLUMN_MRATING + " TEXT, "
                + COLUMN_YEAR + " TEXT"
                + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Should never really call this, it deletes the whole table and makes a new one
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    /**
     * this method adds a movie to the database
     * @param movie the movie that is to be added to the database
     * @return a boolean stating wether it was successful
     */
    public boolean addMovie(Movie movie) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_TITLE + " = '" + movie.getTitle() + "' AND " + COLUMN_YEAR + " = '" + movie.getYear()+ "';", null);
        c.moveToFirst();
        if (c.isBeforeFirst()) {
            values.put(COLUMN_TITLE, movie.getTitle());
            values.put(COLUMN_URATING, 0.0);
            values.put(COLUMN_MRATING, movie.getRating());
            values.put(COLUMN_YEAR, movie.getYear());
            values.put(COLUMN_NUMRATING,0);
            db.insert(TABLE_MOVIES, null, values);
        } else {
            return false;
        }
        c.close();
        db.close();
        return true;
    }

    /**
     * This gets the correct movie by also taking the year into account
     * as a variale when searching the databse
     * @param title
     * @param year
     * @return the name of the movie from that year
     */
    public String getTitle(String title, String year) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "
                + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_YEAR + " = '" + year + "';", null);
        c.moveToFirst();
        String movieTitle = c.getString(c.getColumnIndex(COLUMN_TITLE));
        c.close();
        db.close();
        return movieTitle;

    }

    /**
     * This gets the correct movie release year by also taking the name into account
     * as a variale when searching the databse
     * @param title
     * @param year
     * @return the year that the movie came out
     */
    public String getYear(String title, String year) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "
                + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_YEAR + " = '" + year + "';", null);
        c.moveToFirst();
        String movieYear = c.getString(c.getColumnIndex(COLUMN_YEAR));
        c.close();
        db.close();
        return movieYear;

    }

    /**
     * This method gets the rating by searching for the movie in the databse
     * using year and name as variables to search
     * @param title
     * @param year
     * @return the rating of the movie as a string
     */
    public String getRating(String title, String year) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "
                + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_YEAR + " = '" + year + "';",null);
        c.moveToFirst();
        String movieRating = c.getString(c.getColumnIndex(COLUMN_MRATING));
        c.close();
        db.close();
        return movieRating;

    }

    /**
     * This method gets the current # of ratings by searching for the
     * movie in the database by using year and name as variables to search
     * @param title
     * @param year
     * @return the num of ratings of the movie as a string
     */
    public int getCurrentNum(String title, String year) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "
                + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_YEAR + " = '" + year + "';",null);
        c.moveToFirst();
        int currentNum = c.getInt(c.getColumnIndex(COLUMN_NUMRATING));
        c.close();
        db.close();
        return currentNum;
    }

    /**
     * This method gets the user rating by searching for the movie in the databse
     * using year and name as variables to search
     * @param title
     * @param year
     * @return the  user rating of the movie as a string
     */
    public float getUserRating(String title, String year) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MOVIES + " WHERE "
                + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_YEAR + " = '" + year + "';",null);
        c.moveToFirst();
        float userRating = c.getFloat(c.getColumnIndex(COLUMN_URATING));
        c.close();
        db.close();
        return userRating;

    }

    /**
     * This method upadates the user rating by adding the new and
     * old rating together
     * @param title name of movie
     * @param year
     * @param currentRating
     * @param newRating
     */
    public void updateUserRating(String title, String year, float currentRating, float newRating){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URATING, currentRating + newRating);
        db.update(TABLE_MOVIES, values, COLUMN_TITLE + " = '" + title + "' AND "
                + COLUMN_YEAR + " = '" + year + "';", null);
        db.close();
    }

    /**
     * this adds  one to the number of ratings there are
     * @param title
     * @param year
     * @param currentNum
     */
    public void incrementNumRating(String title, String year, int currentNum) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMRATING, currentNum+1);
        db.update(TABLE_MOVIES, values, COLUMN_TITLE + " = '" + title + "' AND "
        + COLUMN_YEAR + " = '" + year + "';", null);
        db.close();
    }

}
