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
 * Con procedimiento se refiere a exámenes, los cuales generan un diagnóstico
 * que se almacena en la historia clínica del paciente.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class OrdenProcedimiento extends Orden {

    private String Diagnostico;

    /**
     * Constructor para los objetos de la clase OrdenProcedimiento.
     */
    public OrdenProcedimiento(Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(usuario, cita, observaciones, fechaVigencia);
    }

}
