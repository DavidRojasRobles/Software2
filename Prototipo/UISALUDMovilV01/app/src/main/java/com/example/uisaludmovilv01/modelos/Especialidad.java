package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import java.io.Serializable;

@Entity(tableName = "Especialidades",
        indices = {@Index(value = "nombre", unique = true)})
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Especialidad implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String espNombre;

    public Especialidad(@NonNull String espNombre) {
        this.espNombre = espNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getEspNombre() {
        return espNombre;
    }

    public void setEspNombre(@NonNull String espNombre) {
        this.espNombre = espNombre;
    }
}
