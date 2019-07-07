/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;

/**
 *
 * @author DELL
 */
public class OrdenRemision extends Orden {

    public OrdenRemision(Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        super(usuario, cita, observaciones, fechaVigencia);
    }

}
