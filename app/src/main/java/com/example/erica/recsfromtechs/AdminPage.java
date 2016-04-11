package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;


/**
 * The Activity for the admin page.
 * This is where you can block, unblock or lock a user
 * This class gets the user from the database and populates the
 * listview with it. The user can then click on a user and
 * select actions to preform.
 */
public class AdminPage extends AppCompatActivity {



    private String currentUser;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinkedList<User> allUsers = new LinkedList<>();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler = new MyDBHandler(this);



        allUsers = dbHandler.listOfUsers();


        final String[] names = new String[allUsers.size()];
        int[] isBlocked = new int[allUsers.size()];

        int index = 0;
        for (User i : allUsers) {
            names[index] = allUsers.get(index).getUsername();
            isBlocked[index] = allUsers.get(index).getIsLocked();
            index++;
        }

        UserList adapter = new
                UserList(AdminPage.this, names, isBlocked);
        ListView list = (ListView) findViewById(R.id.list3);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(AdminPage.this, "You Clicked at " + names[position], Toast.LENGTH_SHORT).show();

                currentUser = names[position];

                TextView selectedUserText = (TextView) findViewById(R.id.currentUser);
                selectedUserText.setText("Current User: " + currentUser);
                TextView isLocked = (TextView) findViewById(R.id.isLocked);
                if(dbHandler.getIsLocked(currentUser) == 0) {
                    isLocked.setText("Is Locked: NO");
                } else {
                    isLocked.setText("Is Locked: YES");
                }
                TextView isBlocked = (TextView) findViewById(R.id.isblocked);
                if(dbHandler.getIsBlocked(currentUser) == 0) {
                    isBlocked.setText("Is Blocked: NO");
                } else {
                    isBlocked.setText("Is Blocked: YES");
                }



            }
        });


    }

    /**
     * This method locks the user that is currently selected
     */
    public void lock() {
        dbHandler.setLocked(currentUser,1);
        updateTable();

    }

    /**
     * This method unlocks the user that is currently selected
     */
    public void unlock() {
        dbHandler.setLocked(currentUser, 0);
        updateTable();

    }

    /**
     * This method blocks the user that is currently selected
     */
    public void block() {
        dbHandler.setBlocked(currentUser);
        updateTable();
    }

    /**
     * This method goes back to
     */
    public void back() {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }

    /**
     * This method will update the table after the user make a
     * change (block, unblock, etc.) to reflect that change
     *
     */
    private void updateTable() {
        TextView isLocked = (TextView) findViewById(R.id.isLocked);
        if(dbHandler.getIsLocked(currentUser) == 0) {
            isLocked.setText("Is Locked: NO");
        } else {
            isLocked.setText("Is Locked: YES");
        }
        TextView isBlocked = (TextView) findViewById(R.id.isblocked);
        if(dbHandler.getIsBlocked(currentUser) == 0) {
            isBlocked.setText("Is Blocked: NO");
        } else {
            isBlocked.setText("Is Blocked: YES");
        }


    }

}
