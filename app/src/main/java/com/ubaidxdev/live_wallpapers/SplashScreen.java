package com.ubaidxdev.live_wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // Splash screen timeout in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);
        TextView appNameTextView = findViewById(R.id.appNameTextView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               finish();
               startActivity(new Intent(SplashScreen.this, HomeScreen.class));
            }
        }, SPLASH_TIMEOUT);
    }
}
