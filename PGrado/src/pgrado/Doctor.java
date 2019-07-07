/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

/**
 *
 * @author DELL
 */
public class Doctor {
    private String nombre;
    private String consultorio;
    private String especialidad;

    public Doctor(String nombre, String consultorio, String especialidad) {
        this.nombre = nombre;
        this.consultorio = consultorio;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
