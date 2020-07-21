package com.example.uisaludmovilv01.jbossTest.generales;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.TipoDocumentoID;
//import com.google.firebase.messaging.AndroidConfig.Priority;
//
//import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Beneficiario;
import com.example.uisaludmovilv01.jbossTest.beans.Cifrador;
import com.example.uisaludmovilv01.jbossTest.beans.Cita;
import com.example.uisaludmovilv01.jbossTest.beans.CobroCuotaAsistencial;
import com.example.uisaludmovilv01.jbossTest.beans.Contador;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.beans.Especialidad;
import com.example.uisaludmovilv01.jbossTest.beans.FormulaMedica;
import com.example.uisaludmovilv01.jbossTest.beans.MedicamentoFormula;
import com.example.uisaludmovilv01.jbossTest.beans.Persona;
import com.example.uisaludmovilv01.jbossTest.beans.Profesional;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionEspecialidad;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionProcedimiento;
import com.example.uisaludmovilv01.jbossTest.beans.TipoAfiliado;
import com.example.uisaludmovilv01.jbossTest.beans.TipoDocumentoID;
import com.example.uisaludmovilv01.jbossTest.beans.TipoPlan;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;

public interface IConsultasDAO {

	// -------------- TIPO_DOCUMENTO -----------------

