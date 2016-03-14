package com.example.erica.recsfromtechs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Courtney on 3/14/16.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_ISBANNED = "isBanned";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_MAJOR + " TEXT, "
                + COLUMN_ISBANNED + " INTEGER"
                + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Add a new row to the database (used when registering a user)
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MAJOR, user.getMajor());
        values.put(COLUMN_ISBANNED, user.getIsBanned());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String realPassword = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
        c.close();
        db.close();
        if (realPassword.equals(password)) {
            return true;
        } else {
            return false;
        }

    }


}
