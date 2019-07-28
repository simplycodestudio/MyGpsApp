package com.example.mygpsapp.model;

import com.google.gson.annotations.SerializedName;

public class OutputModel {

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("velocity")
    private Double velocity;

    @SerializedName("isDoorLocked")
    private int isDoorLocked;

    @SerializedName("distance_to_oil_change")
    private int distance_to_oil_change;

    @SerializedName("voltage")
    private Double voltage;


    public OutputModel(Double latitude, Double longitude, Double velocity, int isDoorLocked, int distance_to_oil_change, Double voltage) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.velocity = velocity;
        this.isDoorLocked = isDoorLocked;
        this.distance_to_oil_change = distance_to_oil_change;
        this.voltage = voltage;
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

    public int getIsDoorLocked() {
        return isDoorLocked;
    }

    public void setIsDoorLocked(int isDoorLocked) {
        this.isDoorLocked = isDoorLocked;
    }

    public int getDistance_to_oil_change() {
        return distance_to_oil_change;
    }

    public void setDistance_to_oil_change(int distance_to_oil_change) {
        this.distance_to_oil_change = distance_to_oil_change;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }
}
