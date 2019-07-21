package com.example.mygpsapp.model;

import com.google.gson.annotations.SerializedName;

public class OutputModel {

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("velocity")
    private Double velocity;


    public OutputModel(Double latitude, Double longitude, Double velocity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocity = velocity;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }
}
