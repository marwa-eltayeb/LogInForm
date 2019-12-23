package com.marwa.eltayeb.LoginForm;

/**
 * Created by Marwa on 10/1/2018.
 */

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marwa on 1/21/2018.
 */

public class PostUtils {

    /**
     * Create a private constructor (No object is permitted).
     */
    private PostUtils() {
    }

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = PostUtils.class.getSimpleName();

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        // Store our URL
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL.
     */
    private static void makeHttpRequest(URL url) throws IOException {
        // It seems like open up a browser and we are gonna use it to fetch the contents of a URl
        HttpURLConnection urlConnection = null;

        try {
            //Set up url connection
            //To make sure that it is the right type, we cast it to HttpURLConnection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem posting data.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * Sending Data to the database.
     */
    public static void sendData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        try {
           makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
    }
}