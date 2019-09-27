package kr.co.shop.interfaces.module.payment.kakao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 인증 응답
 * @FileName : KakaoPaymentAuthorityReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentAuthorityReturn extends PaymentBase {

	private static final long serialVersionUID = -1578892815886968009L;

	/** 결제 고유 번호. 20자 */
	private String tid;

	/** 요청한 클라이언트가 모바일 앱일 경우 해당 url을 통해 카카오톡 결제페이지를 띄움 */
	@JsonProperty("next_redirect_app_url")
	private String nextRedirectAppUrl;

	/** 요청한 클라이언트가 모바일 웹일 경우 해당 url을 통해 카카오톡 결제페이지를 띄움 */
	@JsonProperty("next_redirect_mobile_url")
	private String nextRedirectMobileUrl;

	/** 요청한 클라이언트가 pc 웹일 경우 redirect. 카카오톡으로 TMS를 보내기 위한 사용자입력화면이으로 redirect */
	@JsonProperty("next_redirect_pc_url")
	private String nextRedirectPcUrl;

	/** 카카오페이 결제화면으로 이동하는 안드로이드 앱스킴 */
	@JsonProperty("android_app_scheme")
	private String androidAppScheme;

	/** 카카오페이 결제화면으로 이동하는 iOS 앱스킴 */
	@JsonProperty("ios_app_scheme")
	private String iosAppScheme;

	/** 결제 준비 요청 시간 Datetime */
	@JsonProperty("created_at")
	private String createdAt;

	/** 응답 실패시 code */
	private String code;

	/** 응답 실패시 message */
	private String msg;

	@Data
	public static class extras {

		@JsonProperty("method_result_code")
		private String methodResultCode;

		@JsonProperty("method_result_message")
		private String methodResultMessage;
	}

	/** tms_result ?? 카카오페이와 확인 필요 spec 상 없는 내용인데 추가 되어 들어옴 */
	@JsonProperty("tms_result")
	private String tmsResult;

}
