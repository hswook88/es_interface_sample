package kr.co.shop.interfaces.module.payment.nice.model;

import java.util.HashMap;

import lombok.Data;

/**
 * @Desc : 상품권 잔액이전 응답전문
 * @FileName : BalanceTransferResponse.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class BalanceTransferResponse extends CommonResponse {

	private static final long serialVersionUID = -3834112304506410354L;

	/**
	 * @Desc : 응답전문 객체화
	 * @Method Name : convertObject
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param param
	 * @return
	 */
	public BalanceTransferResponse convertObject(HashMap<String, Object> param) {
		String responseBody = param.get("responseBody").toString();
		param.remove("responseBody");

		BalanceTransferResponse result = new BalanceTransferResponse();
//		result = (BalanceTransferResponse) NiceGiftUtil.convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		return result;
	}
}
