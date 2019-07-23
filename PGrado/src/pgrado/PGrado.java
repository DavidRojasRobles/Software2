/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Clase principal de la aplicación UISalud Móvil.
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
public class PGrado {

    private static ArrayList<Usuario> afiliados = new ArrayList<>();
    private static ArrayList<Doctor> doctores = new ArrayList<>();
    private static HashMap<Integer, String> directorio = new HashMap<>();

    /**
     * Crea datos iniciales para pruebas. Demo final probablemente no creará los
     * datos iniciales de esta forma.
     */
    public static void onStart() {
        Doctor d1 = new Doctor("Dr. One", "101", "General");
        d1.anadirDia("MONDAY", new boolean[]{true, true, true, false, false,
            false, false, false, true, true, false});
        d1.anadirDia("THURSDAY", new boolean[]{true, true, true, false, false,
            false, false, false, true, true, false});
        doctores.add(d1);

        Doctor d2 = new Doctor("Dr. Two", "102", "Cardiologia");
        d2.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        d2.anadirDia("WEDNESDAY", new boolean[]{true, true, true, true, true,
            false, false, false, false, false, false});
        doctores.add(d2);

        Doctor d3 = new Doctor("Dr. Three", "103", "Odontologia");
        d3.anadirDia("MONDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        d3.anadirDia("WEDNESDAY", new boolean[]{true, true, true, true, true,
            false, false, false, false, false, false});
        d3.anadirDia("FRIDAY", new boolean[]{true, true, true, true, true,
            false, false, false, false, false, false});
        doctores.add(d3);

        Doctor d4 = new Doctor("Dr. Four", "104", "Ginecologia");
        d4.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        d4.anadirDia("THURSDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        doctores.add(d4);

        Doctor d5 = new Doctor("Dr. Five", "104", "General");
        d5.anadirDia("WEDNESDAY", new boolean[]{true, true, true, false, false,
            false, false, false, true, true, false});
        d5.anadirDia("THURSDAY", new boolean[]{true, true, true, false, false,
            false, false, false, true, true, false});
        doctores.add(d5);

        Doctor d6 = new Doctor("Dr. Six", "104", "Ginecologia");
        d6.anadirDia("TUESDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        d6.anadirDia("THURSDAY", new boolean[]{false, false, false, false, false,
            false, true, true, true, true, true});
        doctores.add(d6);

        for (int i = 0; i < doctores.size(); i++) {
            directorio.put(i, doctores.get(i).getEspecialidad());
        }

        afiliados.add(new Usuario("User1", "123", "Cll 1 #2-3", "1234"));
        afiliados.add(new Usuario("User2", "234", "Cll 2 #3-4", "2345"));
        afiliados.add(new Usuario("User3", "345", "Cll 3 #4-5", "3456"));
        afiliados.add(new Usuario("User4", "456", "Cll 4 #5-6", "4567"));
        afiliados.add(new Usuario("User5", "567", "Cll 5 #6-7", "5678"));

    }

    /**
     * Filtra el directorio de doctores por especialidad.
     * 
     * @return lista con los doctores de dicha especialidad.
     */
    public static ArrayList<Doctor> filtrarEsp(HashMap map, String esp) {
        ArrayList<Doctor> docEsp = new ArrayList<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if (pair.getValue() == esp) {
//                System.out.println(doctores.get((Integer) pair.getKey()).getNombre());
                docEsp.add(doctores.get((Integer) pair.getKey()));
            }
            it.remove();
        }
        return docEsp;
    }

    /**
     * Imprime un HashMap.
     *
     * @param map El HashMap a imprimir.
     */
    public static void printDirectorio(HashMap<Integer, String> map) {

        for (HashMap.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    /**
     * Imprime una lista de doctores con sus horarios.
     *
     * @param ArrayList<Doctor> La lista de doctores a imprimir.
     */
    public static void printDoctores(ArrayList<Doctor> list) {

        for(Doctor i : list){
            System.out.println(i.getNombre() + " esta disponible los dias: ");
            i.printHorarioSemanal();
            System.out.println("------------");            
        }
    }

    
    
    public static void main(String[] args) {

        onStart();

//        Usuario ana = afiliados.get(0);
//        Usuario lin = afiliados.get(1);
//
//        ana.solicitarCita(ana, LocalDate.of(2019, 7, 10),LocalTime.of(14, 30), doctores.get(0));
//
//        ana.consultarCitas();
//        lin.consultarCitas();
//        filtrarEsp(directorio, "General");
//        LocalDate a = LocalDate.of(2014, 6, 30);
//
//        System.out.println(a.getDayOfWeek().name() == "MONDAY");
//
//        doctores.get(5).printHorario();
        
//        LocalTime b = LocalTime.of(10,0,0);
//        System.out.println(b);
//        System.out.println(b.getHour() == 10);
//        
//        Scanner scan = new Scanner(System.in);
//        Usuario ana = afiliados.get(0);
//        Set<String> especialidades = new HashSet<>(directorio.values());
//        //Solicitar cita
//        System.out.println("Digite especialidad que desea:");
//        for (int i = 0; i < especialidades.size(); i++) {
//            System.out.println("1. " + especialidades.hashCode());
//        }
//        printDoctores(filtrarEsp(directorio, "General"));
//        printMap(directorio);
        
        doctores.get(0).printHorarioSemanal();
        LocalDate fecha = LocalDate.of(2019, 7, 22);
        System.out.println("\n" + doctores.get(0).disponibilidad(fecha));
        
        int hora = 8; // La hora que quiere el usuario
        
        doctores.get(0).modificarAgenda(fecha, LocalTime.of(hora, 0)); 
        
        System.out.println("\n" + doctores.get(0).disponibilidad(fecha));

        doctores.get(0).cancelar(fecha, LocalTime.of(hora, 0));
        
        System.out.println("\n" + doctores.get(0).disponibilidad(fecha));
    }

}
