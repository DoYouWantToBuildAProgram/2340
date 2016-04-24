package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This is the Activity for the edit profile page
 * This is where the the user can change
 * information about his profile like major, password,
 * username etc.
 */
public class EditProfile extends AppCompatActivity {
    private SharedPreferences currentUser;
    private SharedPreferences.Editor editCurrentUser;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        editCurrentUser.apply();
        dbHandler = new MyDBHandler(this);
        String userName = dbHandler.getName(currentUser.getString("username", null));
        TextView nameText = (TextView) findViewById(R.id.name);
        //nameText.setText(userName);
        //String userEmail = dbHandler.getEmail(currentUser.getString("username", null));
        //TextView emailText = (TextView) findViewById(R.id.email);
        //emailText.setText(userEmail);
        //String userMajor = dbHandler.getMajor(currentUser.getString("username", null));
        //TextView majorText = (TextView) findViewById(R.id.major);
        //majorText.setText(userMajor);
        //TextView usernameText = (TextView) findViewById(R.id.username);
        //usernameText.setText(currentUser.getString("username", null));
        //String password = dbHandler.getPassword(currentUser.getString("username", null));
        //TextView passwordText = (TextView) findViewById(R.id.password);
        //passwordText.setText(password);

    }

    /**
     * Allows the user to change their name
     * takes the new name in the text box and changes it on
     * the activity and in the database
     *
     * @param view The current layout with all the Android widgets
     */
    public void editName(View view) {
        TextView nameText = (TextView) findViewById(R.id.name);
        EditText changedName = (EditText) findViewById(R.id.newName);

        dbHandler.setName(currentUser.getString("username", null), changedName.getText().toString());
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    /**
     * Allows the user to change their name
     * takes the new username in the text box and changes it on
     * the activity and in the database
     *
     * @param view The current layout with all the Android widgets
     */
    public void editUsername(View view) {
        //TextView usernameText = (TextView) findViewById(R.id.username);
        EditText changedUsername = (EditText) findViewById(R.id.newUsername);
        boolean x = dbHandler.authenticateUsername(changedUsername.getText().toString());
        if (x) {
           // usernameText.setText(changedUsername.getText().toString());
            dbHandler.setUsername(currentUser.getString("username", null), changedUsername.getText().toString());
            editCurrentUser.putString("username", changedUsername.getText().toString());
            editCurrentUser.commit();
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence newText = "This username is already taken.";
            Toast toast = Toast.makeText(context, newText, duration);
            toast.show();
        }
    }

    /**
     * This method gets the new password from TextView and sets it to the
     * password saved in the database for this User
     *
     * @param view the current screen
     */
    public void editPassword(View view) {
        //TextView passwordText = (TextView) findViewById(R.id.password);
        EditText changedPassword = (EditText) findViewById(R.id.newPassword);
        String newPassword = changedPassword.getText().toString();
        if (newPassword.length() >= 8) {
            //passwordText.setText(changedPassword.getText().toString());
            dbHandler.setPassword(currentUser.getString("username", null), newPassword);
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence newText = "Password must be at least 8 characters long";
            Toast toast = Toast.makeText(context, newText, duration);
            toast.show();
        }
    }

    /**
     * Allows the user to change their email
     *
     * @param view The current layout with all the Android widgets
     */
    public void editEmail(View view) {
        //TextView emailText = (TextView) findViewById(R.id.email);
        EditText changedEmail = (EditText) findViewById(R.id.newEmail);
        String stringEmail = changedEmail.getText().toString();
        if (stringEmail.contains("@")) {
            //emailText.setText(changedEmail.getText().toString());
            dbHandler.setEmail(currentUser.getString("username", null), stringEmail);
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence newText = "Invalid Email";
            Toast toast = Toast.makeText(context, newText, duration);
            toast.show();
        }

    }

    /**
     * Allows the user to change their major
     *
     * @param view The current layout with all the Android widgets
     */
    public void editMajor(View view) {
        //TextView majorText = (TextView) findViewById(R.id.major);
        EditText changedMajor = (EditText) findViewById(R.id.newMajor);
        String newMajor = changedMajor.getText().toString();
        if (newMajor.equals("Computer Science") || newMajor.equals("Science") ||
                newMajor.equals("Engineering") || newMajor.equals("Business")
                || newMajor.equals("Other")) {
            //majorText.setText(changedMajor.getText().toString());
            dbHandler.setMajor(currentUser.getString("username", null), newMajor);
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        } else {
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence newText = "Invalid Major";
            Toast toast = Toast.makeText(context, newText, duration);
            toast.show();
        }
    }

    /**
     * Allows the user to go back to the Dashboard
     *
     * @param view The current layout with all the Android widgets
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }
    /**
     * TEST METHODS ONLY
     */

    /**
     * Change password for testing
     *
     * @param username    the username of the user we want to see
     * @param newPassword the new password
     */
    public void changePassword(String username, String newPassword) {
        if (newPassword.length() >= 8) {
            dbHandler.setPassword(username, newPassword);
        }
    }

    /**
     * Change username for testing
     *
     * @param username    the username of the user we want to see
     * @param newUsername the new username
     */
    public void changeUsername(String username, String newUsername) {
        boolean x = dbHandler.authenticateUsername(newUsername);
        if (x) {
            dbHandler.setUsername(username, newUsername);
        }
    }

    /**
     * Change email for testing
     *
     * @param username the username of the user we want to see
     * @param newEmail the new email
     */
    public void changeEmail(String username, String newEmail) {
        if (newEmail.contains("@")) {
            dbHandler.setEmail(username, newEmail);
        }
    }

    /**
     * Change major for testing
     *
     * @param username the username of the user we want to see
     * @param newMajor the new major
     */
    public void changeMajor(String username, String newMajor) {
        if (newMajor.equals("Computer Science") || newMajor.equals("Science") ||
                newMajor.equals("Engineering") || newMajor.equals("Business")
                || newMajor.equals("Other")) {
            dbHandler.setMajor(username, newMajor);
        }
    }

    /**
     * Gets the database for testing
     *
     * @return the database
     */
    public MyDBHandler getDb() {
        return this.dbHandler;
    }

}
