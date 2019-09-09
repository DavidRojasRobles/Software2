package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@Dao
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public interface DoctorDao {

    @Insert
    long[] insertarDoctor(Doctor... doctores);

    @Query("SELECT * FROM Doctores")
    LiveData<List<Doctor>> getDoctores();

    @Query("SELECT * FROM Doctores WHERE id = :id")
    LiveData<Doctor> getUnDoctor(int id);

    @Query("SELECT * FROM Doctores WHERE especialidad = :esp")
    LiveData<List<Doctor>> getDoctoresEsp(String esp);

    @Query("SELECT nombre FROM Doctores WHERE id = :id")
    LiveData<String> getNombreDoctor(int id);

    @Update
    int updateDoctor(Doctor... doctores);

    @Delete
    int deleteDoctor(Doctor... doctores);
}
