package kr.co.shop.interfaces.module.payment.kakao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * 
 * @Desc : 카카오 페이 주문내역 조회 응답
 * @FileName : KakaoPaymentOrderListReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : Administrator
 */
@Data
public class KakaoPaymentOrderListReturn extends PaymentBase {

	private static final long serialVersionUID = -4799605991088733683L;

	/** 페이지 정보 */
	private pageInfo page;

	@Data
	private static class pageInfo {

		/** 현재 페이지 */
		@JsonProperty("current_page")
		private int currentPage;

		/** 총 페이지 */
		@JsonProperty("total_pages")
		private int totalPages;

		/** 현재 페이지 데이터 개수 */
		@JsonProperty("current_size")
		private int currentSize;

		/** 총 데이터 개수 */
		@JsonProperty("total_size")
		private int totalSize;

		/** 첫번째 페이지 여부 */
		private boolean first;

		/** 마지막 페이지 여부 */
		private boolean last;

	}

	/** 결제를 요청일. yyyy-MM-dd 형식 */
	@JsonProperty("payment_request_date")
	private String paymentRequestDate;

	/** 가맹점 코드 */
	private String cid;

	/** 결제요청한 주문건들의 List */
	private List<KakaoPaymentOrderDataList> orders;

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
