package kr.co.shop.interfaces.module.payment.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 승인 응답
 * @FileName : KakaoPaymentApprovalReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentApprovalReturn extends PaymentBase {

	private static final long serialVersionUID = -8228741014646286333L;

	/** Request 고유 번호 */
	private String aid;

	/** 결제 고유 번호 */
	private String tid;

	/** 가맹점 코드 */
	private String cid;

	/** "subscription id. 정기(배치)결제 CID로 결제요청한 경우 발급" */
	private String sid;

	/** 가맹점 주문번호 */
	@JsonProperty("partner_order_id")
	private String partnerOrderId;

	/** 가맹점 회원 id */
	@JsonProperty("partner_user_id")
	private String partnerUserId;

	/** 결제 수단. CARD, MONEY 중 하나 */
	@JsonProperty("payment_method_type")
	private String paymentMethodType;

	/** 결제 금액 정보 JSON Object */
	private KakaoPayAmount amount;

	/** 결제 금액 정보 */
	@Data
	public static class KakaoPayAmount {

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

	/** 결제 상세 정보(결제수단이 카드일 경우만 포함) JSON Object */
	private KakaoCardInfo card_info;

	@Data
	public static class KakaoCardInfo {

		/** 매입카드사 한글명 */
		@JsonProperty("purchase_corp")
		private String purchaseCorp;

		/** 매입카드사 코드 */
		@JsonProperty("purchase_corp_code")
		private String purchaseCorpCode;

		/** 카드발급사 한글명 */
		@JsonProperty("issuer_corp")
		private String issuerCorp;

		/** 카드발급사 코드 */
		@JsonProperty("issuer_corp_code")
		private String issuerCorpCode;

		/** 카드 BIN */
		@JsonProperty("bin")
		private String bin;

		/** 카드타입 */
		@JsonProperty("card_type")
		private String cardType;

		/** 할부개월수 */
		@JsonProperty("install_month")
		private String installMonth;

		/** 카드사 승인번호 */
		@JsonProperty("approved_id")
		private String approvedId;

		/** 카드사 가맹점번호 */
		@JsonProperty("card_mid")
		private String cardMid;

		/** 무이자할부 여부(Y/N) */
		@JsonProperty("interest_free_install")
		private String interestFreeInstall;

		/** 카드 상품 코드 */
		@JsonProperty("card_item_code")
		private String cardItemCode;

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

	/** Request로 전달한 값 */
	private String payload;

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
