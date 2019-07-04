package pgrado;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
import java.util.*;
import java.time.LocalDate; // import the LocalDate class

public class CitaMedica
{
    // instance variables - replace the example below with your own
    private String nombre;
    private String cedula;
    private Date fecha;
    private String hora;
    private String doctor;
    private int consultorio;
    private String especialidad;

    /**
     * Constructor for objects of class CItaMedica
     */
    public CitaMedica(String nombre, String cedula, Date fecha, String hora, String doctor, int consultorio, String especialidad)
    {
        // initialise instance variables
        this.nombre = nombre;
        this.cedula = cedula;
        this.fecha = fecha;
        this.hora = hora;
        this.doctor = doctor;
        this.consultorio = consultorio;
        this.especialidad = especialidad;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void notificar()
    {
        // put your code here
        Date today = new Date();
        if(equals(today == this.fecha)){
            System.out.println("Usted tiene una cita de " + this.especialidad + " a las " + this.hora + " en el consultorio " + this.consultorio + " de BU.");
        }
    }
}

