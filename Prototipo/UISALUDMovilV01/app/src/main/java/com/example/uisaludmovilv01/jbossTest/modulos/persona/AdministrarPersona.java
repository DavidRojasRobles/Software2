package com.example.uisaludmovilv01.jbossTest.modulos.persona;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.AdministrarNotificaciones;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.INotificacionDAO;

public class AdministrarPersona {

	/**
	 * Obtiene la edad del paciente a partir del id
	 * 
	 * @param idPersona
	 * @return edad
	 * @throws Exception
	 */
	public static int getEdadPersona(int idPersona) throws Exception {
		int edad = -1;
		try {
			Date fechaNac = IPersonaDAO.getFechaNacPaciente(idPersona);
			if (fechaNac != null)
				edad = calcularEdad(fechaNac);

		} catch (Exception e) {
			throw new Exception(e);
		}
		return edad;
	}

	/**
	 * Calcula la edad del paciente a partir de su fecha de nacimiento
	 * 
	 * @param fechaNac
	 * @return
	 * @throws Exception
	 */
	public static int calcularEdad(Date fechaNac) throws Exception {
		int edad = -1;
		try {
			if (fechaNac != null) {
				Date today = new Date();
				Date cumpleanios = new Date(today.getYear(), fechaNac.getMonth(), fechaNac.getDate());
				edad = today.getYear() - fechaNac.getYear();
				if (today.before(cumpleanios)) {
					edad--;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return edad;
	}

	/**
	 * Comprueba la validez de afiliacion del afiliado revisando su plan y
	 * estado de afiliacion. De no ser valido, esnvía una solicitud de
	 * notificación correspondiente y elimina el token de la base de datos
	 * 
	 * @param idPersona
	 * @return valido: Boolean
	 * @throws Exception
	 */
	public static Boolean getValidezAfiliado(int idPersona) throws Exception {

		Integer plan = null;
		Integer estado = null;
		Boolean valido = null;
		boolean notificado = false;
		boolean eliminado = false;
		try {
			HashMap<String, Integer> planEstado = IPersonaDAO.getPlanEstadoAfiliado(idPersona);

			if (planEstado != null) {
//				plan = 1; //QUITAR. ESTO ES SOLO PARA PRUEBAS.
				plan = planEstado.get("plan");
				estado = planEstado.get("estado");
				
			}

			// Si es valido
			if (plan != null && plan == IConstantes.PLAN_AFIL_FULL && estado != null
					&& estado == IConstantes.ESTADO_AFILIADO)
				valido = true;
			else {
				// Si el plan no es FULL se notifica
				if (plan != null && plan != IConstantes.PLAN_AFIL_FULL) {
					notificado = AdministrarNotificaciones.notificarAfilInvalido(idPersona);
					if (notificado) {
						eliminado = INotificacionDAO.dropTokenBD(idPersona);

					}
				}

				valido = false;
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		System.out.println("getValidezAfil: valido = " + valido);
		return valido;
	}

}
