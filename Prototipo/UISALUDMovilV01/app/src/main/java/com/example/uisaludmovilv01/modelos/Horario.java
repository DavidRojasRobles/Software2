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
    @ColumnInfo(name = "diaSemana")
    private String diaSemana;

    @NonNull
    @ColumnInfo(name = "mananaTarde")
    @TypeConverters(LocalTimeConverter.class)
    private int mananaTarde; // 0 si es mañana, false si es tarde

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
    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(@NonNull String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getMananaTarde() {
        return mananaTarde;
    }

    public void setMananaTarde(int mananaTarde) {
        this.mananaTarde = mananaTarde;
    }
}
