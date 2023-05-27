package com.example.smartplate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //TextView splashTextView = findViewById(R.id.splash_text);
        //splashTextView.setText("SMARTPLATE SRILANKA");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the InputActivity
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);
    }
}
