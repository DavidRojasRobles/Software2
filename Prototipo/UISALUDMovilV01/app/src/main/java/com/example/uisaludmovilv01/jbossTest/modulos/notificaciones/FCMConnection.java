package com.example.uisaludmovilv01.jbossTest.modulos.notificaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.digester.annotations.handlers.SetPropertiesLoaderHandler;
import org.apache.commons.digester.annotations.rules.SetProperty;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidConfig.Priority;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.TopicManagementResponse;

//import java.util.Date;

public class FCMConnection {

	FirebaseOptions options;

	public FCMConnection() {
		this.options = buildOptions();
	}

	private FirebaseOptions buildOptions() {
		System.out.println("buildOptions: FCMConnection");
		FirebaseOptions op = null;
		try {
			op = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.getApplicationDefault())
					// .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
					.build();
			FirebaseApp.initializeApp(op);
			System.out.println("\n\nbuildOptions: app initialized successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\n\nbuildOptions: app couldn't be initialized");
			e.printStackTrace();
		}
		return op;

	}

//	public static boolean createOptions() {
//
//		System.out.println("buildOptions: FCMConnection");
//		boolean built = false;
//		try {
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.getApplicationDefault())
//					// .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
//					.build();
//			FirebaseApp.initializeApp(options);
//			System.out.println("\n\nbuildOptions: app initialized successfully");
//			built = true;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("\n\nbuildOptions: app couldn't be initialized");
//			e.printStackTrace();
//		}
//		return built;
//	}

	/**
	 * Requests a notification for a single device
	 * 
	 * @param registrationToken
	 * @param title
	 * @param msg
	 * @return A string with the FCM server's response
	 */
	public static String notifySingleDevice(String registrationToken, 
			String title, String msg, Priority priority, int ttl, String clickAaction) {
		
		Message message = Message.builder().setNotification(new Notification(title, msg))
				.setAndroidConfig(
						AndroidConfig.builder()
						.setTtl(ttl)
						.setPriority(priority)
						
						.setNotification(
								AndroidNotification.builder().
								setClickAction(clickAaction)
								.build())
						
						.build())
				.setToken(registrationToken).build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = null;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);
		return response;

	}
	
	/**
	 * Requests a notification for a single device with data attached
	 * 
	 * @param registrationToken
	 * @param title
	 * @param msg
	 * @return A string with the FCM server's response
	 */
	public static String notifySingleDeviceData(/*int idPersona,*/ String registrationToken, 
			String title, String msg, Priority priority, int ttl, /*String clickAaction,*/ String tipo, String jsonData) {
		
		if(jsonData != null) System.out.println("FCM.nsdd: jsonData = " + jsonData);		
		else System.out.println("FCM.nsdd: jsonData is null");
		
		Message message = Message.builder()
				.setAndroidConfig(
						AndroidConfig.builder()
						.setTtl(ttl)
						.setPriority(priority)					
						.build())
				.putData("title", title)
				.putData("content", msg)
				.putData("tipo", tipo)
//				.putData("onClickAction", clickAaction)
				.putData("jsonData", jsonData)
				.setToken(registrationToken)
				.build();	

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = null;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);
		return response;

	}

	/**
	 * Requests a notification to multiple devices (up to 500 per invocation).
	 * 
	 * @param registrationTokens
	 * @param title
	 * @param msg
	 * @return A string with the FCM server's response
	 */
	public static int notifyDevices(List<String> registrationTokens, String title, String msg) {
		// This registration token comes from the client FCM SDKs.

		MulticastMessage message = MulticastMessage.builder().setNotification(new Notification(title, msg))
				.addAllTokens(registrationTokens).build();

		// Send a message to the device corresponding to the provided
		// registration token.
		BatchResponse response = null;
		try {
			response = FirebaseMessaging.getInstance().sendMulticast(message);
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Response is a message ID string.
		System.out.println(response.getSuccessCount() + " messages were sent successfully");
		return response.getSuccessCount();

	}

	/**
	 * Devuelve una lista con los tokens que presentaron problemas al enviar un
	 * mensaje multicast
	 * 
	 * @param registrationTokens
	 * @param response
	 */
	public static List<String> checkFailedTokens(ArrayList<String> registrationTokens, BatchResponse response) {
		List<String> failedTokens = null;
		if (response.getFailureCount() > 0) {
			List<SendResponse> responses = response.getResponses();
			failedTokens = new ArrayList<>();
			for (int i = 0; i < responses.size(); i++) {
				if (!responses.get(i).isSuccessful()) {
					// The order of responses corresponds to the order of the
					// registration tokens.
					failedTokens.add(registrationTokens.get(i));
				}
			}

			System.out.println("List of tokens that caused failures: " + failedTokens.size());
		}
		return failedTokens;
	}

	public static List<String> sendMulticastAndHandleErrors(List <String> tokens, 
			String title, String msg, Priority priority, int ttl, String clickAction) throws FirebaseMessagingException {
		System.out.println("sendMulticastAndHandleErrors: called");
	    // [START send_multicast_error]
	    
		List<String> failedTokens = null;
		
	    MulticastMessage message = MulticastMessage.builder()
	    		.setAndroidConfig(AndroidConfig.builder()
	    				.setTtl(ttl)
	    				.setPriority(priority)
	    				.build())
	        .addAllTokens(tokens)
	        .putData("title", title)
	        .putData("content", msg)
	        .putData("onClickAction", clickAction)
	        .build();
	    
	    
	    BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
	    if (response.getFailureCount() > 0) {
	      List<SendResponse> responses = response.getResponses();
	      failedTokens = new ArrayList<>();
	      for (int i = 0; i < responses.size(); i++) {
	        if (!responses.get(i).isSuccessful()) {
	          // The order of responses corresponds to the order of the registration tokens.
	          failedTokens.add(tokens.get(i));
	        }
	      }
	    }
	    return failedTokens;
	}






}
