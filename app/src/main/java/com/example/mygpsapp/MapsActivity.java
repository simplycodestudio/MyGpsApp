package com.example.mygpsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Handler;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    public static final String BASE_URL = "http://192.168.0.18:80/";
    private static Retrofit retrofit = null;
    private Handler mHandler = new Handler();

    public Double lat;
    public Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getDataRunnable.run();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


    }


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
                    // onFailTV.setText("Code: " + response.code());
                    return;
                }


                mMap.clear();
                lat = response.body().getLatitude();
                lon = response.body().getLongitude();
                Log.i("latitudeinfo: ", lat.toString());

                LatLng carLatLng = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(carLatLng).title("Integra Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLatLng, 15.5f));

            }


            @Override
            public void onFailure(Call<OutputModel> call, Throwable t) {
                Toast.makeText(MapsActivity.this,"Unable to connect with server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Runnable getDataRunnable = new Runnable() {
        @Override
        public void run() {
            getData();
            mHandler.postDelayed(this,1000);
        }
    };
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            //onCarChangeLocation();

    }
    public void onCarChangeLocation()
    {

        LatLng carLatLng = new LatLng(51.2377, 22.23);
        mMap.addMarker(new MarkerOptions().position(carLatLng).title("Integra Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLatLng, 15.5f));

   }


}
