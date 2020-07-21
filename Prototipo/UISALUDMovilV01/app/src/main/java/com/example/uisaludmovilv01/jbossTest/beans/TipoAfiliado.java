package com.example.uisaludmovilv01.jbossTest.beans;

public class TipoAfiliado {

	private int id;
	private String nombre;
//	private int idClasificaAfiliado;
//	private String abrevReporteBdex;

	public TipoAfiliado() {
	}

	public TipoAfiliado(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
