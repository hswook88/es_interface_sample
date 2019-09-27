package kr.co.shop.interfaces.module.fcm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;

import kr.co.shop.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FCMService {

	private static String testAndroidToken = "eQIdRsR9BAc:APA91bG2rq5xkmpmx5w4cnhR8A4HhpO3LBYBSTy5vzdb91P5kUDSwVqHTj-GtyDpzEoFHTUmYBYdCPc8f691LQU772-sSVeKmRgAy_eLNJjv6t6JcsMiVSMBCRceAT4BrJ7Ovqdf6FQC";
	private static String testIsoToken = "d8kuzkQBvog:APA91bHoI4xLwvPxNlLqpALX3XKQ4YxyFM-mH2-IHFlIeERl2Z7o2YeGpk2Fm8-9mfXhRAM6pKK3Zl8mSKQEl2TygpnKcV9rbsrPdzNefTrF8Ybp3qu223_aW-xCQ2G0Gb_zxjmzBk5J";

	private static String INSTANCE_NAME = "a-rt";

	/**
	 * @Desc : 앱푸시 전송(multi)
	 * @Method Name : sendMultiAppPush
	 * @Date : 2019. 6. 20.
	 * @Author : 유성민
	 * @param registrationTokens
	 * @param title
	 * @param body
	 * @param targetUrl
	 * @param imageUrl
	 * @return failedTokens
	 * @throws Exception
	 */
	public List<String> sendMultiAppPush(List<String> registrationTokens, String title, String body, String targetUrl,
			String imageUrl) throws Exception {

		this.initializeApp();

		MulticastMessage message = null;

		if (UtilsText.isNotBlank(imageUrl)) {
			message = MulticastMessage.builder().setAndroidConfig(AndroidConfig.builder().setTtl(3600 * 1000)
					.setPriority(AndroidConfig.Priority.HIGH).putData("title", title).putData("body", body).build())
					.setApnsConfig(ApnsConfig.builder().putHeader("apns-priority", "10")
							.setAps(Aps.builder().setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
									.setContentAvailable(true).setMutableContent(true).setSound("default").setBadge(1)
									.build())
							.build())
					.putData("image", imageUrl).putData("target", targetUrl).addAllTokens(registrationTokens).build();

		} else {
			message = MulticastMessage.builder().setAndroidConfig(AndroidConfig.builder().setTtl(3600 * 1000)
					.setPriority(AndroidConfig.Priority.HIGH).putData("title", title).putData("body", body).build())
					.setApnsConfig(ApnsConfig.builder().putHeader("apns-priority", "10")
							.setAps(Aps.builder().setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
									.setContentAvailable(true).setMutableContent(true).setSound("default").setBadge(1)
									.build())
							.build())
					.putData("target", targetUrl).addAllTokens(registrationTokens).build();
		}

		BatchResponse response = FirebaseMessaging.getInstance(FirebaseApp.getInstance(INSTANCE_NAME))
				.sendMulticast(message);

		List<String> failedTokens = new ArrayList<>();
		if (response.getFailureCount() > 0) {
			List<SendResponse> responses = response.getResponses();
			for (int i = 0; i < responses.size(); i++) {
				if (!responses.get(i).isSuccessful()) {
					log.error("sendMultiAppPush error : {}", responses.get(i).getException());
					failedTokens.add(registrationTokens.get(i));
				}
			}
		}
		return failedTokens;
	}

	/**
	 * @Desc : 앱푸시 전송(단일)
	 * @Method Name : sendAppPush
	 * @Date : 2019. 6. 20.
	 * @Author : 유성민
	 * @param registrationToken
	 * @param title
	 * @param body
	 * @param targetUrl
	 * @param imageUrl
	 * @return response
	 * @throws Exception
	 */
	public String sendAppPush(String registrationToken, String title, String body, String targetUrl, String imageUrl)
			throws Exception {

		this.initializeApp();

		Message message = null;

		if (UtilsText.isNotBlank(imageUrl)) {
			message = Message.builder().setAndroidConfig(AndroidConfig.builder().setTtl(3600 * 1000)
					.setPriority(AndroidConfig.Priority.HIGH).putData("title", title).putData("body", body).build())
					.setApnsConfig(ApnsConfig.builder().putHeader("apns-priority", "10")
							.setAps(Aps.builder().setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
									.setContentAvailable(true).setMutableContent(true).setSound("default").setBadge(1)
									.build())
							.build())
					.putData("image", imageUrl).putData("target", targetUrl).setToken(registrationToken).build();

		} else {
			message = Message.builder().setAndroidConfig(AndroidConfig.builder().setTtl(3600 * 1000)
					.setPriority(AndroidConfig.Priority.HIGH).putData("title", title).putData("body", body).build())
					.setApnsConfig(ApnsConfig.builder().putHeader("apns-priority", "10")
							.setAps(Aps.builder().setAlert(ApsAlert.builder().setTitle(title).setBody(body).build())
									.setContentAvailable(true).setMutableContent(true).setSound("default").setBadge(1)
									.build())
							.build())
					.putData("target", targetUrl).setToken(registrationToken).build();
		}

		String response = FirebaseMessaging.getInstance(FirebaseApp.getInstance(INSTANCE_NAME)).send(message);

		return response;
	}

	private void initializeApp() throws Exception {
		InputStream serviceAccount = null;
		FirebaseOptions options = null;
		try {
			ClassPathResource resource = new ClassPathResource("fcm-service-account.json");
			serviceAccount = resource.getInputStream();
			options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			FirebaseApp.initializeApp(options, INSTANCE_NAME);
		} catch (FileNotFoundException e) {
			log.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());
		} catch (IOException e) {
			log.error("FirebaseOptions IOException" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> registrationTokens = new ArrayList<>();
		registrationTokens.add(testAndroidToken);
		registrationTokens.add(testIsoToken);

		FCMService service = new FCMService();

		List<String> failedTokens = service.sendMultiAppPush(registrationTokens, "title111", "body2222",
				"https://www.naver.com", "https://img.lovepik.com/element/40028/3789.png_860.png");
		for (String failedToken : failedTokens) {
			System.out.println("failedToken : " + failedToken);
		}

//		String response = service.sendAppPush(testIsoToken, "title111", "body2222", "https://www.naver.com",
//				"https://img.lovepik.com/element/40028/3789.png_860.png");
//		System.out.println("response : " + response);
	}
}
