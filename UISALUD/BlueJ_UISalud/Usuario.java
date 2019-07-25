import static java.lang.Math.abs;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeSet;

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
    private ArrayList<Procedimiento> citas = new ArrayList<>(); //citas pendientes
    private ArrayList<Orden> ordenes = new ArrayList<>();
    private ArrayList<String> historia = new ArrayList<>();
    
    Scanner scan = new Scanner(System.in);
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
    
    public String imprimirHistoria(){
        String salida = "";
        for(int i=0; i<historia.size(); i++){
            salida = salida+historia.get(i)+"\n\n";
        }
        return salida;
    }

    public void ordenar(Orden orden) {
        ordenes.add(orden);
    }

    /**
     * Imprime lista de citas pendientes sin filtrar.
     */
    public void consultarCitas() {
        if (citas.isEmpty()) {
            System.out.println("El usuario " + nombre + " no tiene citas agendadas\n\n");
        } else {
            for (Procedimiento c : citas) {
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
     * Solicita los datos necesarios para crear la cita médica.
     */
    public void solicitarCita(ArrayList<Doctor> doctores, TreeSet<String> especialidades) {
        String esp;
        OrdenProcedimiento orden = null;
        Doctor doctor;
        LocalTime hora;
        LocalDate fecha;
        String conm = "n";

        System.out.println("Bienvenido, " + this.nombre + "!\n");

        do { // solicitar cita

            do { // tiene orden
                esp = verificarEspecialidad(especialidades);
                orden = verificarOrden(esp);

            } while (orden == null && !esp.equals("General"));

            doctor = selecDoctor(doctores, esp);

            doctor.printHorarioSemanal();
            do {
                fecha = selecFecha(doctor);
                hora = selecHora(doctor, fecha);
            } while (!doctor.verificarHora(fecha, hora));

            System.out.println("\nSu cita será: \n\nDoctor: " + doctor.getNombre()
                    + "\nFecha: " + fecha + "\nHora: " + hora);
            System.out.println("¿Desea agendar la cita?: s: Sí  n: No");
            conm = scan.nextLine();

            if (conm.equals("s")) {
                if (orden == null) {
                    AgendarCita(fecha, hora, doctor);
                } else {
                    AgendarCita(fecha, hora, doctor, orden);
                }
                System.out.println("\nCita agendada correctamente.\n");
            } else {
                System.out.println("No se agendó la cita.\n\n");
            }

        } while (!conm.equals("s"));
    }

    /**
     * Crea la cita médica, la añade a la lista de citas pendientes y modifica
     * la agenda del doctor.
     */


    private void AgendarCita(LocalDate fecha, LocalTime hora, Doctor doctor) {
        if (doctor.getEspecialidad().equals("General")) {
            Procedimiento c = new CitaMedica(this, fecha, hora, doctor);
            doctor.modificarAgenda(c, fecha, hora);
            citas.add(c);
        } else {
            System.out.println("La cita no fue agendada.\n");
            System.out.println("Necesita una orden para obtener citas con "
                    + "especialistas. Por favor, solicite una cita con un médico"
                    + "general para que lo remita.\n");
        }
    }

    /**
     * Crea la cita médica, la añade a la lista de citas pendientes y modifica
     * la agenda del doctor.
     */
    public void AgendarCita(LocalDate fecha, LocalTime hora, Doctor doctor, Orden orden) {
        if (orden != null) {
            Procedimiento c = new CitaMedica(this, fecha, hora, doctor);
            doctor.modificarAgenda(c, fecha, hora);
            citas.add(new CitaMedica(this, fecha, hora, doctor));
            ordenes.remove(orden);
        } else {
            System.out.println("La orden es nula");
        }
    }
    
    public void mostrarListaMedicamentos(){
        String salida = "";
        
        for(int i=0; i<ordenes.size(); i++){
            salida = salida+"\n\n"+(i+1)+"- "+ordenesPro().get(i);
        }
        System.out.println(salida);
    }
    
    public void reclamarMedicamento(){
        System.out.println("Elija el nro de orden de medicamento que desea reclamar: ");
        mostrarListaMedicamentos();
        int opc = scan.nextInt();
        historia.add("El medicamento:\n"+ordenesPro().get(opc-1).getObservaciones()+"\nFecha de Entrega: "+LocalDate.now()+"\nHora: "+LocalTime.now()+"\n");
        ordenes.remove(ordenesPro().get(opc-1));
        System.out.println("El Medicamento se entregó al usuario\n");
    }
    
    /**
     * Elimina una cita de la lista de citas pendientes.
     *
     * @param cita La cita médica a eliminar de las citas pendientes.
     */
    public void cancelarCita(CitaMedica cita) {
        citas.remove(cita);
    }

    /**
     * Filtra un listado de doctores por especialidad.
     *
     * @param ArrayList<Doctor> La lista de todos los doctores.
     * @param String La especialidad para filtrar.
     * @return ArrayList<Doctor> La lista de doctores filtrada por especialidad.
     */
    public static ArrayList<Doctor> filtrarEsp(ArrayList<Doctor> doctores, String esp) {

        ArrayList<Doctor> docEsp = new ArrayList<>();
        for (Doctor doc : doctores) {
            if (doc.getEspecialidad().equals(esp)) {
                docEsp.add(doc);
            }
        }
        return docEsp;
    }

    private OrdenProcedimiento verificarOrden(String esp) {
        OrdenProcedimiento orden = null;
        if (!esp.equals("General")) {
            for (OrdenProcedimiento ord : ordenesPro()) {
                if (ord.getEspecialidad().equals(esp)) {
                    orden = ord;
                }
            }
            if (orden == null) {
                System.out.println("\nNecesita una orden para obtener "
                        + "para citas con especialistas. Por favor, "
                        + "solicite una cita con un médico general para "
                        + "que lo remita.\n");
            }
        }
        return orden;
    }

    private ArrayList<OrdenProcedimiento> ordenesPro() {
        ArrayList<OrdenProcedimiento> ordenesPro = new ArrayList<>();

        for (Orden ord : ordenes) {
            if (ord.getClass() == OrdenProcedimiento.class) {
                ordenesPro.add((OrdenProcedimiento) ord);
            }
        }
        return ordenesPro;
    }

    private String verificarEspecialidad(TreeSet<String> especialidades) {
        String esp;
        do { // la especialidad existe
            System.out.println("Elija una especialidad: ");
            for (String e : especialidades) {
                System.out.println("- " + e);
            }
            esp = scan.nextLine();
            esp = esp.substring(0, 1).toUpperCase() + esp.substring(1).toLowerCase();
            System.out.println(esp);
        } while (!especialidades.contains(esp));

        return esp;
    }

    private Doctor selecDoctor(ArrayList<Doctor> doctores, String esp) {
        int d;
        ArrayList<Doctor> docs;
        do {
            System.out.println("\nElija un doctor (#): ");
            docs = filtrarEsp(doctores, esp);
            for (int i = 0; i < docs.size(); i++) {
                System.out.println((i + 1) + ". " + docs.get(i).getNombre());
            }
            d = scan.nextInt() - 1;
        } while (!(abs(d) < docs.size()));
        return docs.get(d);

    }

    private LocalDate selecFecha(Doctor doctor) {
        String f, h;
        LocalDate fecha;
        boolean valido = false;
        do {
            System.out.println("\nElija una fecha (dd/mm/aa): ");
            f = scan.nextLine();
            String[] farr = f.replaceAll("\\s+", "").split("/");

            fecha = LocalDate.of(
                    Integer.parseInt(farr[2]),
                    Integer.parseInt(farr[1]),
                    Integer.parseInt(farr[0]));
            if (doctor.verificarFecha(fecha)) {
                valido = true;
            } else {
                System.out.println("\nNo hay citas disponibles para esta fecha.\n\n");
            }

        } while (!valido);
        return fecha;
    }

    private LocalTime selecHora(Doctor doctor, LocalDate fecha) {
        System.out.println("\nPara este dia, las horas disponibles son: \n");
        System.out.println(doctor.disponibilidadStr(fecha));

        System.out.println("\nElija una hora:");
        String h = scan.nextLine();

        LocalTime hora = LocalTime.of(
                Integer.parseInt(h.replaceAll(":00", "")), 0);
        return hora;
    }

}