package com.example.mygpsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mygpsapp.ServiceBookClasses.Dziennik_napraw;

public class MainMenu extends AppCompatActivity {

    public Button carFinderBtn;
    public Button serviceBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        inity();
        listenery();
    }

    public void inity() {

        carFinderBtn = findViewById(R.id.carFinderBtn);
        serviceBookBtn = findViewById(R.id.serviceBookBtn);
    }

    public void listenery() {

        carFinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGmaps = new Intent(MainMenu.this, MapsActivity.class);
                startActivity(goToGmaps);
            }
        });
        serviceBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToServiceBook = new Intent(MainMenu.this, Dziennik_napraw.class);
                startActivity(goToServiceBook);
            }
        });
    }
}
