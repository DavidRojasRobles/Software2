package com.example.uisaludmovilv01.jbossTest.beans;

import java.util.ArrayList;
import java.util.List;

public class FormulaMedica {

	private int idNumeroFormula;
	private int persona;
	private int consecutivoAtencion;
	private String fechaHoraCreacionFormula;
	private String fechaHoraNuevaEntrega;
	private String observaciones;
//	private List<MedicamentoFormula> medicamentos;

	public FormulaMedica() {
//		medicamentos = new ArrayList<>();
	}

	public int getIdNumeroFormula() {
		return idNumeroFormula;
	}

	public void setIdNumeroFormula(int idNumeroFormula) {
		this.idNumeroFormula = idNumeroFormula;
	}

	public int getPersona() {
		return persona;
	}

	public void setPersona(int persona) {
		this.persona = persona;
	}

	public int getConsecutivoAtencion() {
		return consecutivoAtencion;
	}

	public void setConsecutivoAtencion(int consecutivoAtencion) {
		this.consecutivoAtencion = consecutivoAtencion;
	}

	public String getFechaHoraCreacionFormula() {
		return fechaHoraCreacionFormula;
	}

	public void setFechaHoraCreacionFormula(String fechaHoraCreacionFormula) {
		this.fechaHoraCreacionFormula = (fechaHoraCreacionFormula == null
				|| fechaHoraCreacionFormula.equals("null")) ? "" : fechaHoraCreacionFormula;
	}

	public String getFechaHoraNuevaEntrega() {
		return fechaHoraNuevaEntrega;
	}

	public void setFechaHoraNuevaEntrega(String fechaHoraNuevaEntrega) {
		this.fechaHoraNuevaEntrega = (fechaHoraNuevaEntrega == null
				|| fechaHoraNuevaEntrega.equals("null")) ? "" : fechaHoraNuevaEntrega;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

//	public List<MedicamentoFormula> getMedicamentos() {
//		return medicamentos;
//	}
//
//	public void setMedicamentos(List<MedicamentoFormula> medicamentos) {
//		this.medicamentos = medicamentos;
//	}
	
	
	
}
