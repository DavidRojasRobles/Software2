package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface UsuarioDao {

    @Insert
    long[] insertarUsuario(Usuario... usuarios);

    @Query("DELETE FROM Agenda")
    void deleteAgenda();

    @Query("DELETE FROM Doctores")
    void deleteDoctor();

    @Query("DELETE FROM Especialidades")
    void deleteEspecialidad();

    @Query("DELETE FROM Evoluciones")
    void deleteEvoluciones();

    @Query("DELETE FROM Horarios")
    void deleteHorarios();

    @Query("DELETE FROM Ordenes")
    void deleteOrdenes();

    @Query("DELETE FROM Procedimientos")
    void deleteProcedimientos();

    @Query("DELETE FROM Usuarios")
    void deleteUsuarios();

    @Query("SELECT * FROM Usuarios")
    LiveData<List<Usuario>> getUsuarios();

    @Query("SELECT * FROM Usuarios WHERE id = :id")
    LiveData<Usuario> getUnUsuario(int id);

    @Query("DELETE FROM Usuarios")
    void dropUsuarios();

    @Update
    int updateUsuario(Usuario... usuarios);

    @Delete
    int deleteUsuario(Usuario... usuarios);

}
