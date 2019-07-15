/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;

/**
 * Esta clase representa las ordenes prescritas a los usuarios por los doctores
 * tras una cita médica.
 *
 * Cada orden cuenta con una fecha máxima de vigencia y un campo de
 * observaciones escritas por el médico.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Orden {

    private final Usuario usuario;
    private final CitaMedica cita;
    private final String observaciones;
    private final LocalDate fechaVigencia;
    private final Boolean vigencia;

    /**
     * Constructor para los objetos de la clase Orden.
     */
    public Orden(Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia) {
        this.usuario = usuario;
        this.cita = cita;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = true;
    }

    public Boolean getVigencia() {
        return vigencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public CitaMedica getCita() {
        return cita;
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
