/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;

/**
 * Esta clase representa las órdenes de medicamentos prescritas a los usuarios
 * por los doctores tras una cita médica.
 *
 * Cada una guarda el registro de si el medicamento fue reclamado o no.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class OrdenMedicamento extends Orden {

    private Boolean reclamado;
    
    /**
     * Constructor para los objetos de la clase OrdenMedicamentoS.
     */
    public OrdenMedicamento(String estado, Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(usuario, cita, observaciones, fechaVigencia);
        this.reclamado = false;
    }

    public Boolean getReclamado() {
        return reclamado;
    }

    public void setReclamado(Boolean reclamado) {
        this.reclamado = reclamado;
    }

}
