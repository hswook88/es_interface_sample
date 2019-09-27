package kr.co.shop.interfaces.module.payment.base;

/**
 * @Desc : 결제모듈 상수
 * @FileName : PaymentConst.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
public final class PaymentConst {

	// 결제방법
	/** KCP */
	public static final String PAYMENT_METHOD_TYPE_KCP = "KCP";
	/** NAVER */
	public static final String PAYMENT_METHOD_TYPE_NAVER = "NAVER";
	/** KAKAO */
	public static final String PAYMENT_METHOD_TYPE_KAKAO = "KAKAO";

	// 처리결과 메세지
	/** 결제승인 성공 */
	public static final String PAYMENT_APPROVAL_SUCCESS_MESSAGE = "결제승인 성공";
	/** 결제승인 실패 */
	public static final String PAYMENT_APPROVAL_FAIL_MESSAGE = "결제승인 실패";
	/** 결제취소 성공 */
	public static final String PAYMENT_CANCEL_SUCCESS_MESSAGE = "결제취소 성공";
	/** 결제취소 실패 */
	public static final String PAYMENT_CANCEL_FAIL_MESSAGE = "결제취소 실패";
	/** 환불 성공 */
	public static final String PAYMENT_REFUND_SUCCESS_MESSAGE = "환불 성공";
	/** 환불 실패 */
	public static final String PAYMENT_REFUND_FAIL_MESSAGE = "환불 실패";

	/** 결제 인증 성공 */
	public static final String PAYMENT_AUTHORITY_SUCCESS_MESSAGE = "결제인증 성공";
	/** 결제 인증 실패 */
	public static final String PAYMENT_AUTHORITY_FAIL_MESSAGE = "결제인증 실패";

	// Y,N
	/** Y */
	public static final String YN_Y = "Y";
	/** N */
	public static final String YN_N = "N";

	// URLConnection
	/** POST */
	public static final String POST = "POST";
	/** GET */
	public static final String GET = "GET";
	/** URLConnection READ TIME OUT(60초) */
	public static final int readTimeOut = 600000;
	/** application/x-www-form-urlencoded */
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	/** application/json */
	public static final String CONTENT_TYPE_JSON = "application/json";
}
