package com.example.mygpsapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mygpsapp.model.OutputModel;
import com.example.mygpsapp.rest.JsonPlaceHolderApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SharedPreferences sharedPreferences;
    private Handler mHandler = new Handler();

    public Handler refreshMarkerHandler = new Handler();
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = getApplicationContext();
        getDataRunnable.run();




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }


    public static Context getAppContext() {
        return mContext;
    }

    LatLng carLatLng;
    String Latitude;
    String Longitude;
    public void onCarChangeLocation(Double lat, Double lon) {

        Latitude = lat.toString();
        Longitude = lon.toString();


        Log.d("wywolano", "wywolano");
        putToShared();
        if (mMap != null) {//Check here map is not initialized

            carLatLng = new LatLng(lat, lon);

            mMap.addMarker(new MarkerOptions().position(carLatLng).title("Integra Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLatLng, 15.5f));
        } else {

            carLatLng = new LatLng(lat, lon);

        }

    }

    private void putToShared() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("latitude", Latitude);
        editor.putString("longitude", Longitude);
        editor.apply();

    }




    public Runnable getDataRunnable = new Runnable() {
        @Override
        public void run() {
            Controller controller = new Controller();
            controller.getData(1);
            mHandler.postDelayed(this, 2000);
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        refreshMarkerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocationSharedPreferences();

                refreshMarkerHandler.postDelayed(this, 2000);
            }
        }, 1500);

    }

    public void getLocationSharedPreferences()
    {
        Log.d("wywolanozgetloc", "wywolano");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String sharedLat = sharedPreferences.getString("latitude","0");
        String sharedLon = sharedPreferences.getString("longitude","0");


        Double doubleLat = Double.valueOf(sharedLat);
        Double doubleLon = Double.valueOf(sharedLon);

        setMarker(doubleLat, doubleLon);

    }

    public void setMarker(Double lat, Double lon) {


        carLatLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(carLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.itr_map_ico)).title("Integra Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLatLng, 14.2f));

    }
}
