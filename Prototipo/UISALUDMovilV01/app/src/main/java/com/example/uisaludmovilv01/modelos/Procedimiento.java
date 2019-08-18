package com.example.uisaludmovilv01.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.Serializable;

/**
 * Esta clase representa los procedimientos que puede agendar el usuario.
 *
 * Depende de un usuario, una fecha, una hora, un doctor. Pueden o no requerir
 * una orden medica. Los procedimientos generan un informe (como resultados de
 * examenes) que se archivan en la historia clínica del usuario.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Procedimiento implements Serializable {

    private Usuario usuario;
    private LocalDate fecha;
    private LocalTime hora;
    private Doctor doctor;
    private Orden orden;

    /**
     * Constructor para los objetos de la clase Procedimiento.
     */
    public Procedimiento(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor, Orden orden) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.doctor = doctor;
        this.orden = orden;
    }

    /**
     * Constructor para los objetos de la clase Procedimiento que no requieran
     * una orden.
     */
    public Procedimiento(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.doctor = doctor;
    }




    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Orden getOrden() {
        return orden;
    }

    /**
     * Retorna cadena con los datos de la cita.
     *
     * @return datos de la cita médica como único String
     */
    public String getDatos() {
        String datos = "Especialidad: " + doctor.getEspecialidad() + "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + hora + "\n"
                + "Consultorio: " + doctor.getConsultorio() + "\n"
                + "Doctor: " + doctor.getNombre() + "\n\n";

        return datos;
    }

    /**
     * Archiva un informe en la historia clínica del usuario.
     */
    public void archivarInforme(String informe) {
        usuario.archivar(getDatos() + informe);
    }

    /**
     * Imprime un recordatorio cuando el momento de la cita se aproxima.
     */
    public void notificar() {
//        LocalDate today = LocalDate.now();
//        if(fecha.compareTo(today) == 0){
//            System.out.println("Usted tiene una cita de " + doctor.getEspecialidad() + " a las " + hora + " en el consultorio " + doctor.getEspecialidad() + " de BU.");
//        }
    }
}
