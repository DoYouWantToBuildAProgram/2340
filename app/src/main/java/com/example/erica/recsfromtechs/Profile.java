package com.example.erica.recsfromtechs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    SharedPreferences userInfo;
    SharedPreferences.Editor editUserInfo;
    public myApplication appState;
    SharedPreferences currentUser;
    SharedPreferences.Editor editCurrentUser;
    Spinner spinner;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        userInfo = getSharedPreferences("AnotherPref", MODE_PRIVATE);
//        editUserInfo = userInfo.edit();
        currentUser = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        editCurrentUser = currentUser.edit();
        dbHandler = new MyDBHandler(this, null, null, 1);
        appState = ((myApplication) this.getApplicationContext());
        Intent oldIntent = getIntent();
        String user = oldIntent.getStringExtra("user");
        //String userName = oldIntent.getStringExtra("userName");
        //String userEmail = oldIntent.getStringExtra("userEmail");
        //String userMajor = oldIntent.getStringExtra("userMajor");
       // String userName = userInfo.getString(user+"name", null);
        User myUser = appState.getCurrentUser();
        TextView nameText = (TextView)findViewById(R.id.name);
        nameText.setText(dbHandler.getName(currentUser.getString("username",null)));
        //String userEmail = userInfo.getString(user+"email", null);
        TextView emailText = (TextView)findViewById(R.id.email);
        emailText.setText(dbHandler.getEmail(currentUser.getString("username",null)));
       // String userMajor = userInfo.getString(user+"major", null);
        TextView majorText = (TextView)findViewById(R.id.major);
        majorText.setText(dbHandler.getMajor(currentUser.getString("username",null)));
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.majors,R.layout.spinner_item);
        spinner.setAdapter(adapter);
        appState = ((myApplication) this.getApplicationContext());
        spinner.setOnItemSelectedListener(this);

    }

    /**
     * Allows the user to change their name
     * @param view The current layout with all the Android widgets
     */
    public void editName(View view) {
        TextView nameText = (TextView)findViewById(R.id.name);
        EditText changedName = (EditText)findViewById(R.id.newName);
        nameText.setText(changedName.getText().toString());
        dbHandler.setName(currentUser.getString("username",null), changedName.getText().toString());
//        editUserInfo.putString("name", changedName.getText().toString());
//        editUserInfo.commit();
//        User myUser = appState.getCurrentUser();
//        appState.setCurrentUser(new User(changedName.toString(), myUser.getEmail(), myUser.getMajor(),myUser.getUsername(),myUser.getPassword(), myUser.getIsBanned(), myUser.getIsLocked()));
    }

    /**
     * Allows the user to change their email
     * @param view The current layout with all the Android widgets
     */
    public void editEmail(View view) {
        TextView emailText = (TextView)findViewById(R.id.email);
        EditText changedEmail = (EditText)findViewById(R.id.newEmail);
        emailText.setText(changedEmail.getText().toString());
        dbHandler.setEmail(currentUser.getString("username",null), changedEmail.getText().toString());
//        editUserInfo.putString("email", changedEmail.getText().toString());
//        editUserInfo.commit();
//        User myUser = appState.getCurrentUser();
//        appState.setCurrentUser(new User(myUser.getName(), changedEmail.toString(), myUser.getMajor(), myUser.getPassword(),myUser.getUsername(),myUser.getIsBanned(),myUser.getIsLocked()));
    }

    /**
     * Allows the user to change their major
     * @param view The current layout with all the Android widgets
     */

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.v("profile", "I think that the spinner was clicked");
        TextView majorText = (TextView) findViewById(R.id.major);
        User myUser = appState.getCurrentUser();
        String changedMajor = adapterView.getItemAtPosition(i).toString();
        if(changedMajor.equals("Select your Major")) {
            majorText.setText(dbHandler.getMajor(currentUser.getString("username",null)));
        } else {
            majorText.setText(changedMajor);
            dbHandler.setMajor(currentUser.getString("username", null), changedMajor);
//            editUserInfo.putString("major", changedMajor);
//            editUserInfo.commit();
//
//            appState.setCurrentUser(new User(myUser.getName(), myUser.getEmail(), changedMajor));
        }
        //Toast.makeText(this,"You Selected: " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        TextView majorText = (TextView) findViewById(R.id.major);
        //String changedMajor = adapterView.getItemAtPosition(i).toString();
        majorText.setText(appState.getCurrentUser().getMajor());
    }

    /**
     * Allows the user to go back to the dashboard
     * @param view The current layout with all the Android widgets
     */
    public void backToDashboard(View view) {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }


}
