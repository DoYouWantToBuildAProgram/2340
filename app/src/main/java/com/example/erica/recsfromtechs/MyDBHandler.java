package com.example.erica.recsfromtechs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

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
    public static final String COLUMN_ISADMIN = "isAdmin";


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
                + COLUMN_ISLOCKED + " INTEGER,"
                + COLUMN_ISADMIN + " INTEGER"
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
            values.put(COLUMN_ISADMIN, user.getIsAdmin());
            //inserts the new line to the table
            db.insert(TABLE_USERS, null, values);
        } else {
            return false;
        }
        c.close();
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
        int isBlocked;
        int isLocked;
        if (!c.isBeforeFirst()) {
            realPassword = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            isBlocked = c.getInt(c.getColumnIndex(COLUMN_ISBANNED));
            isLocked = c.getInt(c.getColumnIndex(COLUMN_ISLOCKED));
        } else {
            return false;
        }
        c.close();
        db.close();
        if (realPassword.equals(password) && isBlocked != 1 && isLocked != 1){
            return true;
        } else {
            return false;
        }

    }

    public LinkedList<User> listOfUsers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        c.moveToFirst();
        LinkedList<User> returnList = new LinkedList<>();
        while(!c.isAfterLast()) {
            String name = c.getString(c.getColumnIndex(COLUMN_NAME));
            String email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
            String major = c.getString(c.getColumnIndex(COLUMN_MAJOR));
            String username = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            String password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            int isBanned = c.getInt(c.getColumnIndex(COLUMN_ISBANNED));
            int isLocked = c.getInt(c.getColumnIndex(COLUMN_ISLOCKED));
            int isAdmin = c.getInt(c.getColumnIndex(COLUMN_ISADMIN));

            System.out.println(name+email+major+username+password+isBanned+isLocked+isAdmin);

            returnList.add(new User(name, email, major, username, password, isBanned, isLocked, isAdmin));
            c.moveToNext();
        }
        c.close();
        db.close();
        return returnList;
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

    public String getPassword(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
        c.close();
        db.close();
        return password;
    }

    public int getIsAdmin(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isAdmin = c.getInt(c.getColumnIndex(COLUMN_ISADMIN));
        c.close();
        db.close();
        return isAdmin;
    }

    public int getIsLocked(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isLocked = c.getInt(c.getColumnIndex(COLUMN_ISLOCKED));
        c.close();
        db.close();
        return isLocked;
    }

    public int getIsBlocked(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isBlocked = c.getInt(c.getColumnIndex(COLUMN_ISBANNED));
        c.close();
        db.close();
        return isBlocked;
    }

    public void setName(String username, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    public void setPassword(String username, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }

    public void setUsername(String username, String newUsername) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }

    public void setEmail(String username, String newEmail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    public void setMajor(String username, String newMajor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAJOR, newMajor);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }

    public void setLocked(String username, int num) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISLOCKED, num);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }

    public void setBlocked(String username, int num) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISBANNED, num);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }


}
