package kr.co.shop.interfaces.module.payment.kakao.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 인증 요청
 * @FileName : KakaoPaymentAuthority.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentAuthority extends PaymentBase {

	private static final long serialVersionUID = 4453275364664638313L;

	/** O 가맹점 코드. 10자. */
	private String cid;

	/** X 가맹점 코드 인증키. 24자 숫자+영문 소문자 */
	private String cidSecret;

	/** O 가맹점 주문번호. 최대 100자 */
	private String partnerOrderId;

	/** O 가맹점 회원 id. 최대 100자 */
	private String partnerUserId;

	/** O 상품명. 최대 100자 */
	private String itemName;

	/** X 상품코드. 최대 100자 */
	private String itemCode;

	/** O 상품 수량 */
	private int quantity;

	/** O 상품 총액 */
	private int totalAmount;

	/** O 상품 비과세 금액 */
	private int taxFreeAmount;

	/** X 상품 부가세 금액(안보낼 경우 (상품총액 - 상품 비과세 금액)/11 : 소숫점이하 반올림) */
	private int vatAmount;

	/** O 결제 성공시 redirect url. 최대 255자 */
	private String approvalUrl;

	/** O 결제 취소시 redirect url. 최대 255자 */
	private String cancelUrl;

	/** O 결제 실패시 redirect url. 최대 255자 */
	private String failUrl;

	/** X 카드사 제한 목록(없을 경우 전체) JSON Array 형태 */
	private String availableCards;

	/** X 결제 수단 제한(없을 경우 전체) CARD, MONEY 중 하나 */
	private String paymentMethodType;

	/** X 카드할부개월수. 0~12(개월) 사이의 값 */
	private String installMonth;

	/**
	 * X 결제화면에 보여주고 싶은 custom message. 사전협의가 필요한 값 JSON Map 형태로 key, value 모두 String
	 */
	private String customJson;

}
