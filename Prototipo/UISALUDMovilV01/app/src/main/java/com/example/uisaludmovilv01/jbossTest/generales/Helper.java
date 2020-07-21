package com.example.uisaludmovilv01.jbossTest.generales;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Helper {

	public static LocalDate dateToLocalDate(Date date) {
		return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
	}

	public static Date localDateToDate(LocalDate local) {
		return new Date(local.getYear() - 1900, local.getMonthValue() - 1, local.getDayOfMonth());
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate(), date.getHours(),
				date.getMinutes(), date.getSeconds());
	}

	public static Time dateToTimeSec(Date date) {
		return new Time(date.getHours(), date.getMinutes(), 0);
	}

	public static String formatearHora(String horaMilitar) {
		String horaMeridiano = "";

		String[] tiempos = horaMilitar.split(":");
		int hora = Integer.valueOf(tiempos[0]);

		if (hora < 12) {
			horaMeridiano = hora + ":" + tiempos[1] + " a.m.";
		} else {
			horaMeridiano = (hora - 12) + ":" + tiempos[1] + " p.m.";
		}

		return horaMeridiano;
	}

	public static int hoursToMillis(int hours) {
		return hours * 3600 * 1000;
	}

	public static String fechaToString(Date date) {

		return "\'" + IConstantes.SQL_DATE_FORMAT.format(date) + "\'";
	}

	public static String fechaHoraCeroToString(Date date) {
		return "\'" + IConstantes.SQL_DATE_FORMAT.format(date) + " 0:0\'";
	}

	public static String TimeToString(Time time) {
		return "\'" + time.getHours() + ":" + time.getMinutes() + "\'";
	}

	public static String addQuotes(String str) {
		return "\'" + str + "\'";
	}

	public static String formatName(String nombre) {
		String nom = deleteHugeSpaces(nombre);
		String[] palabras = nom.split(" ");

		String nombrePascal = "";
		for (int i = 0; i < palabras.length; i++) {
			// Log.i(TAG, "formatName: " + palabras[i]);
			palabras[i] = pascalCase(palabras[i]);
			nombrePascal += palabras[i] + " ";
		}

		return nombrePascal;
	}

	private static String deleteHugeSpaces(String nombre) {

		// nombre += '/';
		//
		// int i = 0;
		// int j;
		// int x = nombre.length() - 1;
		// //
		// while (i < x) {
		// j = i + 1;
		// if ((nombre.charAt(i) == ' ') && (nombre.charAt(i + 1) == ' ')) {
		// while (j < nombre.length() - 1 && nombre.charAt(j + 1) == ' ') {
		// j++;
		// if (nombre.charAt(j) == '/') {
		// nombre = nombre.substring(0, i);
		// i = x;
		// break;
		// }
		//
		// }
		// nombre = nombre.substring(0, i).concat(nombre.substring(j));
		// }
		// x = nombre.length() - 1;
		// i++;
		//
		// }
		//
		// nombre = nombre.substring(0, nombre.length() - 1);

		for (int i = 1; i < nombre.length(); i++) {
			if (nombre.charAt(i) == ' ' && nombre.charAt(i - 1) != ' ' && nombre.charAt(i - 1) != '-') {
				nombre = nombre.substring(0, i) + '-' + nombre.substring(i + 1);
			}
		}

		nombre = nombre.replace(" ", "");
		nombre = nombre.replace("-", " ");
		return nombre;
	}

	public static String pascalCase(String str) {
		str = str.toLowerCase();
		String pascalCase = "";
		if (str.length() > 1) {
			String s1 = str.substring(0, 1).toUpperCase();
			pascalCase = s1 + str.substring(1);
		}
		return pascalCase;
	}

	public static String identificarClaseCobro(char clase) {
		switch (clase) {
		case 'S':
			return "un servicio/cita";
		case 'F':
			return "una formula";
		case 'P':
			return "un procedimiento";
		case 'E':
			return "una especialidad externa";
		default:
			return null;
		}
	}

	public static String removeQuotes(String str) {
		return str.replace("'", "");
	}
}
