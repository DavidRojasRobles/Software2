package com.example.uisaludmovilv01.modelos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * Esta clase representa las citas médicas asignadas a usuarios.
 *
 * Depende de un usuario, una fecha, una hora y un doctor. Si no se tiene una
 * orden, sólo se pueden crear citas con un médico general.
 *
 * Las citas médicas pueden o no generar órdenes.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */

@Entity(tableName = "Citas")
public class CitaMedica implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "tipo")
    private int tipo;

    @NonNull
    @ColumnInfo(name = "usuario")
    private int usuario;

    @NonNull
    @ColumnInfo(name = "doctor")
    private int doctor;

    @NonNull
    @ColumnInfo(name = "especialidad")
    private String especialidad;

    @ColumnInfo(name = "orden")
    private int orden;

    @NonNull
    @ColumnInfo(name = "fecha")
    private LocalDate fecha;

    @NonNull
    @ColumnInfo(name="hora")
    private LocalTime hora;

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

    @NonNull
    public String getEspecialidad() {
        return especialidad;
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
     * Constructor para los objetos de la clase CitaMedica con orden.
     */
    @Ignore
    public CitaMedica(int usuario, LocalDate fecha, LocalTime hora, int doctor, int orden) {

    }

    /**
     * Constructor para los objetos de la clase CitaMedica sin orden.
     */
    @Ignore
    public CitaMedica(int usuario, LocalDate fecha, LocalTime hora, int doctor) {

    }



    /**
     * Genera una órden y la registra en la lista de órdenes del usuario y en su
     * historia clínica.
     */
    public void generarOrden(String observaciones, LocalDate fechaVigencia) {
        /*Orden orden = new Orden(this, observaciones, fechaVigencia);
        getUsuario().ordenar(orden);
        getUsuario().archivar(orden.getDatos());*/
    }
}
