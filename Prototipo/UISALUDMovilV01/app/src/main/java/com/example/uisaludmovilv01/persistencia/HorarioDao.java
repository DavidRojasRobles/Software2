package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface HorarioDao {

    @Insert
    void insertarHorario(Horario... horarios);

    //El horario de un Doctor es consultado por un paciente para ver la disponibilidad de citas
    @Query("SELECT * FROM Horarios WHERE doctor = :idDoctor and dia_semana = :diaSemana")
    LiveData<List<Horario>> getHorarioByDia(int idDoctor, String diaSemana);

    @Update
    int updateHorario(Horario... horarios);

    @Delete
    int deleteHorario(Horario... horarios);

}
