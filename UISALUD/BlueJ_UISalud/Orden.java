import java.time.LocalDate;

/**
 * Esta clase representa las ordenes prescritas a los usuarios por los doctores
 * tras una cita médica.
 *
 * Cada orden cuenta con una fecha máxima de vigencia y un campo de
 * observaciones escritas por el médico. Estas observaciones pueden corresponder
 * al nombre de algún medicamento, un exámem que se debe realizar o la
 * especialidad a la cual se remite al usuario.
 *
 * @author Marianne Solangel Rojas Robles & Fredy Emanuel Mogollón Velandia
 * @version 14 / 07 / 2019
 */
public class Orden {

    private final Procedimiento cita;
    private final String observaciones;
    private final LocalDate fechaVigencia;
    private final Boolean vigencia;

    /**
     * Constructor para los objetos de la clase Orden.
     */
    public Orden(Procedimiento cita, String observaciones, LocalDate fechaVigencia) {
        this.cita = cita;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
        this.vigencia = true;
    }

    public Boolean getVigencia() {
        return vigencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Usuario getUsuario() {
        return cita.getUsuario();
    }

    public Procedimiento getCita() {
        return cita;
    }

    /**
     * Retorna cadena con los datos de la orden.
     *
     * @return datos de la orden como único String
     */
    public String getDatos() {
        String datos = "Doctor: " + cita.getDoctor()
                + "Especialidad: " + cita.getDoctor().getEspecialidad() + "\n"
                + "Ordenado: " + cita.getFecha() + "\n"
                + "Observaciones: " + observaciones + "\n"
                + "VIgente hasta: " + fechaVigencia + "\n\n";

        return datos;
    }
}