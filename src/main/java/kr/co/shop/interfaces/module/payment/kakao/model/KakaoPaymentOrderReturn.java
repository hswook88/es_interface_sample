package kr.co.shop.interfaces.module.payment.kakao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 주문상세 조회 응답
 * @FileName : KakaoPaymentOrderReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentOrderReturn extends PaymentBase {

	private static final long serialVersionUID = -3342350008124884441L;

	/** 결제 고유 번호. 10자. */
	private String tid;

	/** 가맹점 코드. 20자 */
	private String cid;

	/** 결제상태값 */
	private String status;

	/** 가맹점 주문번호. 최대 100자. */
	@JsonProperty("partner_order_id")
	private String partnerOrderId;

	/** 가맹점 회원 id. 최대 100자. */
	@JsonProperty("partner_user_id")
	private String partnerUserId;

	/** 결제 수단. CARD, MONEY 중 하나 */
	@JsonProperty("payment_method_type")
	private String paymentMethodType;

	/** 결제 금액 정보 JSON Object */
	private KakaopayCommonAmount amount;

	/** 이번 요청으로 취소된 금액 정보 JSON Object */
	@JsonProperty("canceled_amount")
	private KakaopayCommonAmount canceledAmount;

	/** 해당 결제에 대해 취소 가능 금액 JSON Object */
	@JsonProperty("cancel_available_amount")
	private KakaopayCommonAmount cancelAvailableAmount;

	/**
	 * 주문 상세 금액 정보 공통 amount 결제 금액 정보 canceled_amount 이번 요청으로 취소된 금액 정보
	 * cancel_available_amount 해당 결제에 대해 취소 가능 금액
	 */
	@Data
	public static class KakaopayCommonAmount {

		/** 전체 결제 금액 */
		private int total;

		/** 비과세 금액 */
		@JsonProperty("tax_free")
		private int taxFree;

		/** 부가세 금액 */
		private int vat;

		/** 사용한 포인트 금액 */
		private int point;

		/** 할인 금액 */
		private int discount;

	}

	/** 상품 이름. 최대 100자 */
	@JsonProperty("item_name")
	private String itemName;

	/** 상품 코드. 최대 100자 */
	@JsonProperty("item_code")
	private String itemCode;

	/** 상품 수량 */
	private int quantity;

	/** 결제 준비 요청 시각 Datetime */
	@JsonProperty("created_at")
	private String createdAt;

	/** 결제 승인 시각 Datetime */
	@JsonProperty("approved_at")
	private String approvedAt;

	/** 결제 취소 시각 Datetime */
	@JsonProperty("canceled_at")
	private String canceledAt;

	/** 사용자가 선택한 카드정보 JSON Object */
	@JsonProperty("selected_card_info")
	private KakaoCardInfo selectedCardInfo;

	@Data
	public static class KakaoCardInfo {

		/** 카드 BIN */
		@JsonProperty("card_bin")
		private String cardBin;

		/** 할부개월수 */
		@JsonProperty("install_month")
		private String installMonth;

		@JsonProperty("card_corp_name")
		private String cardCorpName;

		/** 무이자할부 여부(Y/N) */
		@JsonProperty("interest_free_install")
		private String interestFreeInstall;

	}

	/** 결제/취소 상세 JSON Object */
	@JsonProperty("payment_action_details")
	private List<KakaoPayDetailList> paymentActionDetails;

	@Data
	public static class KakaoPayDetailList {

		/** Request 고유 번호 */
		private String aid;

		/** 거래시간 */
		@JsonProperty("approved_at")
		private String approved_at;

		/** 결제/취소 총액 */
		private String amount;

		/** 결제/취소 포인트 금액 */
		@JsonProperty("point_amount")
		private String point_amount;

		/** 할인 금액 */
		@JsonProperty("discount_amount")
		private String discount_amount;

		/** 결제 타입. PAYMENT(결제), CANCEL(결제취소), ISSUED_SID(SID 발급) 중 하나 */
		@JsonProperty("payment_action_type")
		private String payment_action_type;

		/** Request로 전달한 값 */
		private String payload;

		/** 결제내역 페이지 추가 부분 */

		/** 카드 BIN */
		private String cardBinView;

		/** 할부개월수 */
		private String installMonthView;

		private String cardCorpNameView;

		/** 무이자할부 여부(Y/N) */
		private String interestFreeInstallView;

		/** 결제 수단. CARD, MONEY 중 하나 */
		private String paymentMethodTypeView;
	}

	/** 응답 실패시 code */
	private String code;

	/** 응답 실패시 message */
	private String msg;

	@Data
	public static class extras {

		@JsonProperty("method_result_code")
		private String methodResultCode;

		@JsonProperty("method_result_message")
		private String methodResultMessage;
	}

	/** tms_result ?? 카카오페이와 확인 필요 spec 상 없는 내용인데 추가 되어 들어옴 */
	@JsonProperty("tms_result")
	private String tmsResult;

}
