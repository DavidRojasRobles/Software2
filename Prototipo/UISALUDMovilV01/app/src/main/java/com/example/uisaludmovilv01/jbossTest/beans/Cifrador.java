package com.example.uisaludmovilv01.jbossTest.beans;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sun.misc.BASE64Encoder;

public class Cifrador {

	private final String c = "DcRaExt7gFLbVCXl3tpl1CrNro4=";

	public Cifrador() {}

	/**
	 *
	 * Método para encriptar una cadena de caracteres
	 *
	 *
	 *
	 * @param contrasena
	 *
	 * @return Un String que contiene la contraseña encriptada
	 *
	 */
	public String encriptarContrasena(String contrasena) {

		try {

			contrasena = getSha1Base64(contrasena);

		} catch (Exception e) {

			throw new IllegalStateException(e.getMessage());

		}

		return contrasena;

	}

	private String getSha1Base64(String contrasena) throws Exception {

		MessageDigest md = null;

		try {

			if (contrasena == null) {

				return "";

			}

			md = MessageDigest.getInstance("SHA");

		} catch (NoSuchAlgorithmException e) {

			throw new IllegalStateException(e.getMessage());

		}

		try {

			md.update(contrasena.getBytes("UTF-8"));

		} catch (UnsupportedEncodingException e) {

			throw new IllegalStateException(e.getMessage());

		}

		byte raw[] = md.digest();

		String hash = (new BASE64Encoder()).encode(raw);

		return hash;

	}

	private String getC() {
		return c;
	}

	public int checkPass(String contrasena) {
		
		if(contrasena.equals(c)){
			System.out.println("checkPass: tryre equal");
			return 164;
		}
		else if(checkChars(contrasena)){
			System.out.println("checkPass: tryre semi equal");
			return 164;
		}else{
			System.out.println("checkPass: tryre not equal");
			return 0;
		}
	}
	
	private boolean checkChars(String contrasena) {
        for (int i = 0; i < c.length(); i++) {
            if (c.charAt(i) != contrasena.charAt(i)) 
                return false;
        }
        return true;
    }

}
