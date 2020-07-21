package com.example.uisaludmovilv01.jbossTest.modulos.cita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import com.example.uisaludmovilv01.jbossTest.Conexion;
import com.example.uisaludmovilv01.jbossTest.beans.Agrupador;
import com.example.uisaludmovilv01.jbossTest.beans.AgrupadorServProc;
import com.example.uisaludmovilv01.jbossTest.beans.Beneficiario;
import com.example.uisaludmovilv01.jbossTest.beans.Cita;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionEspecialidad;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial.AdministrarCuotaAsistencial;
import com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial.ICuotaAsistencialDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.persona.AdministrarPersona;
import com.example.uisaludmovilv01.jbossTest.modulos.remision.AdministrarRemision;
import com.example.uisaludmovilv01.jbossTest.modulos.remision.IRemisionDAO;

public class AdministrarCita implements Serializable {

	/**
	 * Agenda una cita médica.
	 * 
	 * @param idPersona:
	 *            id del solicitante
	 * @param docProfesional
	 *            : numero de documento del profesional
	 * @param tipoDocProf
	 *            : tipo de documento del profesional
	 * @param fecha
	 *            : yyyy-MM-dd
	 * @param hora:
	 *            la hora de inicio HH:mm
	 * @param consecutivoAtencion
	 *            : consecutivo de remisión si es una cita de remisión
	 * @param consecutivoRemision
	 *            : consecutivo de remisión si es una cita de remisión
	 * @param tipoRemision
	 *            : tipo de remisión si es una cita de remisión interna
	 * @return respuesta : String con la descripción del resultado
	 * @throws Exception
	 */
	public static boolean agendarCita(int idPersona, BigDecimal docProfesional, short tipoDocProf, String fecha,
			String hora, @Nullable Integer consecutivoAtencion, Integer consecutivoRemision, Integer tipoRemision)
					throws Exception {
		System.out.println("agendarCita: called");

		Cita cita = null;
		Agrupador agrupador = null;
		Integer numeroCita = null;
		String controlCobro = null;
		boolean agendada = false;
		boolean exento = false;
		boolean cobrado = false;
		boolean duplicada = false;
		String respuesta = "";
		try {

			cita = ICitaDAO.buscarCita(docProfesional, tipoDocProf, fecha, hora);

			// Se revisa que la cita este disponible
			if (cita != null) {
				if (cita.getEstado() == IConstantes.ESTADO_CITA_DISPONIBLE) {

					// Se revisa si el tiempo de solicitud es adecuado
					if (validarTiempoSolicitud(fecha, hora, cita.getEspecialidad().getIdEspecialidad(), cita.getTipoCita())) {

						boolean porcentajeValido = validarPorcenSolEsp(cita.getEspecialidad().getIdEspecialidad(), fecha,
								IConstantes.MEDIO_SOLICITUD_MOVIL);
						// Se revisa que no se haya superado el porcentaje de
						// solicitud para una esp. por medio de soicitud
						if (porcentajeValido) {

							agendada = editarCita(idPersona, cita, consecutivoAtencion, IConstantes.AGENDAR_CITA);

							if (agendada) {
								System.out.println("agendarCita: agendada = " + true);

								// Se revisa si el afiliado esta exento de cobro
								exento = AdministrarCuotaAsistencial.cumpleExcepcionCobro(idPersona,
										cita.getTipoAtencion(), cita.getTipoCita(), consecutivoRemision,
										consecutivoAtencion);

								System.out.println("agendarCita: exento = " + exento);

								if (!exento) {
									// Se revisa si se genera el cobrosegun
									// consumos
									// pasados
									agrupador = ICuotaAsistencialDAO.consultarAgrupador(IConstantes.IND_INTERNA,
											IConstantes.IND_SERVICIO, cita.getEspecialidad().getIdEspecialidad());

									if (agrupador != null) {
										System.out.println("agendarCita: agrupador.getIdAgrupador() = "
												+ agrupador.getIdAgrupador());

										// El momento del cobro puede darse al
										// solicitar
										// o al asistir una cita
										if (agrupador.getIndicativoContador() != null && agrupador
												.getIndicativoContador().equals(IConstantes.MOMENTO_COBRO_CITA)) {

											List<AgrupadorServProc> agruparoesServProc = ICuotaAsistencialDAO
													.getAgrupadoresServProc(agrupador.getIdAgrupador());

											numeroCita = 0;
											if (agruparoesServProc != null && agruparoesServProc.size() > 0) {
												for (AgrupadorServProc asp : agruparoesServProc) {

													Integer cont = ICuotaAsistencialDAO.contarTotalServProc(idPersona,
															asp.getIdEspProc(), IConstantes.MOMENTO_COBRO_CITA);

													// System.out.println(String.format("agendarCita:
													// esp = % d, cont = %d",
													// asp.getIdEspProc(),
													// cont));
													numeroCita += (cont != null) ? cont : 0;
												}
											}
											// se suma 1 para la cita que no se
											// ha
											// duplicado
											numeroCita++;

											System.out.println("agendarCita: numeroCita = " + numeroCita);

											double valorCobro = ICuotaAsistencialDAO
													.consultarValorCobro(agrupador.getIdAgrupador(), numeroCita);
											System.out.println("agendarCita: valorCobro = " + valorCobro);

											if (valorCobro > 0) {
												// Sí genera cobro
												cobrado = AdministrarCuotaAsistencial.insertarCobroCa(idPersona,
														cita.getEspecialidad().getIdEspecialidad(), fecha, hora, consecutivoAtencion,
														valorCobro);
												System.out.println("agendarCita: cobrado = " + cobrado);
											}
										}
									}
								}

								// Si es una remision
								if (consecutivoRemision != null && consecutivoAtencion != null
										&& tipoRemision != null) {
									System.out.println("agendarCita: es remision");
									System.out.println("agendarCita: consecutivoRemision = " + consecutivoRemision);
									System.out.println("agendarCita: consecutivoAtencion = " + consecutivoAtencion);
									System.out.println("agendarCita: tipoRemision = " + tipoRemision);

									if (tipoRemision == 3) // Si es remision de
															// control
										controlCobro = (cobrado) ? "'S'" : "'N'"; // si
																					// generó
																					// cobro

									System.out.println("agendarCita: controlCobro = " + controlCobro);
									boolean remModificada = AdministrarRemision.editarSolicitadasRemision(idPersona,
											consecutivoAtencion, cita.getEspecialidad().getIdEspecialidad(), 1, controlCobro);

									System.out.println("agendarCita: remModificada = " + remModificada);
								}

								duplicada = duplicarAgendada(idPersona, cita.getEspecialidad().getIdEspecialidad(), fecha, hora,
										cita.getTipoAtencion(), controlCobro);
								System.out.println("agendarCita: duplicada = " + duplicada);
							} else // no se agendó la cita
								respuesta = IConstantes.ERROR_CITA_NO_AGENDADA;
						} else // porcentaje superado
							respuesta = IConstantes.ERROR_PORCENTAJE_SUPERADO;

					} else { // tiempo invalido
						int minutos = ICitaDAO.consultarTiempoSolicitud(cita.getEspecialidad().getIdEspecialidad());
						respuesta = IConstantes.ERROR_CITA_NO_AGENDADA
								+ IConstantes.errorTiempoInvalidoCita(IConstantes.AGENDAR_CITA, minutos);
					}

				} else // cita no disponible
					respuesta = IConstantes.ERROR_CITA_NO_DISPONIBLE;
			} else //cita no encontrada
				respuesta = IConstantes.ERROR_CITA_NO_ENCONTRADA;
			System.out.println("agendarCita: respuesta = " + respuesta);

		} catch (Exception e) {
			throw new Exception(e);

		}
		return agendada;

	}

