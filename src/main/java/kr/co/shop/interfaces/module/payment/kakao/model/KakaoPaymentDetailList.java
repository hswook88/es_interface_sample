package kr.co.shop.interfaces.module.payment.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoPaymentDetailList {

	/** Request 고유 번호 */
	private String aid;

	/** 거래시간 */
	@JsonProperty("approved_at")
	private String approved_at;

	/** 결제/취소 총액 */
	private String amount;

	/** 결제/취소 포인트 금액 */
	@JsonProperty("point_amount")
	private int point_amount;

	/** 할인 금액 */
	@JsonProperty("discount_amount")
	private String discount_amount;

	/** 결제 타입. PAYMENT(결제), CANCEL(결제취소), ISSUED_SID(SID 발급) 중 하나 */
	@JsonProperty("payment_action_type")
	private String payment_action_type;

	/** Request로 전달한 값 */
	private String payload;
}
