package com.example.uisaludmovilv01.jbossTest.modulos.notificaciones;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.messaging.AndroidConfig.Priority;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;

public interface INotificacionDAO {

	/**
	 * Busca los tokens de registro de FCM de los pacientes que tienen cita el
	 * mismo día o al dia siguiente
	 * 
	 * @param manana:
	 *            true = las citas para el dia siguiente, false = las citas para
	 *            el mismo dia
	 * @return datos: Lista de array de String con el token, el nombre de la
	 *         especialidad y la hora de inicio de la cita
	 * @throws Exception
	 */
	public static List<Object[]> getTokensCitaProx(boolean manana) throws Exception {
		System.out.println("getTokensCuatroHoras: called. manana = " + manana);

		List<Object> parametros = new ArrayList<>();
		List<Object[]> datos = new ArrayList<>();
		String hora1 = null;
		String hora2 = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			Date f = new Date();

			if (manana) {
				f.setDate(f.getDate() + 1);
				hora1 = "\'6:00\'";
				hora2 = "\'9:00\'";
			} else {
				// Corre cada HH:55:00, asi que se suman 5 horas. A las 6:55 se
				// buscan las citas entre las 11 y las 11:59
				int t = f.getHours() + 5;

				hora1 = "\'" + t + ":00\'";
				hora2 = "\'" + (t + 1) + ":00\'";
			}
			// String fechaManana = "\'" + (f.getYear() + 1900) + "-" +
			// (f.getMonth() + 1) + "-" + (f.getDate()) + "\'";
			String fechaManana = Helper.addQuotes(IConstantes.SQL_DATE_FORMAT.format(f));

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT f.id_persona, f.fcm_token, e.nombre as nombre_esp, c.id_tipo_documento_profesional,"
					+ "c.id_documento_profesional, c.id_fecha, c.id_hora_inicio");
			sql.append(" FROM fcm_tokens f" + " JOIN cita c" + " ON c.persona_afiliado = f.id_persona"
					+ " JOIN especialidad e" + " ON c.especialidad = e.id_especialidad" + " WHERE c.id_fecha = "
					+ fechaManana + " AND (c.id_hora_inicio >= " + hora1 + " AND c.id_hora_inicio < " + hora2 + ")"
					+ " AND c.estado = 2");

			System.out.println("getTokensCuatroHoras: called. sql = " + sql);
			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				System.out.println("getTokensCuatroHoras: called. si hay citas");
				Object[] noti = new Object[4];
				noti[0] = (String) rs.getObject("fcm_token");
				noti[1] = (String) rs.getObject("nombre_esp");
				noti[2] = IConstantes.NORMAL_HOUR_FORMAT.format((Date) rs.getObject("id_hora_inicio"));

				Map<String, String> anexo = new HashMap<>();

//				anexo.put("idPersona", (Integer) rs.getObject("id_persona") + "");
				anexo.put("docProf", (BigDecimal) rs.getObject("id_documento_profesional") + "");
				anexo.put("tipoDocProf", (Short) rs.getObject("id_tipo_documento_profesional") + "");
				anexo.put("fechaCita", IConstantes.SQL_DATE_FORMAT.format((Date) rs.getObject("id_fecha")));
				anexo.put("horaCita", IConstantes.SQL_HOUR_FORMAT.format((Date) rs.getObject("id_hora_inicio")));

				noti[3] = anexo;

