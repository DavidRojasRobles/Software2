package com.example.uisaludmovilv01.jbossTest.beans;

public class Remision {

	private String fechaFinVigencia;
//	private String tipoDocumentoProfesional;
//	private String documentoProfesional;
//	private int codigoClaseContrato;
//	private int numeroContrato;
//	private int sucursalConvenio;
	private String fechaRemision;
	private String horaRemision;
	private String estadoRemision;
	
	private Profesional profesional;
	
	public Remision(){
		this.profesional = new Profesional();
	}

	public String getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(String fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

//	public String getTipoDocumentoProfesional() {
//		return tipoDocumentoProfesional;
//	}
//
//	public void setTipoDocumentoProfesional(String tipoDocumentoProfesional) {
//		this.tipoDocumentoProfesional = tipoDocumentoProfesional;
//	}
//
//	public String getDocumentoProfesional() {
//		return documentoProfesional;
//	}
//
//	public void setDocumentoProfesional(String documentoProfesional) {
//		this.documentoProfesional = documentoProfesional;
//	}
	
	

	public String getFechaRemision() {
		return fechaRemision;
	}

	public Profesional getProfesional() {
		return profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

	public void setFechaRemision(String fechaRemision) {
		this.fechaRemision = fechaRemision;
	}

	public String getHoraRemision() {
		return horaRemision;
	}

	public void setHoraRemision(String horaRemision) {
		this.horaRemision = horaRemision;
	}

	public String getEstadoRemision() {
		return estadoRemision;
	}

	public void setEstadoRemision(String estadoRemision) {
		this.estadoRemision = estadoRemision;
	}
}
