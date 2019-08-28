package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.CitaMedica;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.OrdenProcedimiento;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import org.threeten.bp.LocalDateTime;

import java.time.LocalDate;
import java.util.List;

public class ProjectRepositorio {

    private ProjectDatabase mProjectDatabase;

    public ProjectRepositorio(Context contexto){
        mProjectDatabase = ProjectDatabase.getInstance(contexto);
    }


    //USUARIOS
    public void agregarUsuario(Usuario usuario){}
    public void modificarUsuario(Usuario usuario){}
    public void borrarUsuario(Usuario usuario){}
    public LiveData<List<Usuario>> consultarUsuarios(){
        return mProjectDatabase.getUsuarioDao().getUsuarios();
    }
    public LiveData<List<Horario>> consultarHorario(int idDoctor, LocalDate fecha) {
        return mProjectDatabase.consultarHorarioDao(idDoctor, fecha).consultarHorario(idDoctor, fecha);
    }
    public LiveData<List<OrdenMedicamento>> consultarOrdenesMedicamento(int idUsuario) {
        return mProjectDatabase.consultarOrdenesMedicamentoDao(idUsuario).consultarOrdenesMedicamento(idUsuario);
    }
    public LiveData<List<OrdenProcedimiento>> consultarOrdenesProcedimiento(int idUsuario) {
        return mProjectDatabase.consultarOrdenesProcedimientoDao(idUsuario).consultarOrdenesProcedimiento(idUsuario);
    }
    public LiveData<List<Procedimiento>> consultarProcedimientos(int idUsuario) {
        return mProjectDatabase.consultarProcedimientoDao(idUsuario).consultarProcedimientos(idUsuario);
    }

    //DOCTORES
    public void agregarDoctor(Doctor doctor){}
    public void modificarDoctor(Doctor doctor){}
    public void borrarDoctor(Doctor doctor){}
    public LiveData<List<Agenda>> consultarAgenda(int idDoctor, LocalDate fecha){
        return mProjectDatabase.consultarAgendaDao(idDoctor, fecha).consultarAgenda(idDoctor, fecha);
    }
    public LiveData<List<Doctor>> consultarDoctores() {
        return mProjectDatabase.consultarDoctoresDao().consultarDoctores();
    }
    public void agregarCitaMedica(CitaMedica cita){}
    public void borrarCitaMedica(CitaMedica cita){}
    public LiveData<List<CitaMedica>> consultarCitasMedicas(int idUsuario){
        return mProjectDatabase.getCitaMedicaDao(idUsuario).getCitasMedicas(idUsuario);
    }

    //ESPECIALIDADES
    public LiveData<List<Especialidad>> consultarEspecialidades(){
        return mProjectDatabase.consultarEspecialidadesDao().consultarEspecialidades();
    }


}
