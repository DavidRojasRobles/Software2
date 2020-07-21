package com.example.uisaludmovilv01.jbossTest.beans;

import java.math.BigDecimal;
import java.sql.Time;

public class Cita {

	private Profesional profesional;
	private String idFecha;
	private String idHoraInicio;
	private String horaFin;
	private Short sedeId;
	private String sedeNombre;
	private int tipoCita;
	private Especialidad especialidad;
	private int consultorio;
	private String consultorioNum;
	private String consultorioUbi;
	private Integer personaAfiliado;
	private Integer medioCita;
	private String tipoProgramacion;
	private Integer consecutivoAtencion;
	private int tipoAtencion;
	private int estado;
	
	

	public Cita() {
		this.profesional = new Profesional();
		this.especialidad = new Especialidad();
	}
	
	

	public Profesional getProfesional() {
		return profesional;
	}



	public void setProfesional(Profesional doctor) {
		this.profesional = doctor;
	}


	public String getIdFecha() {
		return idFecha;
	}

	public void setIdFecha(String idFecha) {
		this.idFecha = idFecha;
	}

	public String getIdHoraInicio() {
		return idHoraInicio;
	}

	public void setIdHoraInicio(String idHoraInicio) {
		this.idHoraInicio = idHoraInicio;
	}
	
	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Short getSedeId() {
		return sedeId;
	}

	public void setSedeId(Short idSede) {
		this.sedeId = idSede;
	}

	public String getSedeNombre() {
		return sedeNombre;
	}

	public void setSedeNombre(String sedeNombre) {
		this.sedeNombre = sedeNombre;
	}

	public int getTipoCita() {
		return tipoCita;
	}

	public void setTipoCita(int tipoCita) {
		this.tipoCita = tipoCita;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}



	public int getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(int consultorio) {
		this.consultorio = consultorio;
	}

	public String getConsultorioNum() {
		return consultorioNum;
	}

	public void setConsultorioNum(String consultorioNum) {
		this.consultorioNum = consultorioNum;
	}
	
	public String getConsultorioUbi() {
		return consultorioUbi;
	}

	public void setConsultorioUbi(String consultorioUbi) {
		this.consultorioUbi = consultorioUbi;
	}

	public Integer getPersonaAfiliado() {
		return personaAfiliado;
	}

	public void setPersonaAfiliado(Integer personaAfiliado) {
		this.personaAfiliado = personaAfiliado;
	}

	public Integer getMedioCita() {
		return medioCita;
	}

	public void setMedioCita(Integer medioCita) {
		this.medioCita =  medioCita;
	}

	public String getTipoProgramacion() {
		return tipoProgramacion;
	}

	public void setTipoProgramacion(String tipoProgramacion) {
		this.tipoProgramacion = tipoProgramacion;
	}

	public Integer getConsecutivoAtencion() {
		return consecutivoAtencion;
	}

	public void setConsecutivoAtencion(Integer consecutivoAtencion) {
		this.consecutivoAtencion = consecutivoAtencion;
	}

	public int getTipoAtencion() {
		return tipoAtencion;
	}

	public void setTipoAtencion(int tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

}
