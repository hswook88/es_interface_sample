package kr.co.shop.interfaces.module.payment.kakao.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 취소 요청
 * @FileName : KakaoPaymentCancel.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentCancel extends PaymentBase {

	private static final long serialVersionUID = -1348038638730594313L;

	/** O 가맹점 코드. 10자. */
	private String cid;

	/** X 가맹점 코드 인증키. 24자 숫자+영문 소문자 */
	private String cidSecret;

	/** O 결제 고유번호. 20자. */
	private String tid;

	/** O 취소 금액 */
	private int cancelAmount;

	/** O 취소 비과세 금액 */
	private int cancelTaxFreeAmount;

	/** X 취소 부가세 금액(안보낼 경우 (취소 금액 - 취소 비과세 금액)/11 : 소숫점이하 반올림) */
	private int cancelVatAmount;

	/** X 취소가능금액(결제취소 요청 금액 포함) */
	private int cancelAvailableAmount;

	/** X 해당 Request와 매핑해서 저장하고 싶은 값. 최대 200자 */
	private String payload;

}
