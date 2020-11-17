package com.example.keepsafe_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private boolean hasPIN;
    private PINStorage mPinStorage;
    private Intent loginScreenIntent;
    private Intent listScreenIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MAIN ACTIVITY STARTED");
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                                                    //loading screen

        loginScreenIntent = new Intent(MainActivity.this, LoginActivity.class);      //intent to start login
        listScreenIntent = new Intent(MainActivity.this, ListActivity.class);        //intent to start list
        mPinStorage = new PINStorage(this);
        hasPIN = !mPinStorage.isEmpty();

        //if there is a PIN, then on start up --> login
        if (hasPIN) {
            Log.d(TAG, "MAIN: LOGIN ACTIVITY STARTED");
            startActivity(loginScreenIntent);
        }
        //else --> list
        else {
            Log.d(TAG, "MAIN: LIST ACTIVITY STARTED");
            startActivity(listScreenIntent);
        }
    }
}
