package com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Agrupador;
import com.example.uisaludmovilv01.jbossTest.beans.AgrupadorServProc;
import com.example.uisaludmovilv01.jbossTest.beans.Beneficiario;
import com.example.uisaludmovilv01.jbossTest.beans.CobroCuotaAsistencial;
import com.example.uisaludmovilv01.jbossTest.beans.Contador;
import com.example.uisaludmovilv01.jbossTest.beans.Especialidad;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;

public interface ICuotaAsistencialDAO {

	/**
	 * Obtiene el id del cotizante de un afiliado. Si el afiliado es el
	 * cotizante, devuelve su mismo id.
	 *
	 * @param idPersona
	 * @return idCotizante
	 * @throws Exception
	 */
	public static int getCotizante(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		int idCotizante = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COALESCE ( id_afil_aportante, id_persona ) as aportante");
			sql.append(" FROM financiero:uds_afiliados" + " WHERE id_persona = ?");

			parametros.add(idPersona);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				idCotizante = (Integer) rs.getObject("aportante");
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return idCotizante;

	}

	/**
	 * Obtiene la lista de beneficiarios de un afiliado, incluído el afiliado.
	 * En caso de no tener beneficiarios, la lista sólo contendrá su único
	 * registro correspondiente.
	 * 
	 * @param idCotizante
	 *            identificador del afiliado
	 * @return beneficiarios: lista con los beneficiarios de un afiliado
	 *         (incluyendo al afiliado)
	 * @throws Exception
	 */
	public static List<Beneficiario> getBeneficiarios(int idCotizante) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Beneficiario> beneficiarios = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT p.id,  t.abrev_reporte_bdex,  p.documento_id,  p.primer_nombre,"
					+ " p.segundo_nombre,  p.primer_apellido,  p.segundo_apellido");
			sql.append(" FROM financiero:uds_personas p" + " JOIN financiero:uds_afiliados a"
					+ " ON a.id_persona = p.id" + " JOIN financiero:uds_tipos_doc_id t"
					+ " ON p.tipo_doc_id = t.tipo_doc_id" + " WHERE a.id_afil_aportante = ?" + " OR id_persona = ?");

			parametros.add(idCotizante);
			parametros.add(idCotizante);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				Beneficiario benef = new Beneficiario();

				benef.setId((Integer) rs.getObject("id"));
				benef.setTipoDoc((String) rs.getObject("abrev_reporte_bdex"));
				benef.setDocumento((BigDecimal) rs.getObject("documento_id"));
				String nombre = (String) rs.getObject("primer_nombre") + " " + (String) rs.getObject("segundo_nombre")
						+ " " + (String) rs.getObject("primer_apellido") + " "
						+ (String) rs.getObject("segundo_apellido");
				benef.setNombre(Helper.formatName(nombre));
				beneficiarios.add(benef);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return beneficiarios;

	}

	/**
	 * POR SI ACASO Obtiene la clase de cobro de un cobro de cuota asistencial a
	 * partir de la primary key del cobro
	 * 
	 * @param idCobroCuotaAsis
	 * @param idPersona
	 * @param idEsp
	 * @param fechaCita
	 * @param horaCita
	 * @return claseCobro
	 * @throws Exception
	 */
	public static Character getClaseCobro(int idCobroCuotaAsis, int idPersona, int idEsp, Date fechaCita, Time horaCita)
			throws Exception {

		List<Object> parametros = new ArrayList<>();
		List<String> datos = new ArrayList<>();
		Character claseCobro = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT clase_cobro FROM cobro_cuota_asistencial");
			sql.append(" WHERE id_cobro_cuota_asistencial = ?" + " AND id_persona  = ?" + " AND id_especialidad = ?"
					+ " AND id_fecha_cita = ?" + " AND id_hora_cita = ?");

			parametros.add(idCobroCuotaAsis);
			parametros.add(idPersona);
			parametros.add(idEsp);
			parametros.add(Helper.fechaToString(fechaCita));
			parametros.add(Helper.TimeToString(horaCita));

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				claseCobro = ((String) rs.getObject("clase_cobro")).charAt(0);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return claseCobro;

	}

	/**
	 * Obtiene la lista de cobros usuarios FULL por id del paciente
	 * 
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public static List<CobroCuotaAsistencial> getCobrosCuotaAsistencial(int idPersona) throws Exception {
		System.out.println("getCobrosCuotaAsistencial: called");
		List<Object> parametros = new ArrayList<Object>();
		List<CobroCuotaAsistencial> cobroCAsistencial = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			System.out.println("getCobrosCuotaAsistencial: idPersona = " + idPersona);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *");
			sql.append(" FROM cobro_cuota_asistencial WHERE id_persona = ?");

			parametros.add(idPersona);
			
			sql.append(" AND YEAR(id_fecha_cita) = ?");
			parametros.add(Calendar.getInstance().get(Calendar.YEAR));

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				System.out.println("getCobrosCuotaAsistencial: found some");
				CobroCuotaAsistencial cobroAsis = crearCobroCa(rs);

				cobroCAsistencial.add(cobroAsis);
			}
			System.out.println("crearCobroCa: cobros.size() = " + cobroCAsistencial.size());

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			conexion.cerrarConexion();

		}
		return cobroCAsistencial;

	}

	/**
	 * Crea un objeto de cobroCuotaAsistencial
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static CobroCuotaAsistencial crearCobroCa(ResultSet rs) throws Exception {
		System.out.println("crearCobroCa: called");
		CobroCuotaAsistencial cobroAsis = new CobroCuotaAsistencial();
		cobroAsis.setIdCobroCuotaAsistencial((Integer) rs.getObject("id_cobro_cuota_asistencial"));
		cobroAsis.setIdPersona((Integer) rs.getObject("id_persona"));
		// cobroAsis.setIdEspecialidad((Integer)
		// rs.getObject("id_especialidad"));
		Especialidad especialidad = IConsultasDAO.getEspecialidById(rs.getInt("id_especialidad"));
		cobroAsis.setEspecialidad(especialidad);
		cobroAsis.setIdFechaCita(((Date) rs.getObject("id_fecha_cita")).toString());
		cobroAsis.setIdHoraCita(((Date) rs.getObject("id_hora_cita")).toString());
		cobroAsis.setClaseCobro((String) rs.getObject("clase_cobro"));
		cobroAsis.setOrigenCobro((Integer) rs.getObject("origen_cobro") + "");
		cobroAsis.setConsecutivoAtencion((Integer) rs.getObject("consecutivo_atencion") + "");
		cobroAsis.setValor(((BigDecimal) rs.getObject("valor")) + "");
		cobroAsis.setReportadoNomina((String) rs.getObject("reportado_nomina"));
		cobroAsis.setAnulada((String) rs.getObject("anulada"));
		cobroAsis.setCausaAnulacion((String) rs.getObject("causa_anulacion"));
		System.out.println("crearCobroCa: cobro creado");

		return cobroAsis;

	}

	/**
	 * Obtiene el estado de los contadores de atenciones de un afiliado
	 * 
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public static List<Contador> getContadores(int idPersona) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		List<Contador> contadores = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_agrupador, nombre, indicativo_contador FROM agrupador");

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
//				System.out.println("getContadores: found some");

				Contador cont = new Contador();

				int idAgrupador = (Integer) rs.getObject("id_agrupador");
//				System.out.println("getContadores: agrupador = " + idAgrupador);
				String nombreCont = (String) rs.getObject("nombre");
				String indicativoContador = (String) rs.getObject("indicativo_contador" + "");

				List<AgrupadorServProc> agrupadoresServProc = getAgrupadoresServProc(idAgrupador);
//				System.out.println("getContadores: agrupadoresServProc.size() = " + agrupadoresServProc.size());
				if (agrupadoresServProc != null && agrupadoresServProc.size() > 0) {
					int cantidad = 0;
					for (AgrupadorServProc asp : agrupadoresServProc) {
						if (asp.getIndIntExt().equals(IConstantes.IND_INTERNA)) {
							cantidad += contarTotalServProc(idPersona, asp.getIdEspProc(), indicativoContador);
						} else if (asp.getIndIntExt().equals(IConstantes.IND_EXTERNA)) {
							cantidad += contarRemisiones(idPersona, asp.getIdEspProc());
						}
					}

					System.out.println("getContadores: cantidad = " + cantidad);

					double valorProxCobro = consultarValorCobro(idAgrupador, cantidad + 1);

					cont.setNombre(nombreCont);
					cont.setValor(valorProxCobro + "");
				}

				contadores.add(cont);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			conexion.cerrarConexion();

		}
		return contadores;
	}

	/**
	 * Obtiene el Agrupador al cual pertenece un servicio o procedimiento
	 *
	 * @return agrupador
	 * @throws Exception
	 *
	 */
	public static Agrupador consultarAgrupador(String indIntExt, String tipoEspProc, int idEspProc) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		String momentoCobro = null;
		Agrupador agrupador = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.id_agrupador, a.nombre, a.indicativo_contador");
			sql.append(" FROM agrupador a" + " JOIN agrupador_servicio_procedimiento asp"
					+ " ON asp.id_agrupador = a.id_agrupador" + " AND id_indicativo_int_ext = ?"
					+ " AND id_indicativo_esp_proc = ?" + " AND id_esp_proc = ?" + " AND estado = ?");

			parametros.add(indIntExt);
			parametros.add(tipoEspProc);
			parametros.add(idEspProc);
			parametros.add(IConstantes.ESTADO_ACTIVO);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				agrupador = new Agrupador();
				agrupador.setIdAgrupador((Integer) rs.getObject("id_agrupador"));
				agrupador.setNombre((String) rs.getObject("nombre" + ""));
				agrupador.setIndicativoContador((String) rs.getObject("indicativo_contador" + ""));

			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return agrupador;
	}

	/**
	 * Obtiene todos los AgrupadorServProc para un agrupador
	 * 
	 * @param idAgrupador
	 * @return lista con los objetos de AgrupadorServProc
	 * @throws Exception
	 */
	public static List<AgrupadorServProc> getAgrupadoresServProc(int idAgrupador) throws Exception {
		List<Object> parametros = new ArrayList<Object>();
		List<AgrupadorServProc> agrupadoresServProc = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_agrupador, id_indicativo_int_ext, id_indicativo_esp_proc, id_esp_proc, estado"
					+ " FROM agrupador_servicio_procedimiento WHERE id_agrupador = ?");

			parametros.add(idAgrupador);

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				AgrupadorServProc asp = new AgrupadorServProc();
				asp.setIdAgrupador((Integer) rs.getObject("id_agrupador"));
				asp.setIndIntExt((String) rs.getObject("id_indicativo_int_ext"));
				asp.setIndEspProc((String) rs.getObject("id_indicativo_esp_proc"));
				asp.setIdEspProc((Integer) rs.getObject("id_esp_proc"));
				asp.setEstado((String) rs.getObject("estado"));

				agrupadoresServProc.add(asp);
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return agrupadoresServProc;

	}

	/**
	 * Busca si el registro de la cita ya existe en total_servicio_procedimiento
	 * sin importar el estado de la cita
	 *
	 * @param idPersona
	 *            : id de la persona
	 * @param fecha
	 *            : fecha de la cita
	 * @param hora
	 *            : hora de inicio de la cita
	 * @param esp
	 *            : id de la especialidad de la cita
	 * @return true si el registro existe
	 * @throws Exception
	 */
	public static boolean buscarTotalServProc(int idPersona, String fecha, String hora, int esp) throws Exception {

		List<Object> parametros = new ArrayList<>();
		boolean existe = false;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM total_servicio_procedimiento");
			sql.append(" WHERE id_persona = ?" + " AND id_fecha_cita = ?" + " AND id_hora_cita = ?"
					+ " And id_especialidad = ?");

			parametros.add(idPersona);
			parametros.add(fecha);
			parametros.add(hora);
			parametros.add(esp);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				existe = true;
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return existe;
	}

	/**
	 * Consulta la cantidad de servicios o procedimientos de una persona que se
	 * deben tener en cuenta para la generación de cobros de cuota asistencial.
	 * Únicamente para servicios internos
	 * 
	 * @param idPersona
	 * @param idEspProc
	 *            : id de la especialidad o procedimiento
	 * @param indContador
	 *            : momento del cobro. 'S' = solicitada, 'A' = atendida
	 * @return
	 * @throws Exception
	 */
	public static Integer contarTotalServProc(Integer idPersona, Integer idEspProc, String indContador)
			throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Integer total = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;

		try {
			

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT COUNT(id_persona) AS total");
			sql.append(" FROM total_servicio_procedimiento t");
			sql.append(" WHERE id_persona = ?" + " AND id_especialidad = ?" + " AND (t.tipo_atencion = ?"
					+ " OR (t.tipo_atencion = ? AND t.control_cobro = ? )"
					+ ")");
			parametros.add(idPersona);
			parametros.add(idEspProc);
			parametros.add(IConstantes.ATENCION_PRIMERA_VEZ);
			parametros.add(IConstantes.ATENCION_CONTROL);
			parametros.add(IConstantes.AFIRMACION);

//			System.out.println("contarTotalServProc: indContador = " + indContador);
			if (indContador.equals(IConstantes.IND_ATENDIDA)) {
				sql.append(" AND t.estado_cita IN ( ? , ?)");
				parametros.add(IConstantes.IND_ATENDIDA);
				parametros.add(IConstantes.IND_SOLICITADA);
//				parametros.add(IConstantes.IND_INASISTIDA);

			} else if (indContador.equals(IConstantes.IND_SOLICITADA)) {
				sql.append(" AND t.estado_cita IN ( ? , ?, ?, ? ) ");
				parametros.add(IConstantes.IND_ATENDIDA);
				parametros.add(IConstantes.IND_SOLICITADA);
				parametros.add(IConstantes.IND_INASISTIDA);
				parametros.add(IConstantes.IND_APARTADA);
			}

			sql.append(" AND YEAR(t.id_fecha_cita) = ?");
			parametros.add(Calendar.getInstance().get(Calendar.YEAR));

//			System.out.println("contarTotalServProc: sql = \n" + sql);
			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {

				total = rs.getInt("total");
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}
		return total;
	}

	/**
	 * Consulta la cantidad de remisiones de una persona que se deben tener en
	 * cuenta para la generación de cobros de cuota asistencial. Únicamente para
	 * servicios externos
	 * 
	 * @param idPersona
	 * @param idTipoRemision
	 *            : el tipo de remision
	 * @param idTipoRemisionControl
	 *            :
	 * @param idEspProc
	 * @param ano
	 * @return
	 * @throws Exception
	 */
	public static Integer contarRemisiones(Integer idPersona, Integer idEspProc) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Integer total = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT SUM(re.cantidad_citas_remitidas) AS total ");
			sql.append(" FROM remision_especialidad re, remision r ");
			sql.append(" WHERE re.consecutivo_remision=r.id_consecutivo_remision"
					+ " AND re.consecutivo_remision IS NOT NULL" + " AND re.id_persona = ? "
					+ " AND (re.tipo_remision = ? " + " OR (re.tipo_remision = ? AND re.control_cobro = ? ))");

			parametros.add(idPersona);
			parametros.add(IConstantes.REM_EXTERNA);
			parametros.add(IConstantes.REM_CONTROL);
			parametros.add(IConstantes.AFIRMACION);

			if (idEspProc != null) {
				sql.append(" AND re.id_especialidad_remision = ? ");
				parametros.add(idEspProc);
			}

			sql.append(" AND YEAR(r.fecha_remision) = ?");
			parametros.add(Calendar.getInstance().get(Calendar.YEAR));

			rs = conexion.consultarBD(sql.toString(), parametros);

			while (rs.next()) {
				total = rs.getInt("total");
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();
		}

		return total;
	}

	/**
	 * Obtiene el porcentaje de cobro según el número de cita y calcula el valor
	 * del próximo cobro a partir del SMDV
	 * 
	 * @param agrupador
	 * @param numeroCita
	 * @return valor del cobro en pesos
	 * @throws Exception
	 */
	public static double consultarValorCobro(int agrupador, int numeroCita) throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		double porcentajeCita = -1;
		double smdv = -1;
		double valorCobro = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT c.porcentaje_cita, v.valor_criterio");
			sql.append(" FROM criterio_cobro_asistencial c" + " JOIN criterio_valor v"
					+ " ON c.criterio_valor = v.id_codigo_criterio_valor"
					+ " WHERE c.id_agrupador = ?"
					+ " AND (c.id_valor_min_citas <= ?"
					+ " AND (c.id_valor_max_citas >= ?"
					+ "	OR c.id_valor_max_citas IS NULL))");

			parametros.add(agrupador);
			parametros.add(numeroCita);
			parametros.add(numeroCita);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				BigDecimal pc = ((BigDecimal) rs.getObject("porcentaje_cita"));
				BigDecimal sm = ((BigDecimal) rs.getObject("valor_criterio"));

				porcentajeCita = Double.valueOf(pc + "");
				smdv = Double.valueOf(sm + "");
			}

			if (!(porcentajeCita < 0) && !(smdv < 0)) {
//				valorCobro = smdv * porcentajeCita / 100;
				valorCobro = Double.valueOf((new BigDecimal((smdv * porcentajeCita) / 100)).setScale(-2, RoundingMode.HALF_EVEN ).toString());
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return valorCobro;
	}

	/**
	 * Obtiene el máximo id en cobro_cuota_asistencial
	 *
	 * @return maxId
	 * @throws Exception
	 *
	 */
	public static Integer maxIdCobro() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Integer maxId = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MAX(id_cobro_cuota_asistencial) as maxId");
			sql.append(" FROM cobro_cuota_asistencial");

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				maxId = (Integer) rs.getObject("maxId");
			} else { // podría estar vacía
				Integer registros = ICuotaAsistencialDAO.cuentaCobros();
				if (registros != null) { // si obtiene una respuesta
					maxId = registros;
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
		return maxId;
	}

	/**
	 * Obtiene el numero de registros de cobro_cuota_asistencial
	 *
	 * @return registros
	 * @throws Exception
	 *
	 */
	public static Integer cuentaCobros() throws Exception {

		List<Object> parametros = new ArrayList<Object>();
		Integer registros = null;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) AS registros");
			sql.append(" FROM cobro_cuota_asistencial");

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				registros = (Integer) rs.getObject("registros");
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {
			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return registros;
	}

	/**
	 * Busca un cobro y retorna su id
	 * 
	 * @param fecha
	 *            : fecha de la cita
	 * @param hora
	 *            : hora de inicio de la cita
	 * @param idPersona
	 *            : persona a quien pertenece el cobro
	 * @param idEsp
	 *            : id de la especialidad
	 * @param claseCobro
	 *            : 'S'=cita/servicio, 'F'=formula,'P'=procedimiento,
	 *            'E'=especialidad externa
	 * @param consecutivoAtencion
	 *            : consecutivo de la atencion
	 * @return idCobro : id del cobro. Null si no se encontró ningún registro
	 * @throws Exception
	 */
	public static Integer buscarCobro(String fecha, String hora, int idPersona, int idEsp, String claseCobro,
			Integer consecutivoAtencion) throws Exception {
//		System.out.println("buscarCobro called");
		List<Object> parametros = new ArrayList<>();
		Conexion conexion = new Conexion("uisaludDS");
		Integer idCobro = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT id_cobro_cuota_asistencial FROM cobro_cuota_asistencial" + " WHERE id_persona = ?"
					+ " AND id_especialidad = ?" + " AND id_fecha_cita = " + fecha + " AND id_hora_cita = " + hora
					+ " AND clase_cobro = ?"
					+ " AND origen_cobro IS NULL AND anulada = 'N' AND causa_anulacion IS NULL");
			parametros.add(idPersona);
			parametros.add(idEsp);
			parametros.add(claseCobro);
			if (consecutivoAtencion != null && consecutivoAtencion > 0) {
				sql.append(" AND consecutivo_atencion = ?");
				parametros.add(consecutivoAtencion);
			} else {
				sql.append(" AND consecutivo_atencion is null");
			}

//			System.out.println("buscarCobro_ sql = " + sql);

			rs = conexion.consultarBD(sql.toString(), parametros);

			if (rs.next()) {
				idCobro = (Integer) rs.getObject("id_cobro_cuota_asistencial");
			}
		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}

			conexion.cerrarConexion();

		}
		return idCobro;
	}

	/**
	 * Comprueba si el paciente tiene un diagnóstico que corresponde a alto
	 * costo
	 *
	 * @param idPersona
	 * @param consecutivoAtencion
	 * @return esAltoCosto
	 * @throws Exception
	 */
	public static boolean comprobarAltoCosto(int idPersona, Integer consecutivoAtencion) throws Exception {
		boolean esAltoCosto = false;
		if (consecutivoAtencion != null && consecutivoAtencion > 0) {
			List<Object> parametros = new ArrayList<Object>();
			Conexion conexion = new Conexion("uisaludDS");
			ResultSet rs = null;
			try {

				StringBuilder sql = new StringBuilder();
				sql.append("SELECT CASE WHEN (c.id_codigo_caracteristica BETWEEN  1"
						+ " AND 14) AND (tipo_diagnostico=2 OR tipo_diagnostico=3)"
						+ " THEN 'true' ELSE 'false' END AS respuesta");

				sql.append(" FROM atencion_diagnostico ad ,caracteristicas_diagnostico cd, caracteristicas c"
						+ " WHERE ad.id_consecutivo_atencion= ?" + " AND ad.id_persona = ?"
						+ " AND ad.diagnostico_principal = 'S' " + " AND ad.id_diagnostico=cd.id_diagnostico"
						+ " AND cd.id_caracteristica=c.id_codigo_caracteristica");

				parametros.add(consecutivoAtencion);
				parametros.add(idPersona);

				rs = conexion.consultarBD(sql.toString(), parametros);

				if (rs.next()) {

					esAltoCosto = Boolean.valueOf((String) rs.getObject("respuesta"));
				}

			} catch (Exception e) {
				throw new Exception(e);

			} finally {

				if (rs != null) {
					rs.close();
				}
				conexion.cerrarConexion();

			}
		}
		return esAltoCosto;
	}

}
