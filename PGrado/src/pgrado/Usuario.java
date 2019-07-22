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
 * Esta clase gestiona las funciones del usuario de UISALUD.
 *
 * UISalud Móvil permite a los usuarios de UISALUD gestionar sus citas médicas y
 * consultar sus datos personales.
 *
 * Esta clase principal inicaliza todas las demás. Inicializa los datos de
 * prueba (por el momento)
 *
 * NOTA: La vesión actual está incompleta.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Usuario {

    private final String nombre;
    private final String cedula;
    private final String direccion;
    private final String telefono;
    private ArrayList<CitaMedica> citas = new ArrayList<>(); //citas pendientes
    private ArrayList<Orden> ordenes = new ArrayList<>();
    private ArrayList<String> historia = new ArrayList<>();

    /**
     * Constructor para objetos de la clase Usuario.
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

    public ArrayList<String> getHistoria() {
        return historia;
    }

    /**
     * Retorna cadena con los datos personales del usuario.
     *
     * @return datos personales de usuario como único String
     */
    public String getDatosPersonales() {

        String datos = "Nombre: " + getNombre() + "\n"
                + "Cedula: " + getCedula() + "\n"
                + "Direccion: " + getDireccion() + "\n"
                + "Telefono: " + getTelefono() + "\n\n";

        return datos;

    }

    public void archivar(String informe) {
        historia.add(informe);
    }

    public void ordenar(Orden orden) {
        ordenes.add(orden);
    }

    /**
     * Imprime lista de citas pendientes sin filtrar.
     */
    public void consultarCitas() {
        if (citas.isEmpty()) {
            System.out.println("El usuario " + nombre + " no tiene citas reservadas\n\n");
        } else {
            for (CitaMedica c : citas) {
                System.out.println(c.getDatos());
            }
        }
    }

    /**
     * Imprime lista de citas pendientes con filtro.
     *
     * @param filtro La palabra con la que se filtran las citas pendientes.
     *
     * @return Las citas pendientes filtradas.
     */
    public ArrayList<CitaMedica> consultarCitas(String filtro) {

        ArrayList<CitaMedica> citasFiltradas = new ArrayList<CitaMedica>();
        //Filtrar citas

        return citasFiltradas;
    }

    /**
     * Crea la cita médica y la añade a la lista de citas pendientes.
     */
    public void solicitarCita(Usuario user, LocalDate fecha, LocalTime hora, Doctor doctor) {

        CitaMedica cita = new CitaMedica(user, fecha, hora, doctor);
        citas.add(cita);
    }

    /**
     * Elimina una cita de la lista de citas pendientes.
     *
     * @param cita La cita médica a eliminar de las citas pendientes.
     */
    public void cancelarCita(CitaMedica cita) {
        citas.remove(cita);
    }

}
