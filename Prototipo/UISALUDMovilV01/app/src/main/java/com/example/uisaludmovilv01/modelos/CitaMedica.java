package com.example.uisaludmovilv01.modelos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

/**
 * Esta clase representa las citas médicas asignadas a usuarios.
 *
 * Depende de un usuario, una fecha, una hora y un doctor. Si no se tiene una
 * orden, sólo se pueden crear citas con un médico general.
 *
 * Las citas médicas pueden o no generar órdenes.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class CitaMedica extends Procedimiento {

    /**
     * Constructor para los objetos de la clase CitaMedica con orden.
     */
    public CitaMedica(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor, Orden orden) {
        super(usuario, fecha, hora, doctor, orden);
    }

    /**
     * Constructor para los objetos de la clase CitaMedica sin orden.
     */
    public CitaMedica(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor) {
        super(usuario, fecha, hora, doctor);
    }

    /**
     * Genera una órden y la registra en la lista de órdenes del usuario y en su
     * historia clínica.
     */
    public void generarOrden(String observaciones, LocalDate fechaVigencia) {
        Orden orden = new Orden(this, observaciones, fechaVigencia);
        getUsuario().ordenar(orden);
        getUsuario().archivar(orden.getDatos());
    }
}
