package com.example.mygpsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import com.example.mygpsapp.R;
import com.example.mygpsapp.model.Naprawy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Jacek on 2018-02-18.
 */

public class NaprawyDB extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Naprawy1";
    private static String tableName = "naprawy1";
    private static String idColumn = "id1";
    private static String pojazdColumn = "pojazd";
    private static String periodColumn = "period";
    private static String przebiegColumn = "przebieg";
    private static String kwotaColumn = "kwota";
    private static String warsztatColumn = "warsztat";
    private static String opisColumn = "opis";
    private static String nominalColumn = "nominal";
    private static String unitColumn = "unit";
    private Context context;

    public NaprawyDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    private static final String CREATE_TABLE_NAPRAWY = "create table " + tableName + "(" +
            idColumn + " integer primary key autoincrement, " +
            pojazdColumn + " text, " +
            periodColumn + " text, " +
            przebiegColumn + " text, " +
            kwotaColumn + " text, " +
            warsztatColumn + " text, " +
            opisColumn + " text, " +
            nominalColumn + " text, " +
            unitColumn + " text " +
            ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NAPRAWY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + tableName);
        onCreate(sqLiteDatabase);

    }
    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public List<Naprawy> findAll() {
        try {
            List<Naprawy> naprawies = new ArrayList<Naprawy>();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName, null);
            if (cursor.moveToFirst()) {
                do {
                    Naprawy naprawy = new Naprawy();
                    naprawy.setId(cursor.getInt(0));
                    naprawy.setPojazd(cursor.getString(1));
                    naprawy.setPeriod(cursor.getString(2));
                    naprawy.setPrzebieg(cursor.getString(3));
                    naprawy.setKwota(cursor.getString(4));
                    naprawy.setWarsztat(cursor.getString(5));
                    naprawy.setOpis(cursor.getString(6));
                    naprawy.setNominal(cursor.getString(7));
                    naprawy.setUnit(cursor.getString(8));
                    naprawies.add(naprawy);


                } while (cursor.moveToNext());

            }
            sqLiteDatabase.close();
            return naprawies;
        } catch (Exception e) {
            return null;
        }

    }
    public boolean create(Naprawy naprawy){
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(pojazdColumn, naprawy.getPojazd());
            contentValues.put(periodColumn, naprawy.getPeriod());
            contentValues.put(przebiegColumn, naprawy.getPrzebieg());
            contentValues.put(kwotaColumn, naprawy.getKwota());
            contentValues.put(nominalColumn, naprawy.getNominal());
            contentValues.put(warsztatColumn, naprawy.getWarsztat());
            contentValues.put(opisColumn, naprawy.getOpis());
            contentValues.put(unitColumn, naprawy.getUnit());
            long rows = sqLiteDatabase.insert(tableName, null, contentValues);
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e) {
            return false;
        }
    }
    public boolean delete(int id) {
        try {
          SQLiteDatabase sqLiteDatabase = getWritableDatabase();
          int rows = sqLiteDatabase.delete(tableName,idColumn + " = ?", new String[] {String.valueOf(id)});
          sqLiteDatabase.close();
          return rows > 0;

        }catch (Exception e) {
            return false;
        }
    }
    public Naprawy find(int id){
        try {
       SQLiteDatabase sqLiteDatabase = getWritableDatabase();
       Cursor cursor = sqLiteDatabase.rawQuery("select * FROM" + tableName + " where " + idColumn + " = ?", new String[]{String.valueOf(id)} );
       Naprawy naprawy = null;
       if (cursor.moveToFirst()) {
           naprawy = new Naprawy();
           naprawy.setId(cursor.getInt(0));
           naprawy.setPojazd(cursor.getString(1));
           naprawy.setPeriod(cursor.getString(2));
           naprawy.setPrzebieg(cursor.getString(3));
           naprawy.setKwota(cursor.getString(4));
           naprawy.setWarsztat(cursor.getString(5));
           naprawy.setOpis(cursor.getString(6));
           naprawy.setNominal(cursor.getString(7));
           naprawy.setUnit(cursor.getString(8));
       }
       sqLiteDatabase.close();
       return naprawy;
        }catch (Exception e) {
            return null;
        }
    }


    public boolean update (Naprawy naprawy) {
       try {
          SQLiteDatabase sqLiteDatabase = getWritableDatabase();
          ContentValues contentValues = new ContentValues();
          contentValues.put(pojazdColumn, naprawy.getPojazd());
           contentValues.put(periodColumn, naprawy.getPeriod());
           contentValues.put(przebiegColumn, naprawy.getPrzebieg());
           contentValues.put(kwotaColumn, naprawy.getKwota());
           contentValues.put(nominalColumn, naprawy.getNominal());
           contentValues.put(unitColumn, naprawy.getUnit());
           contentValues.put(warsztatColumn, naprawy.getWarsztat());
           contentValues.put(opisColumn, naprawy.getOpis());
           int rows = sqLiteDatabase.update(tableName, contentValues, idColumn + " = ?",new String[]{String.valueOf(naprawy.getId())} );
          sqLiteDatabase.close();
          return rows > 0;
       } catch (Exception e) {
           return false;
       }
    }
    public void backup(String outFileName) {

        //database path
        final String inFileName = context.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(context, R.string.Backup_complete, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
//    public void insertNominal (String nominal)
//    {
//        SQLiteDatabase db=this.getWritableDatabase();
//        db.beginTransaction();
//        ContentValues values;
//        values = new ContentValues();
//        values.put("nominal", nominal);
//        db.insert()
//    }

    public void importDB(String inFileName) {

        final String outFileName = context.getDatabasePath(DATABASE_NAME).toString();

        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(context, R.string.Import_complete, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(context, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + tableName;
    public void deleteAll(SQLiteDatabase db) {

        db.execSQL(DELETE_TABLE);

    }

}
