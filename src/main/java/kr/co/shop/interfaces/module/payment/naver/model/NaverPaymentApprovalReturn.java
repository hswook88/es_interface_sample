package kr.co.shop.interfaces.module.payment.naver.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 승인 응답 class
 * @FileName : NaverPaymentApprovalReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentApprovalReturn extends PaymentBase {

	private static final long serialVersionUID = 6781436881982142319L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 응답 본문 */
	private NaverPaymentApprovalReturnBody body;

	/** 승인 응답 본문 */
	@Data
	public static class NaverPaymentApprovalReturnBody {
		/** 네이버페이 결제번호 */
		private String paymentId;

		/** 결제 승인 상세 */
		private NaverPaymentApprovalReturnBodyDetail detail;
	}

	/** 결제 승인 상세 */
	@Data
	public static class NaverPaymentApprovalReturnBodyDetail {
		/** 네이버페이 결제번호 */
		private String paymentId;

		/** 네이버페이 결제 이력 번호 */
		private String payHistId;

		/** 가맹점 아이디 (가맹점센터 로그인 아이디) */
		private String merchantId;

		/** 가맹점명 */
		private String merchantName;

		/** 가맹점의 결제번호 */
		private String merchantPayKey;

		/** 가맹점의 사용자 키 */
		private String merchantUserKey;

		/** 결제승인 유형 01:원결제 승인건, 03:전체취소 건, 04:부분취소 건 */
		private String admissionTypeCode;

		/** 결제/취소 일시(YYYYMMDDHH24MMSS) */
		private Date admissionYmdt;

		/** 거래완료 일시(정산기준날짜, YYYYMMDDHH24MMSS)  */
		private Date tradeConfirmYmdt;

		/** 결제/취소 시도에 대한 최종결과 SUCCESS:완료, FAIL:실패 */
		private String admissionState;

		/** 총 결제/취소 금액 */
		private Double totalPayAmount;

		/** 주 결제 수단 결제/취소 금액 */
		private Double primaryPayAmount;

		/** 네이버페이 포인트 결제/취소 금액 */
		private Double npointPayAmount;

		/** 주 결제 수단 CARD:신용카드, BANK:계좌이체 */
		private String primaryPayMeans;

		/** 주 결제 수단 카드사  */
		private String cardCorpCode;

		/** 일부 마스킹 된 신용카드 번호 */
		private String cardNo;

		/** 카드승인번호  */
		private String cardAuthNo;

		/** 할부 개월 수 (일시불은 0) */
		private Double cardInstCount;

		/** 주 결제 수단 은행  */
		private String bankCorpCode;

		/** 일부 마스킹 된 계좌 번호 */
		private String bankAccountNo;

		/** 상품명 */
		private String productName;

		/** 정산 예정 금액과 결제 수수료 금액이 계산되었는지 여부 */
		private Boolean settleExpected;

		/** 정산 예정 금액  */
		private Double settleExpectAmount;

		/** 결제 수수료 금액  */
		private Double payCommissionAmount;

		/** 도서 / 공연 소득공제 여부 */
		private Boolean extraDeduction;

		/** 이용완료일(yyyymmdd) */
		private String useCfmYmdt;

	}
}
