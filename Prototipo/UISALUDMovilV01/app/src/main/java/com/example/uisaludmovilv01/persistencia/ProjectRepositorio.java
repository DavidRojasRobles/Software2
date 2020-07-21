package com.example.uisaludmovilv01.persistencia;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.uisaludmovilv01.async.InsertAgendaAsyncTask;
import com.example.uisaludmovilv01.async.InsertDoctorAsyncTask;
import com.example.uisaludmovilv01.async.InsertEspecialidadAsyncTask;
import com.example.uisaludmovilv01.async.InsertEvolucionAsyncTask;
import com.example.uisaludmovilv01.async.InsertHorarioAsyncTask;
import com.example.uisaludmovilv01.async.InsertOrdenAsyncTask;
import com.example.uisaludmovilv01.async.InsertProcedimientoAsyncTask;
import com.example.uisaludmovilv01.async.InsertUsuarioAsyncTask;
import com.example.uisaludmovilv01.modelos.Agenda;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Especialidad;
import com.example.uisaludmovilv01.modelos.Evolucion;
import com.example.uisaludmovilv01.modelos.Horario;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.List;

@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class ProjectRepositorio {

    private static final String TAG = "ProjectRepositorio";

    private ProjectDatabase mProjectDatabase;

    public ProjectRepositorio(Context contexto){
        mProjectDatabase = ProjectDatabase.getInstance(contexto);
    }

    //MISC



    //USUARIOS
    public void insertarUsuarioTask(Usuario usuario) {
        new InsertUsuarioAsyncTask(mProjectDatabase.getUsuarioDao()).execute(usuario);
    }

    public LiveData<List<Usuario>> getUsuarios(){
        Log.i(TAG, "getUsuarios: called from ProjectRepositorio.");
        return mProjectDatabase.getUsuarioDao().getUsuarios();
    }

    public LiveData<Usuario> getUnUsuario(int userId){
        return mProjectDatabase.getUsuarioDao().getUnUsuario(userId);
    }

    //DOCTORES
    public void insertarDoctorTask(Doctor doctor) {
        new InsertDoctorAsyncTask(mProjectDatabase.getDoctorDao()).execute(doctor);
    }

    public LiveData<List<Doctor>> getDoctores() {
        return mProjectDatabase.getDoctorDao().getDoctores();
    }

    public LiveData<Doctor> getUnDoctor(int id) {
        return mProjectDatabase.getDoctorDao().getUnDoctor(id);
    }

    public LiveData<String> getNombreDoctor(int doctorId) {
        return mProjectDatabase.getDoctorDao().getNombreDoctor(doctorId);
    }

    public LiveData<List<Doctor>> getDoctoresEsp(String esp) {
        return mProjectDatabase.getDoctorDao().getDoctoresEsp(esp);
    }

    //PROCEDIMIENTOS
    public void insertarProcedimientoTask(Procedimiento procedimiento) {
        new InsertProcedimientoAsyncTask(mProjectDatabase.getProcedimientoDao()).execute(procedimiento);
    }

    public LiveData<Procedimiento> getProcedimientoById(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosById(id);
    }

    public LiveData<List<Procedimiento>> getProcedimientosUsuario(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosUsuario(id);
    }

    public LiveData<List<Procedimiento>> getProcedimientosDoctor(int id){
        return mProjectDatabase.getProcedimientoDao().getProcedimientosDoctor(id);
    }

    public LiveData<List<Procedimiento>> getProcedimientos(){
        return mProjectDatabase.getProcedimientoDao().getProcedimientos();
    }

    //AGENDAS
    public void insertarAgendaTask(Agenda agenda) {
        new InsertAgendaAsyncTask(mProjectDatabase.getAgendaDao()).execute(agenda);
    }

    public LiveData<List<Agenda>> getAgendaByFecha(int doctorId, String fecha){
        return mProjectDatabase.getAgendaDao().getAgendaByFecha(doctorId, fecha);
    }

    //EVOLUCIONES
    public void insertarEvolucionTask(Evolucion evolucion) {
        new InsertEvolucionAsyncTask(mProjectDatabase.getEvolucionDao()).execute(evolucion);
    }

    public LiveData<List<Evolucion>> getEvolucionesUsuario(int userId){
        return mProjectDatabase.getEvolucionDao().getEvolucionesUsuario(userId);
    }



    //ORDENES
    public void insertarOrdenTask(Orden orden) {
        new InsertOrdenAsyncTask(mProjectDatabase.getOrdenDao()).execute(orden);
    }

    public LiveData<List<Orden>> getOrdenesUsuario(int userId){
        return mProjectDatabase.getOrdenDao().getOrdenesUsuario(userId);
    }

    //HORARIO
    public void insertarHorarioTask(Horario horario) {
        new InsertHorarioAsyncTask(mProjectDatabase.getHorarioDao()).execute(horario);
    }

    public LiveData<List<Horario>> getHorarioByDia(int idDoctor, String diaSemana){
        return mProjectDatabase.getHorarioDao().getHorarioByDia(idDoctor, diaSemana);
    }


    //ESPECIALIDADES
    public void insertarEspecialidadTask(Especialidad especialidad) {
        new InsertEspecialidadAsyncTask(mProjectDatabase.getEspecialidadDao()).execute(especialidad);
    }

    public LiveData<List<Especialidad>> getEspecialidades(){
        return mProjectDatabase.getEspecialidadDao().getEspecialidades();
    }

    public LiveData<Especialidad> getEspecialidadById(int espId){
        return mProjectDatabase.getEspecialidadDao().getEspecialidadById(espId);
    }

    public LiveData<Especialidad> getEspecialidadByNombre(String espNombre){
        return mProjectDatabase.getEspecialidadDao().getEspecialidadByNombre(espNombre);
    }

}