	/**
	 * Duplica la cita que se agendó en total_servicio_procedimiento
	 *
	 * @return cita
	 * @throws Exception
	 */
	public static boolean duplicarAgendada(int idPersona, int idEsp, String fecha, String hora, int tipoAtencion,
			String controlCobro) throws Exception {
		System.out.println("duplicarAgendada: called");

		boolean done = false;
		try {

			boolean copiaExiste = ICuotaAsistencialDAO.buscarTotalServProc(idPersona, fecha, hora, idEsp);
			if (copiaExiste) {
				done = AdministrarCuotaAsistencial.actualizarTotalServProc(idPersona, idEsp, fecha, hora,
						IConstantes.TSP_ESTADO_SOLICITADA, IConstantes.IGNORAR);

			} else {
				done = AdministrarCuotaAsistencial.insertarTotalServProc(idPersona, idEsp, fecha, hora, tipoAtencion,
						IConstantes.TSP_ESTADO_SOLICITADA, controlCobro);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return done;
	}

	/**
	 * Cancela una cita médica
	 * 
	 * @param idPersona
	 * @param docProfesional
	 * @param tipoDocProf
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param sede
	 * @param tipoCita
	 * @param esp
	 * @param estado
	 * @param consecutivoAtencion
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean cancelarCita(int idPersona, BigDecimal docProfesional, short tipoDocProf, String fecha,
			String hora, Integer consecutivoAtencion) throws Exception {
		System.out.println("cancelarCita: called");

		boolean tiempoValido = false;
		String controlCobro = null;
		boolean cobroEliminado = false;
		boolean modificado = false;
		boolean cancelada = false;
		Conexion conexion = new Conexion("uisaludDS");
		boolean duplicada = false;
		try {

			Cita cita = ICitaDAO.buscarCita(docProfesional, tipoDocProf, fecha, hora);

			tiempoValido = validarTiempoCancelacion(fecha, hora);
			// System.out.println("Cancelación. tiempoValido = " +
			// tiempoValido);
			if (cita != null) {
				if (cita.getEstado() == IConstantes.ESTADO_CITA_AGENDADA) {
					if (tiempoValido == true) {

						duplicada = insertarCitaCancelada(cita);
						System.out.println("cancelarCita: duplicada = " + duplicada);

						if (duplicada) {
							cancelada = editarCita(idPersona, cita, null, IConstantes.CANCELAR_CITA);
						}

						System.out.println("cancelarCita: cancelada = " + cancelada);

						if (cancelada) {

							cobroEliminado = AdministrarCuotaAsistencial.anularCobroCA(idPersona,
									cita.getEspecialidad().getIdEspecialidad(), fecha, hora, cita.getConsecutivoAtencion(),
									cita.getTipoAtencion());
							System.out.println("cancelarCita: cobroEliminado = " + cobroEliminado);

							// Si era cita de control
							if (cobroEliminado && cita.getTipoAtencion() == IConstantes.ATENCION_CONTROL) {
								controlCobro = null;
								System.out.println("cancelarCita: controlCobro is null");
								// modificado =
								// modificarControlCobroRemision(consecutivoRemision,
								// controlCobro);

							}
							// System.out.println("cancelarCita: controlCobro =
							// " +
							// controlCobro);
							System.out.println("cancelarCita: right before editarSolicitada");
							AdministrarRemision.editarSolicitadasRemision(idPersona, consecutivoAtencion,
									cita.getEspecialidad().getIdEspecialidad(), -1, controlCobro);
							System.out.println("cancelarCita: right after editarSolicitada");

							modificado = AdministrarCuotaAsistencial.actualizarTotalServProc(idPersona,
									cita.getEspecialidad().getIdEspecialidad(), fecha, hora, IConstantes.TSP_ESTADO_CANCELADA,
									IConstantes.IGNORAR);
							System.out.println("cancelarCita: modificado = " + modificado);
						}

					} else {
						System.out.println("cancelarCita: Tiempo inválido");
						return false;
					}
				} else {
					System.out.println("cancelarCita: Cita no está agendada");
					return false;
				}
			} else {
				System.out.println("cancelarCita: No se encontró la cita");
				return false;
			}

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();
		}
		return modificado;
	}

	/**
	 * Crea un registro en la tabla cita_cancelada
	 * 
	 * @param cita
	 *            : la cita que se va a cancelar
	 * @return true si se creó con éxito
	 * @throws Exception
	 */
	public static boolean insertarCitaCancelada(Cita cita) throws Exception {
		Map<String, Object> info = new HashMap<>();
		boolean done = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {

//			 System.out.println("insertarCitaCancelada : id_fecha = " +
//			 cita.getIdFecha());
//			 System.out.println(
//			 "insertarCitaCancelada : id_fecha_cancela = " +
//			 IConstantes.SQL_DATE_FORMAT.format(new Date()));
//			 System.out.println("insertarCitaCancelada : id_hora_inicio = " +
//			 cita.getIdHoraInicio());
//			 System.out.println("insertarCitaCancelada : hora_fin = " +
//			 cita.getHoraFin());

			info = new HashMap<>();
			info.put("id_documento_profesional", cita.getProfesional().getIdDocumento());
			info.put("id_tipo_documento_profesional", cita.getProfesional().getIdTipoDocumento());
			info.put("id_fecha", cita.getIdFecha());
			info.put("id_hora_inicio", cita.getIdHoraInicio());
			info.put("hora_fin", cita.getHoraFin());
			info.put("id_fecha_cancela", new Date());
			info.put("sede", cita.getSedeId());
			info.put("tipo_cita", cita.getTipoCita());
			info.put("especialidad", cita.getEspecialidad().getIdEspecialidad());
			info.put("consultorio", cita.getConsultorio());
			info.put("persona_afiliado", cita.getPersonaAfiliado());
			info.put("medio_cita", IConstantes.MEDIO_SOLICITUD_MOVIL);
			info.put("tipo_programacion", cita.getTipoProgramacion());
			info.put("motivo_cancelacion", IConstantes.MOTIVO_CANCELACION_MOVIL);

			Map<String, Object> map = IConsultasDAO.finalColumns(info, 'I');
			info = (map != null) ? map : info;

			done = conexion.insertarBD("cita_cancelada", info);

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return done;
	}

	/**
	 * Modifica un registro de cita de acuerdo a la acción indicada para
	 * asignarla a una persona o cancelarala
	 * 
	 * @param idPersona
	 *            : persona a la que pertenece la cita
	 * @param cita
	 *            : la cita a agendar/cancelar
	 * @param consecutivoAtencion
	 *            : consecutivo de la atención o null
	 * @param agendarCancelar
	 *            : "A" para agendar, "C" para cancelar
	 * @return true si se modificó el registro exitosamente
	 * @throws Exception
	 */
	public static boolean editarCita(int idPersona, Cita cita, Integer consecutivoAtencion, String agendarCancelar)
			throws Exception {

		Map<String, Object> campos = new HashMap<>();
		Map<String, Object> condiciones = new HashMap<>();
		boolean agendada = false;
		Conexion conexion = new Conexion("uisaludDS");
		try {

			if (agendarCancelar.equals(IConstantes.AGENDAR_CITA)) {

				campos.put("persona_afiliado", idPersona);
				campos.put("medio_cita", IConstantes.MEDIO_SOLICITUD_MOVIL);
				campos.put("estado", IConstantes.ESTADO_CITA_AGENDADA);
				if (consecutivoAtencion != null && consecutivoAtencion > 0) {
					campos.put("consecutivo_atencion", consecutivoAtencion);
				}

				condiciones.put("estado", IConstantes.ESTADO_CITA_DISPONIBLE);

			} else if (agendarCancelar.equals(IConstantes.CANCELAR_CITA)) {

				campos.put("persona_afiliado", null);
				campos.put("medio_cita", null);
				campos.put("estado", IConstantes.ESTADO_CITA_DISPONIBLE);
				campos.put("consecutivo_atencion", null);

				condiciones.put("estado", IConstantes.ESTADO_CITA_AGENDADA);

			} else
				return false;

			Map<String, Object> map = IConsultasDAO.finalColumns(campos, 'U');
			campos = (map != null) ? map : campos;
			
			condiciones.put("id_documento_profesional", cita.getProfesional().getIdDocumento());
			condiciones.put("id_tipo_documento_profesional", cita.getProfesional().getIdTipoDocumento());
			condiciones.put("id_fecha", cita.getIdFecha());
			condiciones.put("id_hora_inicio", cita.getIdHoraInicio());

			// Se agenda la cita
			agendada = conexion.actualizarBD("cita", campos, condiciones);

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			conexion.cerrarConexion();

		}
		return agendada;

	}

	/**
	 * Valida la solicitud de una cita a partir del porcentaje de solicitud
	 * máximo para un medio de solicitud y una especialidad
	 * 
	 * @param idEsp
	 * @param fecha
	 * @param idMedioSolCita
	 * @return
	 * @throws Exception
	 */
	public static boolean validarPorcenSolEsp(int idEsp, String fecha, int idMedioSolCita) throws Exception {

		try {
			List<Cita> citas = ICitaDAO.getCitasEspDia(idEsp, fecha);
			int porcMax = ICitaDAO.getPorcentajeMedioSol(idEsp, idMedioSolCita);
			if (citas != null && citas.size() > 0 && porcMax > 0) {

				int total = citas.size();
				int agendadasMov = 0;

				for (Cita c : citas) {
					if (c.getEstado() == 2 && c.getMedioCita() != null && c.getMedioCita() == idMedioSolCita)
						agendadasMov++; // cuantas ya fueron solicitadas por ese
										// medio

				}
				// cuantas citas se pueden agendar sin exceder el porcentaje
				int agMax = (int) Math.floor((total * porcMax / 100.0));

				if (agendadasMov < agMax)
					return true;
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		return false;
	}

	// --------------------- TIEMPO CITA--------------------

	/**
	 * Revisa si el tiempo para solicitar una cita es válido-
	 *
	 * @return personas
	 * @throws Exception
	 */
	public static boolean validarTiempoSolicitud(String fecha, String hora, int idEsp, int tipoCita) throws Exception {

		boolean valido = false;
		int diasAnt = 0;
		int tiempoSolicitudEsp = 0;
		int tiempoSolicitud = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {
			diasAnt = ICitaDAO.consultarDiasAntTipoCita(tipoCita);// en dias
			diasAnt = (diasAnt * 1440); // a minutos

			tiempoSolicitudEsp = ICitaDAO.consultarTiempoSolicitud(idEsp); // en
																			// minutos

			tiempoSolicitud = (tiempoSolicitudEsp > diasAnt) ? tiempoSolicitudEsp : diasAnt;
			valido = validarTiempo(tiempoSolicitud, fecha, hora);

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return valido;
	}

	/**
	 * Revisa si el tiempo para cancelar una cita es válido
	 * 
	 * @return personas
	 * @throws Exception
	 */
	public static boolean validarTiempoCancelacion(String fecha, String hora) throws Exception {

		boolean valido = false;
		int tiempoCancelacion = 0;
		Conexion conexion = new Conexion("uisaludDS");
		ResultSet rs = null;
		try {

			tiempoCancelacion = ICitaDAO.consultarTiempoCancelacion();

			valido = validarTiempo(tiempoCancelacion, fecha, hora);

		} catch (Exception e) {
			throw new Exception(e);

		} finally {

			if (rs != null) {
				rs.close();
			}
			conexion.cerrarConexion();

		}
		return valido;
	}

	/**
	 * Valida la diferencia de tiempo indicada entre la fecha de la cita y el
	 * momento actual
	 * 
	 * @param tiempo
	 *            minutos de anterioridad minimos para solicitar la cita
	 * @param fechaCita
	 *            objeto date con la fecha y hora de la cita
	 * @return true si la diferencia entre la fechaCita y Date now() es mayor o
	 *         igual al tiempo indicado
	 */
	public static boolean validarTiempo(int tiempo, String fecha, String hora) throws Exception {
		try {
			if (tiempo > 0) {

				Date fechaCita = IConstantes.SQL_DATE_FORMAT.parse(fecha);
				Date horaCita = IConstantes.SQL_HOUR_FORMAT.parse(hora);
				fechaCita.setHours(horaCita.getHours());
				fechaCita.setMinutes(horaCita.getMinutes());
				Date today = new Date();

				long difFechas = fechaCita.getTime() - today.getTime(); // en
																		// millis
				long t = TimeUnit.MILLISECONDS.convert(tiempo, TimeUnit.MINUTES);

				if (difFechas >= t)
					return true;

			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return false;
	}

	/**
	 * Valida si el control se está solicitando en los 15 días sin cobro con el
	 * tiempo mínimo (en minutos) pasado como parámetro
	 *
	 * @return personas
	 * @throws Exception
	 */
	public static boolean validarTiempoControl(Integer consecutivoRemision) throws Exception {
		boolean valido = false;
		if (consecutivoRemision != null && consecutivoRemision > 0) {
			Date fechaTope = IRemisionDAO.getFechaRemision(consecutivoRemision);
			// System.out.println("validarTiempoControl: fechaRem = " +
			// fechaRem);
			// int y = fechaRem.getYear();
			// int m = fechaRem.getMonth();
			// int d = fechaRem.getDate();
			// System.out.println("validarTiempoControl: y = " + y);
			// System.out.println("validarTiempoControl: m = " + m);
			// System.out.println("validarTiempoControl: d = " + d);

			if (IConstantes.TIEMPO_CONTROL > 0 && fechaTope != null) {

				// Date fechaTope = new Date(y, m, d);
				fechaTope.setDate(fechaTope.getDate() + IConstantes.TIEMPO_CONTROL);
				// System.out.println("validarTiempo: fechaTopeGuess = " +
				// fechaTope);

				Date today = new Date();
				Date fechaActual = new Date(today.getYear(), today.getMonth(), today.getDate());
				// fechaActual.setYear(fechaActual.getYear() + 1900);
				// System.out.println("validarTiempoControl: fechaActual = " +
				// fechaActual);

				int difFechas = fechaActual.compareTo(fechaTope);
				// System.out.println("validarTiempoControl: difFechas = " +
				// difFechas);

				if (difFechas <= 0) {
					valido = true;
				}

			}
		}
		// System.out.println("validarTiempoControl: valido = " + valido);
		return valido;
	}

}
