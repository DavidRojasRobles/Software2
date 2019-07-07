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
import java.time.LocalDate; // import the LocalLocalDate class
import java.time.LocalTime;

public class CitaMedica
{
    // instance variables - replace the example below with your own
    private final Usuario usuario;
    private final LocalDate fecha;
    private final LocalTime hora;
    private final Doctor doctor;

    /**
     * Constructor for objects of class CItaMedica
     */
    
    public CitaMedica(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor){
        // initialise instance variables
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.doctor = doctor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    public LocalTime getHora() {
        return hora;
    }


    public Doctor getDoctor() {
        return doctor;
    }
    
    public String getDatos(){
        String datos = "Especialidad: " + doctor.getEspecialidad()+ "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + hora + "\n"
                + "Consultorio: " + doctor.getConsultorio() + "\n" + 
                "Doctor: " + doctor.getNombre() + "\n\n";

        return datos;
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
        LocalDate today = LocalDate.now();
        if(fecha.compareTo(today) == 0){
            System.out.println("Usted tiene una cita de " + doctor.getEspecialidad() + " a las " + hora + " en el consultorio " + doctor.getEspecialidad() + " de BU.");
        }
    }
}

