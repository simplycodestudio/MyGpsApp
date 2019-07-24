package com.example.mygpsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public TextView usernameTv;
    public TextView ipTv;
    public Button editUsernameBtn;
    public EditText editTextUserName;
    public Button acceptUserName;
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
        editUsernameBtn = findViewById(R.id.editUsernameBtn);
        editTextUserName = findViewById(R.id.editTextUsername);
        acceptUserName = findViewById(R.id.acceptUsernameBtn);

    }

    public void listenery() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = sharedPreferences.getString("nameKey","No name");
        usernameTv.setText(name);
        //final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String ip = sharedPreferences.getString("IpKey", "0");
        ipTv.setText(ip);

        editUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsernameField();
            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent backIntent =  new Intent(SettingsActivity.this, MainMenu.class);
        startActivity(backIntent);
    }

    public void editUsernameField() {
        usernameTv.setVisibility(View.GONE);
        editTextUserName.setVisibility(View.VISIBLE);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = sharedPreferences.getString("nameKey","No name");
        editTextUserName.setText(name);
        editUsernameBtn.setVisibility(View.GONE);
        acceptUserName.setVisibility(View.VISIBLE);

        acceptUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedUserName = editTextUserName.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nameKey", editedUserName);
                editor.apply();
                usernameTv.setText(editedUserName);
                editTextUserName.setVisibility(View.GONE);
                acceptUserName.setVisibility(View.GONE);
                usernameTv.setVisibility(View.VISIBLE);
                editUsernameBtn.setVisibility(View.VISIBLE);

            }
        });

    }
}
