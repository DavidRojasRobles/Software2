package com.example.uisaludmovilv01.jbossTest.beans;

public class TipoPlan {

	private int idPlanSalud;
	private String nombre;
	private String indicativoProcedimientos;
	private String estado;

	public TipoPlan() {
	}

	public TipoPlan(int id, String nombre, String indicativoProcedimientos, String estado) {
		super();
		this.idPlanSalud = id;
		this.nombre = nombre;
		this.indicativoProcedimientos = indicativoProcedimientos;
		this.estado = estado;
	}

	public int getIdPlanSalud() {
		return idPlanSalud;
	}

	public void setIdPlanSalud(int id) {
		this.idPlanSalud = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIndicativoProcedimientos() {
		return indicativoProcedimientos;
	}

	public void setIndicativoProcedimientos(String indicativoProcedimientos) {
		this.indicativoProcedimientos = indicativoProcedimientos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
