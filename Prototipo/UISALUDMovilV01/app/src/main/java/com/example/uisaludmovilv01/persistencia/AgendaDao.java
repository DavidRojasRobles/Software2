package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Agenda;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface AgendaDao {

    //@Insert

    @Query("SELECT * FROM Agenda WHERE doctor = :idDoctor and fecha = :fecha")
    LiveData<List<Agenda>> consultarAgenda(int idDoctor, LocalDate fecha);

    //@Update

    //@Delete
}
