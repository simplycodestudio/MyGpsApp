package com.example.mygpsapp.ServiceBookClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.example.mygpsapp.R;
import com.example.mygpsapp.database.NaprawyDB;
import com.example.mygpsapp.model.Naprawy;


public class NaprawyDetailActivity extends AppCompatActivity {

    private TextView textViewPojazd;
    private TextView textViewPeriod;
    private TextView textViewPrzebieg;
    private TextView textViewKwota;
    private TextView textViewWarsztat;
    private TextView textViewOpis;
    private TextView textViewUnit;
    private TextView textViewVal;



    FloatingActionButton fab_plus, fab_usun, fab_cofnij, fab_edytuj;
    Animation FabOpen, FabClose, FabRClockwisw, FabRanticlockwisw;
    boolean isOpen= false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent(NaprawyDetailActivity.this, Dziennik_napraw.class);
            startActivity(intent1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naprawy_detail);
        Intent intent = getIntent();
        final Naprawy naprawy = (Naprawy) intent.getSerializableExtra("naprawy");
        this.textViewPojazd = (TextView) findViewById(R.id.textViewPojazd);
        this.textViewPojazd.setText(naprawy.getPojazd());

        final NaprawyDB db = new NaprawyDB(getApplicationContext());

        this.textViewUnit = (TextView) findViewById(R.id.textViewUnit);
        this.textViewUnit.setText(naprawy.getUnit());

        this.textViewVal = (TextView) findViewById(R.id.textViewVal);
        this.textViewVal.setText(naprawy.getNominal());


        this.textViewPeriod = (TextView) findViewById(R.id.textViewPeriod);
        this.textViewPeriod.setText(naprawy.getPeriod());

        this.textViewPrzebieg = (TextView) findViewById(R.id.textViewPrzebieg);
        this.textViewPrzebieg.setText(naprawy.getPrzebieg());

        this.textViewKwota = (TextView) findViewById(R.id.textViewKwota);
        this.textViewKwota.setText(naprawy.getKwota());

        this.textViewWarsztat = (TextView) findViewById(R.id.textViewWarsztat);
        this.textViewWarsztat.setText(naprawy.getWarsztat());

        this.textViewOpis = (TextView) findViewById(R.id.textViewOpis);
        this.textViewOpis.setText(naprawy.getOpis());

        this.


        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab_cofnij = (FloatingActionButton) findViewById(R.id.fab_cofnij);
        fab_edytuj = (FloatingActionButton) findViewById(R.id.fab_edytuj);
        fab_usun = (FloatingActionButton) findViewById(R.id.fab_usun);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRanticlockwisw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        FabRClockwisw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    fab_cofnij.startAnimation(FabClose);
                    fab_edytuj.startAnimation(FabClose);
                    fab_usun.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanticlockwisw);

                    fab_cofnij.setClickable(false);
                    fab_usun.setClickable(false);
                    fab_edytuj.setClickable(false);

                    isOpen=false;
                }
                else
                {
                    fab_cofnij.startAnimation(FabOpen);
                    fab_edytuj.startAnimation(FabOpen);
                    fab_usun.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwisw);

                    fab_cofnij.setClickable(true);
                    fab_usun.setClickable(true);
                    fab_edytuj.setClickable(true);

                    isOpen=true;
                }
            }
        });

        this.fab_cofnij = (FloatingActionButton) findViewById(R.id.fab_cofnij);
        this.fab_cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(NaprawyDetailActivity.this, Dziennik_napraw.class);
                startActivity(intent1);

            }
        });
        this.fab_usun = (FloatingActionButton) findViewById(R.id.fab_usun);
        this.fab_usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyDialogTheme);
                builder.setIcon(R.drawable.ic_delete_black_24dp);
                builder.setTitle(NaprawyDetailActivity.this.getString(R.string.usun_pozycje));
                builder.setCancelable(false);
                builder.setMessage(NaprawyDetailActivity.this.getString(R.string.jestes_pewien));
                builder.setPositiveButton(NaprawyDetailActivity.this.getString(R.string.potwierdz), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NaprawyDB naprawyDB = new NaprawyDB(getBaseContext());
                        if (naprawyDB.delete(naprawy.getId())){
                             Intent intent1 = new Intent(NaprawyDetailActivity.this, Dziennik_napraw.class);
                             startActivity(intent1);
                        }else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
                            builder1.setCancelable(false);
                            builder1.setMessage("Fail");
                            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                         dialogInterface.cancel();
                                }
                            });
                            builder1.create().show();

                        }
                    }
                });
                builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    }
                });
                builder.create().show();

            }
        });
        this.fab_edytuj = (FloatingActionButton) findViewById(R.id.fab_edytuj);
        this.fab_edytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(NaprawyDetailActivity.this, EditNaprawyActivity.class);
                intent1.putExtra("naprawy", naprawy);
                startActivity(intent1);

            }
        });


    }

}
