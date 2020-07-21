package com.example.uisaludmovilv01.jbossTest.generales;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public interface IConstantes {

	// manejo de logs
	public static final Logger log = Logger.getLogger("uisalud");

	public static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat NORMAL_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat SQL_HOUR_FORMAT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat SQL_HOUR_TO_SEC_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat NORMAL_HOUR_FORMAT = new SimpleDateFormat("hh:mm aa");

	// OTROS
	public static final String ESTADO_ACTIVO = "A";
	public static final String ESTADO_INACTIVO = "I";
	public static String AFIRMACION = "S";
	public static String NEGACION = "N";
	public static String IGNORAR = "I";

	public static final int PLAN_AFIL_FULL = 2; // plan full
	public static final int ESTADO_AFILIADO = 2;// estado afiliado
	public static final String PLAN_INVALIDO_TITLE = "Su plan ya no es FULL";
	public static final String PLAN_INVALIDO_MSG = "Esto puede ser por alguna"
			+ " incosistencia en sus pagos. Comuníquese con UISALUD para más información.";
	public static final String PLAN_INVALIDO_CLICK_ACTION = "MAIN";
	public static final String NO_TOKEN_PERSONA = "La persona no tiene nigun token registrado";
	public static final String ERROR_DESCONOCIDO = "Error desconocido";
	public static final String NO_REGISTRO_BD = "No se encontro un registro correspondiente";
	public static final String NO_SOLICITUD_FCM = "Error al solicitar notificacion";
	public static final String SOLICITUD_FCM = "Solicitud exitosa";

	// cita
	public static final int MEDIO_SOLICITUD_MOVIL = 3; // indice en la table
														// medio_solicitud_cita
	public static final int MOTIVO_CANCELACION_MOVIL = 3; // indice en la table
															// medio_solicitud_cita
	public static final int ESTADO_CITA_DISPONIBLE = 1; // indice en la tabla
														// estado_cita como
														// disponible
	public static final int ESTADO_CITA_AGENDADA = 2; // indice en la tabla
														// estado_cita como
														// solicitada
	public static final int PARAMETRO_SMDV = 2; // índice en la tabla
												// criterio_valor
	public static final int TIEMPO_CONTROL = 14; // días en los cuales el
													// control es gratis
	public static final String MOMENTO_COBRO_CITA = "A"; // "S" = solicitud, "A"
															// = atencion
	public static final String AGENDAR_CITA = "agendar";
	public static final String CANCELAR_CITA = "cancelar";
	public static final int TIPO_CITA_PROGRAMADA = 1;
	public static final int TIPO_CITA_DIARIA = 2;
	public static final int TIPO_CITA_REM_INT = 8;

	// Errores Cita
	public static final String ERROR_CITA_NO_ENCONTRADA = "Cita no encontrada. ";
	public static final String ERROR_CITA_AGENDADA = "Se agendó la cita. ";
	public static final String ERROR_CITA_NO_AGENDADA = "No se agendó la cita. ";
	public static final String ERROR_CITA_CANCELADA = "Se canceló la cita. ";
	public static final String ERROR_CITA_NO_CANCELADA = "No se canceló la cita. ";
	public static final String ERROR_CITA_NO_DISPONIBLE = "La cita no está disponible. ";
	public static final String ERROR_PORCENTAJE_SUPERADO = "Porcentaje de solicitud por especialidad superado. Solicite para otro día o por otro medio. ";

	// cobros
	public static final int ATENCION_PRIMERA_VEZ = 1;
	public static final int ATENCION_CONTROL = 2;
	public static final int ATENCION_TERAPIA_ODONT = 3;
	public static final int REM_INTERNA = 1;
	public static final int REM_EXTERNA = 2;
	public static final int REM_CONTROL = 3;
	public static final String IND_INTERNA = "I";
	public static final String IND_EXTERNA = "E";
	public static final char IND_REM_ESP = 'E';
	public static final char IND_REM_PROC = 'P';
	public static final String IND_SERVICIO = "S";
	public static final String IND_PROCEDIMIENTO = "P";
	public static final String CLASE_COBRO_SERVICIO = "S";
	public static final String CONTROL_COBRO_SI = "P";
	public static final String IND_ATENDIDA = "A";
	public static final String IND_INASISTIDA = "I";
	public static final String IND_SOLICITADA = "S";
	public static final String IND_APARTADA = "P";
	public static final String TSP_ESTADO_SOLICITADA = "S";
	public static final String TSP_ESTADO_CANCELADA = "C";

	

	public static String ACCESO_DESARROLLO = "DcRaExt7gFLbVCXl3tpl1CrNro4=";

	/** ====================== NOTIFICACIONES ====================== **/
	
	public static final String TIPO_NOTI_CITA = "cita";
	public static final String TIPO_NOTI_REM_ESP = "rem_esp";
	public static final String TIPO_NOTI_REM_PROC = "rem_proc";
	public static final String TIPO_NOTI_MED = "medicamento";
	public static final String TIPO_NOTI_COBRO = "cobro";
	public static final String TIPO_NOTI_INVALIDO = "invalido";
	

	public static final String CAUSA_ANULACION_COBRO = "Cancelación oportuna del servicio";
	public static final String E = "164";
	public static final String EX_EDAD = "SELECT CASE WHEN ? < 1 THEN 'true' ELSE 'false'	END ,'N' FROM tipo_atencion";
	public static final int NOTIF_AUT_TTL = 86400000; // 24 horas en  milis
	public static final String NOTIF_RESPONSE_PATTERN = "projects/uisalud-as/messages/0:.*%.*";

	// limites FCM
	public static final int LIM_DIST_MSG = 1000; // simultaneas, por proyecto.
	public static final int LIM_SOL_MSG = 2; // solicitudes de notificacion a la
												// vez. Total de afiliados
												// posibles es 3k por el
												// momento.
	public static final int LIM_MAX_TTL_MSG = 2419200; // segundos. O 28 dias.
	public static final int LIM_MSG_SIN_COLAPSAR = 100;
	public static final int LIM_SUBS_PROY_TEMA = 3000; // qps
	public static final int LIM_SUBS_TEMA_LOTE = 1000;
	public static final int LIM_TEMAS_INSTANCIA = 2000; // una instancia no
														// puede estar suscrita
														// a más de 2k temas
	// notificaciones cita
	
	public static final String TITLE_CITA = "Recuerde su cita en UISALUD";
	public static final String MSG_CITA = "Recuerde su cita de %s para el día %s a las %s";

	// notificaciones remision
	
	public static final String TITLE_REM = "Se remisión se va a vencer";
	public static final String MSG_REM = "Su remisión para %s se vencerá en %d días. Recuerde agendarla o renovarla oportunamente.";
	
	// notificaciones medicamento
	public static final String TITLE_MED_PENDIENTE = "Reclame sus medicamentos";
	public static final String MSG_MED_PENDIENTE = "Su fórmula va a expirar. Reclame sus medicamentos en UISALUD a tiempo.";

	// notificaciones cobro
	public static final String TITLE_COBRO = "Nuevo crobro";
	public static final String MSG_COBRO = "El día %s su beneficiario %s accedió a %s.";
	public static final String COBRO_CLICK_ACTION = "OPEN_ACTIVITY_COBROS";

	// notificaciones autorizaciones medicamento
	public static final String TITLE_MED_AUT = "Medicamento autorizado";
	public static final String MSG_MED_AUT = "Su medicamento %s fue autorizado. Reclámelo antes de %s";
	public static final String MED_AUT_CLICK_ACTION = "OPEN_ACTIVITY_FORMULAS";

	public static final String TITLE_MED_NO_AUT = "Medicamento NO autorizado";
	public static final String MSG_MED_NO_AUT = "Su medicamento %s no fue autorizado. Ver detalles aquí.";

	// notificaciones autorizaciones remision
	public static final String TITLE_REM_AUT = "Remisión autorizada";
	public static final int DIAS_VENCE_REM_MIN = 3;
	public static final int DIAS_VENCE_REM_MAX = 5;

	public static final String MSG_REM_AUT = "Su remisión para %s ha sido autorizada. Ya puede solicitar su cita.";
	public static final String REM_AUT_CLICK_ACTION = "OPEN_ACTIVITY_REMISIONES";

	public static final String TITLE_REM_NO_AUT = "Remisión  NO autorizada";
	public static final String MSG_REM_NO_AUT = "Su remisión para %s no fue autorizada. Ver detalles aquí.";

	public static String errorTiempoInvalidoCita(String agendarCancelar, int minutos) {

		if(minutos <=  0) return "Tiempo excedido";
		
		if (minutos >= 60) {
			int horas = minutos / 60;
			int min = minutos % 60;

			return String.format("Debe %s con %d h y %d min de anterioridad.", agendarCancelar, horas, min);
		}

		return String.format("Debe %s con %d minutos de anterioridad.", agendarCancelar, minutos);

	}

}
