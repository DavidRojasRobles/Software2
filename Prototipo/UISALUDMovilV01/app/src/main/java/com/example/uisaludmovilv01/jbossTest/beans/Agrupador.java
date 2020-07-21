package com.example.uisaludmovilv01.jbossTest.beans;

public class Agrupador {
	private int idAgrupador;
	private String nombre;
	private String indicativoContador;

	public Agrupador() {
	}

	public int getIdAgrupador() {
		return idAgrupador;
	}

	public void setIdAgrupador(int idAgrupador) {
		this.idAgrupador = idAgrupador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIndicativoContador() {
		return indicativoContador;
	}

	public void setIndicativoContador(String indicativoContador) {
		this.indicativoContador = (indicativoContador == null || indicativoContador.equals("null")) ? ""
				: indicativoContador;
	}
}
