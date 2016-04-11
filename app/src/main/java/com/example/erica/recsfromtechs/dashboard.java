package com.example.erica.recsfromtechs;

import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;

/**
 * This is the activity for the Dashboard
 *
 * It essentially functions as a menu for the whole
 * app and lets you choose from a variety of
 * options
 */
public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

    }

    /**
     * Logs the user out of the dashboard and brings them back to the welcome page
     * @param view The current layout with all the Android widgets
     */
    public void logout(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    /**
     * Allows the user to change their account information, such as name, email, and major
     * @param view The current layout with all the Android widgets
     */
    public void goToProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    /**
     * Redirects user to the movie search page
     * @param view The current layout with all the Android widgets
     */
    public void movieSearch(View view) {
        Intent intent = new Intent(this,searchScreen.class);
        startActivity(intent);

    }

    /**
     * Redirects user to the page that displays box office movies
     * @param view The current layout with all the Android widgets
     */
    public void getBoxOffice(View view) {
        Intent intent = new Intent(this, BoxOffice.class);
        startActivity(intent);
    }

    /**
     * Redirects the user to the page that displys there recommended
     * movies based on their major.
     * @param view The current layout with all the Android widgets
     */
    public void recommendation(View view) {
        Intent intent = new Intent(this, Recommendations.class);
        startActivity(intent);
    }

    /**
     * Redirects the user to the page that displays DVD releases
     * @param view The current layout with all the Android widgets
     */
    public void getDVD(View view) {
        Intent intent = new Intent(this, DVD.class);
        startActivity(intent);
    }

}
