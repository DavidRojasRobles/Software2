/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Esta clase representa los doctores que prestan servicios en UISALUD.
 *
 * De cada doctor se almacena su nombre, consultorio, especialidad, su horario
 * de atención semanal y la agenda que tiene.
 *
 * Este horario se guarda como un HashMap que consta de una llave con el nombre
 * del día de la semana y un arreglo con 11 variables que corresponden a las
 * horas de atención (de 8 am a 6 pm).
 * 
 * La agenda se guarda como un HashMap que consta de una llace con una fecha y
 * un arreglo boolean con 11 variables que corresponden a las horas de atención.
 * Estas horas de atención dirán false si ya están agendadas.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Doctor {

    private String nombre;
    private String consultorio;
    private String especialidad;
    private HashMap<String, boolean[]> horario = new HashMap<>();
//    private Agenda agenda;
    private HashMap<LocalDate, boolean[]> agenda = new HashMap<>();
    
    

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
    public void printHorarioSemanal() {

        for (HashMap.Entry<String, boolean[]> entry : horario.entrySet()) {
            String dia = entry.getKey();
            boolean[] horas = entry.getValue();

            String formatoHoras = parseHorario(horas);
            System.out.println(dia + ": " + formatoHoras);
        }
    }
    
    /**
     * Devuelve un String con las horas de atención en formato de reloj digital.
     * 
     * @param arreglo bool con las 11 horas de trabajo posibles. Dirá true si
     * esta disponible para agendar.
     * 
     * @return String con horas en formato de reloj digita.
     */    
    public String parseHorario(boolean[] horas){
        String formatoHoras = "";
            for (int i = 0; i < horas.length; i++) {
                if (horas[i] == true) {
                    formatoHoras += (8 + i) + ":00\t";
                }
            }
        return formatoHoras;
    }
    
    private void enterAgenda(LocalDate fecha){
        if(!agenda.containsKey(fecha)){
            agenda.put(fecha, horario.get(fecha.getDayOfWeek().toString()).clone());
        }
    }
    
    /**
     * Agenda las citas del medico.
     * 
     * Si la fecha no se encuentra en la agenda, se crea una nueva entrada con
     * las horas que el medico atiende.
     * 
     * Cuando la fecha ya existe, se reserva la hora cambiando el campo a false.
     */
    public void modificarAgenda(LocalDate fecha, LocalTime hora){
        enterAgenda(fecha);
        //Revisa si es un horario de trabajo del doctor
        if(horario.get(fecha.getDayOfWeek().toString())[(hora.getHour())-8] == true)
                agenda.get(fecha)[hora.getHour()-8] = false;
    }
    
    /**
     * Cancela las citas del medico.
     * 
     * Se cancela la cita cambiando el campo de la hora a false.
     */
    public void cancelar(LocalDate fecha, LocalTime hora){
        if(agenda.containsKey(fecha) &&
                horario.get(fecha.getDayOfWeek().toString())[hora.getHour()-8] == true){
                agenda.get(fecha)[hora.getHour()-8] = true;
        }
    }
    
    /**
     * Devuelve los horarios disponibles de un médico en cierta fecha.
     * 
     * @param fecha en la que se desea consultar la disponibilidad.
     * 
     * @return arreglo con el horario de atención del doctor esa fecha. Dirá
     * true si está disponible.
     */
    public boolean[] disponibilidad(LocalDate fecha){
        enterAgenda(fecha);
        return agenda.get(fecha);
    }
    
    /**
     * Devuelve los horarios disponibles de un médico en cierta fecha como Str.
     * 
     * @param fecha en la que se desea consultar la disponibilidad como String.
     * 
     * @return arreglo con el horario de atención del doctor esa fecha. Dirá
     * true si está disponible.
     */
    public String disponibilidadStr(LocalDate fecha){
        return parseHorario(disponibilidad(fecha));
    }
    
    public boolean verificarFecha(LocalDate fecha){
        return horario.containsKey(fecha.getDayOfWeek().toString());
    }
    
    public boolean verificarHora(LocalDate fecha, LocalTime hora){
        return true;
    }
}