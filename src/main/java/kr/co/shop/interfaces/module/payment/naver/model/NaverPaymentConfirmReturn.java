package kr.co.shop.interfaces.module.payment.naver.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 거래완료 응답 class
 * @FileName : NaverPaymentConfirmReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentConfirmReturn extends PaymentBase {

	private static final long serialVersionUID = 3113348285909968520L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 응답 본문 */
	private NaverPaymentConfirmReturnBody body;

	@Data
	public static class NaverPaymentConfirmReturnBody {

		/** 네이버페이 결제번호 */
		private String paymentId;

		/** 구매 확정 일시 */
		private Date confirmTime;

	}
}
