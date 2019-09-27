package kr.co.shop.interfaces.module.payment.naver.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : 네이버페이 승인 MODEL R : 필수, P : 부분필수 , C : 선택
 * @FileName : NaverPaymentCancel.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Data
public class NaverPaymentCancel extends PaymentBase {

	private static final long serialVersionUID = 3879082613795835511L;

	/** 비용 */
	private int amount;

	/** R 네이버페이 결제번호 */
	private String paymentId;

	/** C ABC 마트 결제 번호 */
	private String merchantPayKey;

	/** R 취소 요청 금액 */
	private Double cancelAmount;

	/** R 취소 사유 */
	private String cancelReason;

	/**
	 * R 취소 요청자(1: 구매자, 2: 가맹점 관리자) 구분이 애매한 경우 가맹점 관리자로 입력합니다
	 */
	private String cancelRequester;

	/** P 과세 대상 금액 */
	private Double taxScopeAmount;

	/** P 면세 대상 금액 */
	private Double taxExScopeAmount;

	/**
	 * C 가맹점의 남은 금액과 네이버페이의 남은 금액이 일치하는지 체크하는 기능을 수행할지 여부 1: 수행, 0: 미수행
	 */
	private byte doCompareRest;

	/**
	 * C 이번 취소가 수행되고 난 후에 남을 가맹점의 예상 금액 옵션 파라미터인 doCompareRest값이 1일 때에만 동작합니다
	 */
	private Double expectedRestAmount;

}
