package com.example.mygpsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent goMain = new Intent(Splash.this, MainMenu.class);
                Splash.this.startActivity(goMain);
                // Animatoo.animateFade(Splash.this);
                Splash.this.finish();
            }
        },SPLASH_TIME_OUT);

    }
}
