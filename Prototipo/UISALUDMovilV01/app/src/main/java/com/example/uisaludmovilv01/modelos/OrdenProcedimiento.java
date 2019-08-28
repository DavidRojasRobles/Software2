package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDate;

import java.io.Serializable;

/**
 * Esta clase representa las órdenes de procedimientos prescritas a los usuarios
 * por los doctores tras una cita médica.
 *
 * Con procedimiento se refiere a exámenes y otras citas médicas que requieren
 * una orden, como una cita con un especialista.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */

@Entity(tableName = "OrdenProcedimiento")
public class OrdenProcedimiento implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "observaciones")
    private String observaciones;

    @NonNull
    @ColumnInfo(name = "fechavigencia")
    private LocalDate fechaVigencia;

    @NonNull
    @ColumnInfo(name = "vigencia")
    private Boolean vigencia;

    @NonNull
    @ColumnInfo(name = "anio")
    private int anio;

    @NonNull
    @ColumnInfo(name = "mes")
    private int mes;

    @NonNull
    @ColumnInfo(name = "dia")
    private int dia;

    @NonNull
    @ColumnInfo(name = "especialidad")
    private String especialidad;

    /**
     * Constructor para los objetos de la clase OrdenProcedimiento.
     */
    @Ignore
    public OrdenProcedimiento(int id, String observaciones, LocalDate fechaVigencia, Boolean vigencia, int anio, int mes, int dia, String especialidad) {
        this.id = id;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = vigencia;
        this.anio = anio;
        this.mes = mes;
        this.dia = dia;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getObservaciones() {
        return observaciones;
    }

    @NonNull
    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    @NonNull
    public Boolean getVigencia() {
        return vigencia;
    }

    public int getAnio() {
        return anio;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    @NonNull
    public String getEspecialidad() {
        return especialidad;
    }

    public void setObservaciones(@NonNull String observaciones) {
        this.observaciones = observaciones;
    }

    public void setFechaVigencia(@NonNull LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public void setVigencia(@NonNull Boolean vigencia) {
        this.vigencia = vigencia;
    }

    public void setEspecialidad(@NonNull String especialidad) {
        this.especialidad = especialidad;
    }
}

