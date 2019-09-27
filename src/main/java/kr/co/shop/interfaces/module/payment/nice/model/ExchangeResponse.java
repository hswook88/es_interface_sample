package kr.co.shop.interfaces.module.payment.nice.model;

import java.util.HashMap;

import lombok.Data;

/**
 * @Desc : 상품권 교환 응답 전문
 * @FileName : ExchangeResponse.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class ExchangeResponse extends CommonResponse {

	private static final long serialVersionUID = 3650613490511815913L;
	/**
	 * 선불카드번호
	 */
	private String conCardNo;
	/**
	 * 유효종료일
	 */
	private String couponEndDate;
	/**
	 * 스크래치번호 (추가인증번호)
	 */
	private String conPin;

	/**
	 * @Desc : 상품권 교환 응답 전문 객체화
	 * @Method Name : convertObject
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param param
	 * @return
	 */
	public ExchangeResponse convertObject(HashMap<String, Object> param) {
		String responseBody = param.get("responseBody").toString();
		param.remove("responseBody");

		ExchangeResponse result = new ExchangeResponse();
//		result = (ExchangeResponse) NiceGiftUtil.convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		result.setConCardNo(responseBody.substring(0, 16));
		result.setCouponEndDate(responseBody.substring(16, 24));
		result.setConPin(responseBody.substring(24));

		return result;
	}
}
