package com.example.uisaludmovilv01.jbossTest.beans;

public class RemisionEspecialidad extends Remision{

	private int idPersona;
	private int idConsecutivoAtencion;
//	private int idEspecialidadRemision;
	private Especialidad especialidad;
	private int consecutivoRemision;
	private int tipoRemision;
	private String nombreTipoRemision;
	private String diagnostico;
	private String justificacionRemision;
	private Short cantidadCitasRemitidas;
	private Short cantidadCitasSolicitadas;
//	private Short cantidadCitasAtendidas;
//	private Short cantidadCitasInasistidas;
	private String requiereAprobacionJerarquica;
	private String indicativoAprobada;
	private String fechaAprobacion;
//	private int codigoClaseContrato;
//	private int numeroContrato;
//	private Short sucursalConvenio;
	private String fechaControl;
	private String controlCobro;
	private String procedimiento;
	private int tipoAtencion;
	private String nombreTipoAtencion;
	
	public RemisionEspecialidad(){
		super();
		this.especialidad = new Especialidad();
	}
	
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdConsecutivoAtencion() {
		return idConsecutivoAtencion;
	}
	public void setIdConsecutivoAtencion(int idConsecutivoAtencion) {
		this.idConsecutivoAtencion = idConsecutivoAtencion;
	}
//	public int getIdEspecialidadRemision() {
//		return idEspecialidadRemision;
//	}
//	public void setIdEspecialidadRemision(int idEspecialidadRemision) {
//		this.idEspecialidadRemision = idEspecialidadRemision;
//	}
	
	
	public int getConsecutivoRemision() {
		return consecutivoRemision;
	}
	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public void setConsecutivoRemision(int consecutivoRemision) {
		this.consecutivoRemision = consecutivoRemision;
	}
	public int getTipoRemision() {
		return tipoRemision;
	}
	public void setTipoRemision(int tipoRemision) {
		this.tipoRemision = tipoRemision;
	}
	public String getNombreTipoRemision() {
		return nombreTipoRemision;
	}
	public void setNombreTipoRemision(String nombreTipoRemision) {
		this.nombreTipoRemision = nombreTipoRemision;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	public String getJustificacionRemision() {
		return justificacionRemision;
	}
	public void setJustificacionRemision(String justificacionRemision) {
		this.justificacionRemision = justificacionRemision;
	}
	public Short getCantidadCitasRemitidas() {
		return cantidadCitasRemitidas;
	}
	public void setCantidadCitasRemitidas(Short cantidadCitasRemitidas) {
		this.cantidadCitasRemitidas = cantidadCitasRemitidas;
	}
	public Short getCantidadCitasSolicitadas() {
		return cantidadCitasSolicitadas;
	}
	public void setCantidadCitasSolicitadas(Short cantidadCitasSolicitadas) {
		this.cantidadCitasSolicitadas = cantidadCitasSolicitadas;
	}
//	public Short getCantidadCitasAtendidas() {
//		return cantidadCitasAtendidas;
//	}
//	public void setCantidadCitasAtendidas(Short cantidadCitasAtendidas) {
//		this.cantidadCitasAtendidas = cantidadCitasAtendidas;
//	}
//	public Short getCantidadCitasInasistidas() {
//		return cantidadCitasInasistidas;
//	}
//	public void setCantidadCitasInasistidas(Short cantidadCitasInasistidas) {
//		this.cantidadCitasInasistidas = cantidadCitasInasistidas;
//	}
	public String getRequiereAprobacionJerarquica() {
		return requiereAprobacionJerarquica;
	}
	public void setRequiereAprobacionJerarquica(String requiereAprobacionJerarquica) {
		this.requiereAprobacionJerarquica = requiereAprobacionJerarquica;
	}
	public String getIndicativoAprobada() {
		return indicativoAprobada;
	}
	public void setIndicativoAprobada(String indicativoAprobada) {
		this.indicativoAprobada = (indicativoAprobada == null
				|| indicativoAprobada.equals("null")) ? "" : indicativoAprobada;
	}
	public String getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	public String getFechaControl() {
		return fechaControl;
	}
	public void setFechaControl(String fechaControl) {
		this.fechaControl = fechaControl;
	}
	public String getControlCobro() {
		return controlCobro;
	}
	public void setControlCobro(String controlCobro) {
		this.controlCobro = controlCobro;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public int getTipoAtencion() {
		return tipoAtencion;
	}
	public void setTipoAtencion(int tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}
	public String getNombreTipoAtencion() {
		return nombreTipoAtencion;
	}
	public void setNombreTipoAtencion(String nombreTipoAtencion) {
		this.nombreTipoAtencion = nombreTipoAtencion;
	}
	
}
