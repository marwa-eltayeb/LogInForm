package com.marwa.eltayeb.LoginForm;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa on 10/15/2018.
 */

public class QueryUtils {
    /**
     * Create a private constructor (No object is permitted).
     */
    private QueryUtils() {
    }

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Query the news dataSet and return a list of {@link User} objects.
     */
    public static List<User> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Return the list of {@link User}
        return extractFeaturesFromJson(jsonResponse);
    }

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
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        // It seems like open up a browser and we are gonna use it to fetch the contents of a URl
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            //Set up url connection
            //To make sure that it is the right type, we cast it to HttpURLConnection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news stories JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link User} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<User> extractFeaturesFromJson(String UserJSON) {

        if (TextUtils.isEmpty(UserJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding users to
        List<User> users = new ArrayList<>();
        Log.v("DATA", "DATA" + UserJSON);

        try {

            // Create a base ArrayObject
            JSONArray baseJsonResponse = new JSONArray(UserJSON);

            for (int i = 0; i < baseJsonResponse.length(); i++) {
                // String for user's name, address
                String name = "";
                String address = "";

                // Get a single user at position i within the list of users
                JSONObject currentUser = baseJsonResponse.getJSONObject(i);

                try {
                    name = currentUser.getString("username");
                } catch (Exception e) {
                    Log.v(LOG_TAG, "No UserName");
                }

                try {
                    address = currentUser.getString("address");
                } catch (Exception e) {
                    Log.v(LOG_TAG, "No Address");
                }

                // Create a new {@link User} object.
                User usersData = new User(name, address);
                // Add new user to the list of users.
                users.add(usersData);
            }

        } catch (JSONException e) {
            // A log message is printed with the exception.
            Log.e(LOG_TAG, "Problem parsing the news stories JSON results", e);
        }
        // Returns the list of users.
        return users;
    }
}
