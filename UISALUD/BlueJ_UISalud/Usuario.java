
/**
 * Write a description of class Usuario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.Date;

public class Usuario
{
    // instance variables - replace the example below with your own
    private String nombre;
    private String cedula;
    private String direccion;
    private String telefono;
    private ArrayList<CitaMedica> citas;
    private ArrayList<Orden> orden;

    /**
     * Constructor for objects of class Usuario
     */
    public Usuario(String nombre, String cedula, String direccion, String telefono)
    {
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
    
    public String consultar_datos_personales(){  

        String datos = "Nombre: " + getNombre() + "\n"
                        + "Cedula: " + getCedula() + "\n"
                        + "DIreccion: " + getDireccion() + "\n"
                        + "Telefono: " + getTelefono() + "\n"; 

        return datos; 

    }  
    //Devuelve todas las citas
    public ArrayList<CitaMedica> consultarCitas(){ 

        return citas; 

    } 
    
    public ArrayList<CitaMedica> consultarCitas(String filtro){ 
       
        ArrayList<CitaMedica> citasFiltradas = new ArrayList<CitaMedica>();
        //Filtrar citas

        return citasFiltradas; 
    }  
    
    public void solicitarCita(Usuario user, Date fecha, String hora, String doctor, int consultorio, String especialidad){
        
        CitaMedica cita = new CitaMedica(user.getNombre(), user.getCedula(), fecha, hora, doctor, consultorio, especialidad);
        citas.add(cita);
    }
    
    public void cancelarCita(CitaMedica cita){
        citas.remove(cita);
    }
    
    
}
