package com.example.uisaludmovilv01.jbossTest.generales;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.google.firebase.messaging.AndroidConfig.Priority;

import com.example.uisaludmovilv01.jbossTest.beans.Beneficiario;
import com.example.uisaludmovilv01.jbossTest.beans.Cita;
import com.example.uisaludmovilv01.jbossTest.beans.CobroCuotaAsistencial;
import com.example.uisaludmovilv01.jbossTest.beans.Contador;
import com.example.uisaludmovilv01.jbossTest.beans.Especialidad;
import com.example.uisaludmovilv01.jbossTest.beans.FormulaMedica;
import com.example.uisaludmovilv01.jbossTest.beans.MedicamentoFormula;
import com.example.uisaludmovilv01.jbossTest.beans.Persona;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionEspecialidad;
import com.example.uisaludmovilv01.jbossTest.beans.RemisionProcedimiento;
import com.example.uisaludmovilv01.jbossTest.beans.TipoDocumentoID;
import com.example.uisaludmovilv01.jbossTest.modulos.cita.AdministrarCita;
import com.example.uisaludmovilv01.jbossTest.modulos.cita.ICitaDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial.AdministrarCuotaAsistencial;
import com.example.uisaludmovilv01.jbossTest.modulos.cobroCuotaAsistencial.ICuotaAsistencialDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.AdministrarNotificaciones;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.INotificacionDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.persona.AdministrarPersona;
import com.example.uisaludmovilv01.jbossTest.modulos.persona.IPersonaDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.remision.IRemisionDAO;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;

@WebService
// SoapWebService
public class uisaludMovilWS {

	// -------------- ACTIVIDAD PACIENTE -----------------

