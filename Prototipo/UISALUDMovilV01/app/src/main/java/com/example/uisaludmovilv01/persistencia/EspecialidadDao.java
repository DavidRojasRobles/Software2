package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.uisaludmovilv01.modelos.Especialidad;

import java.util.List;

@Dao
public interface EspecialidadDao {

    //@Insert


    @Query("SELECT * FROM Especialidades")
    LiveData<List<Especialidad>> consultarEspecialidades();


    //@Update


    //@Delete
}
