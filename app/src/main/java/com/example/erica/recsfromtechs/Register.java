package com.example.erica.recsfromtechs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    private MyDBHandler dbHandler;
    private final int minPassLen = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Spinner spinner;

        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler = new MyDBHandler(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.majors, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    /**
     * Creates a user object based off of the information provided from the user such as username,
     * password, name, major, and email.
     * @param view The current layout with all the Android widgets
     */
    public void addUser(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        EditText emailText = (EditText) findViewById(R.id.email);
        EditText nameText = (EditText) findViewById(R.id.name);
        EditText adminPassText = (EditText) findViewById(R.id.adminPassword);
        User currentUser;
        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        String text = mySpinner.getSelectedItem().toString();
        if (adminPassText.getText().toString().equals("CS2340")) {
                    currentUser = new User(nameText.getText().toString(),
                    emailText.getText().toString(),text,
                    usernameText.getText().toString(),passwordText.getText().toString(), 0, 0,1);
        } else{
                    currentUser = new User(nameText.getText().toString(),
                    emailText.getText().toString(), text,
                    usernameText.getText().toString(), passwordText.getText().toString(), 0, 0,0);
        }
        if (passwordText.getText().toString().length() >= minPassLen && (emailText.getText().toString().contains("@"))) {
            boolean test = dbHandler.addUser(currentUser);
            if (!test) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence newText = "This username is already taken.";
                Toast toast = Toast.makeText(context, newText, duration);
                toast.show();
            } else {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        } else {
            if (passwordText.getText().toString().length() < minPassLen) {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence newText = "Password must be at least 8 characters";
                Toast toast = Toast.makeText(context, newText, duration);
                toast.show();
            }else if (!(emailText.getText().toString().contains("@"))){
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence newText = "Invalid Email";
                Toast toast = Toast.makeText(context, newText, duration);
                toast.show();
            }
        }
    }

    /**
     * Allows the user to cancel their registration and takes them back to the welcome screen
     * @param view The current layout with all the Android widgets
     */
    public void cancel(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Gets the database for testing
     * @return the database
     */
    public MyDBHandler getDb(){
        return this.dbHandler;
    }


}
