package com.example.uisaludmovilv01.jbossTest.modulos.remision;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Especialidad;
import com.example.uisaludmovilv01.jbossTest.beans.Profesional;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionEspecialidad;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionProcedimiento;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;

public interface IRemisionDAO {

	/**
	 * Obtiene la fecha de creación de un remision
	 * 
	 * @param consecutivoRemision
	 * @return fecha de creacióm
	 * @throws Exception
	 */
	public static Date getFechaRemision(int consecutivoRemision) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Date fechaRemision = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fecha_remision");
			sql.append(" FROM remision" + " WHERE id_consecutivo_remision = ?");
			parametros.add(consecutivoRemision);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				fechaRemision = ((Date) rs.getObject("fecha_remision"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return fechaRemision;
	}

	// --------------REMISION_ESPECIALIDAD-----------------

	/**
	 * Obtiene la lista de remisiones a especialidad vigentes de una persona
	 * 
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public static List<RemisionEspecialidad> getRemisionesEspecialidad(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<RemisionEspecialidad> remsEsp = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			String fecha = Helper.fechaToString(new Date());
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_persona, id_consecutivo_remision, id_consecutivo_atencion, fecha_fin_vigencia,"
					+ "tipo_documento_profesional, documento_profesional, fecha_remision, hora_remision,"
					+ "id_especialidad_remision, re.tipo_remision, tr.nombre AS nombre_remision, nombre_diagnostico, "
					+ "justificacion_remision, cantidad_citas_remitidas, cantidad_citas_solicitadas,"
					+ "cantidad_citas_atendidas, cantidad_citas_inasistidas, requiere_aprobacion_jerarquica,"
					+ "indicativo_aprobada, fecha_aprobacion, fecha_control, control_cobro, "
					+ "p.nombre AS nombre_procedimiento, re.tipo_atencion, "
					+ "ta.nombre AS nombre_atencion, estado_remision");

			sql.append(" FROM remision_especialidad re" + " JOIN remision r"
					+ " ON re.consecutivo_remision = r.id_consecutivo_remision"
					+ " JOIN tipo_atencion ta" + " ON re.tipo_atencion = ta.id_tipo_atencion"
					+ " JOIN tipo_remision tr" + " ON re.tipo_remision = tr.id_tipo_remision"
					+ " JOIN procedimiento p" + " ON re.procedimiento = p.id_procedimiento"
					+ " JOIN diagnostico d" + " ON re.diagnostico = d.id_diagnostico"
					+ " WHERE consecutivo_remision IS NOT NULL" + " AND id_persona = ?" + " AND fecha_fin_vigencia >= "
					+ fecha + " AND cantidad_citas_solicitadas < cantidad_citas_remitidas"
					+ " ORDER BY fecha_fin_vigencia");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				RemisionEspecialidad remEsp = crearRemisionEsp(rs);
				remsEsp.add(remEsp);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return remsEsp;

	}

	/**
	 * Crea un objeto de remión de especialidad a partir de un ResultSet
	 * 
	 * @param rs
	 *            resultset de un aconsulta previa
	 * @return objeto remisionEspecialidad
	 * @throws Exception
	 */
	public static RemisionEspecialidad crearRemisionEsp(ResultSet rs) throws Exception {
		RemisionEspecialidad remEsp = new RemisionEspecialidad();

		remEsp.setIdPersona((Integer) rs.getObject("id_persona"));
		remEsp.setConsecutivoRemision((Integer) rs.getObject("id_consecutivo_remision"));
		remEsp.setIdConsecutivoAtencion((Integer) rs.getObject("id_consecutivo_atencion"));
		
		Especialidad especialidad = IConsultasDAO.getEspecialidById(rs.getInt("id_especialidad_remision"));
		remEsp.setEspecialidad(especialidad);
		
		remEsp.setTipoRemision((Integer) rs.getObject("tipo_remision"));
		remEsp.setNombreTipoRemision((String) rs.getObject("nombre_remision"));
		remEsp.setDiagnostico((String) rs.getObject("nombre_diagnostico"));
		remEsp.setJustificacionRemision((String) rs.getObject("justificacion_remision"));
		remEsp.setCantidadCitasRemitidas((Short) rs.getObject("cantidad_citas_remitidas"));
		remEsp.setCantidadCitasSolicitadas((Short) rs.getObject("cantidad_citas_solicitadas"));
//		remEsp.setCantidadCitasAtendidas((Short) rs.getObject("cantidad_citas_atendidas"));
//		remEsp.setCantidadCitasInasistidas((Short) rs.getObject("cantidad_citas_inasistidas"));
		remEsp.setRequiereAprobacionJerarquica((String) rs.getObject("requiere_aprobacion_jerarquica"));
		remEsp.setIndicativoAprobada((String) rs.getObject("indicativo_aprobada") + "");
		remEsp.setFechaAprobacion(((Date) rs.getObject("fecha_aprobacion")) + "");
		remEsp.setFechaControl(((Date) rs.getObject("fecha_control")) + "");
		remEsp.setControlCobro((String) rs.getObject("control_cobro") + "");
		remEsp.setProcedimiento((String) rs.getObject("nombre_procedimiento"));
		remEsp.setTipoAtencion((Integer) rs.getObject("tipo_atencion"));
		remEsp.setNombreTipoAtencion((String) rs.getObject("nombre_atencion"));
		remEsp.setFechaFinVigencia(((Date) rs.getObject("fecha_fin_vigencia")) + "");
//		remEsp.setTipoDocumentoProfesional((Short) rs.getObject("tipo_documento_profesional") + "");
//		remEsp.setDocumentoProfesional(((BigDecimal) rs.getObject("documento_profesional")) + "");
		
		
		BigDecimal doc = (BigDecimal) rs.getObject("documento_profesional");
		Short tipoDoc = (Short) rs.getObject("tipo_documento_profesional");
		if(doc != null && tipoDoc != null){
			String nombre = IConsultasDAO.getNombreProfesional(doc, tipoDoc);
			Profesional profesional = new Profesional();
			profesional.setIdDocumento(doc);
			profesional.setIdTipoDocumento(tipoDoc);
			profesional.setNombre(nombre);
			remEsp.setProfesional(profesional);
		}else
			remEsp.setProfesional(null);
		
		
		remEsp.setFechaRemision(((Date) rs.getObject("fecha_remision")).toString());
		remEsp.setHoraRemision(((Time) rs.getObject("hora_remision")).toString());
		remEsp.setEstadoRemision((String) rs.getObject("estado_remision"));

		return remEsp;
	}

	/**
	 * Recupera las cantidades de citas remitidas y solicitadas de una remision
	 * de especialidad y su tipo de remision.
	 * 
	 * @param idPersona
	 * @param ctvAtencion
	 * @param idEsp
	 * @return info
	 * @throws Exception
	 */
	public static HashMap<String, Object> getInfoModRemEsp(int idPersona, int ctvAtencion, int idEsp) throws Exception {
		List<Object> parametros = new ArrayList<>();
		HashMap<String, Object> info = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT cantidad_citas_remitidas, cantidad_citas_solicitadas," + "tipo_remision");
			sql.append(" FROM remision_especialidad" + " WHERE id_persona = ?" + " AND id_consecutivo_atencion = ?"
					+ " AND id_especialidad_remision = ?");

			parametros.add(idPersona);
			parametros.add(ctvAtencion);
			parametros.add(idEsp);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				Short remitidas = (Short) rs.getObject("cantidad_citas_remitidas");
				Short solicitadas = (Short) rs.getObject("cantidad_citas_solicitadas");
				Integer tipoRemision = (Integer) rs.getObject("tipo_remision");
				if (remitidas != null && solicitadas != null && tipoRemision != null) {
					info = new HashMap<>();
					info.put("citasRemitidas", remitidas);
					info.put("citasSolicitadas", solicitadas);
					info.put("tipoRemision", tipoRemision);
				}
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		return info;

	}

	/**
	 * Obtiene una remisión a especialidad según la persona, atención y
	 * especialidad
	 * 
	 * @param idPersona
	 *            : id de la persona a la que pertenece la remisión
	 * @param consecutivoAtencion
	 *            : consecutivo de la atencion
	 * @param esp
	 *            : id de la especialidad de la remisión
	 * @return remEsp : objeto remisionEspecialidad
	 * @throws Exception
	 */
	public static RemisionEspecialidad getRemisionEsp(int idPersona, int consecutivoAtencion, int esp)
			throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		RemisionEspecialidad remEsp = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_persona, id_consecutivo_remision, id_consecutivo_atencion, fecha_fin_vigencia,"
					+ "tipo_documento_profesional, documento_profesional, fecha_remision, hora_remision,"
					+ "id_especialidad_remision, re.tipo_remision, tr.nombre AS nombre_remision, nombre_diagnostico, "
					+ "justificacion_remision, cantidad_citas_remitidas, cantidad_citas_solicitadas,"
					+ "cantidad_citas_atendidas, cantidad_citas_inasistidas, requiere_aprobacion_jerarquica,"
					+ "indicativo_aprobada, fecha_aprobacion, fecha_control, control_cobro, "
					+ "p.nombre AS nombre_procedimiento, re.tipo_atencion, "
					+ "ta.nombre AS nombre_atencion, estado_remision");

