package com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.tasks;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.TimerTask;

import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.INotificacionDAO;

/**
 * Tarea que se ejecuta cada hora y que envía solicitudes de notificaciones al
 * servidor de FCM para notificar a los pacientes que tienen cita con 4 horaS de
 * anterioridad. En el caso de las citas para las horas de 6 AM a 8:59 AM, el
 * recordatorio se envia el dia anterior.
 * 
 * @author PROGRAMADORES
 *
 */
public class RecordarCita extends TimerTask {

	private final LocalTime START_TASK = LocalTime.of(4, 55, 0);
	private final LocalTime END_TASK = LocalTime.of(13, 55, 0);
	private final LocalTime LAST_TASK_FLOOR = LocalTime.of(17, 50, 0);
	private final LocalTime LAST_TASK_CEIL = LocalTime.of(18, 5, 0);
	private final String CLICK_ACTION = "OPEN_ACTIVITY_CITAS";
	private ScheduledExecutorService scheduler;
	
	private String dia;
	private int ttl;

	public RecordarCita() {
		this.scheduleTask();
	}

	/**
	 * Programa ejecucion automatica cada hora, comenzando a las 4 am.
	 */
	private void scheduleTask() {
		System.out.println("RecordarCita: scheduleTask called");	
		long delay = ChronoUnit.HOURS.between(LocalTime.now(), START_TASK);
		scheduler = Executors.newScheduledThreadPool(1);
//		scheduler.scheduleAtFixedRate(this, delay, 1, TimeUnit.HOURS);
		scheduler.scheduleAtFixedRate(this, 0, 2, TimeUnit.MINUTES);
	}

	@Override
	public void run() {
		System.out.println("RecordarCita: running...");	
		// El último recordatorio se envía a las 3:35 pm de cada día
		if (checkTime() == 1)
			recordarCita(false);
		else if (checkTime() == 2)
			recordarCita(true);

	}

	/**
	 * Revisa si se está en el horario para enviar los recordatorios
	 * 
	 * @return
	 */
	private int checkTime() {
		return 2;
//		LocalTime now = LocalTime.now();
//		if ((now.isAfter(START_TASK)) && (now.isBefore(END_TASK)))
//			return 1; //hoy mismo
//		else if ((now.isAfter(LAST_TASK_FLOOR)) && (now.isBefore(LAST_TASK_CEIL)))
//			return 2; //manana
//		return 0;
	}

	private void recordarCita(boolean manana) {
		System.out.println("RecordarCita: called");
		List<Object[]> datos = null;
		List<String> answers = new ArrayList<>();
		dia = (manana) ? "mañana" : "hoy";
		ttl = (manana) ? Helper.hoursToMillis(11) : Helper.hoursToMillis(4);
		try {
			// Obtiene los tokens que tienen cita manana
			datos = INotificacionDAO.getTokensCitaProx(manana);

			if (datos != null && datos.size() > 0) {

				
				while (datos.size() > IConstantes.LIM_SOL_MSG) {
					System.out.println("RecordarCita: before clear datos.size() = " + datos.size());
					// Subscribe the first n tokens in the list
					// n being the limit of subscription per batch
					
					for (Object[] d : datos.subList(0, IConstantes.LIM_SOL_MSG)) {
						answers.add(notificarCita(d));
					}
					TimeUnit.SECONDS.sleep(30);
					
					datos.subList(0, IConstantes.LIM_SOL_MSG).clear();
				}
				
				if(datos.size() > 0){
				// Subscribe what's left in the list
					for (Object[] d : datos) {
						answers.add(notificarCita(d));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("RecordarCita: answer.size() = " + answers.size());
	}
	
	private String notificarCita(Object[] d){
		System.out.println("notificarCita: enviando a token = " + d[0]);
		String msg = String.format(IConstantes.MSG_CITA, d[1], dia,  d[2]);
//		String msg = IConstantes.MSG_CITA_BEG + d[1] + IConstantes.MSG_CITA_MIDDLE + dia
//				+ IConstantes.MSG_CITA_END + d[2];
		String jsonData = "";

		Gson gson = new Gson();
		Type gsonType = new TypeToken<HashMap>() {
		}.getType();
		jsonData = gson.toJson(d[3], gsonType);
		return FCMConnection.notifySingleDeviceData((String)d[0], IConstantes.TITLE_CITA, msg, Priority.HIGH, ttl,
				IConstantes.TIPO_NOTI_CITA, jsonData);
		
	}
	

}
