package com.example.uisaludmovilv01.jbossTest.modulos.remision;

import java.util.HashMap;
import java.util.Map;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;

public class AdministrarRemision {
	

	/**
	 * Modifica la cantidad de citas solicitadas de una remisión a especialidad
	 * interna en caso de solicitar o cancelar la cita médica correspondiente.
	 * 
	 * @param idPersona
	 *            : id de la persona a la que pertenece la remisiÓn
	 * @param consecutivoAtencion
	 *            : el consecutivo de la atencion
	 * @param esp
	 *            : id de la especialidad de la remision
	 * @param offset
	 *            : cantidad de citas a sumar o restar de la cantidad actual
	 * @param controlCobro
	 *            : 'S' para sí y 'N' para no en caso de ser una remisión de
	 *            control
	 * @return modificado : true si modificado exitosamente
	 * @throws Exception
	 */
	public static boolean editarSolicitadasRemision(int idPersona, int consecutivoAtencion, int esp, int offset,
			String controlCobro) throws Exception {
		System.out.println("editarSolicitadasRemision: called");

		Map<String, Object> campos = null;
		Map<String, Object> condiciones = null;
		Map<String, Object> cantidades = null;
		Conexion conexion = new Conexion("uisaludDS");
		boolean rs = false;
		try {

			cantidades = IRemisionDAO.getInfoModRemEsp(idPersona, consecutivoAtencion, esp);

			if (cantidades != null) {

				short citasRemitidas = (Short) cantidades.get("citasRemitidas");
				short citasSolicitadas = (Short) cantidades.get("citasSolicitadas");
				int tipoRemision = (Integer) cantidades.get("tipoRemision");
				int nuevaCantidad = citasSolicitadas + offset;

				if (Math.abs(offset) == 1 && nuevaCantidad <= citasRemitidas && nuevaCantidad >= 0) {

					campos = new HashMap<>();
					campos.put("cantidad_citas_solicitadas", nuevaCantidad);
					campos.put("control_cobro", controlCobro);
					Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'U');
					campos = (map != null) ? map : campos;

					condiciones = new HashMap<>();
					condiciones.put("id_persona", idPersona);
					condiciones.put("id_consecutivo_atencion", consecutivoAtencion);
					condiciones.put("id_especialidad_remision", esp);

					if (controlCobro != null) {
						condiciones.put("tipo_remision", IConstantes.REM_CONTROL);
					}

					rs = conexion.actualizarBD("remision_especialidad", campos, condiciones);
				}

			} else {
				rs = false;
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return rs;
	}

}
