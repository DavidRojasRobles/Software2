package com.example.uisaludmovilv01.modelos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.uisaludmovilv01.persistencia.LocalDateConverter;
import com.example.uisaludmovilv01.persistencia.LocalTimeConverter;

import org.threeten.bp.LocalDate;

import java.io.Serializable;

/**
 * Esta clase representa las ordenes prescritas a los usuarios por los doctores
 * tras una cita médica.
 *
 * Cada orden cuenta con una fecha máxima de vigencia y un campo de
 * observaciones escritas por el médico. Estas observaciones pueden corresponder
 * al nombre de algún medicamento, un exámem que se debe realizar o la
 * especialidad a la cual se remite al usuario.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */

@Entity(tableName = "Ordenes",
        foreignKeys = {
            @ForeignKey(
                    entity = Procedimiento.class,
                    parentColumns = "id",
                    childColumns = "cita"),
            @ForeignKey(
                    entity = Especialidad.class,
                    parentColumns = "id",
                    childColumns = "especialidad")
        })
@TypeConverters({LocalDateConverter.class, LocalTimeConverter.class})
public class Orden implements Serializable {

    public void setId(int id) {
        this.id = id;
    }

    public void setCita(int cita) {
        this.cita = cita;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "cita")
    private int cita;

    @NonNull
    @ColumnInfo(name = "tipo")
    private int tipo;  //0 si es de medicamento y 1 si es de procedimiento

    @NonNull
    @ColumnInfo(name = "observaciones")
    private String observaciones;

    @NonNull
    @ColumnInfo(name = "fechaVIgencia")
    @TypeConverters(LocalDateConverter.class)
    private LocalDate fechaVigencia;

    @NonNull
    @ColumnInfo(name = "vigencia")
    private Boolean vigencia;

    @ColumnInfo(name = "especialidad")
    private int especialidad;

    @ColumnInfo(name = "reclamado")
    private boolean reclamado;


    public Orden(int cita, int tipo, @NonNull String observaciones, @NonNull LocalDate fechaVigencia, @NonNull Boolean vigencia, int especialidad, boolean reclamado) {
        this.cita = cita;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = vigencia;
        this.especialidad = especialidad;
        this.reclamado = reclamado;
    }

    public int getId() {
        return id;
    }

    public int getCita() {
        return cita;
    }

    public int getTipo() {
        return tipo;
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

    public int getEspecialidad() {
        return especialidad;
    }

    public boolean isReclamado() {
        return reclamado;
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

    public void setReclamado(boolean reclamado) {
        this.reclamado = reclamado;
    }

/**
     * Constructor para los objetos de la clase Orden.
     */


    /**
     * Retorna cadena con los datos de la orden.
     *
     * @return datos de la orden como único String
     */
    /*public String getDatos() {
        String datos = "Doctor: " + cita.getDoctor().getNombre() + "\n"
                + "Especialidad Dr: " + cita.getDoctor().getEspecialidad() + "\n"
                + "Ordenado: " + cita.getFecha() + "\n"
                + "Observaciones: " + observaciones + "\n"
                + "Vigente hasta: " + fechaVigencia + "\n\n";

        return datos;
    }*/

    /**
     * Imprime un recordatorio cuando se aproxime la fecha máxima de vigencia de
     * la orden.
     */
    public void notificar() {
//        java.time.LocalDate today = java.time.LocalDate.now();
//        if(fechaVigencia.compareTo(today) == 0){
//            //NOTIFY
//        }
    }


}
