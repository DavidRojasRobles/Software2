package com.example.uisaludmovilv01.jbossTest.beans;

public class MedicamentoFormula {

	private int idNumeroFormula;
	private String nombreComercial;
	private String unidad;
	private int cantidad;
	private int cantidadPorMes;
	private int tiempoFormulacion;
	private String modoUso;
	private String observaciones;
	private String requiereAutorizacion;
	private String fechaAutorizacion;
	private String fueAutorizado;
	private String justificacionMedicamento;
	private String justificacionNoAutorizado;

	public int getIdNumeroFormula() {
		return idNumeroFormula;
	}

	public void setIdNumeroFormula(int idNumeroFormula) {
		this.idNumeroFormula = idNumeroFormula;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = (nombreComercial == null
				|| nombreComercial.equals("null")) ? "" : nombreComercial;
		
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = (unidad == null
				|| unidad.equals("null")) ? "" : unidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadPorMes() {
		return cantidadPorMes;
	}

	public void setCantidadPorMes(int cantidadPorMes) {
		this.cantidadPorMes = cantidadPorMes;
	}

	public int getTiempoFormulacion() {
		return tiempoFormulacion;
	}

	public void setTiempoFormulacion(int tiempoFormulacion) {
		this.tiempoFormulacion = tiempoFormulacion;
	}

	public String getModoUso() {
		return modoUso;
	}

	public void setModoUso(String modoUso) {
		this.modoUso = (modoUso == null
				|| modoUso.equals("null")) ? "" : modoUso;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = (observaciones == null
				|| observaciones.equals("null")) ? "" : observaciones;
	}

	public String getRequiereAutorizacion() {
		return requiereAutorizacion;
	}

	public void setRequiereAutorizacion(String requiereAutorizacion) {
		this.requiereAutorizacion = (requiereAutorizacion == null
				|| requiereAutorizacion.equals("null")) ? "" : requiereAutorizacion;
	}

	public String getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(String fechaAutorizacion) {
		this.fechaAutorizacion = (fechaAutorizacion == null
				|| fechaAutorizacion.equals("null")) ? "" : fechaAutorizacion;
	}

	public String getFueAutorizado() {
		return fueAutorizado;
	}

	public void setFueAutorizado(String fueAutorizado) {
		this.fueAutorizado = (fueAutorizado == null
				|| fueAutorizado.equals("null")) ? "" : fueAutorizado;
	}

	public String getJustificacionMedicamento() {
		return justificacionMedicamento;
	}

	public void setJustificacionMedicamento(String justificacionMedicamento) {
		this.justificacionMedicamento = (justificacionMedicamento == null
				|| justificacionMedicamento.equals("null")) ? "" : justificacionMedicamento;
	}

	public String getJustificacionNoAutorizado() {
		return justificacionNoAutorizado;
	}

	public void setJustificacionNoAutorizado(String justificacionNoAutorizado) {
		this.justificacionNoAutorizado = (justificacionNoAutorizado == null
				|| justificacionNoAutorizado.equals("null")) ? "" : justificacionNoAutorizado;
	}
}