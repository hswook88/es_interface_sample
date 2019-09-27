package kr.co.shop.interfaces.module.member.config;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.MembershipInfo;
import kr.co.shop.interfaces.module.member.utils.Url;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 설정 파일 load관리
 * @FileName : MembershipConfig.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 18.
 * @Author : 백인천
 */
@Slf4j
public class MembershipConfig {

	/**
	 * 기본 문자셋
	 */
	public static final String DEFAULT_SITE_CHAR_SET = "UTF-8";

	/**
	 * 컨낵션 타임아웃시간(4초)
	 */
	public final static int MAX_CONNECTION_TIMEOUT = 4000;

	/**
	 * 소켓 타임아웃시간(4초)
	 */
	public final static int MAX_SOCKET_TIMEOUT = 4000;

	private static Properties properties = new Properties();

	public static String getString(String key) {
		String propertiesValue = properties.getProperty(key);

		if (propertiesValue == null) {
			// throw new ConfigValueNotFoundException(key + " 에 해당하는 환경 변수값을 찾을 수 없음");
			log.debug(UtilsText.concat(key, " 에 해당하는 환경 변수값을 찾을 수 없음"));
			return UtilsText.concat(key, " 에 해당하는 환경 변수값을 찾을 수 없음");
		} else {
			return propertiesValue;
		}
	}

	/**
	 * int형 반환
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 형변환 String to Long
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(String key, long defaultValue) {
		try {
			return Long.parseLong(properties.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Properties properties() {
		return (Properties) properties.clone();
	}

	/**
	 * @Desc : newline(\n) 으로 분리되는 설정값 리스트를 반환.
	 * @Method Name : getStrings
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param key
	 * @return
	 */
	public static List<String> getStrings(String key) {
		String propertyValue = getString(key);
		BufferedReader reader = null;
		StringReader stringReader = new StringReader(propertyValue);

		List<String> propertyValues = new ArrayList<String>();
		try {
			reader = new BufferedReader(stringReader);
			for (;;) {
				String line = null;
				try {
					line = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					log.debug(UtilsText.concat("getStrings - BufferReader error > ", e.getMessage()));
				}
				if (line == null) {
					break;
				} else if (UtilsText.isEmpty(line.trim())) {
					continue;
				} else {
					propertyValues.add(line.trim());
				}
			}
		} finally {
			if (reader != null) {
				if (reader instanceof Closeable) {
					Closeable c = (Closeable) reader;
					try {
						c.close();
					} catch (Exception e) {
						log.debug(UtilsText.concat("RuntimeException > ", e.getMessage()));
					}
				}
			}
		}

		return propertyValues;
	}

	/**
	 * @Desc : 맴버십 서버 구성
	 * @Method Name : getMemberShipServer
	 * @Date : 2019. 2. 22.
	 * @Author : 백인천
	 * @return
	 */
	public static String getMemberShipServer() {
		StringBuilder buffer = new StringBuilder(512);
		buffer.append(Url.DEFAULT_PROTOCOL);
		buffer.append("://");
		buffer.append(MembershipInfo.ip);
		int port = MembershipConfig.getInt(MembershipInfo.port, 80);
		if (port != 80) {
			buffer.append(":");
			buffer.append(port);
		}
		return buffer.toString();
	}

}
