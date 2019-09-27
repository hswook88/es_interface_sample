package kr.co.shop.interfaces.module.payment.naver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 현금영수증 조회 응답 class
 * @FileName : NaverPaymentCashAmountReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentCashAmountReturn extends PaymentBase {

	private static final long serialVersionUID = 2922531746980527886L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 응답 본문 */
	private NaverPaymentCashAmountReturnBody body;

	@Data
	public class NaverPaymentCashAmountReturnBody {

		/** 네이버페이 포인트 결제 금액 중 현금 영수증 발행 대상 금액 */
		private Double npointCashAmount;

		/** 현금성 주 결제 수단 결제 금액 중 현금 영수증 발행 대상 금액 */
		private Double primaryCashAmount;

		/** 결제에 사용된 현금성 주 결제 수단 정보(BANK: 계좌 이체) */
		private String primaryPayMeans;

		/**
		 * 현금 영수증 발행 대상 총 금액  npointCashAmount + primaryCashAmount
		 */
		private Double totalCashAmount;

		/**
		 * 현금성 총 공급가  네이버페이 포인트 현금성 공급가 + 현금성 주 결제 수단(계좌 간편 결제) 공급가
		 */
		private Double supplyCashAmount;

		/**
		 * 현금성 총 부가세  현금성 네이버페이 포인트 부가세 + 현금성 주 결제 수단(계좌 간편 결제) 부가세
		 */
		private Double vatCashAmount;

	}
}
