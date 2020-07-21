package com.example.uisaludmovilv01.jbossTest.beans;

public class TipoDocumentoID {

	private int tipoDocId;
	private String descripcionDoc;

	public TipoDocumentoID() {
	}

	public TipoDocumentoID(int tipoDocId, String descripcionDoc) {
		super();
		this.tipoDocId = tipoDocId;
		this.descripcionDoc = descripcionDoc;
	}

	public int getTipoDocId() {
		return tipoDocId;
	}

	public void setTipoDocId(int tipoDocId) {
		this.tipoDocId = tipoDocId;
	}

	public String getDescripcionDoc() {
		return descripcionDoc;
	}

	public void setDescripcionDoc(String descripcionDoc) {
		this.descripcionDoc = descripcionDoc;
	}


}
