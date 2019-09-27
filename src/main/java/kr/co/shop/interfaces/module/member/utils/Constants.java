package kr.co.shop.interfaces.module.member.utils;

public interface Constants {

	/**
	 * 기본버퍼사이즈
	 */
	public static final int DEFAULT_BUFFER_SIZE = 4096;

	/**
	 * 기본 리트라이 횟수
	 */
	public static final int MAX_RETRY_COUNT = 3;

	/**
	 * 디폴트 문자셋
	 */
	public static final String DEFAULT_SITE_CHAR_SET = "UTF-8";

	/**
	 * 에러메시지키
	 */
	public final static String ERROR_MESSAGE = "errorMessages";

	/**
	 * 와스 문자셋
	 */
	public final static String WAS_CHARSET = "UTF-8";

	/**
	 * URL_SEPERATOR
	 */
	public final static String URL_SEPERATOR = "/";

	/**
	 * ROOT CATEGORY ID
	 */
	public final static String ROOT_CATEGORY_ID = "00";

	/**
	 * ABC마트 업체아이디
	 */
	public final static String ABCMART_VNDR_ID = "0001";

	/**
	 * ABC마트 업체계약아이디
	 */
	public final static String ABCMART_VNDR_CONTRACT_ID = "0072001";

	/**
	 * system아이디
	 */
	public final static String SYSTEM_ADMIN_ID = "system";

	/**
	 * 바로구매 매장픽업 여부
	 */
	public final static String ORDER_DIRECT_PICKUP_YN = "ORDER_DIRECT_PICKUP_YN";

	/**
	 * 주문세션아이디
	 */
	public final static String ORDER_SESSION_ID = "ORDER_SESSION_ID";

	/**
	 * 주문완료세션아이디
	 */
	public final static String ORDER_COMPLETE_NUM = "ORDER_COMPLETE_NUM";

	/**
	 * 비회원인증세션키
	 */
	public final static String NON_USER_JSESSIONID = "NON_USER_JSESSIONID";

	/**
	 * 2015.01.7 keyrang, 멤버십 포인트 내역
	 */
	public final static String MEMBERSHIP_POINT_LIST = "MEMBERSHIP_POINT_LIST";

	/**
	 * 사이트키
	 */
	public final static String SITE_KEY = "site.key";

	/**
	 * 사이트키의 프론트오피스 서버값
	 */
	public final static String SITE_FRONT_OFFICE = "F";

	/**
	 * 사이트키의 백오피스 서버값
	 */
	public final static String SITE_BACK_OFFICE = "B";

	/**
	 * 사이트키의 매장오피스 서버값
	 */
	public final static String SITE_STORE_OFFICE = "S";

	/**
	 * 사이트키의 프론트/백오피스/매장오피스 서버값(개발자용)
	 */
	public final static String SITE_ALL = "A";

	/**
	 * 실명 인증 후 전달되는 파라미터 이름
	 */
	public final static String Certified_Parameter_UserName = "userName";
	public final static String Certified_Parameter_SafeKey = "safeKey";
	public final static String Certified_Parameter_SafeKeyNumber = "safeKeyNumber";
	public final static String Certified_Parameter_resIdNo = "resIdNo";
	public final static String Certified_Parameter_NameYn = "nameYn";
	public final static String Certified_Parameter_IpinYn = "ipinYn";
	public final static String Certified_Parameter_UserGubunCode = "userGubunCode";
	public final static String Certified_Parameter_BirthDay = "birthDay";
	public final static String Certified_Parameter_SexCode = "sexCode";
	public final static String Certified_Parameter_IpinNum = "ipinNum";
	public final static String Certified_Parameter_HpCheckYn = "hpCheckYn";
	public final static String Certified_Parameter_OfflineMembershipYn = "offlineMembershipYn";
	public final static String Certified_Parameter_CI = "CI";
	public final static String Certified_Parameter_PhoneNo = "phoneNo";

