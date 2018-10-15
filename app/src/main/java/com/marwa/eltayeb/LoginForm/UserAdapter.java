package com.marwa.eltayeb.LoginForm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marwa on 10/15/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {


    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link User} object located at this position in the list
        final User currentUser = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.nameTextView);
        name.setText(currentUser.getName());

        TextView address = (TextView) listItemView.findViewById(R.id.addressTextView);
        address.setText(currentUser.getAddress());

        return listItemView;
    }

}
