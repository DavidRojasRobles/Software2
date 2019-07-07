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
public class Orden {
    
    private final Usuario usuario;
    private final CitaMedica cita;
    private final String observaciones;
    private final LocalDate fechaVigencia;
    private final Boolean vigencia;
    
    public Orden(Usuario usuario, CitaMedica cita, String observaciones, LocalDate fechaVigencia){
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
    
    public void notificar(){
        java.time.LocalDate today = java.time.LocalDate.now();
        if(fechaVigencia.compareTo(today) == 0){
            //NOTIFY
        }
    }    
}
