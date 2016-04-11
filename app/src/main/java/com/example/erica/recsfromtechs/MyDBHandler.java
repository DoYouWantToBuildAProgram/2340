package com.example.erica.recsfromtechs;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.LinkedList;

/**
 * This class creates the database used for logging in and editing a profile.
 * It contains all registered users and their info
 */
public class MyDBHandler extends SQLiteOpenHelper {

    //Please use these names for the variables, don't hardcode them in case we need to change something
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MAJOR = "major";
    private static final String COLUMN_ISBANNED = "isBanned";
    private static final String COLUMN_ISLOCKED = "isLocked";
    private static final String COLUMN_ISADMIN = "isAdmin";

    /**
     * constructor for the database
     * @param context The context we're using
     * @param factory the factor we're using
     */
    public MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //This creates all of the columns in our table followed by their data
        // type with a boolean represented as an integer that is either 1 or 0
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

    /**
     * Add a new row to the database (used when registering a user)
     * @param user The user info to be added to the table
     * @return boolean value of whether or not the new user has been added
     */
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

    /**
     * Checks that the username and password entered when logging in is correct
     * @param username The username that the person typed in
     * @param password The password that the person typed in
     * @return boolean value of whether or not the user put in the right info.
     *         If they put in the correct info it will return true.
     *         If they put in the wrong info, or their account is blocked or locked then it will
     *         return false.
     */
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        //This call is what creates a smaller table, so right now this creates a smaller
        // table with all of the usernames that match what was inputted
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        //Cursor d = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        //d.moveToFirst();
        c.moveToFirst();
        String realPassword;
        int isBlocked;
        int isLocked;
        if (!c.isBeforeFirst()) {
            realPassword = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            isBlocked = c.getInt(c.getColumnIndex(COLUMN_ISBANNED));
            isLocked = c.getInt(c.getColumnIndex(COLUMN_ISLOCKED));
        } else {
            return false;
        }
        //d.close();
        c.close();
        db.close();
        return realPassword.equals(password) && isBlocked != 1 && isLocked != 1;

    }

    /**
     * Provides a list of all the registered users
     * @return A list of user objects that are registered
     */
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

    /**
     * gives the name registered under some username
     * @param username The username we want the name of
     * @return String representing the name of the user
     */
    public String getName(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex(COLUMN_NAME));
        c.close();
        db.close();
        return name;
    }

    /**
     * gives the email registered under some username
     * @param username The username we want the email of
     * @return String representing the email of the user
     */
    public String getEmail(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
        c.close();
        db.close();
        return email;
    }

    /**
     * gives the major registered under some username
     * @param username The username we want the major of
     * @return String representing the major of the user
     */
    public String getMajor(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String major = c.getString(c.getColumnIndex(COLUMN_MAJOR));
        c.close();
        db.close();
        return major;
    }

    /**
     * gives the password registered under some username
     * @param username The username we want the password of
     * @return String representing the password of the user
     */
    public String getPassword(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        String password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
        c.close();
        db.close();
        return password;
    }

    /**
     * gives the admin status registered under some username
     * @param username The username we want the admin status of
     * @return int representing the admin status of the user (either 1 for true or 0 for false
     */
    public int getIsAdmin(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isAdmin = c.getInt(c.getColumnIndex(COLUMN_ISADMIN));
        c.close();
        db.close();
        return isAdmin;
    }

    /**
     * gives the locked status registered under some username
     * @param username The username we want the locked status of
     * @return int representing the locked status of the user (either 1 for true or 0 for false
     */
    public int getIsLocked(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isLocked = c.getInt(c.getColumnIndex(COLUMN_ISLOCKED));
        c.close();
        db.close();
        return isLocked;
    }

    /**
     * gives the block status registered under some username
     * @param username The username we want the block status of
     * @return int representing the block status of the user (either 1 for true or 0 for false
     */
    public int getIsBlocked(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        int isBlocked = c.getInt(c.getColumnIndex(COLUMN_ISBANNED));
        c.close();
        db.close();
        return isBlocked;
    }

    /**
     * changes the name of the user that uses a certain username
     * @param username The username we want to change the name of
     * @param newName the new name the user wants to use
     */
    public void setName(String username, String newName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    /**
     * changes the password of the user that uses a certain username
     * @param username The username we want to change the password of
     * @param newPassword the new password the user wants to use
     */
    public void setPassword(String username, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    /**
     * changes the username of the user that uses a certain username
     * @param username The username we want to change the username of
     * @param newUsername the new username the user wants to use
     */
    public void setUsername(String username, String newUsername) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newUsername);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    /**
     * changes the email of the user that uses a certain username
     * @param username The username we want to change the email of
     * @param newEmail the new email the user wants to use
     */
    public void setEmail(String username, String newEmail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, newEmail);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }

    /**
     * changes the major of the user that uses a certain username
     * @param username The username we want to change the major of
     * @param newMajor the new major the user wants to use
     */
    public void setMajor(String username, String newMajor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAJOR, newMajor);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }

    /**
     * changes the lock status of the user that uses a certain username
     * @param username The username we want to change the lock status of
     * @param num the changed lock status (1 for locked, 0 for unlocked)
     */
    public void setLocked(String username, int num) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISLOCKED, num);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }
    /**
     * deletes user from database
     * @param username The username we want to change the lock status of
     */
    public void deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USERNAME + "= '" + username + "';", null);
        db.close();
    }
    /**
     * Returns boolean of whether the user exists
     * @param username The username we want to change the lock status of
     * @return boolean of whether user exists
     */
    public boolean containsUser(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        if(c.isBeforeFirst()){
            return false;
        } else {
            return true;
        }

    }
    /**
     * Makes sure the user exists in system
     * @param username The username we want to change the lock status of
     * @return boolean of whether username is okay
     */
    public boolean authenticateUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = '" + username + "';", null);
        c.moveToFirst();
        if(c.isBeforeFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * changes the block status of the user that uses a certain username
     * @param username The username we want to change the block status of
     */
    public void setBlocked(String username) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISBANNED, 1);
        db.update(TABLE_USERS, values, COLUMN_USERNAME + "= '" + username + "';",null);
        db.close();
    }


}




