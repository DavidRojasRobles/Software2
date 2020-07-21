package com.example.uisaludmovilv01.jbossTest.modulos.notificaciones;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial.ICuotaAsistencialDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.tasks.RecordarCita;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.tasks.RecordarMedicamentos;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.tasks.RecordarRemision;
import com.example.uisaludmovilv01.jbossTest.modulos.persona.IPersonaDAO;

public class AdministrarNotificaciones {

	public static final FCMConnection FCM_CONNECTION = new FCMConnection();
//	public static final RecordarCita RECORDAR_CITA = new RecordarCita();
//	public static final RecordarMedicamentos RECORDAR_MEDS = new RecordarMedicamentos();
//	public static final RecordarRemision RECORDAR_REM = new RecordarRemision();

	/**
	 * Notifica a un usuario que su plan de afiliacion ha cambiado.
	 * 
	 * @param idPersona
	 * @return ok : true si la respuesta del servidor de FCM cumple con el
	 *         patrón de éxito
	 * @throws Exception
	 */
	public static boolean notificarAfilInvalido(int idPersona) throws Exception {
		List<Object> parametros = new ArrayList<>();
		String response;
		boolean ok = false;

		try {
			// Lista con token, nombreMed, fueAutorizado (S/N), fechaEntrega

			String token = (String) INotificacionDAO.getTokenPersona(idPersona);

			response = FCMConnection.notifySingleDeviceData(/*idPersona,*/ token, IConstantes.PLAN_INVALIDO_TITLE,
					IConstantes.PLAN_INVALIDO_MSG, Priority.NORMAL, IConstantes.NOTIF_AUT_TTL,
					IConstantes.TIPO_NOTI_INVALIDO, "");

			ok = Pattern.matches(IConstantes.NOTIF_RESPONSE_PATTERN, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}

	/**
	 * Solicita la notificacion al token de un usuario sobre su autorizacion ( o
	 * no autorizacion) de un medicamento
	 * 
	 * @param numeroFormula
	 * @param codigoMedicamento
	 * @return response: String con un mensaje describiendo el resultado de la
	 *         operación (o el error en dado caso)
	 * @throws Exception
	 */
	public static String notificarAutMed(int numeroFormula, String codigoMedicamento) throws Exception {

		List<Object> parametros = new ArrayList<>();
		List<Object> datos = null;
		String response;
		String result = "";
		String jsonData = "";

		Map<String, String> elements = new HashMap();
//		elements.put("tipo", "medicamento");
		elements.put("numeroFormula", numeroFormula + "");
		elements.put("codigoMedicamento", codigoMedicamento);
		// elements.put("color", "yellow");

		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		jsonData = gson.toJson(elements, gsonType);


		boolean ok = false;
		try {
			// Lista con token, nombreMed, fueAutorizado (S/N), fechaEntrega
			datos = INotificacionDAO.getDatosAutMed(numeroFormula, codigoMedicamento);
			if (datos != null && datos.size() > 0) {
				
				String token = (String) datos.get(0);
				if (token != null) {
					String nombre = (String) datos.get(1);
					String fueAutorizado = (String) datos.get(2);
					String fechaEntrega = IConstantes.NORMAL_DATE_FORMAT.format((Date) datos.get(3));

					if (token != null && nombre != null && fueAutorizado != null) {
						String title;
						String msg;
						if (fueAutorizado.equals("S")) {
							title = IConstantes.TITLE_MED_AUT;
							msg = String.format(IConstantes.MSG_MED_AUT, nombre, fechaEntrega);
						} else {
							title = IConstantes.TITLE_MED_NO_AUT;
							msg = String.format(IConstantes.MSG_MED_NO_AUT, nombre);
						}

						response = FCMConnection.notifySingleDeviceData(/*(Integer) datos.get(4),*/token, title, msg, Priority.HIGH,
								IConstantes.NOTIF_AUT_TTL, IConstantes.TIPO_NOTI_MED, jsonData);

						ok = Pattern.matches(IConstantes.NOTIF_RESPONSE_PATTERN, response);
						result = (ok) ? IConstantes.SOLICITUD_FCM : IConstantes.NO_SOLICITUD_FCM;
					}

				} else
					result = IConstantes.NO_TOKEN_PERSONA;
			} else
				result = IConstantes.NO_REGISTRO_BD;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Solicita la notificacion al token de un usuario de la autorizacion ( o no
	 * autorizacion) de una remision de especialidad o procedimiento
	 * 
	 * @param idPersona
	 *            : id de la persona a quien pertenece la remision
	 * @param idEspProc
	 *            : id de la especialidad o procedimiento en su respectiva tabla
	 * @param indicativoEspProc
	 *            : 'E' para remisiones de especialidad o 'P' para remisiones de
	 *            procedimiento
	 * @param consAtencion
	 *            : consecutivo de atencion de la remisión
	 * @param consRemProc
	 *            : consecutivo de remision procedimiento. Si es remisión de
	 *            especialidad, null
	 * @param fueAutorizado
	 * @return
	 * @throws Exception
	 */
	public static String notificarAutRem(int idPersona, int idEspProc, char indicativoEspProc, int consAtencion,
			Integer consRemProc, boolean fueAutorizado) throws Exception {

		String token = null;
		String response = null;
		boolean ok = false;
		String result = "";
		String nombre = null;
		ResultSet rs = null;

		try {

			nombre = IConsultasDAO.getNombreEspProc(idEspProc, indicativoEspProc);
			token = INotificacionDAO.getTokenPersona(idPersona);
			if (token != null && nombre != null) {

				ok = requestMsgAutRem(token, nombre, indicativoEspProc, consAtencion, idEspProc, consRemProc,
						fueAutorizado);

				result = (ok) ? IConstantes.SOLICITUD_FCM : IConstantes.NO_SOLICITUD_FCM;
			} else {
				result = (token == null) ? IConstantes.NO_TOKEN_PERSONA : IConstantes.NO_REGISTRO_BD;
			}
		} catch (Exception e) {
			throw new Exception(e);

		}
		return result;
	}

	/**
	 * 
	 * @param token
	 * @param nombre
	 *            nombre de la especialidad o procedimiento
	 * @param indEspProc
	 *            : 'E' para remisiones de especialidad o 'P' para remisiones de
	 *            procedimiento
	 * @param consAtencion
	 *            : consecutivo de atencion de la remisión
	 * @param idEspProc
	 *            : id de la especialidad o procedimiento en su respectiva tabla
	 * @param consRemProc
	 *            : consecutivo de remision procedimiento. Si es remisión de
	 *            especialidad, null
	 * @param fueAutorizado
	 *            : true si fue autorizado
	 * @return ok : true si la respuesta enviada por FCM cumple con el patrón
	 *         correcto
	 * @throws Exception
	 */
	public static boolean requestMsgAutRem(String token, String nombre, char indEspProc, int consAtencion,
			int idEspProc, Integer consRemProc, Boolean fueAutorizado) throws Exception {

		String response = null;
		boolean ok = false;
		String jsonData = "";
		String tipo;

		Map<String, String> elements = new HashMap();
		if (indEspProc == IConstantes.IND_REM_ESP) {
			tipo = IConstantes.TIPO_NOTI_REM_ESP;

		} else if (indEspProc == IConstantes.IND_REM_PROC) {
			tipo = IConstantes.TIPO_NOTI_REM_PROC;
			elements.put("consRemProc", consRemProc + "");

		} else
			return false;

		elements.put("ctvAtencion", consAtencion + "");
		elements.put("idEspProc", idEspProc + "");

		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		jsonData = gson.toJson(elements, gsonType);
		try {
			if (fueAutorizado != null) {
				String title;
				String msg;
				if (fueAutorizado) {
					title = IConstantes.TITLE_REM_AUT;
					msg = String.format(IConstantes.MSG_REM_AUT, nombre);
				} else {
					title = IConstantes.TITLE_REM_NO_AUT;
					msg = String.format(IConstantes.MSG_REM_NO_AUT, nombre);
				}

				response = FCMConnection.notifySingleDeviceData(token, title, msg, Priority.NORMAL,
						IConstantes.NOTIF_AUT_TTL, tipo, jsonData);
				ok = Pattern.matches(IConstantes.NOTIF_RESPONSE_PATTERN, response);
			}

		} catch (Exception e) {
			throw new Exception(e);

		}
		return ok;
	}

	/**
	 * Solicita la notificacion al token de un usuario sobre la creacion de un
	 * cobro por alguno de sus beneficiarios
	 * 
	 * @param idPersona
	 * @param idCobro
	 * @param idEsp
	 * @param fechaCita
	 *            : yyyy-MM-dd
	 * @param horaCita
	 *            : HH:mm
	 * @param claseCobro
	 * @return result : String con un mensaje describiendo el resultado de la
	 *         operación (o el error en dado caso)
	 * @throws Exception
	 */
	public static String notifyCobro(int idPersona, int idCobro, int idEsp, @Nonnull String fechaCita,
			@Nonnull String horaCita, char claseCobro) throws Exception {
		String token = null;
		String nombre = null;
		String response = null;
		String result = "";
		boolean ok = false;
		String jsonData = "";

		Map<String, String> elements = new HashMap();
//		elements.put("tipo", IConstantes.TIPO_NOTI_COBRO);
		elements.put("idCobro", idCobro + "");
		elements.put("idEsp", idEsp + "");
		elements.put("fechaCita", fechaCita + "");
		elements.put("horaCita", horaCita + "");

		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		jsonData = gson.toJson(elements, gsonType);
		try {

			token = INotificacionDAO.getTokenPersona(ICuotaAsistencialDAO.getCotizante(idPersona));
			nombre = Helper.formatName(IPersonaDAO.getNombrePersona(idPersona));

			if (token != null && nombre != null) {

				String msg = String.format(IConstantes.MSG_COBRO, fechaCita, nombre,
						Helper.identificarClaseCobro(claseCobro));

				response = FCMConnection.notifySingleDeviceData(/*idPersona,*/ token, IConstantes.TITLE_COBRO, msg, Priority.NORMAL,
						IConstantes.NOTIF_AUT_TTL, IConstantes.TIPO_NOTI_COBRO, jsonData);

				ok = Pattern.matches(IConstantes.NOTIF_RESPONSE_PATTERN, response);

				result = (ok) ? IConstantes.SOLICITUD_FCM : IConstantes.NO_SOLICITUD_FCM;
			} else {
				result = (token == null) ? IConstantes.NO_TOKEN_PERSONA : IConstantes.NO_REGISTRO_BD;
			}

		} catch (Exception e) {
			throw new Exception(e);

		}

		return result;
	}

	public static String notifySingleDeviceData(int idPersona, String message) throws Exception {
		String token = null;
		String response = "";
		String result = "";
		String jsonData = "";

		Map<String, String> elements = new HashMap();
		elements.put("title", "Prueba Datos");
		elements.put("content", message);
		// elements.put("color", "yellow");

		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		jsonData = gson.toJson(elements, gsonType);

		if (jsonData != null)
			System.out.println("jsonData = " + jsonData);
		else
			System.out.println("jsonData is null");

		boolean ok = false;
		try {

			token = INotificacionDAO.getTokenPersona(ICuotaAsistencialDAO.getCotizante(idPersona));

			if (token != null) {

				response = FCMConnection.notifySingleDeviceData(/*idPersona,*/ token, "Prueba Datos", message, Priority.NORMAL,
						IConstantes.NOTIF_AUT_TTL, "", jsonData);

				ok = Pattern.matches(IConstantes.NOTIF_RESPONSE_PATTERN, response);

				result = (ok) ? IConstantes.SOLICITUD_FCM : IConstantes.NO_SOLICITUD_FCM;
			} else {
				result = (token == null) ? IConstantes.NO_TOKEN_PERSONA : IConstantes.NO_REGISTRO_BD;
			}

		} catch (Exception e) {
			throw new Exception(e);

		}

		return result;
	}

}
