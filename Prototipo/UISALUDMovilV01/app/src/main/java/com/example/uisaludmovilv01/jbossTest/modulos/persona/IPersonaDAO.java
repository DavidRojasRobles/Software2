package com.example.uisaludmovilv01.jbossTest.modulos.persona;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Beneficiario;
import com.example.uisaludmovilv01.jbossTest.beans.Cifrador;
import com.example.uisaludmovilv01.jbossTest.beans.Persona;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;

public interface IPersonaDAO {

	final String DATOS_PACIENTE = "p.id, p.tipo_doc_id, p.documento_id," + "td.abrev_reporte_bdex,"
			+ "p.primer_nombre, p.segundo_nombre," + "p.primer_apellido, p.segundo_apellido,"
			+ "p.sexo, p.fecha_nacimiento," + "ts.nombre AS tipo_sangre, p.telefono," + "p.telefono_contacto,"
			+ "p.celular, p.email_institucional," + "p.email_personal, p.email_alterno,"
			+ "p.direccion_residencia,a.codigo_carnet," + "a.fecha_carnet,ta.nombre AS tipo_afil,"
			+ "tpa.nombre AS plan_afil, a.id_afil_aportante";

	final String JOIN_PACIENTE = " JOIN financiero:uds_afiliados a" + " ON p.id = a.id_persona"
			+ " JOIN financiero:uds_tipos_doc_id td" + " ON p.tipo_doc_id = td.tipo_doc_id"
			+ " JOIN financiero:uds_tipos_sangre ts" + " ON p.id_tipo_sanguineo = ts.id"
			+ " JOIN financiero:uds_tipos_afiliado ta" + " ON a.id_tipo_afiliado = ta.id"
			+ " JOIN financiero:uds_tipos_plan_afiliado tpa" + " ON a.id_plan_afiliado = tpa.id";

	/**
	 * Revisa las credenciales de inicio de sesión. De ser correctas devuelve
	 * una lista con los datos del afiliado
	 * 
	 * @param tipoDocumento
	 * @param nroDocumento
	 * @param contrasena
	 * @return datos : lista de Integer con el id de la persona, el plan del
	 *         afiliado y el estado del afiliado
	 * @throws Exception
	 */
	public static List<Integer> iniciarSesion(int tipoDocumento, BigDecimal nroDocumento, String contrasena)
			throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Integer> datos = new ArrayList<>();
		int idPersona = -1;
		int planAfiliado = -1;
		int estadoAfiliado = -1;
		Cifrador cif = new Cifrador();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			// String c = cif.encriptarContrasena(contrasena);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT p.id AS id_persona, id_plan_afiliado, id_estado_afiliacion");
			sql.append(" FROM financiero:uds_personas p" + " JOIN financiero:uds_afiliados a"
					+ " ON p.id = a.id_persona" + " WHERE tipo_doc_id = ?" + " AND documento_id = ?"
					+ " AND (contrasena = ? OR " + IConstantes.E + " = " + cif.checkPass(contrasena) + ")");

			System.out.println("iniciarSesion sql = " + sql);

