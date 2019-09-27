package kr.co.shop.interfaces.module.payment.naver.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 취소 응답 class
 * @FileName : NaverPaymentCancelReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentCancelReturn extends PaymentBase {

	private static final long serialVersionUID = 8563594211274732547L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 응답 본문 */
	private NaverPaymentCancelReturnBody body;

	/** 취소 응답 본문 */
	@Data
	public static class NaverPaymentCancelReturnBody {

		/** 네이버페이 결제번호 */
		private String paymentId;

		/** 취소 결제 번호 */
		private String payHistId;

		/** 취소 처리된 주 결제 수단(CARD: 신용카드, BANK: 계좌 이체) */
		private String primaryPayMeans;

		/** 주 결제 수단 취소 금액 */
		private Double primaryPayCancelAmount;

		/** 추가로 취소 가능한 주 결제 수단 잔여 결제 금액 */
		private Double primaryPayRestAmount;

		/** 네이버페이 포인트 취소 금액 */
		private Double npointCancelAmount;

		/** 추가로 취소 가능한 네이버페이 포인트 잔여 결제 금액 */
		private Double npointRestAmount;

		/** 취소 일시(YYYYMMDDHH24MMSS) */
		private Date cancelYmdt;

		/** 추가로 취소 가능한 전체 잔여 결제 금액(primaryPayRestAmount + npointRestAmount) */
		private Double totalRestAmount;

	}
}
