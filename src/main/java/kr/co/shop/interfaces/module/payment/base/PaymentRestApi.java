package kr.co.shop.interfaces.module.payment.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpStatus;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.client.HttpStatusCodeException;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 타 시스템 API 통신용 REST 요청 CLASS
 * @FileName : PaymentRestApi.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Slf4j
public class PaymentRestApi {

	/** API 통신 HEADER */
	public static Map<String, Object> header;

	/** API 통신 PARAMETER */
	public static Map<String, Object> parameter;

	/** API 통신 응답 결과 */
	public static String responseStr;

	/** API 통신 URL */
	private static String reqUrl;

	/** REQUEST CONTENT TYPE 파라미터 전송 방법 결정을 위해 사용. */
	private static String contentType;

	/**
	 * API 통신 HEADER 정보가 필요 없는 경우
	 * 
	 * @param 통신URL
	 * @param PARAMETER
	 */
	public PaymentRestApi(String _reqUrl, Map<String, Object> _parameter) {
		reqUrl = _reqUrl;
		parameter = _parameter;
	}

	/**
	 * API 통신 HEADER 정보가 필요 한 경우
	 * 
	 * @param 통신URL
	 * @param HEADER
	 * @param PARAMETER
	 */
	public PaymentRestApi(String _reqUrl, Map<String, Object> _header, Map<String, Object> _parameter) {
		reqUrl = _reqUrl;
		header = _header;
		parameter = _parameter;
	}

	/**
	 * @Desc : 통신 HEADER 추가
	 * @Method Name : addHeader
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param _key
	 * @param _value
	 */
	public void addHeader(String _key, Object _value) {
		header.put(_key, _value);
	}

	/**
	 * 통신 PARAMETER 추가
	 * 
	 * @param _key
	 * @param _value
	 */
	public void addParameter(String _key, Object _value) {
		parameter.put(_key, _value);
	}

	/**
	 * @Desc :API 호출(POST)
	 * @Method Name : sendHttpsPost
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @return String RESPONSE_BODY
	 */
	public String sendHttpsPost() {
		return sendHttpsRequest(PaymentConst.POST);
	}

	/**
	 * @Desc : API 호출(GET)
	 * @Method Name : sendHttpsGet
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @return RESPONSE_BODY
	 */
	public String sendHttpsGet() {
		return sendHttpsRequest(PaymentConst.GET);
	}

	/**
	 * @Desc : API 호출
	 * @Method Name : sendHttpsRequest
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param methodType API 전송 METHOD TYPE
	 * @return String RESPONSE_BODY
	 */
	private String sendHttpsRequest(String methodType) {
		if (log.isDebugEnabled()) {
			log.debug("reqUrl : " + reqUrl);
			log.debug("header : " + header.toString());
			log.debug("reqParams : " + parameter.toString());
			log.debug("methodType : " + methodType);
		}

		try {
			URL url = new URL(reqUrl);
			URLConnection urlcon = url.openConnection();
			urlcon.setReadTimeout(PaymentConst.readTimeOut);
			HttpsURLConnection conn = (HttpsURLConnection) urlcon;

			SSLContext context = SSLContext.getInstance("TLSv1.2");
			context.init(null, null, new java.security.SecureRandom());
			conn.setSSLSocketFactory(context.getSocketFactory());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(methodType);

			setReqHeader(conn);

			conn.getOutputStream().write(getParameter().toString().getBytes("UTF-8"));

			if (conn.getResponseCode() == HttpStatus.SC_OK) {
				String buff = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				responseStr = "";
				while ((buff = br.readLine()) != null) {
					responseStr += buff;
				}
			} else {
				String buff = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
				responseStr = "";
				while ((buff = br.readLine()) != null) {
					responseStr += buff;
				}
			}

		} catch (HttpStatusCodeException e) {
			// TODO: 에러 코드에 따른 Exception 처리
			log.debug(" HttpStatusCodeException : " + e.getResponseBodyAsString());
			e.printStackTrace();
		} catch (IOException e) {
			log.debug(" IOException : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.debug(" Exception : " + e.getMessage());
			e.printStackTrace();
		}

		if (log.isDebugEnabled()) {
			log.debug("responseStr : " + responseStr);
		}
		return responseStr;
	}

	/**
	 * @Desc :REQUEST HEADER 추가.
	 * @Method Name : setReqHeader
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param conn {@link HttpsURLConnection}
	 */
	private void setReqHeader(HttpsURLConnection conn) {
		Set<String> keys = header.keySet();
		for (String key : keys) {
			if (log.isDebugEnabled()) {
				log.debug("header key : " + key);
			}
			if (key.equals("Content-Type"))
				contentType = header.get(key).toString();
			conn.setRequestProperty(key, header.get(key).toString());
		}
	}

	/**
	 * @Desc : REQUEST PARAMETER 추가. 전송 방식에 따라 파라미터 STRING을 변경 해서 처리 해야함.
	 * @Method Name : getParameter
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @return STRING_PARAMETER
	 */
	private String getParameter() {
		switch (contentType) {
		case PaymentConst.CONTENT_TYPE_FORM:
			StringBuffer strParameters = new StringBuffer();
			for (Map.Entry<String, Object> param : parameter.entrySet()) {
				strParameters.append(param.getKey());
				strParameters.append("=");
				strParameters.append(param.getValue());
				strParameters.append("&");

			}
			if (log.isDebugEnabled()) {
				log.debug("getParameter return string : "
						+ strParameters.substring(0, strParameters.length() - 1).toString());
			}
			return strParameters.substring(0, strParameters.length() - 1).toString();
		case PaymentConst.CONTENT_TYPE_JSON:
			JSONObject json = new JSONObject(parameter);
			if (log.isDebugEnabled()) {
				log.debug("getParameter return string : " + json.toString());
			}
			return json.toString();
		}
		return "";
	}
}
