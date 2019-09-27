package kr.co.shop.interfaces.module.payment.naver.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 승인 MODEL R : 필수, P : 부분필수 , C : 선택
 * @FileName : NaverPaymentApproval.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentApproval extends PaymentBase {

	private static final long serialVersionUID = -229378282914780893L;

	/** 비용 */
	private int amount;

	/** R 네이버페이 결제번호 */
	private String paymentId;
}
