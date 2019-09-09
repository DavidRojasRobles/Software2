package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface AgendaDao {

    @Insert
    void insertarAgenda(Agenda... agendas);

    @Query("SELECT * FROM Agenda WHERE doctor = :doctorId and fecha = :fecha")
    LiveData<List<Agenda>> getAgendaByFecha(int doctorId, String fecha);

    @Update
    int updateAgenda(Agenda... agendas);

    @Delete
    int deleteAgenda(Agenda... agendas);
}
