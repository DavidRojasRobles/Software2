package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UsuarioDao {

    //@Insert


    @Query("SELECT * FROM Usuarios")
    LiveData<List<Usuario>> getUsuarios();

    @Query("SELECT * FROM Usuarios WHERE id = :id")
    LiveData<List<Usuario>> getUnUsuario(int id);


    //@Update


    //@Delete

}
