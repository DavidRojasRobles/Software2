package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Horario;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface HorarioDao {

    //@Insert

    //El horario de un Doctor es consultado por un paciente para ver la disponibilidad de citas
    @Query("SELECT * FROM Horarios WHERE doctor = :idDoctor and diaSemana = :diaSemana")
    LiveData<List<Horario>> getHorarioByDia(int idDoctor, String diaSemana);

    @Query("SELECT * FROM Horarios WHERE doctor = :idDoctor and diaSemana = :diaSemana and mananaTarde = :mananaTarde")
    LiveData<List<Horario>> getDispByDia(int idDoctor, String diaSemana, int mananaTarde);




    //@Update


    //@Delete
}
