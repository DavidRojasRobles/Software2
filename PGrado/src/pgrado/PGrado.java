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

/**
 *
 * @author DELL
 */
public class PGrado {

    /**
     * @param args the command line arguments
     */
    
    private static ArrayList<Usuario> afiliados = new ArrayList<>();
    private static ArrayList<Doctor> doctores = new ArrayList<>();
    private static HashMap<String, String> directorio = new HashMap<>(); 
    
    public static void onStart(){
        doctores.add(new Doctor("Dr. One", "101", "General"));
        doctores.add(new Doctor("Dr. Two", "102", "Cardiologia"));
        doctores.add(new Doctor("Dr. Three", "103", "Odontologia"));
        doctores.add(new Doctor("Dr. Four", "104", "Ginecologia"));
        doctores.add(new Doctor("Dr. Five", "104", "General"));
        doctores.add(new Doctor("Dr. Six", "104", "Ginecologia"));
        
        directorio.put(doctores.get(0).getNombre(), doctores.get(0).getEspecialidad());
        directorio.put(doctores.get(1).getNombre(), doctores.get(1).getEspecialidad());
        directorio.put(doctores.get(2).getNombre(), doctores.get(2).getEspecialidad());
        directorio.put(doctores.get(3).getNombre(), doctores.get(3).getEspecialidad());
        directorio.put(doctores.get(4).getNombre(), doctores.get(4).getEspecialidad());
        directorio.put(doctores.get(5).getNombre(), doctores.get(5).getEspecialidad());
        
        afiliados.add(new Usuario("User1", "123", "Cll 1 #2-3", "1234"));
        afiliados.add(new Usuario("User2", "234", "Cll 2 #3-4", "2345"));
        afiliados.add(new Usuario("User3", "345", "Cll 3 #4-5", "3456"));
        afiliados.add(new Usuario("User4", "456", "Cll 4 #5-6", "4567"));
        afiliados.add(new Usuario("User5", "567", "Cll 5 #6-7", "5678"));
        
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        onStart();
        
        Usuario ana = afiliados.get(0);
        Usuario lin = afiliados.get(1);

        
        ana.solicitarCita(ana, LocalDate.of(2019,7,10), LocalTime.of(14,30), doctores.get(0));
        
        ana.consultarCitas();
        lin.consultarCitas();

    }

}
