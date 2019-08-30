package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.Serializable;


@Entity(tableName = "Agenda")
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Agenda implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "ocho")
    private boolean ocho;

    @NonNull
    @ColumnInfo(name = "nueve")
    private boolean nueve;

    @NonNull
    @ColumnInfo(name = "diez")
    private boolean diez;

    @NonNull
    @ColumnInfo(name = "once")
    private boolean once;

    @NonNull
    @ColumnInfo(name = "catorce")
    private boolean catorce;

    @NonNull
    @ColumnInfo(name = "quince")
    private boolean quince;

    @NonNull
    @ColumnInfo(name = "diezYSeis")
    private boolean diezYSeis;

    @NonNull
    @ColumnInfo(name = "diezYSiete")
    private boolean diezYSiete;

    public Agenda(int doctor, @NonNull String fecha, boolean ocho,
                  boolean nueve, boolean diez, boolean once, boolean catorce,
                  boolean quince, boolean diezYSeis, boolean diezYSiete) {
        this.doctor = doctor;
        this.fecha = fecha;
        this.ocho = ocho;
        this.nueve = nueve;
        this.diez = diez;
        this.once = once;
        this.catorce = catorce;
        this.quince = quince;
        this.diezYSeis = diezYSeis;
        this.diezYSiete = diezYSiete;
    }

    public boolean isOcho() {
        return ocho;
    }

    public void setOcho(boolean ocho) {
        this.ocho = ocho;
    }

    public boolean isNueve() {
        return nueve;
    }

    public void setNueve(boolean nueve) {
        this.nueve = nueve;
    }

    public boolean isDiez() {
        return diez;
    }

    public void setDiez(boolean diez) {
        this.diez = diez;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public boolean isCatorce() {
        return catorce;
    }

    public void setCatorce(boolean catorce) {
        this.catorce = catorce;
    }

    public boolean isQuince() {
        return quince;
    }

    public void setQuince(boolean quince) {
        this.quince = quince;
    }

    public boolean isDiezYSeis() {
        return diezYSeis;
    }

    public void setDiezYSeis(boolean diezYSeis) {
        this.diezYSeis = diezYSeis;
    }

    public boolean isDiezYSiete() {
        return diezYSiete;
    }

    public void setDiezYSiete(boolean diezYSiete) {
        this.diezYSiete = diezYSiete;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }


    public int getId() {
        return id;
    }

    public int getDoctor() {
        return doctor;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

}
