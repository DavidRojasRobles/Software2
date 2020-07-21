package com.example.uisaludmovilv01.jbossTest.modulos.cita;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Agrupador;
import com.example.uisaludmovilv01.jbossTest.beans.Cita;
import com.example.uisaludmovilv01.jbossTest.beans.Especialidad;
import com.example.uisaludmovilv01.jbossTest.beans.Profesional;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;

public interface ICitaDAO {

	final String DATOS_CITA = "id_documento_profesional, id_tipo_documento_profesional,"
			+ " id_fecha, id_hora_inicio,hora_fin, sede_uisalud.id_sede_uisalud AS id_sede,"
			+ "sede_uisalud.nombre AS sede, tipo_cita,"
			+ "especialidad, cita.consultorio, consultorio.numero AS con_num, consultorio.ubicacion AS con_ubi, persona_afiliado,"
			+ "medio_cita, tipo_programacion, consecutivo_atencion,tipo_atencion, cita.estado AS cita_estado";

	final String JOIN_CITA = "JOIN sede_uisalud"
			+ " ON cita.sede = sede_uisalud.id_sede_uisalud" + " JOIN consultorio"
			+ " ON cita.consultorio = consultorio.id_consultorio"
			+ " JOIN tipo_cita tc ON cita.tipo_cita = tc.id_tipo_cita ";

