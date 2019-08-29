package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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

@Entity(tableName = "Procedimientos")
public class Procedimiento implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "tipo")
    private int tipo; // 0 si es cita medica general; 1 si es cita especializada y 2 si es procedimiento (Examen médico)

    @NonNull
    @ColumnInfo(name = "usuario")
    private int usuario;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @ColumnInfo(name = "orden")
    private int orden;

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    @NonNull
    @ColumnInfo(name="hora")
    private LocalTime hora;

    @ColumnInfo(name = "especialidad")
    private String especialidad;

    /**
     * Constructor para los objetos de la clase Procedimiento.
     */
    @Ignore
    public Procedimiento(int tipo, int usuario, int doctor, int orden, @NonNull LocalDate fecha, @NonNull LocalTime hora, String especialidad) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.doctor = doctor;
        this.orden = orden;
        this.fecha = fecha;
        this.hora = hora;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getDoctor() {
        return doctor;
    }

    public int getOrden() {
        return orden;
    }

    @NonNull
    public LocalDate getFecha() {
        return fecha;
    }

    @NonNull
    public LocalTime getHora() {
        return hora;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setFecha(@NonNull LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(@NonNull LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Retorna cadena con los datos de la cita.
     *
     * @return datos de la cita médica como único String
     */
//    public String getDatos() {
//        String datos = "Especialidad: " + doctor.getEspecialidad() + "\n"
//                + "Fecha: " + fecha + "\n"
//                + "Hora: " + hora + "\n"
//                + "Consultorio: " + doctor.getConsultorio() + "\n"
//                + "Doctor: " + doctor.getNombre() + "\n\n";
//
//        return datos;
//    }

    /**
     * Archiva un informe en la historia clínica del usuario.
     */
    public void archivarInforme(int usuario, String informe) {
        //usuario.archivar(getDatos() + informe);
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
