package com.example.uisaludmovilv01.jbossTest.beans;

//import java.util.ArrayList;

public class Especialidad {

	private int idEspecialidad;
	private String nombre;
	private String estado;
	private String requiereRemision;
	private String sede;

	public Especialidad() {
	}

	public int getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(int idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRequiereRemision() {
		return requiereRemision;
	}

	public void setRequiereRemision(String requiereRemision) {
		this.requiereRemision = requiereRemision;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = (sede == null) ? "" : sede;
	}
}
