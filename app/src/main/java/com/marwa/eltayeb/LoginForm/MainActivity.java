package com.marwa.eltayeb.LoginForm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;

import static com.marwa.eltayeb.LoginForm.LoginActivity.PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    Uri.Builder uriBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        //String email = intent.getStringExtra("email2");

        SharedPreferences sPrefs = getSharedPreferences(PREFS_NAME, 0);
        String receiveEmail = sPrefs.getString("storeEmail", "no mail");

        Uri baseUri = Uri.parse("http://192.168.1.5/learn/delete.php?");
        uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("email", receiveEmail);

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
            // Sign Out
            case R.id.sign_out:
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.apply();
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;

            // Delete Account
            case R.id.delete_account:
                // Kick off an {@link AsyncTask} to perform the network request
                LogInAsyncTask task = new LogInAsyncTask();
                task.execute();
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor2 = prefs.edit();
                editor2.remove("logged");
                editor2.apply();
                MainActivity.this.finish();
                Intent intent2 = new Intent(this, SignUpActivity.class);
                startActivity(intent2);
                break;
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
