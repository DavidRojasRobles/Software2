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
 * Tarea que se ejecuta cada dia y que envía solicitudes de notificaciones al
 * servidor de FCM para notificar a los pacientes que tienen remisiones con
 * fecha de vigencia para dentro de 5 y 3 dias.
 * 
 * @author PROGRAMADORES
 *
 */
public class RecordarRemision extends TimerTask {

	private final LocalTime START_TASK = LocalTime.of(19, 0, 0);
	private final LocalTime END_TASK = LocalTime.of(19, 5, 0);
	private final String CLICK_ACTION = "OPEN_ACTIVITY_REMISIONES";
	private ScheduledExecutorService scheduler;

	public RecordarRemision() {
		this.scheduleTask();
	}

	/**
	 * Programa ejecucion automatica cada día a las 7 pm.
	 */
	private void scheduleTask() {
		System.out.println("RecordarRemision: scheduler called");
		long delay = ChronoUnit.HOURS.between(LocalTime.now(), START_TASK);
		scheduler = Executors.newScheduledThreadPool(1);
//		scheduler.scheduleAtFixedRate(this, delay, 24, TimeUnit.HOURS);
		 scheduler.scheduleAtFixedRate(this, 0, 2, TimeUnit.MINUTES);
	}

	@Override
	public void run() {
		// Si la hora está en el rango de 7pm y 7:05pm
		System.out.println("RecordarRemision: running ...");
		if (checkTime())
			recordarRemision();

	}

	/**
	 * Revisa si se está en el horario para enviar los recordatorios
	 * 
	 * @return
	 */
	private boolean checkTime() {
		 return true;
//		LocalTime now = LocalTime.now();
//		if ((now.isAfter(START_TASK)) && (now.isBefore(END_TASK)))
//			return true;
//		return false;
	}

	private void recordarRemision() {
		System.out.println("recordarRemision: called");
		List<Object[]> datos = null;
		List<String> answers = new ArrayList<>();
		try {
			// Obtiene los tokens que tienen cita manana
			datos = INotificacionDAO.getTokensRemision();

			if (datos != null && datos.size() > 0) {

				while (datos.size() > IConstantes.LIM_SOL_MSG) {
					System.out.println("recordarRemision: before clear datos.size() = " + datos.size());

					for (Object[] d : datos.subList(0, IConstantes.LIM_SOL_MSG)) {
						answers.add(notificarRem(d));
					}
					TimeUnit.SECONDS.sleep(30);

					datos.subList(0, IConstantes.LIM_SOL_MSG).clear();
				}

				if (datos.size() > 0) {
					// Subscribe what's left in the list
					for (Object[] d : datos) {
						answers.add(notificarRem(d));
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("recordarRemision: answer.size() = " + answers.size());
	}

	private String notificarRem(Object[] d) {
		String answer = null;
		try {
			String jsonData = "";
			
			Gson gson = new Gson();
			Type gsonType = new TypeToken<HashMap>() {
			}.getType();
			jsonData = gson.toJson(d[4], gsonType);

			
			System.out.println("notificarRem: enviando a d[0] = " + d[0]);
			
			String msg = String.format(IConstantes.MSG_REM, d[1], d[2]);
//			String msg = IConstantes.MSG_REM_BEG + d[1] + IConstantes.MSG_REM_MIDDLE + d[2] + IConstantes.MSG_REM_END;
			
			int ttl = ((Integer) d[2] == 5) ? Helper.hoursToMillis(48) : Helper.hoursToMillis(24);
			answer = FCMConnection.notifySingleDeviceData((String) d[0], IConstantes.TITLE_REM, msg, Priority.NORMAL, ttl,
					(String) d[3], jsonData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

}
