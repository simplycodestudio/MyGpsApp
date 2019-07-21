package com.example.mygpsapp.rest;

import com.example.mygpsapp.model.OutputModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("out_gps.json")
    Call<OutputModel> getCords();
}
