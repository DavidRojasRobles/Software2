/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * Esta clase representa los doctores que prestan servicios en UISALUD.
 *
 * De cada doctor se almacena su nombre, consultorio, especialidad y su horario
 * de atención semanal.
 *
 * Este horario se guarda como un HashMap que consta de una llave con el nombre
 * del día de la semana y un arreglo con 10 variables que corresponden a las
 * horas de atención (de 8 am a 6 pm).
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Doctor {

    private String nombre;
    private String consultorio;
    private String especialidad;
    private HashMap<String, boolean[]> horario = new HashMap<>();
    private HashMap<LocalDate, LocalTime> agenda = new HashMap<>();
    

    /**
     * Constructor para los objetos de la clase Doctor.
     */
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

    /**
     * Añade un registro al horario de atención.
     *
     * @param key Dia de la semana a añadir.
     * @param horas Horas de 8:00 a 18:00, true si atiende.
     */
    public void anadirDia(String key, boolean[] horas) {
        horario.put(key, horas);
    }

    /**
     * Elimina un registro al horario de atención.
     *
     * @param key Dia de la semana a eliminar.
     */
    public void eliminarDia(String key) {
        horario.remove(key);
    }

    /**
     * Imprime el horario del médico en formato humano. Toma el arreglo de
     * valores boolean y les da un formato de horas de reloj digital.
     */
    public void printHorario() {

        for (HashMap.Entry<String, boolean[]> entry : horario.entrySet()) {
            String dia = entry.getKey();
            boolean[] horas = entry.getValue();

            String formatoHoras = "";
            for (int i = 0; i < horas.length; i++) {
                if (horas[i] == true) {
                    formatoHoras += (8 + i) + ":00\t";
                }
            }
            System.out.println(dia + ": " + formatoHoras);
        }
    }
    
    public void agendar(LocalDate fecha, LocalTime hora){
        agenda.put(fecha, hora);
    }
}
