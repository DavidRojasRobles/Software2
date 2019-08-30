package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;


import com.example.uisaludmovilv01.modelos.Especialidad;

import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface EspecialidadDao {

    //@Insert


    @Query("SELECT * FROM Especialidades")
    LiveData<List<Especialidad>> getEspecialidades();

    @Query("SELECT * FROM Especialidades WHERE id = :espId")
    LiveData<Especialidad> getEspecialidadById(int espId);

    @Query("SELECT * FROM Especialidades WHERE nombre = :espNombre")
    LiveData<Especialidad> getEspecialidadByNombre(String espNombre);


    //@Update


    //@Delete
}
