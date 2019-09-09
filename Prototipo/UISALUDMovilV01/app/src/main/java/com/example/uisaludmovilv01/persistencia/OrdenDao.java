package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface OrdenDao {

    @Insert
    void insertarOrden(Orden... ordenes);

    @Query("SELECT * FROM Ordenes WHERE id = :idUsuario")
    LiveData<List<Orden>> getOrdenesUsuario(int idUsuario);

    @Query("SELECT * FROM Ordenes WHERE id = :idUsuario and tipo = 0")
    LiveData<List<Orden>> getOrdenesMedicamento(int idUsuario);

    @Query("SELECT * FROM Ordenes WHERE id = :idUsuario and tipo = 1")
    LiveData<List<Orden>> getOrdenesProcedimiento(int idUsuario);

    @Update
    int updateOrden(Orden... ordenes);

    @Delete
    int deleteOrden(Orden... ordenes);

}
