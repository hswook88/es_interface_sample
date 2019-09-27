package kr.co.shop.interfaces.module.payment.base;

import lombok.Data;

/**
 * @Desc : 결제모듈 응답용 공통 model
 * @FileName : PaymentResult.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class PaymentResult {
	private String successYn; // 성공 여부
	private String code; // 업무성 코드
	private String statusCode; // 상태(http) 코드
	private Object data; // 데이터 객체
	private String message; // 메세지
	private String devMessage; // 개발자용 메세지

	public PaymentResult(String successYn) {
		this.successYn = successYn;
	}

	public PaymentResult(String successYn, String message) {
		this.successYn = successYn;
		this.message = message;

	}

	public PaymentResult(String successYn, String code, Object data, String message) {
		this.successYn = successYn;
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public PaymentResult(String successYn, String code, String statusCode, Object data, String message,
			String devMessage) {
		this.successYn = successYn;
		this.code = code;
		this.statusCode = statusCode;
		this.data = data;
		this.message = message;
		this.devMessage = devMessage;
	}

}