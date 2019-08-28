package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDate;

import java.io.Serializable;

/**
 * Esta clase representa las órdenes de medicamentos prescritas a los usuarios
 * por los doctores tras una cita médica.
 *
 * Cada una guarda el registro de si el medicamento fue reclamado o no.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */

@Entity(tableName = "OrdenMedicamento")
public class OrdenMedicamento implements Serializable {

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
    @ColumnInfo(name = "reclamado")
    private Boolean reclamado;

    /**
     * Constructor para los objetos de la clase OrdenMedicamentoS.
     */
    @Ignore
    public OrdenMedicamento(int id, String observaciones, LocalDate fechaVigencia, Boolean vigencia, int anio, int mes, int dia, Boolean reclamado) {
        this.id = id;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = vigencia;
        this.anio = anio;
        this.mes = mes;
        this.dia = dia;
        this.reclamado = reclamado;
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
    public Boolean getReclamado() {
        return reclamado;
    }

    public void setFechaVigencia(@NonNull LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public void setVigencia(@NonNull Boolean vigencia) {
        this.vigencia = vigencia;
    }

    public void setReclamado(@NonNull Boolean reclamado) {
        this.reclamado = reclamado;
    }
}
