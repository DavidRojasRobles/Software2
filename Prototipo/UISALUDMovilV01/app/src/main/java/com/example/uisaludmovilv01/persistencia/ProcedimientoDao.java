package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface ProcedimientoDao {

    @Insert
    void insertarProcedimiento(Procedimiento... procedimientos);

    @Query("SELECT * FROM Procedimientos WHERE usuario = :idUsuario")
    LiveData<List<Procedimiento>> getProcedimientosUsuario(int idUsuario);

    @Query("SELECT * FROM Procedimientos WHERE doctor = :idDoctor")
    LiveData<List<Procedimiento>> getProcedimientosDoctor(int idDoctor);

    @Query("SELECT * FROM Procedimientos WHERE usuario = :idUsuario and tipo in (0,1)")
    LiveData<List<Procedimiento>> getCitasMedicas(int idUsuario);

    @Query("SELECT * FROM Procedimientos WHERE usuario = :idUsuario and tipo = 2")
    LiveData<List<Procedimiento>> getProcedimientos(int idUsuario);

    @Query("SELECT * FROM Procedimientos WHERE id = :id")
    LiveData<Procedimiento> getProcedimientosById(int id);

    @Query("SELECT * FROM Procedimientos")
    LiveData<List<Procedimiento>> getProcedimientos();

    @Update
    int updateProcedimiento(Procedimiento... procedimientos);

    @Delete
    int deleteProcedimiento(Procedimiento... procedimientos);


}