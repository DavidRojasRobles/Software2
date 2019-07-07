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
public class OrdenMedicamento extends Orden {

    private Boolean reclamado;

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
