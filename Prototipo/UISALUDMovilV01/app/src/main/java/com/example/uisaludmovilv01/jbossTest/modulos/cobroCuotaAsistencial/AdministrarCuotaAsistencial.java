package com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.cita.AdministrarCita;
import com.example.uisaludmovilv01.jbossTest.modulos.cita.ICitaDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.persona.AdministrarPersona;

public class AdministrarCuotaAsistencial {

	/**
	 * Crea un cobro de cuota asistencial en la base de datos
	 *
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public static boolean insertarCobroCa(int idPersona, int esp, String fecha, String hora,
			Integer consecutivoAtencion, double valorCobro) throws Exception {
		System.out.println("insertarCobroCa: called");
//		if (consecutivoAtencion != null)
//			System.out.println("insertarCobroCa: consecutivoAtencion = " + consecutivoAtencion);
//		else
//			System.out.println("insertarCobroCa: consecutivoAtencion is null");

		List<Object> parametros = new ArrayList<>();
		Map<String, Object> info = new HashMap<>();
		int idCobro = -1;
		boolean existe = false;
		boolean done = false;
		boolean duplicadoAud = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {
			Date date = IConstantes.SQL_DATE_FORMAT.parse(fecha);
			Time time = Helper.dateToTimeSec(IConstantes.SQL_HOUR_FORMAT.parse(hora));
			Integer atencion = (consecutivoAtencion != null && consecutivoAtencion > 0) ? consecutivoAtencion : null;


			parametros.add(idPersona);
			parametros.add(esp);
			parametros.add(date);
			parametros.add(time);
			parametros.add("S");
			parametros.add(atencion);

			Integer id = ICuotaAsistencialDAO.maxIdCobro();
			if (id != null) {

				info = new HashMap<>();
				idCobro = id + 1;

				info.put("id_cobro_cuota_asistencial", idCobro);
				info.put("id_persona", idPersona);
				info.put("id_especialidad", esp);

//				System.out.println("fecha = " + fecha);
//				System.out.println("time = " + time);
				info.put("id_fecha_cita", date);
				info.put("id_hora_cita", time);
				info.put("clase_cobro", "S");
				info.put("origen_cobro", null);
				info.put("consecutivo_atencion", atencion);
				info.put("valor", valorCobro);
				info.put("reportado_nomina", "N");
				info.put("anulada", "N");
				info.put("causa_anulacion", null);
				Map<String, Object> map = IConsultasDAO.finalColumns(info, 'I');
				info = (map != null) ? map : info;

				done = conexion.insertarBD("cobro_cuota_asistencial", info);
				

			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return done;
	}

	/**
	 * Consulta si un paciente está excento del cobro de cuota asistencial según
	 * el reglamento de UISALUD
	 *
	 * @param idPersona
	 * @param tipoCita
	 * @param consecutivaRemision
	 * @param tipoRemision
	 * @return exento
	 * @throws Exception
	 */
	public static boolean cumpleExcepcionCobro(int idPersona, int tipoAtencion, int tipoCita,
			Integer consecutivoRemision, Integer consecutivoAtencion) throws Exception {

		boolean exento = false;
		try {

			int edad = AdministrarPersona.getEdadPersona(idPersona);
			// Si edad < 1
			boolean menorAnio = (edad >= 0 && edad < 1) ? true : false;

			// Si control dentro de los 15 días después de remitido
			boolean controlRemitido = (tipoAtencion == 2 || tipoAtencion == 3)
					? AdministrarCita.validarTiempoControl(consecutivoRemision) : false;

			// Si tipoAtencion es pyp
			boolean pyp = (tipoCita == 3 || tipoCita == 9);

			// Si el paciente es de alto costo
			boolean altoCosto = ICuotaAsistencialDAO.comprobarAltoCosto(idPersona, consecutivoAtencion);

			exento = (menorAnio || controlRemitido || pyp || altoCosto);

		} catch (Exception e) {
			throw new Exception(e);

		}
		return exento;

	}

