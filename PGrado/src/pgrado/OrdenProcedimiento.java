/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;

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
public class OrdenProcedimiento extends Orden {

    /**
     * Constructor para los objetos de la clase OrdenProcedimiento.
     */
    public OrdenProcedimiento(CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(cita, observaciones, fechaVigencia);
    }

}
