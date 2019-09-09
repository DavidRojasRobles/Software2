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

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.Serializable;


@Entity(tableName = "Agenda",
        indices = {@Index(
                name = "agenda",
                value = {"fecha", "hora"},
                unique = true)},
        foreignKeys = @ForeignKey(entity = Doctor.class,
                parentColumns = "id", childColumns = "doctor"))

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
    @ColumnInfo(name = "hora")
    private String hora;


    public Agenda(int doctor, @NonNull String fecha, @NonNull String hora) {
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