				datos.add(noti);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return datos;
	}

	/**
	 * Busca los tokens de registro de FCM de los pacientes que tienen fórmulas
	 * próximas a vencerse con medicamentos sin reclamar
	 * 
	 * @return tokens: lista de tokens
	 * @throws Exception
	 */
	public static List<Object[]> getTokensMedsPendientes() throws Exception {

		List<Object> parametros = new ArrayList<>();
		List<Object[]> datos = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			Date now = new Date();

			String hoy = Helper.fechaHoraCeroToString(now);
			now.setDate(now.getDate() - 1);
			String ayer = Helper.fechaHoraCeroToString(now);

			now.setDate(now.getDate() + 3);
			String tresDias = Helper.fechaHoraCeroToString(now);

			now.setDate(now.getDate() + 1);
			String cuatroDias = Helper.fechaHoraCeroToString(now);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT unique(f.id_numero_formula), fcm.id_persona, fcm.fcm_token");
			sql.append(" from fcm_tokens fcm" + " JOIN formula_medica f on f.persona = fcm.id_persona"
					+ " JOIN medicamento_formula m" + " ON m.id_numero_formula = f.id_numero_formula"
					+ " LEFT JOIN entrega_elemento_medicamento e"
					+ " ON (e.id_codigo_medicamento = m.id_codigo_medicamento"
					+ " AND e.id_numero_formula = m.id_numero_formula)" + " JOIN vademecum_institucional v"
					+ " ON  v.id_codigo_medicamento = m.id_codigo_medicamento"
					+ " WHERE (e.id_codigo_medicamento is null" + " OR e.indicativo_entregado = 'N')"
					+ " AND (( f.fecha_hora_nueva_entrega  >= " + tresDias + " AND f.fecha_hora_nueva_entrega < "
					+ cuatroDias + ") OR (f.fecha_hora_creacion_formula >=" + ayer
					+ " AND f.fecha_hora_creacion_formula < " + hoy + "))");

			System.out.println("getTokensMedsPendientes: sql = " + sql);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				Object[] noti = new Object[3];
				
				noti[0] = (String) rs.getObject("fcm_token");
				
				Map<String, String> anexo = new HashMap<>();
//				anexo.put("tipo", IConstantes.TIPO_NOTI_MED);
//				anexo.put("idPersona", (Integer) rs.getObject("id_persona") + "");
				anexo.put("numeroFormula", (Integer) rs.getObject("id_numero_formula") + "");
				anexo.put("codigoMedicamento", null);
				
				noti[1] = anexo;
				
				System.out.println("getTokensMedsPendientes: si hay meds");
				datos.add(noti);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return datos;
	}

	/**
	 * Busca los tokens de registro de FCM de un paciente
	 * 
	 * @return lista de tokens
	 * @throws Exception
	 */
	public static String getTokenPersona(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<>();
		String token = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fcm_token");
			sql.append(" from fcm_tokens WHERE id_persona = ?");
			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				token = (String) rs.getObject("fcm_token");
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return token;
	}

	/**
	 * Busca los tokens de registro de FCM de los pacientes que tienen una
	 * remision próxima a vencerse (en 5 y en 3 días)
	 * 
	 * @return datos: lista de arreglos de objetos con el token, el nombre de
	 *         especialidad o procedimiento y dias en los que se vencerá la
	 *         remisión
	 * @throws Exception
	 */
	public static List<Object[]> getTokensRemision() throws Exception {
		System.out.println("getTokensRemision: called");

		List<Object[]> datos = new ArrayList<>();
		try {
			Date tresDias = new Date();
			tresDias.setDate(tresDias.getDate() + IConstantes.DIAS_VENCE_REM_MIN);
			Date cincoDias = new Date();
			cincoDias.setDate(cincoDias.getDate() + IConstantes.DIAS_VENCE_REM_MAX);

			String fechaTresDias = Helper.fechaToString(tresDias);
			String fechaCincoDias = Helper.fechaToString(cincoDias);

			datos.addAll(getTokensRemisionesFecha(fechaTresDias, IConstantes.DIAS_VENCE_REM_MIN));
			datos.addAll(getTokensRemisionesFecha(fechaCincoDias, IConstantes.DIAS_VENCE_REM_MAX));

		} catch (Exception e) {
			throw new Exception(e);

		}
		return datos;
	}

	/**
	 * Consulta en la base de datos las remisiones de especialidad y de
	 * procedimiento que se vencerán en determinada fecha
	 * 
	 * @param fecha
	 *            : fecha en la que se vencerá la remisión
	 * @param diasFaltantes
	 *            : dias en los que se vencerá la remisión Se pasa como
	 *            parámetro para añadirlo a cada uno de los arreglos en la lista
	 * @return datos : lista de arreglos de objetos con el token, el nombre de
	 *         especialidad o procedimiento y dias en los que se vencerá la
	 *         remisión
	 * @throws Exception
	 */
	public static List<Object[]> getTokensRemisionesFecha(String fecha, int diasFaltantes) throws Exception {
		System.out.println("getTokensRemisionesFecha: called");
		List<Object> parametros = new ArrayList<>();
		List<Object[]> datos = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;

		try {

			// Remisiones Especialidad
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fcm.id_persona, fcm.fcm_token, e.nombre, re.id_consecutivo_atencion, re.id_especialidad_remision");
			sql.append(" FROM fcm_tokens fcm" + " JOIN remision_especialidad re" + " ON fcm.id_persona = re.id_persona"
					+ " JOIN especialidad e" + " ON e.id_especialidad = re.id_especialidad_remision"
					+ " JOIN remision r" + " ON re.consecutivo_remision = r.id_consecutivo_remision"
					+ " WHERE r.fecha_fin_vigencia = " + fecha
					+ " AND re.cantidad_citas_solicitadas < re.cantidad_citas_remitidas");

			rs = conexion.consultarBD(sql.toString(), parametros);
			System.out.println("getTokensRemisionesFecha: \n esp sql = " + sql);
			datos.addAll(getDatosRemNoti(rs, diasFaltantes, IConstantes.IND_REM_ESP));

			// Remisiones Procedimiento
			sql = new StringBuilder();
			sql.append(
					"SELECT fcm.id_persona, fcm.fcm_token, p.nombre, rp.id_consecutivo_atencion, rp.id_procedimiento, rp.id_consecutivo_remision_procedimiento");
			sql.append(" FROM fcm_tokens fcm" + " JOIN remision_procedimiento rp" + " ON fcm.id_persona = rp.id_persona"
					+ " JOIN procedimiento p" + " ON p.id_procedimiento = rp.id_procedimiento" + " JOIN remision r"
					+ " ON rp.consecutivo_remision = r.id_consecutivo_remision" + " WHERE fecha_fin_vigencia = "
					+ fecha);
			System.out.println("getTokensRemisionesFecha: \n proc sql = " + sql);

			rs = conexion.consultarBD(sql.toString(), parametros);
			datos.addAll(getDatosRemNoti(rs, diasFaltantes, IConstantes.IND_REM_PROC));

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return datos;

	}

	/**
	 * Extrae los datos de una consulta de remisiones proximas a vencerse
	 * 
	 * @param rs:
	 *            resultset de la consulta
	 * @param diasFaltantes
	 *            : dias en los que se vencerpa la remision
	 * @return datos : lista de arreglos de objetos con el token, el nombre de
	 *         la especialidad o procedimiento y los dias faltantes para el
	 *         vencimiento de la remision
	 * @throws Exception
	 */
	public static List<Object[]> getDatosRemNoti(ResultSet rs, int diasFaltantes, char indEspProc) throws Exception {
		List<Object[]> datos = new ArrayList<>();
		try {
			while (rs.next()) {
				Object[] noti = new Object[5];

				noti[0] = (String) rs.getObject("fcm_token");
				noti[1] = (String) rs.getObject("nombre");
				noti[2] = diasFaltantes;

				Map<String, String> anexo = new HashMap<>();

				if (indEspProc == IConstantes.IND_REM_ESP) {
//					anexo.put("tipo", IConstantes.TIPO_NOTI_REM_ESP);
					noti[3] = IConstantes.TIPO_NOTI_REM_ESP;
					anexo.put("idEspProc", (Integer) rs.getObject("id_especialidad_remision") + "");

				} else if (indEspProc == IConstantes.IND_REM_PROC) {
//					anexo.put("tipo", IConstantes.TIPO_NOTI_REM_PROC);
					noti[3] = IConstantes.TIPO_NOTI_REM_PROC;
					anexo.put("idEspProc", (Integer) rs.getObject("id_procedimiento") + "");
					anexo.put("consRemProc", (Integer) rs.getObject("id_consecutivo_remision_procedimiento") + "");
				} else
					return null;

//				anexo.put("idPersona", (Integer) rs.getObject("id_persona") + "");
				anexo.put("ctvAtencion", (Integer) rs.getObject("id_consecutivo_atencion") + "");

				noti[4] = anexo;

				datos.add(noti);

			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return datos;
	}

	/**
	 * Registra el token de un dispositivo o lo actualiza si ya existe
	 * 
	 * @param idPersona
	 * @param token
	 * @return ok : true si se incluyó/actualizó el token con éxito
	 * @throws Exception
	 */
	public static boolean onNewToken(int idPersona, String token) throws Exception {

		Map<String, Object> campos = new HashMap<>();
		char tipoTransaccion;
		boolean existe = false;
		boolean done = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {

			existe = verificarTokenDevice(idPersona);
			if (!existe) {
				tipoTransaccion = 'I';
				campos = new HashMap<>();

				campos.put("id_persona", idPersona);
				campos.put("fcm_token", token);
				Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'I');
				campos = (map != null) ? map : campos;

				done = conexion.insertarBD("fcm_tokens", campos);

			} else {

				tipoTransaccion = 'U';
				campos = new HashMap<>();

				campos.put("fcm_token", token);
				Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'U');
				campos = (map != null) ? map : campos;

				Map<String, Object> condiciones = new HashMap<>();
				condiciones.put("id_persona", idPersona);

				done = conexion.actualizarBD("fcm_tokens", campos, condiciones);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			conexion.cerrarConexion();

		}
		return done;
	}

	/**
	 * Elimina el registro correspondiente a una persona de la tabla fcm_tokens
	 * 
	 * @param idPersona
	 * @return eliminado
	 * @throws Exception
	 */
	public static boolean dropTokenBD(int idPersona) throws Exception {
		List<Object> parametros = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		boolean rs = false;
		try {

			Map<String, Object> condiciones = new HashMap<>();
			condiciones.put("id_persona", idPersona);

			rs = conexion.eliminarBD("fcm_tokens", condiciones);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			conexion.cerrarConexion();
		}
		return rs;
	}

	/**
	 * Verifica si cierta persona ya tiene registrado un dispositivo
	 * 
	 * @param idPersona
	 * @return registrado
	 * @throws Exception
	 */
	public static boolean verificarTokenDevice(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		boolean registrado = false;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM fcm_tokens");
			sql.append(" WHERE id_persona = ?");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				registrado = true;
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return registrado;

	}

	/**
	 * Obtiene los datos necesarios para notificar a un usuario de la
	 * autorizacion de un medicamento
	 * 
	 * @param numeroFormula
	 * @param codigoMedicamento
	 * @return datos : lista de objetos con el token, nombre del medicamento, si
	 *         fue autorizado y la fecha de entrega
	 * @throws Exception
	 */
	public static List<Object> getDatosAutMed(int numeroFormula, String codigoMedicamento) throws Exception {

		List<Object> parametros = new ArrayList<>();
		List<Object> datos = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("select fcm.id_persona, fcm.fcm_token, v.nombre_comercial, med.fue_autorizado,"
					+ "COALESCE ( fecha_hora_nueva_entrega, fecha_hora_creacion_formula ) as fecha_entrega");
			sql.append(" FROM formula_medica f" + " JOIN fcm_tokens fcm on fcm.id_persona = f.persona"
					+ " JOIN medicamento_formula med" + " ON med.id_numero_formula = f.id_numero_formula"
					+ " JOIN vademecum_institucional v" + " ON v.id_codigo_medicamento = med.id_codigo_medicamento"
					+ " WHERE f.id_numero_formula = ?" + " AND med.id_codigo_medicamento = ?");

			parametros.add(numeroFormula);
			parametros.add(codigoMedicamento);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				datos.add((String) rs.getObject("fcm_token"));
				datos.add((String) rs.getObject("nombre_comercial"));
				datos.add((String) rs.getObject("fue_autorizado"));
				datos.add((Date) rs.getObject("fecha_entrega"));
//				datos.add((Integer) rs.getObject("id_persona"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return datos;
	}



}
