/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;

/**
 * Esta clase representa las órdenes de Remisión prescritas a los usuarios
 * por los doctores tras una cita médica.
 *
 * Con remisión se refiere a una cita medica de cualquier especialidad.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class OrdenRemision extends Orden {
    
    /**
     * Constructor para los objetos de la clase OrdenRemision.
     */
    public OrdenRemision(Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(usuario, cita, observaciones, fechaVigencia);
    }

}
