package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@Dao
public interface DoctorDao {

    //@Insert


    @Query("SELECT * FROM Doctores")
    LiveData<List<Doctor>> consultarDoctores();

    @Query("SELECT * FROM Doctores WHERE id = :id")
    LiveData<Doctor> getUnDoctor(int id);

    @Query("SELECT * FROM Doctores WHERE especialidad = :esp")
    LiveData<List<Doctor>> getDoctorEsp(String esp);

    @Query("SELECT nombre FROM Doctores WHERE id = :id")
    LiveData<String> getNombreDoctor(int id);


    //@Update


    //@Delete
}
