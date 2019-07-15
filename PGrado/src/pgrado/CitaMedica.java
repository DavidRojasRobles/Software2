package pgrado;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Esta clase representa las citas médicas asignadas a usuarios.
 *
 * Depende de un usuario, una fecha, una hora y un doctor.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class CitaMedica {

    private final Usuario usuario;
    private final LocalDate fecha;
    private final LocalTime hora;
    private final Doctor doctor;

    /**
     * Constructor para los objetos de la clase CitaMedica.
     */
    public CitaMedica(Usuario usuario, LocalDate fecha, LocalTime hora, Doctor doctor) {
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

    /**
     * Retorna cadena con los datos de la cita.
     *
     * @return datos de la cita médica como único String
     */
    public String getDatos() {
        String datos = "Especialidad: " + doctor.getEspecialidad() + "\n"
                + "Fecha: " + fecha + "\n"
                + "Hora: " + hora + "\n"
                + "Consultorio: " + doctor.getConsultorio() + "\n"
                + "Doctor: " + doctor.getNombre() + "\n\n";

        return datos;
    }

    /**
     * Imprime un recordatorio cuando el momento de la cita se aproxima.
     */
    public void notificar() {
//        LocalDate today = LocalDate.now();
//        if(fecha.compareTo(today) == 0){
//            System.out.println("Usted tiene una cita de " + doctor.getEspecialidad() + " a las " + hora + " en el consultorio " + doctor.getEspecialidad() + " de BU.");
//        }
    }
}
