package com.example.uisaludmovilv01.modelos;

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
public class OrdenMedicamento extends Orden implements Serializable {

    private Boolean reclamado;

    /**
     * Constructor para los objetos de la clase OrdenMedicamentoS.
     */
    public OrdenMedicamento(CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(cita, observaciones, fechaVigencia);
        this.reclamado = false;
    }

    public Boolean getReclamado() {
        return reclamado;
    }

    public void setReclamado(Boolean reclamado) {
        this.reclamado = reclamado;
    }

}
