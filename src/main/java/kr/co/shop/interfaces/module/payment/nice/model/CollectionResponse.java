package kr.co.shop.interfaces.module.payment.nice.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import lombok.Data;

/**
 * @Desc : 회수/사용 응답 전문
 * @FileName : CollectionResponse.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class CollectionResponse extends CommonResponse {

	private static final long serialVersionUID = -5639284905789098602L;
	/**
	 * 잔액
	 */
	private long conBalanceAmount;
	/**
	 * 환불가능금액
	 */
	private String returnAmount;
	/**
	 * 유효종료일
	 */
	private String couponEndDate;
	/**
	 * OTP
	 */
	private String conPin;

	/**
	 * @Desc : 회수/사용 응답전문 객체화
	 * @Method Name : convertObject
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param param
	 * @return
	 */
	public CollectionResponse convertObject(HashMap<String, Object> param) {
		String responseBody = param.get("responseBody").toString();
		param.remove("responseBody");

		CollectionResponse result = new CollectionResponse();
//		result = (CollectionResponse) NiceGiftUtil.convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		result.setConBalanceAmount(Long.parseLong(responseBody.substring(0, 12)));
		result.setReturnAmount(responseBody.substring(12, 24));
		result.setCouponEndDate(responseBody.substring(24, 32));
		result.setConPin(responseBody.substring(32));

		return result;
	}

	/**
	 * @Desc : Map을 Object로 변환
	 * @Method Name : convertMapToObject
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param param
	 * @param obj
	 * @return
	 */
	public static Object convertMapToObject(HashMap<String, Object> param, Object obj) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = param.keySet().iterator();

		int j = 0;
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methodString.equals(methods[i].getName())) {
					try {
						methods[i].invoke(obj, param.get(keyAttribute));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;

	}
}
