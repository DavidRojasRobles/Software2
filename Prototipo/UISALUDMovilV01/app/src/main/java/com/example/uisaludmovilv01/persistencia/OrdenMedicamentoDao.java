package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.OrdenMedicamento;

import java.util.List;

@Dao
public interface OrdenMedicamentoDao {

    //@Insert


    @Query("SELECT * FROM OrdenMedicamento WHERE id = :idUsuario")
    LiveData<List<OrdenMedicamento>> consultarOrdenesMedicamento(int idUsuario);


    //@Update


    //@Delete
}
