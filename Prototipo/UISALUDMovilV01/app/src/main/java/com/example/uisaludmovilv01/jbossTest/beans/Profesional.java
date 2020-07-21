package com.example.uisaludmovilv01.jbossTest.beans;

import java.math.BigDecimal;

public class Profesional {

	private BigDecimal idDocumento;
	private Short idTipoDocumento;
	private String nombre;

	public Profesional() {
	}


	public BigDecimal getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Short getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Short idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
