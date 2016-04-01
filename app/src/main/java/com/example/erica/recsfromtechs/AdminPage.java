package com.example.erica.recsfromtechs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class AdminPage extends AppCompatActivity {


    LinkedList<User> allUsers = new LinkedList<>();

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        //get all the user from the database
        //put them in allUsers Linkedlist

        //test
        allUsers.add(new User ("sad","happy","other","another","yes",1,1));


        final String[] names = new String[allUsers.size()];
        int[] isBlocked = new int[allUsers.size()];

        int index = 0;
        for (User i : allUsers) {
            names[index]=allUsers.get(index).getName();
            isBlocked[index] = allUsers.get(index).getIsLocked();
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

                currentUser = allUsers.get(0); //get the user from the database;

                TextView selctedUserText = (TextView) findViewById(R.id.currentUser);
                selctedUserText.setText("Current User: " + currentUser.getName());


            }
        });


    }
    //Needs to implemented with database
    public void lock(View view) {
        System.out.println("lock");

    }
    public void unlock(View view) {
        System.out.println("unlock");

    }
    public void block(View view) {
        System.out.println("block");
    }
    public void back(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

}
