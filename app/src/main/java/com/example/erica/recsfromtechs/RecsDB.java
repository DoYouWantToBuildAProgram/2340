package com.example.erica.recsfromtechs;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.LinkedList;

/**
 * Created by Erica on 4/1/2016.
 */
public class RecsDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recs.db";
    private static final String TABLE_RECS = "recs";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_RATING = "rating";

    public RecsDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_RECS + " ("
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_YEAR + " TEXT, "
                + COLUMN_MAJOR + " TEXT, "
                + COLUMN_RATING + " DOUBLE"
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_RECS);
        onCreate(db);
    }

    public void addRec(Recs rec){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_RECS+";",null);
        values.put(COLUMN_MAJOR, rec.getMajor());
        values.put(COLUMN_RATING, rec.getRating());
        values.put(COLUMN_TITLE, rec.getTitle());
        values.put(COLUMN_YEAR, rec.getYear());
        db.insert(TABLE_RECS, null, values);
    }

    public LinkedList<Recs> listOfRecs(String major) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_RECS + " WHERE " + COLUMN_MAJOR + " = '" + major + "';",null);
        c.moveToFirst();
        LinkedList<Recs> recs = new LinkedList<>();
        while(!c.isAfterLast()) {
            String title = c.getString(c.getColumnIndex(COLUMN_TITLE));
            String year = c.getString(c.getColumnIndex(COLUMN_YEAR));
            float rating = c.getFloat(c.getColumnIndex(COLUMN_RATING));
            String userMajor = c.getString(c.getColumnIndex(COLUMN_MAJOR));

            recs.add(new Recs(userMajor, rating, title, year));
            c.moveToNext();
        }
        c.close();
        db.close();
        return recs;
    }




}