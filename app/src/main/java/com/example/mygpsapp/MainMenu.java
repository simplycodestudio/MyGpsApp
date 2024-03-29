package com.example.mygpsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygpsapp.ServiceBookClasses.Dziennik_napraw;
import com.example.mygpsapp.dialog.StartDialog;

public class MainMenu extends AppCompatActivity implements StartDialog.StartDialogListener {

    public Button carFinderBtn;
    public Button serviceBookBtn;
    public TextView textViewUsername;
    public ImageView lockedDoorsImageview;
    public Button goToSettingsBtn;
    Typeface myfont;

    SharedPreferences sharedPreferences;
    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean initialDialogDisplayed = sharedPreferences.getBoolean("InitialDialog", false);

        if(!initialDialogDisplayed)
        {
            openDialog();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("InitialDialog", true);
            editor.apply();
        }
        else
        {
            //Toast.makeText(MainMenu.this,"Welcome user", Toast.LENGTH_LONG).show();

        }
        inity();
        listenery();



    }

    public void inity() {

        carFinderBtn = findViewById(R.id.carFinderBtn);
        serviceBookBtn = findViewById(R.id.serviceBookBtn);
        textViewUsername = findViewById(R.id.tvUserName);
        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/MYRIADPRO-SEMIBOLDIT.OTF");
        lockedDoorsImageview = findViewById(R.id.lockedDoorsImageView);
        lockedDoorsImageview.setImageResource(R.drawable.door_closed);
        textViewUsername.setTypeface(myfont);
        goToSettingsBtn = findViewById(R.id.settingsBtn);
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
        String name = sharedPreferences.getString("nameKey","No name");
        textViewUsername.setText(name + "'s");
        goToSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });
    }

    public void openDialog(){
        StartDialog startDialog = new StartDialog();
        startDialog.show(getSupportFragmentManager(), "start Dialog");
    }

    @Override
    public void applyTexts(String username, String password) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nameKey", username);

        textViewUsername.setText(username + "'s");
        editor.putString("IpKey", password);
        editor.apply();
        editor.commit();

    }

    public void goToSettings() {
        Intent goToSettingsIntent = new Intent(MainMenu.this, SettingsActivity.class);
        startActivity(goToSettingsIntent);
    }


}
