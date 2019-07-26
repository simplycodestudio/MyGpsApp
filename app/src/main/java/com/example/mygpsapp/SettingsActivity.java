package com.example.mygpsapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public TextView usernameTv;
    public TextView ipTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
        listenery();
    }

    public void init() {
        usernameTv = findViewById(R.id.UserNameTV);
        ipTv = findViewById(R.id.IpTv);
    }

    public void listenery() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = sharedPreferences.getString("nameKey","No name");
        usernameTv.setText(name);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String ip = prefs.getString("IpKey", "0");
        ipTv.setText(ip);


    }
}
