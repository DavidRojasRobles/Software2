package com.example.uisaludmovilv01.jbossTest.beans;

import java.math.BigDecimal;

public class Beneficiario {

	private int id;
	private String tipoDoc;
	private BigDecimal documento;
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public BigDecimal getDocumento() {
		return documento;
	}

	public void setDocumento(BigDecimal documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
