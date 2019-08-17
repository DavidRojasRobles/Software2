package com.example.uisaludmovilv01.modelos;

import org.threeten.bp.LocalDate;

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
public class Orden {

    private CitaMedica cita;
    private String observaciones;
    private LocalDate fechaVigencia;
    private Boolean vigencia;

    /**
     * Constructor para los objetos de la clase Orden.
     */
    public Orden(CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        this.cita = cita;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = true;
    }

    public Boolean getVigencia() {
        return vigencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Usuario getUsuario() {
        return cita.getUsuario();
    }

    public CitaMedica getCita() {
        return cita;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    /**
     * Retorna cadena con los datos de la orden.
     *
     * @return datos de la orden como único String
     */
    public String getDatos() {
        String datos = "Doctor: " + cita.getDoctor().getNombre() + "\n"
                + "Especialidad Dr: " + cita.getDoctor().getEspecialidad() + "\n"
                + "Ordenado: " + cita.getFecha() + "\n"
                + "Observaciones: " + observaciones + "\n"
                + "Vigente hasta: " + fechaVigencia + "\n\n";

        return datos;
    }

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
