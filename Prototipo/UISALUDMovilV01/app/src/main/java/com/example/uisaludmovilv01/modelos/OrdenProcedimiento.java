package com.example.uisaludmovilv01.modelos;

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
public class OrdenProcedimiento extends Orden implements Serializable {

    private String especialidad;

    /**
     * Constructor para los objetos de la clase OrdenProcedimiento.
     */
    public OrdenProcedimiento(CitaMedica cita, String especialidad, String observaciones, LocalDate fechaVigencia) {
        super(cita, observaciones, fechaVigencia);

        this.especialidad = especialidad.substring(0, 1).toUpperCase()
                + especialidad.substring(1);
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
