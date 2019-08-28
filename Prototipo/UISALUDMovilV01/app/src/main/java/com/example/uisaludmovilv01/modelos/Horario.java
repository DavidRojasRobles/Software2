package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Horarios")
public class Horario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    @NonNull
    @ColumnInfo(name = "hora")
    private LocalTime hora;

}