	/**
	 * Busca una cita y retorna su objeto correspondiente
	 * 
	 * @param docProfesional
	 *            : numero de documento del profesional
	 * @param tipoDocProf
	 *            : tipo de documento del profesional
	 * @param fecha
	 *            : la fecha de la cita
	 * @param hora
	 *            : la hora de inicio de la cita
	 * @return cita
	 * @throws Exception
	 */
	public static Cita buscarCita(BigDecimal docProfesional, short tipoDocProf, String fecha, String hora)
			throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Cita cita = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + DATOS_CITA);
			sql.append(" FROM cita " + JOIN_CITA
					+ " WHERE id_documento_profesional = ?" + " AND id_tipo_documento_profesional = ?"
					+ " AND id_fecha = ? AND id_hora_inicio = ?");

			parametros.add(docProfesional);
			parametros.add(tipoDocProf);
			parametros.add(fecha);
			parametros.add(hora);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				cita = crearCita(rs);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return cita;

	}

	/**
	 * Obtiene la lista de citas asignadas no atendidas a una persona
	 * 
	 * @param idPersona
	 * @return citas : ista con las citas solicitadas
	 * @throws Exception
	 */
	public static List<Cita> getCitasPersona(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Cita> citas = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + DATOS_CITA);
			sql.append(" FROM cita " + JOIN_CITA + " WHERE persona_afiliado = ?"
					+ " AND (cita.estado = 2 OR cita.estado = 9)" + " ORDER BY id_fecha, id_hora_inicio");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				Cita cita = crearCita(rs);
				citas.add(cita);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return citas;

	}

	/**
	 * Obtiene todas las citas de cierta especialidad creadas para un dia
	 * 
	 * @param idEsp
	 *            : id de la especialidad
	 * @param fecha
	 *            : string YYYY-MM-dd del dia a consultar
	 * @return citas : ista con las citas
	 * @throws Exception
	 */
	public static List<Cita> getCitasEspDia(int idEsp, String fecha) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Cita> citas = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			if (fecha != null) {
				// fecha = Helper.addQuotes(fecha);

				StringBuilder sql = new StringBuilder();
				sql.append("SELECT " + DATOS_CITA);
				sql.append(" FROM cita " + JOIN_CITA + " WHERE id_fecha = ? AND especialidad = ?");

				parametros.add(fecha);
				parametros.add(idEsp);

				rs = conexion.consultarBD(sql.toString(), parametros);

				while (rs.next()) {
					Cita cita = crearCita(rs);
					citas.add(cita);
				}
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return citas;

	}


	/**
	 * Obtiene la lista de citas disponibles para cierta fecha, especialidad y
	 * uno o todos los profesionales
	 * 
	 * @param idEsp
	 *            : id de la especialidad
	 * @param fecha
	 *            : yyyy-MM-dd
	 * @param tipoCita
	 *            : id del tipo de cita
	 * @param docProfesional
	 *            : numero de documento del profesional. NULL para todos los
	 *            profesionales
	 * @param tipoDocProf
	 *            : tipo de documento del profesional. NULL para todos los
	 *            profesionales
	 * @return citas : lista de citas disponibles
	 * @throws Exception
	 */
	public static List<Cita> getCitasDisponibles(int idEsp, String fecha, int tipoCita, BigDecimal docProfesional,
			Short tipoDocProf) throws Exception {
		System.out.println("getCitasDisponibles: called");
		

		List<Object> parametros = new ArrayList<Object>();
		List<Cita> citas = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			String fechaCita = Helper.addQuotes(fecha);
			
			System.out.println("getCitasDisponibles: idEsp" + idEsp);
			System.out.println("getCitasDisponibles: fecha" + fecha);
			System.out.println("getCitasDisponibles: tipoCita" + tipoCita);
			System.out.println("getCitasDisponibles: docProfesional" + docProfesional);
			System.out.println("getCitasDisponibles: tipoDocProf" + tipoDocProf);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + DATOS_CITA);
			sql.append(" FROM cita " + JOIN_CITA + " WHERE "
					+ "(tc.indicativo_exclusiva = 'N' OR tc.id_tipo_cita = 8)"
					+ " AND especialidad = ?" + " AND (cita.circuito IS NULL)"
					+ " AND id_fecha = ? AND cita.estado = ?");

			parametros.add(idEsp);
			parametros.add(fecha);
			parametros.add(IConstantes.ESTADO_CITA_DISPONIBLE);
			
			if (tipoCita == IConstantes.TIPO_CITA_PROGRAMADA || tipoCita == IConstantes.TIPO_CITA_DIARIA) {
				sql.append(" AND tipo_cita IN ( ? , ?) ");
				parametros.add(IConstantes.TIPO_CITA_PROGRAMADA);
				parametros.add(IConstantes.TIPO_CITA_DIARIA);
					
			} else if(tipoCita == IConstantes.TIPO_CITA_REM_INT){
				sql.append(" AND tipo_cita = ? ");
				parametros.add(IConstantes.TIPO_CITA_REM_INT);
			}

			if (docProfesional != null) {
				sql.append(" AND id_documento_profesional = ?");
				parametros.add(docProfesional);
			}

			if (tipoDocProf != null) {
				sql.append(" AND id_tipo_documento_profesional = ?");
				parametros.add(tipoDocProf);
			}

			sql.append(" ORDER BY id_fecha, id_hora_inicio");
			
			System.out.println("getCitasDisponibles: sql = " + sql);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				System.out.println("getCitasDisponibles: found some");
				Cita cita = crearCita(rs);
				citas.add(cita);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return citas;

	}

	/**
	 * Cosulta el porcentaje máximo de solicitud de citas según el medio de
	 * solicitud y la especialidad.
	 * 
	 * @param idEsp
	 *            : id de la especialidad
	 * @param medioSol:
	 *            id del medio de solicitud
	 * @return porcentaje máximo de solicitud (entero de 0 a 100). 100 por
	 *         defecto.
	 * @throws Exception
	 */
	public static short getPorcentajeMedioSol(int idEsp, int medioSol) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		short porcentaje = 100;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT porcentaje_maximo");
			sql.append(" FROM porcentaje_medio_solicitud_cita_especialidad" + " WHERE id_especialidad = ?"
					+ " AND id_medio_solicitud_cita = ?");
			parametros.add(idEsp);
			parametros.add(medioSol);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				porcentaje = (Short) rs.getObject("porcentaje_maximo");
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return porcentaje;
	}

	/**
	 * Crea un objeto cita a partir de un Resultset
	 * 
	 * @param rs
	 *            : ResultSet
	 * @return cita
	 * @throws Exception
	 */
	public static Cita crearCita(ResultSet rs) throws Exception {
		Cita cita = new Cita();
		
		BigDecimal doc = (BigDecimal) rs.getObject("id_documento_profesional");
		Short tipoDoc = (Short) rs.getObject("id_tipo_documento_profesional");
		if(doc != null && tipoDoc != null){
			String nombre = IConsultasDAO.getNombreProfesional(doc, tipoDoc);
			Profesional profesional = new Profesional();
			profesional.setIdDocumento(doc);
			profesional.setIdTipoDocumento(tipoDoc);
			profesional.setNombre(nombre);
			cita.setProfesional(profesional);
		}else
			cita.setProfesional(null);
		
		Especialidad especialidad = IConsultasDAO.getEspecialidById(rs.getInt("especialidad"));
		
		cita.setEspecialidad(especialidad);
		
		Date fechaCita = (Date) rs.getObject("id_fecha");
		cita.setIdFecha(IConstantes.SQL_DATE_FORMAT.format(fechaCita));
		
		Date horaInicio = (Time) rs.getObject("id_hora_inicio");
		cita.setIdHoraInicio(IConstantes.SQL_HOUR_FORMAT.format(horaInicio));
		
		Date horaFin = (Time) rs.getObject("hora_fin");
		cita.setHoraFin(IConstantes.SQL_HOUR_FORMAT.format(horaFin));
		
		cita.setSedeId((Short) rs.getObject("id_sede"));
		cita.setSedeNombre((String) rs.getObject("sede"));
		cita.setTipoCita((Integer) rs.getObject("tipo_cita"));
		cita.setConsultorio((Integer) rs.getObject("consultorio"));
		cita.setConsultorioNum((String) rs.getObject("con_num"));
		cita.setConsultorioUbi((String) rs.getObject("con_ubi"));
		cita.setPersonaAfiliado((Integer) rs.getObject("persona_afiliado"));
		cita.setMedioCita((Integer) rs.getObject("medio_cita"));
		cita.setTipoProgramacion((String) rs.getObject("tipo_programacion"));
		cita.setConsecutivoAtencion((Integer) rs.getObject("consecutivo_atencion"));
		cita.setTipoAtencion((Integer) rs.getObject("tipo_atencion"));
		cita.setEstado((Integer) rs.getObject("cita_estado"));
		return cita;
	}

	/**
	 * Obtiene el tiempo de solicitud de la base de datos segun la especialidad
	 * en minutos
	 *
	 * @return tiempoSolicitud
	 * @throws Exception
	 */
	public static int consultarTiempoSolicitud(int idEsp) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		int tiempoSolicitud = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT valor");
			sql.append(" FROM parametro_sede_especialidad" + " WHERE id_parametro_general_especialidad = 8"
					+ " AND id_especialidad = ?");

			parametros.add(idEsp);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				tiempoSolicitud = Integer.valueOf((String) rs.getObject("valor"));
			} else {
				sql = new StringBuilder();
				sql.append("SELECT valor");
				sql.append(" FROM parametro_general_especialidad" + " WHERE id_parametro_general_especialidad = 8");

				rs = conexion.consultarBD(sql.toString(), new ArrayList<>());

				if (rs.next()) {
					tiempoSolicitud = Integer.valueOf((String) rs.getObject("valor"));
				}
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return tiempoSolicitud;
	}

	/**
	 * Obtiene el tiempo de cancelación de la base de datos
	 * 
	 * @return personas
	 * @throws Exception
	 */
	public static int consultarTiempoCancelacion() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		int tiempoCancelacion = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT valor");
			sql.append(" FROM parametro_general" + " WHERE id_parametro_general = 30");

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				tiempoCancelacion = Integer.valueOf((String) rs.getObject("valor"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return tiempoCancelacion;
	}

	public static int consultarDiasAntTipoCita(int tipoCita) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		int diasAnt = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dias_anterioridad");
			sql.append(" FROM tipo_cita WHERE id_tipo_cita = ?");

			parametros.add(tipoCita);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				diasAnt = Integer.valueOf((Short) rs.getObject("dias_anterioridad"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return diasAnt;
	}

}