	// 2015.10.02. 멤버십 가입전 비밀번호 확인 여부
	public final static String Membership_Join_CheckPasswordYn = "MembershipJoinCheckPasswordYn";

	/**
	 * vip 회원 가입 결제 금액
	 */
	public final static int VIP_USER_PAYMENT_AMOUNT = 20000;

	/**
	 * vip 회원 가입 결제 상품명
	 */
	public final static String VIP_USER_PAYMENT_PRODUCT_NAME = "VIP회원가입";

	/**
	 * 최상위 메뉴
	 */
	public final static String ROOT_ADMIN_MENU_ID = "00";

	/**
	 * 일반등급 아이디
	 */
	public final static String DEFAULT_GRADE_ID = "01";

	/**
	 * 클레임 신청 가능 기간
	 */
	public final static int CLAIM_REQUEST_DAYS = 14;

	/**
	 * 점검여부
	 */
	public static boolean check = false;

	/** [보안강화] 서블릿컨텍스트에 저장되는 조건 기간 맵 키 */
	public static String CONDITION_DATE_MAP_KEY = "CONDITION_DATE_MAP_KEY";

	/** [보안강화] 관리자 비밀번호 변경 기한 */
	public static String ADMIN_PSWD_RENEW_CONDITION = "ADMIN_PSWD_RENEW_CONDITION";

	/** [보안강화] 관리자의 장기 미사용 판단 기준 일수 */
	public static String LONG_UNUSED_ADMIN_CONDITION = "LONG_UNUSED_ADMIN_CONDITION";

	/** [보안강화] 사용자 비밀번호 변경 기한 */
	public static String USER_PSWD_RENEW_CONDITION = "USER_PSWD_RENEW_CONDITION";

	/** [보안강화] 사용자의 장기 미사용 판단 기준 월수 */
	public static String LONG_UNUSED_USER_CONDITION = "LONG_UNUSED_USER_CONDITION";

	/* 스마트폰 결제 주문정보 */
	public final static String PAYREQ_INFO = "lgUplus";
	/* 스마트폰 결제 주문 데이터 */
	public final static String PAYREQ_ORDER_INFO = "orderInfo";
	/* 스마트폰 클레임배송비 결제 데이터 */
	public final static String PAYREQ_CLAIM_PARAM_INFO = "claimParam";
	/* 스마트폰 AS배송비 결제 데이터 */
	public final static String PAYREQ_AS_PARAM_INFO = "asParam";
	/* 스마트폰 결제 주문 데이터(상품권) */
	public final static String PAYREQ_GIFTCARD_ORDER_INFO = "giftcardOrderInfo";

	/** ABC마트 디지털 상품권 */
	public final static String GIFTCARD_NAME = "ABC마트 디지털 상품권";
	/** 상품권주문세션아이디 **/
	public final static String GIFTCARD_ORDER_SESSION_ID = "GIFTCARD_ORDER_SESSION_ID";
	/** 상품권주문카트세션 **/
	public final static String GIFTCARD_ORDER_CART = "GIFTCARD_ORDER_CART";

	/**
	 * FRONT 기본 리스트 사이즈
	 */
	public final static int FRONT_DEFAULT_LIST_SIZE = 40;

	/**
	 * 풋스탬프 최종 지급일
	 */
	public final static String LAST_FOOTSTAMP_INSERT_DT = "LAST_FOOTSTAMP_INSERT_DT";

	/**
	 * 포인트 지급 구분(차감취소)
	 */
	public final static String POINT_GUBUN_USE = "use";

	/**
	 * 포인트 지급 구분(차감)
	 */
	public final static String POINT_GUBUN_CANCEL = "cancel";

	/**
	 * 모바일에서 pc버전 보기
	 */
	public final static String MOBILE_PCVERSION_VIEW = "MOBILE_PCVERSION_VIEW";

	/**
	 * 2011.11.23.웹취약점보안조치 : 해당 작업을 요청한 회원 ID
	 */
	public final static String REQUEST_USER_ID = "REQUEST_USER_ID";

}
