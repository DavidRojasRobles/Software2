package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Evolucion;

import java.util.List;

@Dao
public interface EvolucionDao {

//    @Insert
//    Long[] agregarEvolucion(String texto);


    @Query("SELECT * FROM Evoluciones WHERE usuario = :userId")
    LiveData<List<Evolucion>> getEvolucionesUsuario(int userId);



    //@Update


    //@Delete
}
