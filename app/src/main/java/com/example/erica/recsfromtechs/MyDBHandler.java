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

    //Please use these names for the variables, don't hardcode them in case we need to change something
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_ISBANNED = "isBanned";
    public static final String COLUMN_ISLOCKED = "isLocked";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //This creates all of the columns in our table followed by their data type with a boolean represented as an integer thats either 1 or 0
        String query = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_MAJOR + " TEXT, "
                + COLUMN_ISBANNED + " INTEGER, "
                + COLUMN_ISLOCKED + " INTEGER"
                + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Should never really call this, it deletes the whole table and makes a new one
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Add a new row to the database (used when registering a user)
    public boolean addUser(User user) {
        //Gets a user object and puts all the data in its respective column
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + user.getUsername() + "';", null);
        c.moveToFirst();
        if (c.isBeforeFirst()) {
            values.put(COLUMN_USERNAME, user.getUsername());
            values.put(COLUMN_PASSWORD, user.getPassword());
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_MAJOR, user.getMajor());
            values.put(COLUMN_ISBANNED, user.getIsBanned());
            values.put(COLUMN_ISLOCKED, user.getIsLocked());
            //inserts the new line to the table
            db.insert(TABLE_USERS, null, values);
        } else {
            return false;
        }
        db.close();
        c.close();
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        //This call is what creates a smaller table, so right now this creates a smaller table with all the usernames that match what was inputted
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String realPassword = null;
        if (!c.isBeforeFirst()) {
            realPassword = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
        } else {
            return false;
        }
        c.close();
        db.close();
        if (realPassword.equals(password)) {
            return true;
        } else {
            return false;
        }

    }

    public String getName(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex(COLUMN_NAME));
        c.close();
        db.close();
        return name;
    }

    public String getEmail(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
        c.close();
        db.close();
        return email;
    }

    public String getMajor(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String major = c.getString(c.getColumnIndex(COLUMN_MAJOR));
        c.close();
        db.close();
        return major;
    }

    public void setName(String username, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
//        db.beginTransaction();
//        String updateName = "UPDATE " + TABLE_USERS + " SET " + COLUMN_NAME + "= '" + newName + "' WHERE " + COLUMN_USERNAME + "='" + username + "';";
//        SQLiteStatement statement = db.compileStatement(updateName);
//        statement.executeUpdateDelete();
//        db.endTransaction();
        db.close();
    }

    public void setEmail(String username, String newEmail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
//        db.beginTransaction();
//        String updateEmail = "UPDATE " + TABLE_USERS + " SET " + COLUMN_EMAIL + "= '" + newEmail + "' WHERE " + COLUMN_USERNAME + "='" + username + "';";
//        SQLiteStatement statement = db.compileStatement(updateEmail);
//        statement.executeUpdateDelete();
//        db.endTransaction();
//        db.close();
    }

    public void setMajor(String username, String newMajor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAJOR, newMajor);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);

//        db.beginTransaction();
//        String updateMajor = "UPDATE " + TABLE_USERS + " SET " + COLUMN_MAJOR + "= '" + newMajor + "' WHERE " + COLUMN_USERNAME + "='" + username + "';";
//        SQLiteStatement statement = db.compileStatement(updateMajor);
//        statement.executeUpdateDelete();
//        db.endTransaction();
        db.close();
    }


}
