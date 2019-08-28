package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Procedimiento;

import java.util.List;

@Dao
public interface ProcedimientoDao {

    //@Insert


    @Query("SELECT * FROM Procedimientos WHERE id = :idUsuario")
    LiveData<List<Procedimiento>> consultarProcedimientos(int idUsuario);


    //@Update


    //@Delete
}
