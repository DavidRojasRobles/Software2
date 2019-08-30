package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import java.io.Serializable;

@Entity(tableName = "Especialidades")
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Especialidad implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    public int getId() {
        return id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }
}
