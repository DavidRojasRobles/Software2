package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Evoluciones")
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Evolucion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "usuario")
    private int usuario;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private String hora;

    @NonNull
    @ColumnInfo(name = "evolucion")
    private String evolucion;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }


    public String getNombreDoctor() {
        return nombreDoctor;
    }



    private String nombreDoctor;

    public int getId() {
        return id;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getDoctor() {
        return doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getHora() {
        return hora;
    }

    public void setHora(@NonNull String hora) {
        this.hora = hora;
    }

    @NonNull
    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(@NonNull String evolucion) {
        this.evolucion = evolucion;
    }

    public void setNombreDoctor(String nombre) {
        nombreDoctor = nombre;
    }

    public String toString() {
        return "\nDoctor: " + nombreDoctor +
                "\nFecha: " + fecha.toString() +
                "\nHora: " + hora.toString() +
                "\nObservaciones: " + evolucion;
    }
}