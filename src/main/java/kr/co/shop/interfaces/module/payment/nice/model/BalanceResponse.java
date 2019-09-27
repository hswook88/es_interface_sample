package kr.co.shop.interfaces.module.payment.nice.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class BalanceResponse extends CommonResponse {

	/**
	 * 잔액
	 */
	public String conBalanceAmount;
	/**
	 * 최종충전후잔액
	 */
	public String lastBalanceAmount;
	/**
	 * 환불가능금액
	 */
	public String returnBalanceAmount;
	/**
	 * 회원번호
	 */
	public String returnCompanyUserNo;
	/**
	 * 유효기간
	 */
	public String validity;
	/**
	 * 전체잔액
	 */
	public Long totalBalanceAmount = 0L;
	/**
	 * 공통브랜드잔액
	 */
	public Long commonBrandBalanceAmount = 0L;
	/**
	 * 요청발생사잔액
	 */
	public Long reqGeneratorBalanceAmount = 0L;
	/**
	 * 카드제작 이미지코드
	 */
	private String cardImgCode = "";

	/**
	 * @Desc : 응답전문 객체화
	 * @Method Name : convertObject
	 * @Date : 2019. 4. 11.
	 * @Author : YSW
	 * @param param
	 * @return
	 */
	public BalanceResponse convertObject(HashMap<String, Object> param) {
		String responseBody = param.get("responseBody").toString();
		log.debug("##### responseBody: {}", responseBody);
		param.remove("responseBody");

		BalanceResponse result = new BalanceResponse();
//		result = (BalanceResponse) convertMapToObject(param, result);
		result.setApprovalNo(param.get("approvalNo").toString());
		result.setApprovalDate(param.get("approvalDate").toString());
		result.setApprovalTime(param.get("approvalTime").toString());
		result.setResponseCode(param.get("responseCode").toString());
		result.setResponseMessage(param.get("responseMessage").toString());

		result.setConBalanceAmount(responseBody.substring(0, 12));
		result.setLastBalanceAmount(responseBody.substring(12, 24));
		result.setReturnBalanceAmount(responseBody.substring(24, 36));
		result.setReturnCompanyUserNo(responseBody.substring(36, 52));
		result.setValidity(responseBody.substring(52, 60)); // 60보다 클 수 있음

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
