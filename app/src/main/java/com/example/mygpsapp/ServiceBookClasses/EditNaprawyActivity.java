package com.example.mygpsapp.ServiceBookClasses;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.mygpsapp.R;
import com.example.mygpsapp.database.NaprawyDB;
import com.example.mygpsapp.model.Naprawy;

import java.util.Calendar;



public class EditNaprawyActivity extends AppCompatActivity {
    private static final String TAG = "EditNaprawyActivity";

    private AutoCompleteTextView editTextPojazd;
    private EditText editTextPeriod;
    private EditText editTextPrzebieg;
    private EditText editTextKwota;
    private EditText editTextWarsztat;
    private EditText editTextOpis;
    private Button buttonBack;
    private Button buttonSave;
    private AutoCompleteTextView Nominal;
    private AutoCompleteTextView Unit;
    private ImageView ArrowDwn; //value
    private ImageView ArrowDwnPojazd;
    private ImageView ArrowDwnUnit;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_naprawy);
        Intent intent = getIntent();
        final Naprawy naprawy = (Naprawy) intent.getSerializableExtra("naprawy");

        android.app.ActionBar actionBar = getActionBar();


        mDisplayDate = (EditText) findViewById(R.id.editTextPeriod);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditNaprawyActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "OnDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };



        this.ArrowDwnUnit = (ImageView) findViewById(R.id.arrow_down_Przebieg);
        this.ArrowDwnPojazd = (ImageView) findViewById(R.id.Arrow_down_Pojazd);
        this.ArrowDwn = (ImageView) findViewById(R.id.Arrow_down_val);

        this.editTextPojazd = (AutoCompleteTextView) findViewById(R.id.editTextPojazd);
        this.editTextPojazd.setText(naprawy.getPojazd());

        this.editTextPeriod = (EditText) findViewById(R.id.editTextPeriod);
        this.editTextPeriod.setText(naprawy.getPeriod());
        editTextPeriod.setFocusable(false);

        this.editTextPrzebieg = (EditText) findViewById(R.id.editTextPrzebieg);
        this.editTextPrzebieg.setText(naprawy.getPrzebieg());

        this.editTextKwota = (EditText) findViewById(R.id.editTextKwota);
        this.editTextKwota.setText(naprawy.getKwota());

        this.editTextWarsztat = (EditText) findViewById(R.id.editTextWarsztat);
        this.editTextWarsztat.setText(naprawy.getWarsztat());

        this.editTextOpis = (EditText) findViewById(R.id.editTextOpis);
        this.editTextOpis.setText(naprawy.getOpis());

        this.Nominal = (AutoCompleteTextView) findViewById(R.id.Nominal_autocomplete);
        this.Nominal.setText(naprawy.getNominal());

        this.Unit = (AutoCompleteTextView) findViewById(R.id.unit_autocomplete);
        this.Unit.setText(naprawy.getUnit());



        this.buttonBack = (Button) findViewById(R.id.buttonBack);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent1 = new Intent(EditNaprawyActivity.this, Dziennik_napraw.class);

             startActivity(intent1);
            }
        });

        this.buttonSave = (Button) findViewById(R.id.buttonSave);
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             NaprawyDB naprawyDB = new NaprawyDB(getBaseContext());
                naprawy.setPojazd(editTextPojazd.getText().toString());
                naprawy.setPeriod(editTextPeriod.getText().toString());
                naprawy.setPrzebieg(editTextPrzebieg.getText().toString());
                naprawy.setKwota(editTextKwota.getText().toString());
                naprawy.setWarsztat(editTextWarsztat.getText().toString());
                naprawy.setOpis(editTextOpis.getText().toString());
                naprawy.setUnit(Unit.getText().toString());
                naprawy.setNominal(Nominal.getText().toString());
                if (naprawyDB.update(naprawy)){
                    Intent intent1 = new Intent(EditNaprawyActivity.this, Dziennik_napraw.class);
                    startActivity(intent1);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Fail");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                }

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nominal);
        Nominal.setAdapter(adapter);
        Nominal.setThreshold(3);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pojazd);
        editTextPojazd.setAdapter(adapter1);
        editTextPojazd.setThreshold(2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, unit);
        Unit.setAdapter(adapter2);
        Unit.setThreshold(1);




        ArrowDwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nominal.showDropDown();
            }
        });

        ArrowDwnPojazd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {editTextPojazd.showDropDown();
            }
        });

        ArrowDwnUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Unit.showDropDown();
            }
        });

    }
    private  static final String[] nominal = new String[] {"PLN", "USD", "EUR"};
    private  static final String[] pojazd = new String[] {"Accord", "Integra", "Civic", "Prelude", "Crx", "Del Sol", "S2000", "Nsx", "CR-V", "Jazz", "Cr-Z", "Concerto", "City", "HR-V", "Shuttle", "Ridgeline", "Stream", "Logo", "Odyssey", "Legend", "Acura TL"};
    private  static final String[] unit = new String[] {"km", "miles"};
}
