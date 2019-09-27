package kr.co.shop.interfaces.module.payment.nice.common;

public class NmcConstants {
	public static final String NMC_LIB_VERSION = "NMCLITEV1.0B190325"; // 버전

	public static final int SERVER_TIMEOUT_MILL = 25000;

	public static final String NMC_SERVER_DOMAIN = "https://api.nmcs.co.kr"; // 운영 서버
	public static final String TEST_NMC_SERVER_DOMAIN = "https://devapi.nmcs.co.kr"; // 개발 서버
	public static final String NMC_EXCHANGE_URI = "/external/exchangeBarcode.do"; // 쿠폰승인
	public static final String NMC_EXCHANGE_CANCEL_URI = "/external/exchangeCancel.do"; // 쿠폰승인취소
	public static final String NMC_EXCHANGE_NETCANCEL_URI = "/external/exchangeBarcodeNetCancel.do"; // 쿠폰승인망취소
	public static final String NMC_EXCHANGE_QUERY_URI = "/external/exchangeQuery.do"; // 쿠폰승인조회

	public static final String NMC_ORDER_SEND_URI = "/external/orderSend.do"; // 쿠폰판매
	public static final String NMC_ORDER_CANCEL_URI = "/external/orderCancel.do"; // 쿠폰판매취소
	public static final String NMC_ORDER_RESEND_URI = "/external/orderResend.do"; // 쿠폰재전송
	public static final String NMC_ORDER_DETAIL_URI = "/external/orderDetail.do"; // 판매조회
	public static final String NMC_CHANGE_RECEIVER_MOBILE_URI = "/external/changeReceivermobile.do"; // 수신자정정

	public static final String API_ERR_4001_CODE = "4001";
	public static final String API_ERR_4001_MSG = "요청전문 생성오류";

	public static final String API_ERR_4002_CODE = "4002";
	public static final String API_ERR_4002_MSG = "암호화 생성오류";

	public static final String API_ERR_4003_CODE = "4003";
	public static final String API_ERR_4003_MSG = "암호화 생성오류";

	public static final String API_ERR_4004_CODE = "4004";
	public static final String API_ERR_4004_MSG = "검증데이터 불일치";

	public static final String API_ERR_5001_CODE = "5001";
	public static final String API_ERR_5001_MSG = "서버 연결오류(Timeout)";

	public static final String API_ERR_5002_CODE = "5002";
	public static final String API_ERR_5002_MSG = "서버 수신오류(Server Internal Error)";

	public static final String API_ERR_5003_CODE = "5003";
	public static final String API_ERR_5003_MSG = "서버 수신오류";

	public static final String API_ERR_5004_CODE = "5004";
	public static final String API_ERR_5004_MSG = "서버 통신오류";

	public static final String API_ERR_6001_CODE = "6001";
	public static final String API_ERR_6001_MSG = "응답데이터 파싱오류";
}
