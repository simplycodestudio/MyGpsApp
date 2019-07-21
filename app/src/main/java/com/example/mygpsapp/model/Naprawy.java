package com.example.mygpsapp.model;

import java.io.Serializable;

/**
 * Created by Jacek on 2018-02-18.
 */

public class Naprawy implements Serializable {

    private int id;
    private String pojazd;
    private String period;
    private String przebieg;
    private String kwota;
    private String warsztat;
    private String opis;
    private String nominal;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPojazd() {
        return pojazd;
    }

    public void setPojazd(String pojazd) {
        this.pojazd = pojazd;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(String przebieg) {
        this.przebieg = przebieg;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getNominal() {return nominal;}

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getUnit() {return unit;}

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarsztat() {
        return warsztat;
    }

    public void setWarsztat(String warsztat) {
        this.warsztat = warsztat;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;

    }
    public Naprawy(int id, String nominal, String pojazd, String period, String przebieg, String kwota, String warsztat, String opis) {
        this.id = id;
        this.pojazd = pojazd;
        this.period = period;
        this.przebieg = przebieg;
        this.kwota = kwota;
        this.warsztat = warsztat;
        this.nominal = nominal;
        this.opis = opis;
    }

    public Naprawy() {
    }
}