			sql.append(" FROM remision_especialidad re" + " JOIN remision r"
					+ " ON re.consecutivo_remision = r.id_consecutivo_remision"
					+ " JOIN tipo_atencion ta" + " ON re.tipo_atencion = ta.id_tipo_atencion"
					+ " JOIN tipo_remision tr" + " ON re.tipo_remision = tr.id_tipo_remision"
					+ " JOIN procedimiento p" + " ON re.procedimiento = p.id_procedimiento"
					+ " JOIN diagnostico d" + " ON re.diagnostico = d.id_diagnostico"
					+ " WHERE id_persona = ?" + " AND id_consecutivo_atencion = ?" + " AND id_especialidad_remision = ?"
					+ " ORDER BY fecha_fin_vigencia");

			parametros.add(idPersona);
			parametros.add(consecutivoAtencion);
			parametros.add(esp);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				remEsp = crearRemisionEsp(rs);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return remEsp;

	}

	/**
	 * Obtiene la lista de remisiones de procedimiento vigentes de una persona
	 * 
	 * @param idPersona
	 * @return lista de remisionProcedimiento
	 * @throws Exception
	 */
	public static List<RemisionProcedimiento> getRemisionesProcedimiento(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<RemisionProcedimiento> remsProc = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			String fecha = Helper.fechaToString(new Date());

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_persona, consecutivo_remision, id_consecutivo_atencion, fecha_fin_vigencia,"
					+ "tipo_documento_profesional, documento_profesional, fecha_remision,"
					+ "hora_remision, rp.tipo_remision, tr.nombre AS nombre_remision, nombre_diagnostico,"
					+ "justificacion_remision, requiere_aprobacion_jerarquica,indicativo_aprobada,"
					+ "fecha_aprobacion, rp.id_procedimiento, p.nombre AS nombre_procedimiento,"
					+ "estado_remision, id_consecutivo_remision_procedimiento,"
					+ "observaciones_resultado_procedimiento");

			sql.append(" FROM remision_procedimiento rp" + " JOIN remision r"
					+ " ON rp.consecutivo_remision = r.id_consecutivo_remision"
					+ " JOIN tipo_remision tr"
					+ " ON rp.tipo_remision = tr.id_tipo_remision"
					+ " JOIN procedimiento p"
					+ " ON rp.id_procedimiento = p.id_procedimiento"
					+ " JOIN diagnostico d" + " ON rp.diagnostico = d.id_diagnostico"
					+ " WHERE consecutivo_remision IS NOT NULL" + " AND id_persona = ?" + " AND fecha_fin_vigencia >= "
					+ fecha + " ORDER BY fecha_fin_vigencia");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				RemisionProcedimiento remProc = new RemisionProcedimiento();

				remProc.setIdPersona((Integer) rs.getObject("id_persona"));
				remProc.setConsecutivoRemision(((Integer) rs.getObject("consecutivo_remision")) + "");
				remProc.setIdConsecutivoAtencion((Integer) rs.getObject("id_consecutivo_atencion"));
				remProc.setTipoRemision((Integer) rs.getObject("tipo_remision"));
				remProc.setNombreTipoRemision((String) rs.getObject("nombre_remision"));
				remProc.setDiagnostico((String) rs.getObject("nombre_diagnostico"));
				remProc.setJustificacionRemision((String) rs.getObject("justificacion_remision"));
				remProc.setRequiereAprobacionJerarquica((String) rs.getObject("requiere_aprobacion_jerarquica"));
				remProc.setIndicativoAprobada((String) rs.getObject("indicativo_aprobada") + "");
				remProc.setFechaAprobacion(((Date) rs.getObject("fecha_aprobacion")) + "");
				remProc.setIdProcedimiento((Integer) rs.getObject("id_procedimiento"));
				remProc.setProcedimiento((String) rs.getObject("nombre_procedimiento"));
