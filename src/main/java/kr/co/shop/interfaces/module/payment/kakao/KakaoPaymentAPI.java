package kr.co.shop.interfaces.module.payment.kakao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.interfaces.module.payment.base.PaymentConst;
import kr.co.shop.interfaces.module.payment.base.PaymentRestApi;
import kr.co.shop.interfaces.module.payment.config.KakaoPaymentConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 카카오 페이 연동 공통 API
 * @FileName : KakaoPaymentAPI.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Service
@Slf4j
public class KakaoPaymentAPI {

	@Autowired
	KakaoPaymentConfig kakaoPaymentConfig;

	/**
	 * 
	 * @Desc : 카카오 페이 API연동 호출
	 * @Method Name : sendHttpsRequest
	 * @Date : 2019. 1. 31.
	 * @Author : flychani@3top.co.kr
	 * @param reqUrl
	 * @param reqParams
	 * @return
	 * @throws Exception
	 */
	public String sendHttpsRequest(String reqUrl, Map<String, Object> reqParams) throws Exception {
		return sendHttpsRequest(reqUrl, reqParams, PaymentConst.CONTENT_TYPE_FORM);
	}

	/**
	 * 
	 * @Desc : 카카오 페이 API연동 호출
	 * @Method Name : sendHttpsRequest
	 * @Date : 2019. 1. 31.
	 * @Author : flychani@3top.co.kr
	 * @param reqUrl
	 * @param reqParams
	 * @param contentType
	 * @return
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
	 * 
	 * @Desc : 카카오 페이 header 정보
	 * @Method Name : setHeader
	 * @Date : 2019. 1. 31.
	 * @Author : flychani@3top.co.kr
	 * @param header
	 * @param contentType
	 */
	void setHeader(Map<String, Object> header, String contentType) {
		header.put("Authorization", kakaoPaymentConfig.getAdminKey());
		header.put("Content-Type", contentType);
	}
}
