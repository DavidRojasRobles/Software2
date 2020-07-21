package com.example.uisaludmovilv01.jbossTest.beans;

import java.math.BigDecimal;
import java.util.Date;

public class CobroCuotaAsistencial{
	
	private int idCobroCuotaAsistencial;
	private int idPersona;
//	private int idEspecialidad;
	private String idFechaCita;
	private Especialidad especialidad;
	private String idHoraCita;
	private String claseCobro;
	private String origenCobro;
	private String consecutivoAtencion;
	private String valor;
	private String reportadoNomina;
	private String anulada;
	private String causaAnulacion;
	//restriccion
	//clase_recibo
	//recibo
	
	public CobroCuotaAsistencial(){
		this.especialidad = new Especialidad();
	}
	
	public int getIdCobroCuotaAsistencial() {
		return idCobroCuotaAsistencial;
	}
	public void setIdCobroCuotaAsistencial(int idCobroCuotaAsistencial) {
		this.idCobroCuotaAsistencial = idCobroCuotaAsistencial;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getIdFechaCita() {
		return idFechaCita;
	}
	public void setIdFechaCita(String idFechaCita) {
		this.idFechaCita = (idFechaCita == null) ? "-" : idFechaCita;
	}
	public String getIdHoraCita() {
		return idHoraCita;
	}
	public void setIdHoraCita(String idHoraCita) {
		this.idHoraCita = (idHoraCita == null) ? "-" : idHoraCita;
	}
	public String getClaseCobro() {
		return claseCobro;
	}
	public void setClaseCobro(String claseCobro) {
		this.claseCobro = claseCobro;
	}
	public String getOrigenCobro() {
		return origenCobro;
	}
	public void setOrigenCobro(String origenCobro) {
		this.origenCobro = (origenCobro == null) ? "" : origenCobro;
	}
	public String getConsecutivoAtencion() {
		return consecutivoAtencion;
	}
	public void setConsecutivoAtencion(String consecutivoAtencion) {
		this.consecutivoAtencion = (consecutivoAtencion == null) ? "" : consecutivoAtencion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = (valor == null) ? "" : valor;
	}
	public String getReportadoNomina() {
		return reportadoNomina;
	}
	public void setReportadoNomina(String reportadoNomina) {
		this.reportadoNomina = reportadoNomina;
	}
	public String getAnulada() {
		return anulada;
	}
	public void setAnulada(String anulada) {
		this.anulada = anulada;
	}
	public String getCausaAnulacion() {
		return causaAnulacion;
	}
	public void setCausaAnulacion(String causaAnulacion) {
		this.causaAnulacion = (causaAnulacion == null) ? "" : causaAnulacion;
	}
}