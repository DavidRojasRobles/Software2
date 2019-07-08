/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgrado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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
    private static HashMap<Integer, String> directorio = new HashMap<>();

    public static void onStart() {
        doctores.add(new Doctor("Dr. One", "101", "General"));
        doctores.add(new Doctor("Dr. Two", "102", "Cardiologia"));
        doctores.add(new Doctor("Dr. Three", "103", "Odontologia"));
        doctores.add(new Doctor("Dr. Four", "104", "Ginecologia"));
        doctores.add(new Doctor("Dr. Five", "104", "General"));
        doctores.add(new Doctor("Dr. Six", "104", "Ginecologia"));

        for (int i = 0; i < doctores.size(); i++) {
            directorio.put(i, doctores.get(i).getEspecialidad());
        }
        
        afiliados.add(new Usuario("User1", "123", "Cll 1 #2-3", "1234"));
        afiliados.add(new Usuario("User2", "234", "Cll 2 #3-4", "2345"));
        afiliados.add(new Usuario("User3", "345", "Cll 3 #4-5", "3456"));
        afiliados.add(new Usuario("User4", "456", "Cll 4 #5-6", "4567"));
        afiliados.add(new Usuario("User5", "567", "Cll 5 #6-7", "5678"));

    }
    
    public static void filtrarEsp(HashMap map, String esp){
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            if(pair.getValue() == esp)
//                map.put(pair.getKey().toString(), pair.getValue().toString());
            System.out.println(doctores.get((Integer)pair.getKey()).getNombre());
            it.remove();
        }
    }
     
    public static void printMap(HashMap<String, String> map){
        System.out.println(map.isEmpty());
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove();
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        onStart();

//        Usuario ana = afiliados.get(0);
//        Usuario lin = afiliados.get(1);
//
//        ana.solicitarCita(ana, LocalDate.of(2019, 7, 10), LocalTime.of(14, 30), doctores.get(0));
//
//        ana.consultarCitas();
//        lin.consultarCitas();

        filtrarEsp(directorio, "General");
        
        LocalDate a = LocalDate.of(2014, 6, 30);

        System.out.println(a.getDayOfWeek().name() == "MONDAY");

    }

}