	@WebMethod()
	@WebResult(name = "validezAfiliado")
	public Boolean consultarvalidezAfil(@WebParam(name = "id_persona") int idPersona) {
		System.out.println("consultarvalidezAfil : called");
		Boolean valido = null;
		try {

			valido = AdministrarPersona.getValidezAfiliado(idPersona);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return valido;
//		 return false;
	}

	// --------------BENEFICIARIOS-----------------

	@WebMethod()
	@WebResult(name = "beneficiarios")
	public List<Beneficiario> consultarBeneficiarios(@WebParam(name = "id_cotizante") int id) {
		List<Beneficiario> datosBeneficiarios = null;
		try {

			datosBeneficiarios = ICuotaAsistencialDAO.getBeneficiarios(id);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return datosBeneficiarios;
	}

	// --------------CITAS-----------------

	@WebMethod()
	@WebResult(name = "agendada")
	public boolean agendarCitaPersona(@WebParam(name = "idPersona") int idPersona,
			@WebParam(name = "docProfesional") String docProfesional, @WebParam(name = "tipoDocProf") int tipoDocProf,
			@WebParam(name = "fecha") String fecha, @WebParam(name = "horaInicio") String horaInicio,
			@WebParam(name = "consecutivoAtencion") Integer consecutivoAtencion,
			@WebParam(name = "tipoAtencion") int tipoAtencion,
			@WebParam(name = "consecutivoRemision") Integer consecutivoRemision,
			@WebParam(name = "tipoRemision") Integer tipoRemision) {

		BigDecimal doc = new BigDecimal(docProfesional);
		short tipoDoc = ((Integer) tipoDocProf).shortValue();

		boolean agendada = false;
		try {
			agendada = AdministrarCita.agendarCita(idPersona, doc, tipoDoc, fecha, horaInicio, consecutivoAtencion,
					consecutivoRemision, tipoRemision);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return agendada;
	}

	@WebMethod()
	@WebResult(name = "cancelada")
	public boolean cancelarCitaPersona(@WebParam(name = "idPersona") int idPersona,
			@WebParam(name = "docProfesional") String docProfesional, @WebParam(name = "tipoDocProf") int tipoDocProf,
			@WebParam(name = "fecha") String fecha, @WebParam(name = "horaInicio") String horaInicio,
			@WebParam(name = "consecutivoAtencion") int consecutivoAtencion) {

		BigDecimal doc = new BigDecimal(docProfesional);
		short tipoDoc = ((Integer) tipoDocProf).shortValue();
		boolean cancelada = false;
		try {
			cancelada = AdministrarCita.cancelarCita(idPersona, doc, tipoDoc, fecha, horaInicio, consecutivoAtencion);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}
		return cancelada;
	}

	@WebMethod()
	@WebResult(name = "citas")
	public List<Cita> consultarCitasPersona(@WebParam(name = "id_paciente") int idPaciente) {

		List<Cita> citas = null;
		try {
			citas = ICitaDAO.getCitasPersona(idPaciente);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return citas;
	}

	@WebMethod()
	@WebResult(name = "citasDisponibles")
	public List<Cita> consultarCitasDisponibles(@WebParam(name = "id_esp") int idEsp,
			@WebParam(name = "fecha") String fecha, @WebParam(name = "tipoCita") int tipoCita,
			@WebParam(name = "docProfesional") String docProfesional,
			@WebParam(name = "tipoDocProf") Integer tipoDocProf) {

		List<Cita> citas = null;
		BigDecimal doc = null;
		Short tipoDoc = null;
		
		if (docProfesional != null)
			doc = new BigDecimal(docProfesional);
		if (tipoDocProf != null)
			tipoDoc = ((Integer) tipoDocProf).shortValue();
		
		try {
			
			citas = ICitaDAO.getCitasDisponibles(idEsp, fecha, tipoCita, doc, tipoDoc);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return citas;
	}



	// --------------COBROS-----------------

	@WebMethod()
	@WebResult(name = "cobros_asistenciales")
	public List<CobroCuotaAsistencial> consultarCobrosAsistenciales(@WebParam(name = "id_persona") int idPersona) {
		List<CobroCuotaAsistencial> cobrosAsistenciales = null;
		try {
			System.out.println("consultarCobrosAsistenciales: called");

			cobrosAsistenciales = ICuotaAsistencialDAO.getCobrosCuotaAsistencial(idPersona);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return cobrosAsistenciales;
	}

	@WebMethod()
	@WebResult(name = "contadores")
	public List<Contador> consultarContadores(@WebParam(name = "id_persona") int idPersona) {
		List<Contador> contadores = null;
		try {
			System.out.println("consultarContadores: called");

			contadores = ICuotaAsistencialDAO.getContadores(idPersona);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return contadores;
	}

	// --------------ESPECIALIDADES-----------------

	@WebMethod()
	@WebResult(name = "especialidades")
	public List<Especialidad> consultarEspecialidades() {

		List<Especialidad> esp = null;
		try {
			esp = IConsultasDAO.getEspecialidades();

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return esp;
	}

	@WebMethod()
	@WebResult(name = "especialidad")
	public Especialidad consultarEspecialidadById(@WebParam(name = "id_esp") int idEsp) {

		Especialidad especialidad = null;
		try {
			especialidad = IConsultasDAO.getEspecialidById(idEsp);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return especialidad;
	}

	@WebMethod()
	@WebResult(name = "nombre_esp")
	public String consultarNombreEsp(@WebParam(name = "id_esp") int idEsp) {

		String nombreEsp = null;
		try {
			nombreEsp = IConsultasDAO.getNombreEspProc(idEsp, 'E');

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return nombreEsp;
	}

	// -------------- FIREBASE CLOUD MESSAGING-----------------
	
	private final AdministrarNotificaciones ADMIN_NOTI = new AdministrarNotificaciones();

	@WebMethod()
	@WebResult(name = "registerFCMToken")
	public boolean registerFCMToken(@WebParam(name = "idPersona") int idPersona,
			@WebParam(name = "token") String token) {

		boolean response = false;
		try {

			response = INotificacionDAO.onNewToken(idPersona, token);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return response;
	}

	@WebMethod()
	@WebResult(name = "notifyAutRem")
	public String notificarAutRem(@WebParam(name = "idPersona") int idPersona,
			@WebParam(name = "idEspProc") int idEspProc,
			@WebParam(name = "indicativoEspProc") String indicativoEspProc,
			@WebParam(name = "consAtencion") int consAtencion,
			@WebParam(name = "consRemProc") Integer consRemProc,
			@WebParam(name = "fueAutorizado") boolean fueAutorizado) {

		String response = "";
		try {
			char indEspPro = indicativoEspProc.charAt(0);

			response = AdministrarNotificaciones.notificarAutRem(idPersona, idEspProc, indEspPro,
					consAtencion, consRemProc, fueAutorizado);
		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return response;
	}

	@WebMethod()
	@WebResult(name = "notificarAutMed")
	public String notificarAutMed(@WebParam(name = "numeroFormula") int numeroFormula,
			@WebParam(name = "codigoMedicamento") String codigoMedicamento) {

		String response = "";
		try {

			response = AdministrarNotificaciones.notificarAutMed(numeroFormula, codigoMedicamento);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return response;
	}

	@WebMethod()
	@WebResult(name = "notifyGenCobro")
	public String notificarGenCobro(@WebParam(name = "idPersona") int idPersona,
			@WebParam(name = "idCobro") int idCobro,
			@WebParam(name = "idEsp") int idEsp,
			 @WebParam(name = "fechaCita") String fechaCita,
			 @WebParam(name = "horaCita") String horaCita,
			@WebParam(name = "claseCobro") String claseCobro) {

		String response = "";

		System.out.println("notificarGenCobro: fechaCita = " + fechaCita);
		try {
			char clase = claseCobro.charAt(0);

			response = AdministrarNotificaciones.notifyCobro(idPersona, idCobro, idEsp, fechaCita, horaCita, clase);
			

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return response;
	}

	@WebMethod()
	@WebResult(name = "notificarSingleDeviceData")
	public String notificarSingleDeviceData(@WebParam(name = "idPersona") int idPersona,
			 @WebParam(name = "message") String message) {

		String response = "";

		try {

			response = AdministrarNotificaciones.notifySingleDeviceData(idPersona, message);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return response;
	}

	// -------------- FROMULA MEDICA -----------------

	@WebMethod()
	@WebResult(name = "formulas")
	public List<FormulaMedica> consultarFormulasPaciente(@WebParam(name = "id_paciente") int idPaciente) {
		List<FormulaMedica> formulas = null;
		try {

			formulas = IConsultasDAO.getFormulasMedicasPaciente(idPaciente);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return formulas;
	}

	// -------------- LOGIN -----------------

	@WebMethod()
	@WebResult(name = "login")
	public List<Integer> iniciarSesion(@WebParam(name = "tipo_doc") int tipoDocumento,
			@WebParam(name = "num_doc") String documento, @WebParam(name = "password") String contrasena) {
		List<Integer> idAfiliado = null;
		try {

			idAfiliado = IPersonaDAO.iniciarSesion(tipoDocumento, new BigDecimal(documento), contrasena);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return idAfiliado;
	}

	// -------------- MEDICAMENTO FORMULA -----------------

	@WebMethod()
	@WebResult(name = "medicamentos")
	public List<MedicamentoFormula> consultarMedicamentos(@WebParam(name = "id_formula") int idFormula) {
		List<MedicamentoFormula> medicamentos = null;
		try {

			medicamentos = IConsultasDAO.getMedicamentos(idFormula);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return medicamentos;
	}

	// -------------- PACIENTES -----------------

	@WebMethod()
	@WebResult(name = "nombre_paciente")
	public String consultarNombrePaciente(@WebParam(name = "id_paciente") int idPaciente) {

		String nombrePaciente = null;
		try {
			nombrePaciente = IPersonaDAO.getNombrePersona(idPaciente);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return nombrePaciente;
	}

	@WebMethod()
	@WebResult(name = "pacienteId")
	public Persona consultarPacienteById(@WebParam(name = "id_paciente") int idPaciente) {
		Persona persona = null;
		try {
			persona = IPersonaDAO.getPersonaById(idPaciente);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return persona;
	}

	// -------------- PROFESIONAL -----------------

	@WebMethod()
	@WebResult(name = "nombre_doctor")
	public String consultarNombreDoctor(@WebParam(name = "tipo_doc") int tipoDocId,
			@WebParam(name = "doc_id") String docProfesional) {
		String nombreDoc = null;
		try {
			BigDecimal doc = new BigDecimal(docProfesional);
			short tipoDoc = ((Integer) tipoDocId).shortValue();
			

			nombreDoc = IConsultasDAO.getNombreProfesional(doc, tipoDoc);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return nombreDoc;
	}

	// -------------- REMISION ESPECIALIDAD -----------------

	@WebMethod()
	@WebResult(name = "rem_esp")
	public List<RemisionEspecialidad> consultarRemisionesEspPaciente(@WebParam(name = "id_persona") int idPersona) {
		List<RemisionEspecialidad> remEsp = null;
		try {

			remEsp = IRemisionDAO.getRemisionesEspecialidad(idPersona);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return remEsp;
	}

	@WebMethod()
	@WebResult(name = "comprobarAltoCosto")
	public boolean comprobarAltoCosto(@WebParam(name = "id_paciente") int idPaciente,
			@WebParam(name = "consecutivo_aten") int consecutivoAten) {

		boolean valido = false;
		try {

			valido = ICuotaAsistencialDAO.comprobarAltoCosto(idPaciente, consecutivoAten);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return valido;
	}

	// -------------- REMISION PROCEDIMIENTO -----------------

	@WebMethod()
	@WebResult(name = "rem_proc")
	public List<RemisionProcedimiento> consultarRemisionesProcedimiento(@WebParam(name = "id_persona") int idPersona) {
		List<RemisionProcedimiento> remProc = null;
		try {

			remProc = IRemisionDAO.getRemisionesProcedimiento(idPersona);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return remProc;
	}

	// -------------- TIEMPO -----------------

	@WebMethod()
	@WebResult(name = "tiempoSolicitud")
	public int consultarTiempoSolicitud(@WebParam(name = "id_esp") int idEsp) {
		int tiempoSolicitud = 0;
		try {

			tiempoSolicitud = ICitaDAO.consultarTiempoSolicitud(idEsp);

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return tiempoSolicitud;
	}

	@WebMethod()
	@WebResult(name = "tiempoCancelacion")
	public int consultarTiempoCancelacion() {
		int tiempoCancelacion = 0;
		try {

			tiempoCancelacion = ICitaDAO.consultarTiempoCancelacion();

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return tiempoCancelacion;
	}

	// -------------- TIPOS DOCUMENTO -----------------

	@WebMethod()
	@WebResult(name = "tipos_documento")
	public List<TipoDocumentoID> consultarTiposDocumento() {
		List<TipoDocumentoID> tiposDocumento = null;
		try {

			tiposDocumento = IConsultasDAO.getTiposDocumentoID();

		} catch (Exception e) {
			IConstantes.log.error(e, e);
		}

		return tiposDocumento;
	}

}