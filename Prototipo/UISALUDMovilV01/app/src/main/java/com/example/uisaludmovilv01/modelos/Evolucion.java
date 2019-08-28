package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Evoluciones")
public class Evolucion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "usuario")
    private int usuario;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private LocalTime hora;

    @NonNull
    @ColumnInfo(name = "evolucion")
    private String evolucion;

    public int getId() {
        return id;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getDoctor() {
        return doctor;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    @NonNull
    public LocalTime getHora() {
        return hora;
    }

    @NonNull
    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(@NonNull String evolucion) {
        this.evolucion = evolucion;
    }
}