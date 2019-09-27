package kr.co.shop.interfaces.module.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @Desc : 네이버페이 Configration Class, app-configration.xml에서 설정.
 * @FileName : NaverPaymentConfig.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : ljyoung@3top.co.kr
 */
@Configuration
@Data
public class NaverPaymentConfig {

	/** 네이버페이 API 주소 */
	@Value("${naver.api.url}")
	private String apiUrl;

	/** 네이버페이 API 파트너 코드 */
	@Value("${naver.api.partnerCode}")
	private String partnerCode;

	/** 네이버페이 API CLIENT ID */
	@Value("${naver.api.clientId}")
	private String clientId;

	/** 네이버페이 API CLIENT SECRET */
	@Value("${naver.api.clientSecret}")
	private String clientSecret;

	/** 네이버페이 API PC 도메인 */
	@Value("${naver.api.pcDomain}")
	private String pcDomain;

	/** 네이버페이 API 모바일 도메인 */
	@Value("${naver.api.mobileDomain}")
	private String mobileDomain;

	/** 결제 승인 요청 */
	@Value("${naver.api.url.approval}")
	private String approval;

	/** 결제 취소​ */
	@Value("${naver.api.url.cancel}")
	private String cancel;

	/** 결제 내역 조회​ */
	@Value("${naver.api.url.list}")
	private String list;

	/** 거래완료​ */
	@Value("${naver.api.url.confirm}")
	private String confirm;

	/** 결제 예약 */
	@Value("${naver.api.url.reserve}")
	private String reserve;

	/** 현금 영수증 발행 대상 금액 조회​ */
	@Value("${naver.api.url.view.cashAmount}")
	private String viewCashAmount;

	/** 신용카드 매출 전표 조회​ */
	@Value("${naver.api.url.view.cardCheck}")
	private String viewCardCheck;
}
