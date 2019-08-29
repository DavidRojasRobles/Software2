package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

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

    public LiveData<List<Usuario>> encontrarUsuario(int id){
        return mProjectDatabase.getUsuarioDao().getUnUsuario(id);
    }

//    public LiveData<List<Horario>> consultarHorario(int idDoctor, LocalDate fecha) {
//        return mProjectDatabase.getHorarioDao(idDoctor, fecha).consultarHorario(idDoctor, fecha);
//    }
//    public LiveData<List<Orden>> consultarOrdenesMedicamento(int idUsuario) {
//        return mProjectDatabase.consultarOrdenesMedicamentoDao(idUsuario).consultarOrdenesMedicamento(idUsuario);
//    }
//    public LiveData<List<Orden>> consultarOrdenesProcedimiento(int idUsuario) {
//        return mProjectDatabase.consultarOrdenesProcedimientoDao(idUsuario).consultarOrdenesProcedimiento(idUsuario);
//    }
//    public LiveData<List<Procedimiento>> consultarProcedimientos(int idUsuario) {
//        return mProjectDatabase.consultarProcedimientoDao(idUsuario).consultarProcedimientos(idUsuario);
//    }

    //DOCTORES
    public void agregarDoctor(Doctor doctor){}

    public void modificarDoctor(Doctor doctor){}

    public void borrarDoctor(Doctor doctor){}

//    public LiveData<List<Agenda>> consultarAgenda(int idDoctor, LocalDate fecha){
//        return mProjectDatabase.consultarAgendaDao(idDoctor, fecha).consultarAgenda(idDoctor, fecha);
//    }
//    public LiveData<List<Doctor>> consultarDoctores() {
//        return mProjectDatabase.consultarDoctoresDao().consultarDoctores();
//    }

    public LiveData<Doctor> encontrarDoctor(int id) {
        return mProjectDatabase.getDoctorDao().getUnDoctor(id);
    }

    public LiveData<String> getNombreDoctor(int doctorId) {
        return mProjectDatabase.getDoctorDao().getNombreDoctor(doctorId);
    }

    public void agregarCitaMedica(int cita){}

    public void borrarCitaMedica(int cita){}

//    public LiveData<List<Procedimiento>> consultarCitasMedicas(int idUsuario){
//        return mProjectDatabase.consultarCitasMedicasDao(idUsuario).consultarCitasMedicas(idUsuario);
//    }
//
//    //ESPECIALIDADES
//    public LiveData<List<Especialidad>> consultarEspecialidades(){
//        return mProjectDatabase.consultarEspecialidadesDao().consultarEspecialidades();
//    }

    //Procedimientos
    public LiveData<Procedimiento> getProcedimientoById(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosById(id);
    }

    public LiveData<List<Procedimiento>> getProcedimientosUsuario(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosUsuario(id);
    }

    public LiveData<List<Procedimiento>> getProcedimientosDoctor(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosDoctor(id);
    }


    //EVOLUCIONES

    public LiveData<List<Evolucion>> getEvolucionesUsuario(int userId){
        return mProjectDatabase.getEvolucionDao().getEvolucionesUsuario(userId);
    }


    //ORDENES

    public LiveData<List<Orden>> getOrdenesUsuario(int userId){
        return mProjectDatabase.getOrdenDao().getOrdenesUsuario(userId);
    }


}