	public static List<TipoDocumentoID> getTiposDocumentoID() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<TipoDocumentoID> tiposDocumentoID = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT tipo_doc_id, descripcion_doc");
			sql.append(" FROM financiero:uds_tipos_doc_id ");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				TipoDocumentoID tipoDocumentoID = new TipoDocumentoID();
				tipoDocumentoID.setTipoDocId((Integer) rs.getObject("tipo_doc_id"));
				tipoDocumentoID.setDescripcionDoc((String) rs.getObject("descripcion_doc"));
				tiposDocumentoID.add(tipoDocumentoID);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return tiposDocumentoID;

	}

	// --------------ESPECIALIDAD-----------------
	/**
	 * Obtiene el nombre de una especialidad
	 * 
	 * @return nombre
	 * @throws Exception
	 */
	public static String getNombreEspProc(int id, char espProc) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		String nombre = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT nombre");
			if (espProc == 'E')
				sql.append(" FROM especialidad" + " WHERE id_especialidad = ?");
			else if (espProc == 'P')
				sql.append(" FROM procedimiento" + " WHERE id_procedimiento = ?");
			else
				return null;

			parametros.add(id);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				nombre = (String) rs.getObject("nombre");

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return nombre;

	}

	/**
	 * Obtiene una especialidad por Id
	 * 
	 * @return personas
	 * @throws Exception
	 */
	public static Especialidad getEspecialidById(int idEsp) throws Exception {
		List<Object> parametros = new ArrayList<Object>();
		Especialidad esp = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT especialidad.id_especialidad AS id_esp, codigo_especialidad_reps, nombre,"
					+ "especialidad.estado AS esp_estado, aplica_cuota_moderadora, contenido_correo,"
					+ "requiere_remision, indicativo_principal, indicativo_odontologia, id_sede");

			sql.append(" FROM especialidad" + " JOIN sede_especialidad"
					+ " ON especialidad.id_especialidad = sede_especialidad.id_especialidad"
					+ " WHERE especialidad.id_especialidad = ?");

			parametros.add(idEsp);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				esp = new Especialidad();
				esp.setIdEspecialidad((Integer) rs.getObject("id_esp"));
				esp.setNombre((String) rs.getObject("nombre"));
				esp.setEstado((String) rs.getObject("esp_estado"));
				esp.setRequiereRemision((String) rs.getObject("requiere_remision"));
				esp.setSede(((Short) rs.getObject("id_sede")) + "");

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return esp;

	}

	/**
	 * Obtiene la lista de todas las personas
	 * 
	 * @return personas
	 * @throws Exception
	 */
	public static List<Especialidad> getEspecialidades() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Especialidad> especialidades = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT especialidad.id_especialidad AS id_esp, codigo_especialidad_reps, nombre,"
					+ "especialidad.estado AS esp_estado, aplica_cuota_moderadora, contenido_correo,"
					+ "requiere_remision, indicativo_principal, indicativo_odontologia, id_sede");

			sql.append(" FROM especialidad" + " JOIN sede_especialidad"
					+ " ON especialidad.id_especialidad = sede_especialidad.id_especialidad" + " WHERE id_sede = 1"
					+ " AND sede_especialidad.estado = 'A'" + " AND especialidad.estado = 'A'"
					+ " AND requiere_remision = 'N'" + " AND sede_especialidad.indicativo_uisalud = 'S'"
					+ " ORDER BY nombre");

			// parametros.add();

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				Especialidad esp = new Especialidad();
				esp.setIdEspecialidad((Integer) rs.getObject("id_esp"));
				// esp.setGrupoEspecialidad((Integer)
				// rs.getObject("grupo_especialidad"));
				esp.setNombre((String) rs.getObject("nombre"));
				esp.setEstado((String) rs.getObject("esp_estado"));
				esp.setRequiereRemision((String) rs.getObject("requiere_remision"));
				esp.setSede(((Short) rs.getObject("id_sede")) + "");

				especialidades.add(esp);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return especialidades;

	}

	// --------------FORMULA_MEDICA-----------------
	/**
	 * Devuelve una formulaMedica por id
	 * 
	 * @param idFormulaMedica
	 * @return
	 * @throws Exception
	 */
	public static FormulaMedica getFormulaMedicaById(int idFormulaMedica) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		FormulaMedica formulaMedica = new FormulaMedica();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_numero_formula, persona, consecutivo_atencion, "
					+ "fecha_hora_creacion_formula, fecha_hora_nueva_entrega," + "observaciones");
			sql.append("  FROM formula_medica WHERE id_numero_formula = ?");

			parametros.add(idFormulaMedica);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				formulaMedica.setIdNumeroFormula((Integer) rs.getObject("id_numero_formula"));
				formulaMedica.setPersona((Integer) rs.getObject("persona"));
				formulaMedica.setConsecutivoAtencion((Integer) rs.getObject("consecutivo_atencion"));
				formulaMedica
						.setFechaHoraCreacionFormula(((Timestamp) rs.getObject("fecha_hora_creacion_formula")) + "");
				formulaMedica.setFechaHoraNuevaEntrega(((Timestamp) rs.getObject("fecha_hora_nueva_entrega")) + "");
				formulaMedica.setObservaciones((String) rs.getObject("observaciones"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return formulaMedica;

	}

	/**
	 * Devuelve las fórmulas médicas de un paciente
	 * 
	 * @param idPaciente
	 * @return formulasMedicas
	 * @throws Exception
	 */

	public static List<FormulaMedica> getFormulasMedicasPaciente(int idPaciente) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<FormulaMedica> formulasMedicas = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_numero_formula, persona, consecutivo_atencion, "
					+ "fecha_hora_creacion_formula, fecha_hora_nueva_entrega," + "observaciones");
			sql.append("  FROM formula_medica WHERE persona = ?");

			parametros.add(idPaciente);

			rs = conexion.consultarBD(sql.toString(), parametros);
			while (rs.next()) {
				FormulaMedica formulaMedica = new FormulaMedica();
				int idFormula = (Integer) rs.getObject("id_numero_formula");
				formulaMedica.setIdNumeroFormula(idFormula);
				formulaMedica.setPersona((Integer) rs.getObject("persona"));
				formulaMedica.setConsecutivoAtencion((Integer) rs.getObject("consecutivo_atencion"));
				
				String fhc = ((Timestamp) rs.getObject("fecha_hora_creacion_formula")) + "";
				fhc = (fhc.length() > 4) ? fhc.substring(0,16) : fhc;
				formulaMedica .setFechaHoraCreacionFormula(fhc);
				
				String fhn = ((Timestamp) rs.getObject("fecha_hora_nueva_entrega")) + "";
				System.out.println("getFormulas: fhn.length() = " + fhn.length());
				System.out.println("getFormulas: fhn = " + fhn);
				fhn = (fhn.length() > 4) ? fhn.substring(0,16) : fhn;
				formulaMedica.setFechaHoraNuevaEntrega(fhn);
				
				formulaMedica.setObservaciones((String) rs.getObject("observaciones"));
				formulasMedicas.add(formulaMedica);
				
//				List<MedicamentoFormula> meds = getMedicamentos(idFormula);
//				if(meds != null) formulaMedica.setMedicamentos(meds);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return formulasMedicas;

	}

	// --------------MEDICAMENTO_FORMULA-----------------

	public static List<MedicamentoFormula> getMedicamentos(int idFormula) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<MedicamentoFormula> medicamentos = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_numero_formula, medicamento_formula.id_codigo_medicamento,"
					+ "vademecum_institucional.nombre_comercial,"
					+ "financiero:desc_adic_elem_med.unidad AS unidad,cantidad, cantidad_por_mes,"
					+ "tiempo_formulacion, modo_uso, medicamento_formula.observaciones,"
					+ "requiere_autorizacion, fecha_autorizacion, fue_autorizado,"
					+ "justificacion_medicamento, justificacion_no_autorizado");

			sql.append(" FROM medicamento_formula" + " JOIN vademecum_institucional"
					+ " ON medicamento_formula.id_codigo_medicamento = vademecum_institucional.id_codigo_medicamento"
					+ " JOIN financiero:desc_adic_elem_med"
					+ " ON medicamento_formula.id_codigo_medicamento = financiero:desc_adic_elem_med.codigo_elemento"
					+ " AND id_numero_formula = ?");

			parametros.add(idFormula);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				MedicamentoFormula medicamento = new MedicamentoFormula();
				medicamento.setIdNumeroFormula((Integer) rs.getObject("id_numero_formula"));
				medicamento.setNombreComercial((String) rs.getObject("nombre_comercial"));
				medicamento.setUnidad(((String) rs.getObject("unidad")) + "");
				medicamento.setCantidad((Integer) rs.getObject("cantidad"));
				medicamento.setCantidadPorMes((Integer) rs.getObject("cantidad_por_mes"));
				medicamento.setTiempoFormulacion((Integer) rs.getObject("tiempo_formulacion"));
				medicamento.setModoUso((String) rs.getObject("modo_uso"));
				medicamento.setObservaciones((String) rs.getObject("observaciones") + "");
				medicamento.setRequiereAutorizacion((String) rs.getObject("requiere_autorizacion") + "");
				medicamento.setFechaAutorizacion(((Date) rs.getObject("fecha_autorizacion")) + "");
				medicamento.setFueAutorizado((String) rs.getObject("fue_autorizado") + "");
				medicamento.setJustificacionMedicamento((String) rs.getObject("justificacion_medicamento") + "");
				medicamento.setJustificacionNoAutorizado((String) rs.getObject("justificacion_no_autorizado") + "");
				medicamentos.add(medicamento);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return medicamentos;

	}

	// --------------PROFESIONAL-----------------

	public static String getNombreProfesional(BigDecimal doc, short tipoDoc) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		String nombreProf = "";
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT (primer_nombre || ' ' || segundo_nombre"
					+ " || ' ' || primer_apellido || ' ' || segundo_apellido) AS nombre");

			sql.append("  FROM profesional WHERE id_tipo_documento = ?" + " AND id_documento = ?");

			parametros.add(tipoDoc);
			parametros.add(doc);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				nombreProf = (String) rs.getObject("nombre");
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return nombreProf;

	}

	public static Profesional getProfesionalById(BigDecimal doc, short tipoDoc) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Profesional profesional = new Profesional();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_documento, id_tipo_documento," + " (primer_nombre || ' ' || segundo_nombre"
					+ " || ' ' || primer_apellido || ' ' || segundo_apellido) AS nombre");

			sql.append("  FROM profesional WHERE id_documento = ? and id_tipo_documento = ?");

			parametros.add(doc);
			parametros.add(tipoDoc);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				profesional.setIdDocumento((BigDecimal) rs.getObject("id_documento"));
				profesional.setIdTipoDocumento((Short) rs.getObject("id_tipo_documento"));
				profesional.setNombre((String) rs.getObject("nombre"));
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return profesional;

	}


	// --------------TIPO_ATENCION-----------------

	public static List<TipoAfiliado> getTiposAfiliado() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<TipoAfiliado> tiposAfiliado = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id, nombre");
			sql.append("  FROM financiero:uds_tipos_afiliado");

			// parametros.add();

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				TipoAfiliado tipoAfiliado = new TipoAfiliado();
				tipoAfiliado.setId((Integer) rs.getObject("id"));
				tipoAfiliado.setNombre((String) rs.getObject("nombre"));

				tiposAfiliado.add(tipoAfiliado);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return tiposAfiliado;

	}

	// --------------TIPO_PLAN-----------------

	public static List<TipoPlan> getTiposPlan() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<TipoPlan> tiposPlan = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT id_plan_salud, nombre," + "indicativo_procedimientos,estado");
			sql.append(" FROM plan_salud");

			// parametros.add();

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				TipoPlan tipoPlan = new TipoPlan();
				tipoPlan.setIdPlanSalud((Integer) rs.getObject("id_plan_salud"));
				tipoPlan.setNombre((String) rs.getObject("nombre"));
				tipoPlan.setIndicativoProcedimientos((String) rs.getObject("indicativo_procedimientos"));
				tipoPlan.setEstado((String) rs.getObject("estado"));

				tiposPlan.add(tipoPlan);

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return tiposPlan;

	}

	// -------------- GENERAL -----------------

	/**
	 * Consulta si cierto numero de dias a partir de hoy en un dia habil. Si no,
	 * recupera el siguiente dia habil.
	 * 
	 * @param fechaActual
	 * @param dias
	 * @return siguiente dia habil
	 * @throws Exception
	 */
	public static Date getProximoDiaHabil(int dias) throws Exception {
		List<Object> parametros = new ArrayList<Object>();
		Date proxDiaHabil = new Date(); // hoy
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			proxDiaHabil.setDate(proxDiaHabil.getDate() + dias); // en n dias
			String fecha = Helper.fechaToString(proxDiaHabil);

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT id_dia_festivo");
			sql.append(" FROM dia_festivo" + " WHERE id_dia_festivo = " + fecha);

			// parametros.add();

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) { // si esta es porque es festivo
				getProximoDiaHabil(++dias);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return proxDiaHabil;
	}

	/**
	 * Coloca los valores por defecto para las últimas columnas de las tablas en
	 * la BD a un HashMap
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> finalColumns(Map<String, Object> map, char tripoTransaccion) throws Exception {
		Map<String, Object> mapa = null;
		try {
			mapa = new HashMap<>();
			mapa.putAll(map);
			Date f = new Date();
			Time t = new Time(f.getHours(), f.getMinutes(), f.getSeconds());
			mapa.put("fecha_transaccion", f);
			mapa.put("hora_transaccion", IConstantes.SQL_HOUR_TO_SEC_FORMAT.format(f));
			mapa.put("tipo_transaccion", tripoTransaccion + "");
			mapa.put("documento_transaccion", "91486984");
			mapa.put("login_transaccion", "cxusalud");
			mapa.put("tipo_documento_transaccion", 1);
			mapa.put("rol_transaccion", "Proyecto Grado UISALUD");
			mapa.put("ip_transaccion", "10.1.68.22");
			mapa.put("mac_transaccion", "D4-BE-D9-8B-57-85");
		} catch (Exception e) {
			throw new Exception(e);
		}
		return mapa;
	}

	/**
	 * Devuelve un String con los valores por defecto para las últimas columnas
	 * dependiendo del tipo de transacción
	 * 
	 * @param tripoTransaccion
	 * @return
	 */
	public static String finalColumns(char tripoTransaccion) {
		Date f = new Date();
		Time t = new Time(f.getHours(), f.getMinutes(), f.getSeconds());
		String ff = "\'" + (f.getYear() + 1900) + "-" + (f.getMonth() + 1) + "-" + f.getDate() + "\'";
		String tt = "\'" + (t.getHours()) + ":" + t.getMinutes() + ":" + t.getSeconds() + "\'";

		String str = " fecha_transaccion = " + ff + ", hora_transaccion = " + tt + ", tipo_transaccion = \'"
				+ tripoTransaccion + "\'," + " documento_transaccion = \'91486984\',"
				+ " login_transaccion = \'cxusalud\'," + " tipo_documento_transaccion = 1,"
				+ " rol_transaccion = \'Proyecto Grado UISALUD\'," + " ip_transaccion = \'10.1.68.22\',"
				+ " mac_transaccion = \'D4-BE-D9-8B-57-85\'";

		return str;
	}


}