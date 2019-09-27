package kr.co.shop.interfaces.module.payment.kakao.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 주문 상세 조회 요청
 * @FileName : KakaoPaymentOrder.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentOrder extends PaymentBase {

	private static final long serialVersionUID = -6368461712455842871L;

	/** O 가맹점 코드. 10자. */
	private String cid;

	/** X 가맹점 코드 인증키. 24자 숫자+영문 소문자 */
	private String cidSecret;

	/** O 결제 고유번호. 결제준비 API의 응답에서 얻을 수 있음 */
	private String tid;

}
