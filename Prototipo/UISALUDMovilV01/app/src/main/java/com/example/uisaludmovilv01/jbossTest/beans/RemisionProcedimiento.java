package com.example.uisaludmovilv01.jbossTest.beans;

import java.math.BigDecimal;
import java.util.Date;

public class RemisionProcedimiento extends Remision {

	private int idPersona;
	private int idConsecutivoAtencion;
	private int idProcedimiento;
	private String procedimiento;
	private int idConsecutivoRemisionProcedimiento;
	private String consecutivoRemision;
	private int tipoRemision;
	private String nombreTipoRemision;
	private String diagnostico;
	private String justificacionRemision;
	private String observacionesResultadoProcedimiento;
	private String requiereAprobacionJerarquica;
	private String indicativoAprobada;
	private String fechaAprobacion;

	public RemisionProcedimiento() {
		super();
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

	public int getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(int idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public int getIdConsecutivoRemisionProcedimiento() {
		return idConsecutivoRemisionProcedimiento;
	}

	public void setIdConsecutivoRemisionProcedimiento(int idConsecutivoRemisionProcedimiento) {
		this.idConsecutivoRemisionProcedimiento = idConsecutivoRemisionProcedimiento;
	}

	public String getConsecutivoRemision() {
		return consecutivoRemision;
	}

	public void setConsecutivoRemision(String consecutivoRemision) {
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

	public String getObservacionesResultadoProcedimiento() {
		return observacionesResultadoProcedimiento;
	}

	public void setObservacionesResultadoProcedimiento(String observacionesResultadoProcedimiento) {
		this.observacionesResultadoProcedimiento = observacionesResultadoProcedimiento;
	}

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
		this.indicativoAprobada = indicativoAprobada;
	}

	public String getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(String fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

}
