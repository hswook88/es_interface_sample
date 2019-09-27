package kr.co.shop.interfaces.module.payment.kakao.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 주문 내역 조회 요청
 * @FileName : KakaoPaymentOrderList.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentOrderList extends PaymentBase {

	private static final long serialVersionUID = 2370268354228256845L;

	/** O 가맹점 코드. 10자. */
	private String cid;
	/** X 가맹점 코드 인증키. 24자 숫자+영문 소문자 */

	private String cidSecret;

	/** O 결제를 요청해서 주문을 생성한 날짜. yyyyMMdd 형식 */
	private String paymentRequestDate;

	/** X 결제 고유번호. 20자 */
	private String tid;

	/** X 가맹점 주문번호. 결제준비 API에서 요청한 값과 일치해야 함 */
	private String partnerOrderId;

	/** X 가맹점 회원 id. 결제준비 API에서 요청한 값과 일치해야 함 */
	private String partnerUserId;

	/** X 보여줄 페이지(default 1), 최소값 1 */
	private String page;

}
