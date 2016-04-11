package com.example.erica.recsfromtechs;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] name;
    private final int[] isBlocked;

    public UserList(Activity context,
                    String[] name, int[] isBlocked) {
        super(context, R.layout.list_single, name);
        this.context = context;
        this.name = name;
        this.isBlocked = isBlocked;


    }
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