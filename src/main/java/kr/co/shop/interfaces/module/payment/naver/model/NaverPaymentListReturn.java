package kr.co.shop.interfaces.module.payment.naver.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 결제 내역 응답 class
 * @FileName : NaverPaymentListReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentListReturn extends PaymentBase {

	private static final long serialVersionUID = 3349332448543644634L;

	/** 응답 결과코드 */
	private String code;

	/** 응답 에러코드 */
	@JsonProperty("error_code")
	private String errorCode;

	/** 결과 코드에 따른 상세 메시지 */
	private String message;

	/** 응답 본문 */
	private NaverPaymentListReturnBody body;

	/** 응답 본문 */
	@Data
	public static class NaverPaymentListReturnBody {

		/** 조회조건에 대한 전체건수 */
		private int totalCount;

		/** 현재 페이지에 대한 응답건수 */
		private int responseCount;

		/** 전체 페이지 개수 */
		private int totalPageCount;

		/** 응답 된 현재 페이지 번호 */
		private int currentPageNumber;

		/** 결제 내역 배열 */
		private List<NaverPaymentListReturnBodyList> list;

		/** 결제 내역 */
		@Data
		public static class NaverPaymentListReturnBodyList {
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

			/** 결제승인 유형 */
			private String admissionTypeCode;

			/** 결제/취소 일시 */
			private String admissionYmdt;

			/** 거래완료 일시 */
			private Date tradeConfirmYmdt;

			/** 결제/취소 시도에 대한 최종결과  */
			private String admissionState;

			/** 총 결제/취소 금액 */
			private int totalPayAmount;

			/** 주 결제 수단 결제/취소 금액 */
			private int primaryPayAmount;

			/** 네이버페이 포인트 결제/취소 금액 */
			private int npointPayAmount;

			/** 주 결제 수단  CARD:신용카드, BANK:계좌이체 */
			private String primaryPayMeans;

			/** 주 결제 수단 카드사  */
			private String cardCorpCode;

			/** 일부 마스킹 된 신용카드 번호 */
			private String cardNo;

			/** 카드승인번호  */
			private String cardAuthNo;

			/** 할부 개월 수 (일시불은 0) */
			private int cardInstCount;

			/** 주 결제 수단 은행  */
			private String bankCorpCode;

			/** 일부 마스킹 된 계좌 번호 */
			private String bankAccountNo;

			/** 상품명 */
			private String productName;

			/** 도서 / 공연 소득공제 여부 */
			private Boolean extraDeduction;

			/** 이용완료일(yyyymmdd) */
			private String useCfmYmdt;

			/** 정산 데이터 그룹 */
			private NaverPaymentListReturnBodySettleInfo settleInfo;

		}

		/** 정산 데이터 그룹 */
		@Data
		public static class NaverPaymentListReturnBodySettleInfo {
			/** 정산 데이터 생성 여부 (true/false) */
			private Boolean settleCreated;

			/** 전체 정산 입금금액 */
			private int totalSettleAmount;

			/** 전체 네이버페이 정산 수수료 금액 */
			private int totalCommissionAmount;

			/** 주결제수단 정산 입금금액 */
			private int primarySettleAmount;

			/** 주결제수단 정산 수수료 금액 */
			private int primaryCommissionAmount;

			/** 네이버페이 포인트 정산 입금금액 */
			private int npointSettleAmount;

			/** 네이버페이 포인트 정산 수수료 금액 */
			private int npointCommissionAmount;
		}
	}
}
