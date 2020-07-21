package com.example.uisaludmovilv01.jbossTest.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
//import java.util.Date;

//import java.util.ArrayList;

public class Persona implements Serializable{

	private int id;
    private int edad;
    private int tipoDocId;
    private BigDecimal documentoId;
    private String tipoDocIdAbrev;
    private String nombre;
    private String sexo;
    private String fechaNacimiento;
    private String tipoSanguineo;
    private String telefono;
    private String telefonoContacto;
    private String celular;
    private String emailInstitucional;
    private String emailPersonal;
    private String emailAlterno;
    private String direccionResidencia;
    private String codigoCarnet;
    private String fechaCarnet;
    private String tipoAfiliado;
    private String planAfiliado;
    private String afilAportante;

    public Persona() {
        // estructuraTabla = new EstructuraTabla();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
    	this.edad = edad;
//        this.edad = (edad == null
//                || edad.equals("null")
//                || edad.equals("")) ? -1 : edad;
        System.out.println("Persona.setEdad: edad = " + this.edad);
    }

    public int getTipoDocId() {
        return tipoDocId;
    }

    public void setTipoDocId(int tipoDocId) {
        this.tipoDocId = tipoDocId;
    }

    public BigDecimal getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(BigDecimal documentoId) {
        this.documentoId = documentoId;
    }

    public String getTipoDocIdAbrev() {
        return tipoDocIdAbrev;
    }

    public void setTipoDocIdAbrev(String tipoDocIdAbrev) {
        this.tipoDocIdAbrev = (tipoDocIdAbrev == null
                || tipoDocIdAbrev.equals("null")
                || tipoDocIdAbrev.equals("") ? "#" : tipoDocIdAbrev);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = (nombre == null
                || nombre.equals("null")
                || nombre.equals("") ? "N/A" : nombre);
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = (sexo == null
                || sexo.equals("null")
                || sexo.equals("") ? "N/A" : sexo);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = (fechaNacimiento == null
                || fechaNacimiento.equals("null")
                || fechaNacimiento.equals("") ? "N/A" : fechaNacimiento);
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = (tipoSanguineo == null
                || tipoSanguineo.equals("null")
                || tipoSanguineo.equals("") ? "N/A" : tipoSanguineo);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = (telefono == null
                || telefono.equals("null")
                || telefono.equals("") ? "" : telefono);
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = (telefonoContacto == null
                || telefonoContacto.equals("null")
                || telefonoContacto.equals("") ? "" : telefonoContacto);
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = (celular == null
                || celular.equals("null")
                || celular.equals("") ? "" : celular);
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = (emailInstitucional == null
                || emailInstitucional.equals("null")
                || emailInstitucional.equals("") ? "N/A" : emailInstitucional);
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = (emailPersonal == null
                || emailPersonal.equals("null")
                || emailPersonal.equals("") ? "N/A" : emailPersonal);
    }

    public String getEmailAlterno() {
        return emailAlterno;
    }

    public void setEmailAlterno(String emailAlterno) {
        this.emailAlterno = (emailAlterno == null
                || emailAlterno.equals("null")
                || emailAlterno.equals("") ? "N/A" : emailAlterno);
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public void setDireccionResidencia(String direccionResidencia) {
        this.direccionResidencia = (direccionResidencia == null
                || direccionResidencia.equals("null")
                || direccionResidencia.equals("") ? "N/A" : direccionResidencia);
    }

    public String getCodigoCarnet() {
        return codigoCarnet;
    }

    public void setCodigoCarnet(String codigoCarnet) {
        this.codigoCarnet = (codigoCarnet == null
                || codigoCarnet.equals("null")
                || codigoCarnet.equals("") ? "N/A" : codigoCarnet);
    }

    public String getFechaCarnet() {
        return fechaCarnet;
    }

    public void setFechaCarnet(String fechaCarnet) {
        this.fechaCarnet = (fechaCarnet == null
                || fechaCarnet.equals("null")
                || fechaCarnet.equals("") ? "N/A" : fechaCarnet);
    }

    public String getTipoAfiliado() {
        return tipoAfiliado;
    }

    public void setTipoAfiliado(String tipoAfiliado) {
        this.tipoAfiliado = (tipoAfiliado == null
                || tipoAfiliado.equals("null")
                || tipoAfiliado.equals("") ? "N/A" : tipoAfiliado);
    }

    public String getPlanAfiliado() {
        return planAfiliado;
    }

    public void setPlanAfiliado(String planAfiliado) {
        this.planAfiliado = (planAfiliado == null
                || planAfiliado.equals("null")
                || planAfiliado.equals("") ? "N/A" : planAfiliado);
    }

    public String getAfilAportante() {
        return afilAportante;
    }

    public void setAfilAportante(String afilAportante) {
        this.afilAportante = (afilAportante == null
                || afilAportante.equals("null")
                || afilAportante.equals("")) ? "-1" : afilAportante;
    }

}
