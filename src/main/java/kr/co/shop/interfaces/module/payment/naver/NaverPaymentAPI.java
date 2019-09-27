package kr.co.shop.interfaces.module.payment.naver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.module.payment.base.PaymentConst;
import kr.co.shop.interfaces.module.payment.base.PaymentRestApi;
import kr.co.shop.interfaces.module.payment.config.NaverPaymentConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : NAVER페이 연동에 사용할 API CLASS
 * @FileName : NaverPaymentAPI.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Service
@Slf4j
public class NaverPaymentAPI {

	@Autowired
	NaverPaymentConfig naverPayConfig;

	/**
	 * @Desc : 네이버 페이 API연동 호출 application/x-www-form-urlencoded을 기본으로 전송처리.
	 * @Method Name : sendHttpsRequest
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param reqUrl    호출URL
	 * @param reqParams {@link Map}
	 * @return RESPONSE RESULT
	 * @throws Exception
	 */
	public String sendHttpsRequest(String reqUrl, Map<String, Object> reqParams) throws Exception {
		return sendHttpsRequest(reqUrl, reqParams, PaymentConst.CONTENT_TYPE_FORM);
	}

	/**
	 * @Desc : 네이버 페이 API연동 호출
	 * @Method Name : sendHttpsRequest
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param reqUrl      호출URL
	 * @param reqParams   {@link Map}
	 * @param contentType request전송타입
	 * @return String {@link PaymentRestApi#sendHttpsPost()}
	 * @throws Exception
	 */
	public String sendHttpsRequest(String reqUrl, Map<String, Object> reqParams, String contentType) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("reqUrl : " + reqUrl);
			log.debug("reqParams : " + reqParams.toString());
		}

		String resResult = "";

		try {
			Map<String, Object> header = new HashMap<String, Object>();
			setHeader(header, contentType);

			PaymentRestApi api = new PaymentRestApi(reqUrl, header, reqParams);
			resResult = api.sendHttpsPost();

		} catch (Exception e) {
			e.printStackTrace();

			if (log.isDebugEnabled()) {
				log.debug("e : " + e.getMessage());
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("resResult : " + resResult);
		}

		return resResult;
	}

	/**
	 * @Desc : 네이버 페이에서 요구되는 인증키, Request Content-Type 설정.
	 * @Method Name : setHeader
	 * @Date : 2019. 1. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param header
	 * @param contentType
	 */
	void setHeader(Map<String, Object> header, String contentType) {
		header.put("X-Naver-Client-Id", naverPayConfig.getClientId());
		header.put("X-Naver-Client-Secret", naverPayConfig.getClientSecret());
		header.put("Content-Type", contentType);
	}
}