			parametros.add(tipoDocumento);
			parametros.add(nroDocumento);
			parametros.add(contrasena);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {

				idPersona = (Integer) rs.getObject("id_persona");
				planAfiliado = (Integer) rs.getObject("id_plan_afiliado");
				estadoAfiliado = (Integer) rs.getObject("id_estado_afiliacion");

				datos.add(idPersona);
				datos.add(planAfiliado);
				datos.add(estadoAfiliado);

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
	 * Obtiene el nombre de la persona
	 * 
	 * @param idPersona
	 * @return nombre nombre completo
	 * @throws Exception
	 */
	public static String getNombrePersona(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		String nombre = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT p.primer_nombre," + " p.segundo_nombre," + " p.primer_apellido," + " p.segundo_apellido");
			sql.append(" FROM financiero:uds_personas p" + " WHERE p.id = ?");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				nombre = (String) rs.getObject("primer_nombre") + " " + (String) rs.getObject("segundo_nombre") + " "
						+ (String) rs.getObject("primer_apellido") + " " + (String) rs.getObject("segundo_apellido");

				nombre = Helper.formatName(nombre);

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
	 * Obtiene un objeto persona a partir de su id
	 * 
	 * @param idPersona
	 * @return Persona o null
	 * @throws Exception
	 */
	public static Persona getPersonaById(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Persona persona = new Persona();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + DATOS_PACIENTE);
			sql.append(" FROM financiero:uds_personas p" + JOIN_PACIENTE + " WHERE p.id = ?");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				Integer id = (Integer) rs.getObject("id");
				Date fechaNac = (Date) rs.getObject("fecha_nacimiento");

				if (id != null && (fechaNac != null) && !(fechaNac.after(new Date()))) {
					persona = crearPaciente(rs);
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
		return persona;

	}

	/**
	 * Consulta el plan y el estado de afiliacion de un afiliado.
	 * 
	 * @param idPersona
	 * @return planEstado : HashMap<String, Integer> con el plan y el estado de afiliacion
	 * @throws Exception
	 */
	public static HashMap<String, Integer> getPlanEstadoAfiliado(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<>();
		HashMap<String, Integer> planEstado = null;		
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_plan_afiliado, id_estado_afiliacion FROM financiero:uds_afiliados"
					+ " WHERE id_persona = ?");
			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				Integer plan = (Integer) rs.getObject("id_plan_afiliado");
				Integer estado = (Integer) rs.getObject("id_estado_afiliacion");
				
				planEstado = new HashMap<>();
				
				planEstado.put("plan", plan);
				planEstado.put("estado", estado);

			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return planEstado;
	}

	/**
	 * Crea el paciente con los datos obtenidos de la BD
	 *
	 * @param rs
	 * @return paciente
	 * @throws Exception
	 */
	public static Persona crearPaciente(ResultSet rs) throws Exception {
		System.out.println("crear paciente:called");
		Persona persona = new Persona();
		persona.setId((Integer) rs.getObject("id"));
		Date fechaNac = (Date) rs.getObject("fecha_nacimiento");
		int edad = AdministrarPersona.calcularEdad(fechaNac);
		persona.setEdad((Integer) edad);
		persona.setTipoDocId((Integer) rs.getObject("tipo_doc_id"));
		persona.setDocumentoId((BigDecimal) rs.getObject("documento_id"));
		persona.setTipoDocIdAbrev(((String) rs.getObject("abrev_reporte_bdex")) + "");
		persona.setNombre((String) rs.getObject("primer_nombre") + ' ' + (String) rs.getObject("segundo_nombre") + ' '
				+ (String) rs.getObject("primer_apellido") + ' ' + (String) rs.getObject("segundo_apellido"));
		persona.setSexo(((String) rs.getObject("sexo")) + "");
		persona.setFechaNacimiento(((fechaNac).toString()) + "");
		persona.setTipoSanguineo(((String) rs.getObject("tipo_sangre")) + "");
		persona.setTelefono(((String) rs.getObject("telefono")) + "");
		persona.setTelefonoContacto(((String) rs.getObject("telefono_contacto")) + "");
		persona.setCelular(((String) rs.getObject("celular")) + "");
		persona.setEmailInstitucional(((String) rs.getObject("email_institucional")) + "");
		persona.setEmailPersonal(((String) rs.getObject("email_personal")) + "");
		persona.setEmailAlterno(((String) rs.getObject("email_alterno")) + "");
		persona.setDireccionResidencia(((String) rs.getObject("direccion_residencia")) + "");
		persona.setCodigoCarnet(((String) rs.getObject("codigo_carnet")) + "");
		persona.setFechaCarnet((((Date) rs.getObject("fecha_carnet")).toString()) + "");
		persona.setTipoAfiliado(((String) rs.getObject("tipo_afil")) + "");
		persona.setPlanAfiliado(((String) rs.getObject("plan_afil")) + "");
		persona.setAfilAportante((Integer) rs.getObject("id_afil_aportante") + "");

		return persona;
	}

	/**
	 * COnsulta la fecha de nacimiento del paciente
	 * 
	 * @param idPersona
	 *            identificador de la persona
	 * @return Date: fecha de nacimiento
	 * @throws Exception
	 */
	public static Date getFechaNacPaciente(int idPersona) throws Exception {
		List<Object> parametros = new ArrayList<>();
		Date fechaNac = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT fecha_nacimiento FROM financiero:uds_personas");
			sql.append(" WHERE id = ?");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				fechaNac = (Date) rs.getObject("fecha_nacimiento");
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return fechaNac;
	}

}
