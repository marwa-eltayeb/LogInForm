package com.marwa.eltayeb.LoginForm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Marwa on 10/7/2018.
 */

public class Utils {


    private Context context;

    //Shared Preferences Database Name
    private static final String PREFERENCES = "preferences";

    public Utils(Context context) {
        this.context = context;
    }

    public void saveUserInfo() {
        //Check if it first time to insert or not
        if (isUserLoggedIn()) {

            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);

            //Change Key value to True
            setUserLoggedIn();
        }
    }



    /**
     * Make key Value in SharedPreferences is True
     */
    public void setUserLoggedIn() {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("key", true);
        editor.apply();
    }

    /**
     * return true if it first time to insert
     */
    public boolean isUserLoggedIn() {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("key", false); //False is the default value
    }

}
