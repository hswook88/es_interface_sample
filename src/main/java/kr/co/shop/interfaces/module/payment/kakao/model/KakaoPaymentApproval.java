package kr.co.shop.interfaces.module.payment.kakao.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 승인 요청
 * @FileName : KakaoPaymentApproval.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentApproval extends PaymentBase {

	private static final long serialVersionUID = -352284295538063069L;

	/** O 가맹점 코드. 10자. */
	private String cid;

	/** X 가맹점 코드 인증키. 24자 숫자+영문 소문자 */
	private String cidSecret;

	/** O 결제 고유번호. 결제준비 API의 응답에서 얻을 수 있음 */
	private String tid;

	/** O 가맹점 주문번호. 결제준비 API에서 요청한 값과 일치해야 함 */
	private String partnerOrderId;

	/** O 가맹점 회원 id. 결제준비 API에서 요청한 값과 일치해야 함 */
	private String partnerUserId;

	/** O 결제승인 요청을 인증하는 토큰. 사용자가 결제수단 선택 완료시 approval_ */
	private String pgToken;

	/** X 해당 Request와 매핑해서 저장하고 싶은 값. 최대 200자 */
	private String payload;

	/** X 상품총액. 결제준비 API에서 요청한 total_amount 값과 일치해야 함 */
	private int totalAmount;
}
