import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;


/**
 * Write a description of class SetUp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SetUp
{
    

    /**
     * Crea datos iniciales para pruebas. Demo final probablemente no crearĂ¡ los
     * datos iniciales de esta forma.
     */
    SetUp() {
    }
    
    public static ArrayList<Doctor> crearDoctores(){
        ArrayList<Doctor> doctores = new ArrayList<>();
        
        Doctor d1 = new Doctor("Dr. One", "101", "General");
        d1.anadirDia("MONDAY", new boolean[]{true, true, false, false, false,
            false, false, false, false, false, false});
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
        return doctores;
    }
    
    public static TreeSet<String> crearEspecialidades(ArrayList<Doctor> doctores){
    TreeSet<String> especialidades = new TreeSet<>();
     for (int i = 0; i < doctores.size(); i++) {
            especialidades.add(doctores.get(i).getEspecialidad());
        }   
        return especialidades;
    }
    
    // public void run(){
        
        // Usuario user0 = afiliados.get(0);
        // Usuario user1 = afiliados.get(1);
        // Usuario user2 = afiliados.get(2);
        
        // user0.consultarOrdenes();
        
        // System.out.println("-------------");
        
        // CitaMedica cita = new CitaMedica(user0, LocalDate.of(2019,7,22), LocalTime.of(8,0), doctores.get(0));
        // // user0.solicitarCita(doctores, especialidades);
        // user0.ordenar(new OrdenProcedimiento(cita,"Cardiologia", "Meh observaciones card", LocalDate.of(2019,10,20)));
        // user0.ordenar(new OrdenProcedimiento(cita,"Ginecologia", "Meh observaciones gin", LocalDate.of(2019,10,30)));
        // user0.ordenar(new OrdenProcedimiento(cita,"Cardiologia", "Meh observaciones card2", LocalDate.of(2019,10,21)));
        
        // user0.consultarOrdenes();
        
        // user1.solicitarCita(doctores, especialidades);
        // user2.solicitarCita(doctores, especialidades);
    // }
    /*
    public void crearUsuarios(){
        Usuario user1 = new Usuario("User1", "123", "Cll 1 #2-3", "1234");
        Usuario user2 = new Usuario("User1", "123", "Cll 1 #2-3", "1234");
        Usuario user3 = new Usuario("User2", "234", "Cll 2 #3-4", "2345");
        Usuario user4 = new Usuario("User3", "345", "Cll 3 #4-5", "3456");
        Usuario user5 = new Usuario("User4", "456", "Cll 4 #5-6", "4567");
        Usuario user6 = new Usuario("User5", "567", "Cll 5 #6-7", "5678");
    }*/
    
    
}