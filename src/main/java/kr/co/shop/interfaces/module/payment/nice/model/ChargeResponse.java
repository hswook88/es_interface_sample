package kr.co.shop.interfaces.module.payment.nice.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class ChargeResponse extends CommonResponse {

	private static final long serialVersionUID = -387059311881187739L;
	/**
	 * 잔액
	 */
	private Long conBalanceAmount;
	/**
	 * 유효종료일
	 */
	private String couponEndDate;

	public ChargeResponse convertObject(HashMap<String, Object> param) {
		String responseBody = param.get("responseBody").toString();
		param.remove("responseBody");

		ChargeResponse result = new ChargeResponse();
//		result = (ChargeResponse) NiceGiftUtil.convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		result.setConBalanceAmount(Long.parseLong(responseBody.substring(0, 12)));
		result.setCouponEndDate(responseBody.substring(12, 20));

		return result;
	}
}
