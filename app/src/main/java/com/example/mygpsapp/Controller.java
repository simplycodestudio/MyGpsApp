package com.example.mygpsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.mygpsapp.model.OutputModel;
import com.example.mygpsapp.rest.JsonPlaceHolderApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller  {

    public static final String BASE_URL = "http://172.20.10.2/";
    private static Retrofit retrofit = null;
    private Handler mHandler = new Handler();
    private MapsActivity mapsActivity = new MapsActivity();
    public Double lat;
    public Double lon;


    public void getData() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<OutputModel> call = jsonPlaceHolderApi.getCords();

        call.enqueue(new Callback<OutputModel>() {
            @Override
            public void onResponse(Call<OutputModel> call, retrofit2.Response<OutputModel> response) {

                if (!response.isSuccessful()) {
                     Log.d("!sucesfully","Code: " + response.code());
                    return;
                }


               // mMap.clear();
                lat = response.body().getLatitude();
                lon = response.body().getLongitude();


               mapsActivity.onCarChangeLocation(lat, lon);
                //mapsActivity.onCarChangeLocation(lat, lon);
                //Log.i("latitudeinfo: ", lat.toString());



            }


            @Override
            public void onFailure(Call<OutputModel> call, Throwable t) {
              Log.d("errorMsg", t.getMessage());

            }
        });


    }



//    public Runnable getDataRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Controller controller = new Controller();
//            controller.getData();
//            mHandler.postDelayed(this, 1000);
//        }
//    };
}
