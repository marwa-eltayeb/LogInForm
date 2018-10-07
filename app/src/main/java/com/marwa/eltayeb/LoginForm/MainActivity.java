package com.marwa.eltayeb.LoginForm;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Uri.Builder uriBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email2");

        Uri baseUri = Uri.parse("http://192.168.1.5/learn/delete.php?");
        uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("email", email);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Delete Account
            case R.id.delete_account:
                // Kick off an {@link AsyncTask} to perform the network request
                LogInAsyncTask task = new LogInAsyncTask();
                task.execute();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread
     */
    private class LogInAsyncTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... params) {
            try{
                new URL(uriBuilder.toString()).openStream();
            } catch (Exception e) {
                Log.v("Error","Error connecting with Api link");
            }
            return null;
        }

    }

}
