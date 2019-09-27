package kr.co.shop.interfaces.module.payment.naver.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 거래 완료 MODEL R : 필수, P : 부분필수 , C : 선택
 * @FileName : NaverPaymentConfirm.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentConfirm extends PaymentBase {

	private static final long serialVersionUID = -4001972561426853441L;

	/** R 네이버페이 결제번호 */
	private String paymentId;

	/** R 요청자(1: 구매자, 2: 가맹점 관리자)  */
	private String requester;
}
