package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.CitaMedica;

import java.util.List;

@Dao
public interface CitaMedicaDao {

    //@Insert


    @Query("SELECT * FROM Citas WHERE id = :idUsuario")
    LiveData<List<CitaMedica>> getCitasMedicas(int idUsuario);


    //@Update


    //@Delete

}
