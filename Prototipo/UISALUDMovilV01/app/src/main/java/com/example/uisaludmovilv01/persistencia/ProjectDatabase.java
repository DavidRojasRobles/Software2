package com.example.uisaludmovilv01.persistencia;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.OrdenProcedimiento;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.time.LocalDate;

@Database(entities = {Agenda.class, CitaMedica.class, Doctor.class, Especialidad.class, Evolucion.class, Horario.class, OrdenMedicamento.class, OrdenProcedimiento.class, Procedimiento.class, Usuario.class}, version = 1)
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

    //USUARIOS
    public abstract UsuarioDao getUsuarioDao();
    public abstract CitaMedicaDao getCitaMedicaDao(int idUsuario);
    public abstract HorarioDao consultarHorarioDao(int idDoctor, LocalDate fecha);
    public abstract OrdenMedicamentoDao consultarOrdenesMedicamentoDao(int idUsuario);
    public abstract OrdenProcedimientoDao consultarOrdenesProcedimientoDao(int idUsuario);
    public abstract ProcedimientoDao consultarProcedimientoDao(int idUsuario);

    //DOCTORES
    public abstract DoctorDao consultarDoctoresDao();
    public abstract AgendaDao consultarAgendaDao(int idDoctor, LocalDate fecha);

    //ESPECIALIDADES
    public abstract EspecialidadDao consultarEspecialidadesDao();
}