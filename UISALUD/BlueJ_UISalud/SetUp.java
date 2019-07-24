
/**
 * Write a description of class SetUp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SetUp
{
    private static ArrayList<Usuario> afiliados = new ArrayList<>();
    private static ArrayList<Doctor> doctores = new ArrayList<>();
    private static TreeSet<String> especialidades = new TreeSet<>();

    /**
     * Crea datos iniciales para pruebas. Demo final probablemente no creará los
     * datos iniciales de esta forma.
     */
    SetUp() {
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

        for (int i = 0; i < doctores.size(); i++) {
            especialidades.add(doctores.get(i).getEspecialidad());
        }

        afiliados.add(new Usuario("User1", "123", "Cll 1 #2-3", "1234"));
        afiliados.add(new Usuario("User2", "234", "Cll 2 #3-4", "2345"));
        afiliados.add(new Usuario("User3", "345", "Cll 3 #4-5", "3456"));
        afiliados.add(new Usuario("User4", "456", "Cll 4 #5-6", "4567"));
        afiliados.add(new Usuario("User5", "567", "Cll 5 #6-7", "5678"));

    }
}
