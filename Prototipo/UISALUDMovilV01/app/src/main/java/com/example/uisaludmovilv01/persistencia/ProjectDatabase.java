package com.example.uisaludmovilv01.persistencia;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;


@Database(entities = {Agenda.class, Doctor.class, Especialidad.class, Evolucion.class, Horario.class, Orden.class, Procedimiento.class, Usuario.class},
        version = 2)
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public abstract class ProjectDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "project_db";
    private static ProjectDatabase instance;
    static ProjectDatabase getInstance(final Context contexto){
        if(instance == null){
            instance = Room.databaseBuilder(
                    contexto.getApplicationContext(),
                    ProjectDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }


    public abstract AgendaDao getAgendaDao();
    public abstract DoctorDao getDoctorDao();
    public abstract EspecialidadDao getEspecialidadDao();
    public abstract EvolucionDao getEvolucionDao();
    public abstract HorarioDao getHorarioDao();
    public abstract OrdenDao getOrdenDao();
    public abstract ProcedimientoDao getProcedimientoDao();
    public abstract UsuarioDao getUsuarioDao();

    //USUARIOS
//    public abstract UsuarioDao getUsuarioDao();
//    public abstract ProcedimientoDao getCitasMedicasDao(int idUsuario);
//    public abstract HorarioDao getHorarioDao(int idDoctor, LocalDate fecha);
//    public abstract OrdenDao getOrdenesMedicamentoDao(int idUsuario);
//    public abstract OrdenDao getOrdenesProcedimientoDao(int idUsuario);
//    public abstract ProcedimientoDao getProcedimientoDao(int idUsuario);

    //DOCTORES
//    public abstract DoctorDao getDoctoresDao();
//    public abstract AgendaDao getAgendaDao(int idDoctor, LocalDate fecha);
//
//    //ESPECIALIDADES
//    public abstract EspecialidadDao getEspecialidadesDao();
}