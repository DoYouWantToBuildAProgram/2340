package com.example.erica.recsfromtechs;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] name;
    private final int[] isBlocked;

    /**
     * Constructs a list of users
     * @param context The activity context
     * @param name The names of the users
     * @param isBlocked The blocked status of the users
     */
    public UserList(Activity context,
                    String[] name, int[] isBlocked) {
        super(context, R.layout.list_single, name);
        this.context = context;
        this.name = name;
        this.isBlocked = isBlocked;


    }

    /**
     * Creates a list view of all the users in the database
     * @param position the position of the user clicked
     * @param view the activity
     * @param parent view group
     * @return the view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txt);
        TextView txtIsBlocked = (TextView) rowView.findViewById(R.id.txt2);

        txtName.setText(name[position]);

        String blockedString = "";
        if (isBlocked[position] == 0) {
            blockedString = "Is not Blocked";
        } else {
            blockedString = "Is not blocked";
        }

        txtIsBlocked.setText(blockedString);

        return rowView;
    }
}