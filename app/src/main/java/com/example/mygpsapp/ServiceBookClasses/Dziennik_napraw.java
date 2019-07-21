package com.example.mygpsapp.ServiceBookClasses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.widget.*;
import android.content.*;
import android.view.View;

import com.example.mygpsapp.MainMenu;
import com.example.mygpsapp.R;
import com.example.mygpsapp.adapters.NaprawyListAdapter;
import com.example.mygpsapp.database.NaprawyDB;
import com.example.mygpsapp.model.Naprawy;

import com.google.android.gms.common.api.GoogleApiClient;



import java.io.File;

import java.lang.ref.WeakReference;

import static com.example.mygpsapp.database.NaprawyDB.getDatabaseVersion;


//import static com.example.jacek.hondadiagnostic.database.NaprawyDB.getDatabaseVersion;


public class Dziennik_napraw extends AppCompatActivity {

    //implements PopupMenu.OnMenuItemClickListener, GoogleApiClient.OnConnectionFailedListener
    private ListView listViewNaprawy;


    private boolean bckORrst = true;
    private Button deletebtn;
    @Nullable
    private GoogleApiClient googleApiClient;

    @Nullable
    private WeakReference<Activity> activityRef;

    private static final String TAG = "Google Drive Activity";

    private static final int REQUEST_CODE_OPENER = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    FloatingActionButton fab_plus, fab_export, fab_usun,fab_import, fab_edytuj, fab_add;
    Animation FabOpen, FabClose, FabRClockwisw, FabRanticlockwisw;
    boolean isOpen= false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent(Dziennik_napraw.this, MainMenu.class);
            startActivity(intent1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    SwipeRefreshLayout swipeRefreshLayout;




    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_dziennik_napraw);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        Intent intent2 = new Intent(Dziennik_napraw.this, Dziennik_napraw.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.push_down_in,R.anim.push_down_out);
                    }
                }, 2000);

            }
        });




        final NaprawyDB naprawyDB = new NaprawyDB(getApplicationContext());
        this.listViewNaprawy = (ListView) findViewById(R.id.listViewNaprawy);
        this.listViewNaprawy.setAdapter(new NaprawyListAdapter(this,  naprawyDB.findAll()));
        this.listViewNaprawy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Naprawy naprawy = naprawyDB.findAll().get(i);
                Intent intent1 = new Intent(Dziennik_napraw.this, NaprawyDetailActivity.class);
                intent1.putExtra("naprawy", naprawy);
                startActivity(intent1);

            }
        });



        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_dodaj);
        fab_import = (FloatingActionButton) findViewById(R.id.fab_import);



        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRanticlockwisw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        FabRClockwisw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        this.fab_usun = (FloatingActionButton) findViewById(R.id.fab_usun);
        this.fab_usun.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyDialogTheme);
                builder.setIcon(R.drawable.ic_delete_black_24dp);
                builder.setTitle(Dziennik_napraw.this.getString(R.string.usun_wszystkie_pozycje));
                builder.setCancelable(false);
                builder.setMessage(Dziennik_napraw.this.getString(R.string.jestes_pewien));
                builder.setPositiveButton(Dziennik_napraw.this.getString(R.string.potwierdz), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase database = naprawyDB.getWritableDatabase();
                        naprawyDB.onUpgrade(database, getDatabaseVersion(), getDatabaseVersion());
                        ListView table = (ListView) findViewById(R.id.listViewNaprawy);
                        table.removeAllViewsInLayout();
                    }
                });
                builder.setNegativeButton(Dziennik_napraw.this.getString(R.string.zaneguj), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();

            }
        });


        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    fab_add.startAnimation(FabClose);
                    fab_export.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanticlockwisw);
                    fab_import.startAnimation(FabClose);
                    fab_usun.startAnimation(FabClose);
                    fab_add.setClickable(false);
                    fab_export.setClickable(false);
                    fab_import.setClickable(false);
                    fab_usun.setClickable(false);
                    isOpen=false;
                }
                else
                {
                    fab_add.startAnimation(FabOpen);
                    fab_export.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwisw);
                    fab_import.startAnimation(FabOpen);
                    fab_usun.startAnimation(FabOpen);

                    fab_export.setClickable(true);
                    fab_add.setClickable(true);
                    fab_import.setClickable(true);
                    fab_usun.setClickable(true);
                    isOpen=true;
                }
            }
        });
        this.fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Dziennik_napraw.this, AddNaprawyActivity.class);
                startActivity(intent1);

            }
        });
        fab_export = (FloatingActionButton) findViewById(R.id.fab_export);
        fab_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NaprawyDB naprawyDB = new NaprawyDB(getApplicationContext());
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
                performBackup(naprawyDB, outFileName);
            }
        });
        fab_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRestore(naprawyDB);




            }
        });


    }
//
//    public void showPopup(View v){
//        PopupMenu popup = new PopupMenu(this, v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.podmenu_export);
//        popup.show();
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.Internal:
//                final NaprawyDB naprawyDB = new NaprawyDB(getApplicationContext());
//                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
//                performBackup(naprawyDB, outFileName);
//                return true;
//            case R.id.Gdrive:
////                bckORrst = true;
////                if (mGoogleApiClient != null)
////                    mGoogleApiClient.disconnect();
////                mGoogleApiClient = gApiCLient(mGoogleApiClient);
////                mGoogleApiClient.connect();
//                default:
//                    return false;
//        }
//    }





    private void performBackup(final NaprawyDB naprawyDB, final String outFileName) {

        verifyStoragePermissions(this);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));

        boolean success = true;
        if (!folder.exists())
            success = folder.mkdirs();
        if (success) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogExport);
            builder.setTitle(R.string.backup_name);
            builder.setIcon(R.drawable.fab_export);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton(R.string.zapisz, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_Text = input.getText().toString();
                    String out = outFileName + m_Text + ".db";
                    naprawyDB.backup(out);
                }
            });
            builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else
            Toast.makeText(this, "Unable to create directory. Retry", Toast.LENGTH_SHORT).show();
    }

    //ask to the user what backup to restore
    private void performRestore(final NaprawyDB db) {

        verifyStoragePermissions(this);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name));
        if (folder.exists()) {

            final File[] files = folder.listFiles();

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);
            for (File file : files)
                arrayAdapter.add(file.getName());

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(this,R.style.MyDialogImport);
            builderSingle.setTitle(R.string.Restore);
            builderSingle.setIcon(R.drawable.fab_import);
            builderSingle.setNegativeButton(
                    R.string.Cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            builderSingle.setAdapter(
                    arrayAdapter,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                db.importDB(files[which].getPath());
                            } catch (Exception e) {
                                Toast.makeText(Dziennik_napraw.this, "Unable to restore. Retry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            builderSingle.show();
        } else
            Toast.makeText(this, "Backup folder not present.\nDo a backup before a restore!", Toast.LENGTH_SHORT).show();
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



//    @Override
//    // Called whenever the API client fails to connect.
//    public void onConnectionFailed(ConnectionResult result) {
//
//        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
//        if (!result.hasResolution()) {
//            // show the localized error dialog.
//            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
//            return;
//        }
//
//        try {
//            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
//        } catch (IntentSender.SendIntentException e) {
//            Log.e(TAG, "Exception while starting resolution activity", e);
//        }
//    }
//
//











}
