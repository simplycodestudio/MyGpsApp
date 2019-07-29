package com.example.mygpsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygpsapp.ServiceBookClasses.Dziennik_napraw;
import com.example.mygpsapp.dialog.StartDialog;
import com.jgabrielfreitas.core.BlurImageView;

public class MainMenu extends AppCompatActivity implements StartDialog.StartDialogListener {

    public Button carFinderBtn;
    public Button serviceBookBtn;
    public TextView textViewUsername;
    public ImageView lockedDoorsImageview;
    public Button goToSettingsBtn;
    public TextView voltageField;
    public TextView carfindertv;
    public TextView servicebookTv;
    public TextView oilTextView;
    public TextView doorLockTextView;
    public TextView gasolineTv;
    Typeface userNameFont;
    Typeface myriad;
    private static Context mContext;
    SharedPreferences sharedPreferences;
    Boolean firstTime;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mContext = getApplicationContext();
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
        getDataRunnable.run();


    }
    public static Context getAppContext() {
        return mContext;
    }

    public void inity() {

        carFinderBtn = findViewById(R.id.carFinderBtn);
        serviceBookBtn = findViewById(R.id.serviceBookBtn);
        textViewUsername = findViewById(R.id.tvUserName);
        carfindertv = findViewById(R.id.CarfinderTv);
        servicebookTv = findViewById(R.id.servicebookTv);
        oilTextView = findViewById(R.id.OilTv);
        doorLockTextView = findViewById(R.id.doorLockTv);
        gasolineTv = findViewById(R.id.GasolineTv);
        userNameFont = Typeface.createFromAsset(this.getAssets(), "fonts/MYRIADPRO-BOLD.OTF");
        myriad = Typeface.createFromAsset(this.getAssets(),"fonts/Myriad_Pro_Condensed.ttf");


       // lockedDoorsImageview.setImageResource(R.drawable.door_closed);
        textViewUsername.setTypeface(userNameFont);
        goToSettingsBtn = findViewById(R.id.settingsBtn);
        voltageField = findViewById(R.id.accumulatorTv);
        voltageField.setTypeface(myriad);
        carfindertv.setTypeface(myriad);
        servicebookTv.setTypeface(myriad);
        oilTextView.setTypeface(myriad);
        gasolineTv.setTypeface(myriad);
        doorLockTextView.setTypeface(myriad);
        textViewUsername.setTypeface(userNameFont);
        gasolineTv.setText("23%");
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
        getAllSharedPreferences();
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

    String DoorState;
    String Voltage;
    String OilChange;
    public void getAllMainData(int doorStatus, Double voltage, int Oilchange)
    {
        DoorState = String.valueOf(doorStatus);
        Voltage = String.valueOf(voltage);
        OilChange = String.valueOf(Oilchange);
        putToShared();

    }

    private void putToShared() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainMenu.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("isDoorLocked", DoorState);
        editor.putString("voltage", Voltage);
        editor.putString("distanceToOilChange", OilChange);
        editor.apply();

    }

    public void goToSettings() {
        Intent goToSettingsIntent = new Intent(MainMenu.this, SettingsActivity.class);
        startActivity(goToSettingsIntent);
    }

    public Runnable getDataRunnable = new Runnable() {
        @Override
        public void run() {
            Controller controller = new Controller();
            controller.getData(0);
            mHandler.postDelayed(this, 2000);
        }
    };

    public void getAllSharedPreferences()
    {
        //Log.d("wywolanozgetloc", "wywolano");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sharedlockstate = sharedPreferences.getString("isDoorLocked", "2");
        String sharedvoltage = sharedPreferences.getString("voltage", "0");
        String sharedOilChange = sharedPreferences.getString("distanceToOilChange", "10000");
        int Lockstate = Integer.parseInt(sharedlockstate);
        Double VoltageState = Double.valueOf(sharedvoltage);
        int OilChange = Integer.parseInt(sharedOilChange);
        DoorLockInfo(Lockstate);
        setVoltage(VoltageState);
        setDistanceToOilChange(OilChange);

    }

    public void DoorLockInfo(int status) {

        lockedDoorsImageview = findViewById(R.id.lockedDoorsImageView);

        if (status == 0)
        {
            lockedDoorsImageview.setImageResource(R.drawable.door_closed);
            doorLockTextView.setText("Door Locked");
        }
        else if (status == 1)
        {
            lockedDoorsImageview.setImageResource(R.drawable.door_unlocked);
            doorLockTextView.setText("Door Unlocked");
        }
        else if(status == 2)
        {
            Toast.makeText(MainMenu.this, "brak polaczenia", Toast.LENGTH_LONG).show();
        }


    }

    public void setVoltage(Double voltage) {

        voltageField.setText(voltage.toString() + " V");

    }

    public void setDistanceToOilChange(int distance)
    {
        oilTextView.setText(String.valueOf(distance) + " km");
    }


}
