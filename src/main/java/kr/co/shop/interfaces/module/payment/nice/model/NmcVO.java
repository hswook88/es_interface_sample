package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

@Data
public class NmcVO {

	/**
	 * 쿠폰번호
	 */
	private String couponNum;
	/**
	 * 원거래승인번호
	 */
	private String originAdmitNum;
	/**
	 * 응답코드 - 0000 만 성공
	 */
	private String resultCode;
	/**
	 * 응답메세지
	 */
	private String resultMsg;
	/**
	 * 통신응답코드(로그 추적용)
	 */
	private String responseNo;
	/**
	 * 쿠폰타입
	 */
	private String productType;
	/**
	 * 쿠폰잔액
	 */
	private String orderBalance;
	/**
	 * 교환제품가능수
	 */
	private String productCnt;
	/**
	 * 교환제품쿠폰번호1
	 */
	private String productBarcode1;
	/**
	 * 잔여횟수(횟수권 쿠폰인 경우)
	 */
	private String usableCnt;
	/**
	 * 인증번호 - 바코드 유형이 Two-Barcode 형태인 경우만 사용
	 */
	private String authPnm;
	/**
	 * 사용금액
	 */
	private String useAmount;
	/**
	 * 승인번호
	 */
	private String admitNum;
	/**
	 * 현금영수증 대상 금액
	 */
	private String cashReceiptAmount;
	/**
	 * 비과세 금액
	 */
	private String noTaxAmount;
	/**
	 * 발신자번호
	 */
	private String orderMobile;
	/**
	 * 수신자번호
	 */
	private String receiverMobile;
	/**
	 * 문자타입
	 */
	private String smsType;
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 내용
	 */
	private String content;
	/**
	 * 유효기간종료일자(yyyymmdd)
	 */
	private String exchangeEnddate;
	/**
	 * hash data String
	 */
	private String dataHash;
	/**
	 * tr_id
	 */
	private String trId;
}
