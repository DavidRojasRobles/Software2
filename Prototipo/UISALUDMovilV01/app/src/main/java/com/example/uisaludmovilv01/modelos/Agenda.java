package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Agenda")
public class Agenda {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "usuario")
    private int usuario;

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private LocalTime hora;

    public int getId() {
        return id;
    }

    public int getDoctor() {
        return doctor;
    }

    public int getUsuario() {
        return usuario;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    @NonNull
    public LocalTime getHora() {
        return hora;
    }

    public void setFecha(@NonNull LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(@NonNull LocalTime hora) {
        this.hora = hora;
    }
}