	/**
	 * Elimina un cobro de cuota asistencial en la base de datos
	 * 
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public static boolean anularCobroCA(int idPersona, int esp, String fecha, String hora, Integer consecutivoAtencion,
			int tipoAtencion) throws Exception {
		System.out.println("anularCobroCA called");

		List<Object> parametros = new ArrayList<>();
		Map<String, Object> campos = null;
		Map<String, Object> condiciones = null;
		Integer idCobro = null;
		boolean done = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {
//			Date date = IConstantes.SQL_DATE_FORMAT.parse(fecha);
//			Time time = Helper.dateToTimeSec(IConstantes.SQL_HOUR_TO_SEC_FORMAT.parse(hora));
			// Integer atencion = (consecutivoAtencion < 0 ? null :
			// consecutivoAtencion);

			idCobro = ICuotaAsistencialDAO.buscarCobro(Helper.addQuotes(fecha), Helper.addQuotes(hora), idPersona, esp,
					IConstantes.CLASE_COBRO_SERVICIO, consecutivoAtencion); // si
																			// el
																			// cobro
																			// existe,
																			// idCobro
																			// >=
																			// 0

			if (idCobro != null && idCobro > 0) {
				System.out.println("idCobro = " + idCobro);
				// String fecha = year + "-" + month + "-" + day;
				// String hora = hour + ":" + min;

				campos = new HashMap<>();
				campos.put("anulada", "S");
				campos.put("causa_anulacion", IConstantes.CAUSA_ANULACION_COBRO);
				Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'U');
				campos = (map != null) ? map : campos;

				condiciones = new HashMap<>();
				condiciones.put("id_cobro_cuota_asistencial", idCobro);
				condiciones.put("id_persona", idPersona);
				condiciones.put("id_especialidad", esp);
				condiciones.put("id_fecha_cita", fecha);
				condiciones.put("id_hora_cita", hora);

				done = conexion.actualizarBD("cobro_cuota_asistencial", campos, condiciones);

				// String sql = "UPDATE cobro_cuota_asistencial" + " SET anulada
				// = 'S'," + " causa_anulacion = \'"
				// + IConstantes.CAUSA_ANULACION_COBRO + "\'," +
				// finalColumns('U')
				// + " WHERE id_cobro_cuota_asistencial = " + idCobro;
				//
				// System.out.println(sql);
				//
				// done = conexion.executeBD(sql);

				// USELESS TRIGGER. ANOTHER ALREADY EXISTS IN THE DB. SHOULD
				// ALSO BE
				// EXECUTED BEFORE THE UPDATE.
				// if (done){
				// boolean duplicadoAud = IConsultasAudDAO.duplicarCobroAsisAud(
				// idCobro, idPersona, esp, Helper.fechaToString(fecha),
				// Helper.TimeToString(time), 'U');
				// System.out.println("anularCobroCA: duplicadoAud = " +
				// duplicadoAud);
				// }
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			// if (rs != null) {
			// rs.close();
			// }

			conexion.cerrarConexion();

		}
		return done;
	}
	
	/**
	 * Crea un registro en total_servicio_procedimiento
	 *
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	
	public static boolean insertarTotalServProc(int idPersona, int esp, String fecha, String hora,
			int tipoAtencion, String estadoCita, String controlCobro) throws Exception {
		

		List<Object> parametros = new ArrayList<>();
		Map<String, Object> info = new HashMap<>();
		boolean done = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {

				info = new HashMap<>();

				info.put("id_persona", idPersona);
				info.put("id_especialidad", esp);
				info.put("id_fecha_cita", fecha);
				info.put("id_hora_cita", hora);
				info.put("tipo_atencion", tipoAtencion);
				info.put("estado_cita", estadoCita);
				info.put("control_cobro", controlCobro);
				Map<String, Object> map = IConsultasDAO.finalColumns(info, 'I');
				info = (map != null) ? map : info;

				done = conexion.insertarBD("total_servicio_procedimiento", info);
				
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return done;
	}

	/**
	 * Crea un cobro de cuota asistencial en la base de datos
	 *
	 * @param parametros
	 * @return
	 * @throws Exception
	 */
	public static boolean actualizarTotalServProc(int idPersona, int esp, String fecha, String hora, String estadoCita,
			String controlCobro) throws Exception {
		boolean done = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {
			if (controlCobro != null)
				controlCobro = Helper.removeQuotes(controlCobro);

			Map<String, Object> campos = new HashMap<>();

//			System.out.println("actualizarTotalServProc: estadoCita = " + estadoCita);
			campos.put("estado_cita", estadoCita);
			
			//añade cobro si es null o distinto a "I"
			if(controlCobro != null){
				if(!controlCobro.equals(IConstantes.IGNORAR))
					campos.put("control_cobro", controlCobro);
				
			}else campos.put("control_cobro", controlCobro);
			
			Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'U');
			campos = (map != null) ? map : campos;

			Map<String, Object> condiciones = new HashMap<>();
			condiciones.put("id_persona", idPersona);
			condiciones.put("id_fecha_cita", fecha);
			condiciones.put("id_hora_cita", hora);
			condiciones.put("id_especialidad", esp);

			done = conexion.actualizarBD("total_servicio_procedimiento", campos, condiciones);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			conexion.cerrarConexion();
		}
		return done;
	}

}
