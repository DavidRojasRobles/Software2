package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;


import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Evoluciones",
        foreignKeys = @ForeignKey(
                entity = Procedimiento.class,
                parentColumns = "id",
                childColumns = "procedimiento"
        ))
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Evolucion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "procedimiento")
    private int prcedimiento;

    @NonNull
    @ColumnInfo(name = "evolucion")
    private String evolucion;

    public Evolucion(int prcedimiento, @NonNull String evolucion) {
        this.prcedimiento = prcedimiento;
        this.evolucion = evolucion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrcedimiento() {
        return prcedimiento;
    }

    public void setPrcedimiento(int prcedimiento) {
        this.prcedimiento = prcedimiento;
    }

    @NonNull
    public String getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(@NonNull String evolucion) {
        this.evolucion = evolucion;
    }

    //    public String toString() {
//        return "\nDoctor: " + nombreDoctor +
//                "\nFecha: " + fecha.toString() +
//                "\nHora: " + hora.toString() +
//                "\nObservaciones: " + evolucion;
//    }
}