//				remProc.setTipoDocumentoProfesional((Short) rs.getObject("tipo_documento_profesional") + "");
//				remProc.setDocumentoProfesional(((BigDecimal) rs.getObject("documento_profesional")) + "");
				
				BigDecimal doc = (BigDecimal) rs.getObject("documento_profesional");
				Short tipoDoc = (Short) rs.getObject("tipo_documento_profesional");
				if(doc != null && tipoDoc != null){
					String nombre = IConsultasDAO.getNombreProfesional(doc, tipoDoc);
					Profesional profesional = new Profesional();
					profesional.setIdDocumento(doc);
					profesional.setIdTipoDocumento(tipoDoc);
					profesional.setNombre(nombre);
					remProc.setProfesional(profesional);
				}else
					remProc.setProfesional(null);
				
				
				remProc.setFechaRemision(((Date) rs.getObject("fecha_remision")).toString());
				remProc.setHoraRemision(((Time) rs.getObject("hora_remision")).toString());
				remProc.setEstadoRemision((String) rs.getObject("estado_remision"));
				remProc.setIdConsecutivoRemisionProcedimiento(
						(Integer) rs.getObject("id_consecutivo_remision_procedimiento"));
				remProc.setObservacionesResultadoProcedimiento(
						((String) rs.getObject("observaciones_resultado_procedimiento")) + "");
				remProc.setFechaFinVigencia(((Date) rs.getObject("fecha_fin_vigencia")) + "");

				remsProc.add(remProc);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return remsProc;

	}

}
