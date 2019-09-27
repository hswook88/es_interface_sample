/**
 * 
 */
package kr.co.shop.interfaces.module.payment.naver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버 페이 신용카드 매출 전표 응답 class
 * @FileName : NaverPaymentCardCheckReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentCardCheckReturn extends PaymentBase {

	private static final long serialVersionUID = -4283266135958835614L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 매출 전표 응답 본문 */
	private NaverPaymentCardCheckReturnBody body;

	/** 매출 전표 응답 본문 */
	@Data
	public static class NaverPaymentCardCheckReturnBody {
		/** 영수증 확인 URL */
		private String receiptUrl;

	}

}
