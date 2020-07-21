package com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.tasks;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.TimerTask;

import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.FCMConnection;
import com.example.uisaludmovilv01.jbossTest.generales.Helper;
import com.example.uisaludmovilv01.jbossTest.generales.IConstantes;
import com.example.uisaludmovilv01.jbossTest.generales.IConsultasDAO;
import com.example.uisaludmovilv01.jbossTest.modulos.notificaciones.INotificacionDAO;

/**
 * Tarea que se ejecuta cada día y que envía solicitudes de notificaciones al
 * servidor de FCM para notificar a los pacientes que tienen fórmulas próximas a
 * vencerse (en 3 días) con medicamentos sin reclamar.
 * 
 * @author PROGRAMADORES
 *
 */
public class RecordarMedicamentos extends TimerTask {

	private final LocalTime START_TASK = LocalTime.of(17, 0, 0);
	private final LocalTime END_TASK = LocalTime.of(17, 5, 0);
	private final String CLICK_ACTION = "OPEN_ACTIVITY_FORMULAS";
	private final int ttl = Helper.hoursToMillis(24);
	private ScheduledExecutorService scheduler;

	public RecordarMedicamentos() {
		this.scheduleTask();
	}

	/**
	 * Programa ejecucion automatica cada día a las 5 pm.
	 */
	private void scheduleTask() {
		System.out.println("RecordarMedicamentos: scheduleTask called");
		long delay = ChronoUnit.HOURS.between(LocalTime.now(), START_TASK);
		scheduler = Executors.newScheduledThreadPool(1);
//		scheduler.scheduleAtFixedRate(this, delay, 24, TimeUnit.HOURS);
		 scheduler.scheduleAtFixedRate(this, 0, 2, TimeUnit.MINUTES);
	}

	@Override
	public void run() {
		System.out.println("RecordarMedicamentos: running ...");
		// Si la hora está en el rango de 5 pm y 5:05 pm
		if (checkTime())
			recordarMedicamentos();

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

	private void recordarMedicamentos() {
		List<Object[]> datos = null;
		List<String> answers = new ArrayList<>();
		try {
			// Obtiene los tokens que tienen cita manana
			datos = INotificacionDAO.getTokensMedsPendientes();

			if (datos != null && datos.size() > 0) {

				while (datos.size() > IConstantes.LIM_SOL_MSG) {
					System.out.println("recordarMedicamentos: before clear datos.size() = " + datos.size());

					for (Object[] d : datos.subList(0, IConstantes.LIM_SOL_MSG)) {
						answers.add(notificarMeds(d));
					}
					TimeUnit.SECONDS.sleep(30);

					datos.subList(0, IConstantes.LIM_SOL_MSG).clear();
				}

				if (datos.size() > 0) {
					// Subscribe what's left in the list
					for (Object[] d : datos) {
						answers.add(notificarMeds(d));
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("recordarRemision: answer.size() = " + answers.size());
	}

	private String notificarMeds(Object[] d) {
		
		String answer = null;
		try {
			String jsonData = "";
			
			Gson gson = new Gson();
			Type gsonType = new TypeToken<HashMap>() {
			}.getType();
			jsonData = gson.toJson(d[1], gsonType);

			
			System.out.println("notificarMeds: enviando a d[0] = " + d[0]);
//			String msg = IConstantes.MSG_REM_BEG + d[1] + IConstantes.MSG_REM_MIDDLE + d[2] + IConstantes.MSG_REM_END;
			
			answer = FCMConnection.notifySingleDeviceData((String) d[0], IConstantes.TITLE_MED_PENDIENTE, IConstantes.MSG_MED_PENDIENTE, Priority.NORMAL, ttl,
					IConstantes.TIPO_NOTI_MED, jsonData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}
}
