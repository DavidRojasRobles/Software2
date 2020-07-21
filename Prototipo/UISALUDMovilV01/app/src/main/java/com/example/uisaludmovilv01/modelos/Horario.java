package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Horarios",
        indices = {@Index(
            name = "horario",
            value = {"dia_semana", "hora"},
            unique = true)},
        foreignKeys = @ForeignKey(entity = Doctor.class,
            parentColumns = "id", childColumns = "doctor"))

@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Horario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "doctor", index = true)
    private int doctor;

    @NonNull
    @ColumnInfo(name = "dia_semana")
    private String diaSemana;

    @NonNull
    @ColumnInfo(name = "hora")
    private String hora;

    public Horario(int doctor, @NonNull String diaSemana, @NonNull String hora) {
        this.doctor = doctor;
        this.diaSemana = diaSemana;
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
        return diaSemana;
    }

    public void setFecha(@NonNull String diaSemana) {
        this.diaSemana = diaSemana;
    }

    @NonNull
    public String getHora() {
        return hora;
    }

    public void setHora(@NonNull String hora) {
        this.hora = hora;
    }

    @NonNull
    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(@NonNull String diaSemana) {
        this.diaSemana = diaSemana;
    }
}
