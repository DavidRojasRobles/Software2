/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author DELL
 */
public class Usuario {

    private final String nombre;
    private final String cedula;
    private final String direccion;
    private final String telefono;
    private ArrayList<CitaMedica> citas = new ArrayList<>();
    private ArrayList<Orden> ordenes = new ArrayList<>();

    /**
     * Constructor for objects of class Usuario
     */
    public Usuario(String nombre, String cedula, String direccion, String telefono) {
        // initialise instance variables
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    //Imprime datos personales del usuario
    public String getDatosPersonales() {

        String datos = "Nombre: " + getNombre() + "\n"
                + "Cedula: " + getCedula() + "\n"
                + "Direccion: " + getDireccion() + "\n"
                + "Telefono: " + getTelefono() + "\n\n";

        return datos;

    }

    //Devuelve todas las citas
    public void consultarCitas() {
        if(citas.isEmpty()) System.out.println("El usuario " + nombre + " no tiene citas reservadas\n\n");
        else{
            for(CitaMedica c: citas){
                System.out.println(c.getDatos());
            }
        }
    }

    public ArrayList<CitaMedica> consultarCitas(String filtro) {

        ArrayList<CitaMedica> citasFiltradas = new ArrayList<CitaMedica>();
        //Filtrar citas

        return citasFiltradas;
    }

    public void solicitarCita(Usuario user, LocalDate fecha, LocalTime hora, Doctor doctor) {

        CitaMedica cita = new CitaMedica(user, fecha, hora, doctor);
        citas.add(cita);
    }

    public void cancelarCita(CitaMedica cita) {
        citas.remove(cita);
    }

}
