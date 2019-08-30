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

@Entity(tableName = "Horarios")
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Horario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private String hora;

    public Horario(int doctor, @NonNull String fecha, @NonNull String hora) {
        this.doctor = doctor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor() {
        return doctor;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getHora() {
        return hora;
    }

    public void setHora(@NonNull String hora) {
        this.hora = hora;
    }
}
