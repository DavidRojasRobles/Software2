package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface EvolucionDao {

    @Insert
    void insertarEvolucion(Evolucion... evoluciones);

//    @Query("SELECT * FROM Evoluciones WHERE usuario = :userId")
//    LiveData<List<Evolucion>> getEvolucionesUsuario(int userId);

    @Update
    int updateEvolucion(Evolucion... evoluciones);

    @Delete
    int deleteEvolucion(Evolucion... evoluciones);

}
