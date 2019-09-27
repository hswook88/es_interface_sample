package kr.co.shop.interfaces.module.payment.nice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 나이스 전문 통신 유틸
 * @FileName : NiceGiftUtil.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Slf4j
public class NiceGiftUtil {

	/**
	 * @Desc : 문자열이 비어있으면 공백으로 대체
	 * @Method Name : chkEmpty
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param obj
	 * @return
	 */
	public static String chkEmpty(String obj) {
		String result;
		if (StringUtils.isEmpty(obj) || "".equals(obj) || obj == null) {
			result = " ";
		} else {
			result = obj;
		}
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

	/**
	 * @Desc : 응답코드를 기반으로 응답 메세지 반환
	 * @Method Name : convertCodeToMsg
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @param code
	 * @return
	 */
	public static String convertCodeToMsg(String code) {
		log.debug("##### code: {}", code);
		String result = "";

		if ("0000".equals(code)) {
			result = "정상";
		} else if ("0314".equals(code)) {
			result = "가맹점 상이";
		} else if ("7573".equals(code)) {
			result = "거래정지 가맹점";
		} else if ("7575".equals(code)) {
			result = "미등록 가맹점";
		} else if ("7576".equals(code)) {
			result = "해지 가맹점";
		} else if ("8030".equals(code)) {
			result = "바코드번호 확인요망";
		} else if ("8037".equals(code)) {
			result = "카드번호 확인요망";
		} else if ("8041".equals(code)) {
			result = "인증번호(스크레치)번호 오류";
		} else if ("8314".equals(code)) {
			result = "유효기간 경과";
		} else if ("8323".equals(code)) {
			result = "잔액부족";
		} else if ("8373".equals(code)) {
			result = "콜센터 전화요망";
		} else if ("8374".equals(code)) {
			result = "통신장애 재시도 요망";
		} else if ("8376".equals(code)) {
			result = "카드사 전화요망";
		} else if ("8448".equals(code)) {
			result = "유효하지 않은 카드";
		} else if ("8449".equals(code)) {
			result = "활성화되지 않은 카드";
		} else if ("8501".equals(code)) {
			result = "전문오류";
		} else if ("8502".equals(code)) {
			result = "상품권 권종 오류";
		} else if ("8504".equals(code)) {
			result = "원거래금액 입력오류";
		} else if ("8505".equals(code)) {
			result = "취소가능일수 경과";
		} else if ("8506".equals(code)) {
			result = "미취소 거래";
		} else if ("8507".equals(code)) {
			result = "기취소 거래";
		} else if ("8508".equals(code)) {
			result = "KEYIN 불가 거래";
		} else if ("8511".equals(code)) {
			result = "거래금액 오류";
		} else if ("8515".equals(code)) {
			result = "사용정지 카드";
		} else if ("8521".equals(code)) {
			result = "한도 초과";
		} else if ("8536".equals(code)) {
			result = "입고점과 판매점 상이";
		} else if ("8537".equals(code)) {
			result = "사용된카드여부 확인요망";
		} else if ("8538".equals(code)) {
			result = "판매수량상이";
		} else if ("8539".equals(code)) {
			result = "판매자정보 없음";
		} else if ("8540".equals(code)) {
			result = "고객번호입력요망";
		} else if ("8541".equals(code)) {
			result = "보유수량한도초과";
		} else if ("8555".equals(code)) {
			result = "충전회수초과";
		} else if ("8666".equals(code)) {
			result = "원거래 없음";
		} else if ("8667".equals(code)) {
			result = "기 활성화된 카드";
		} else if ("8668".equals(code)) {
			result = "영수증번호 상이";
		} else if ("8669".equals(code)) {
			result = "POS번호 상이";
		} else if ("8804".equals(code)) {
			result = "폐기된 카드"; // 제공되지 않아서 추가함
		} else if ("8809".equals(code)) {
			result = "부정확한 카드번호";
		} else if ("0180".equals(code)) {
			result = "동일내용 기등록";
		} else if ("8814".equals(code)) {
			result = "미사용된 상품권";
		} else if ("8815".equals(code)) {
			result = "잔액 환불 불가";
		} else if ("8818".equals(code)) {
			result = "기명해지";
		}

		return result;
	}

	public static String isNullZeroFill(String param) {
		if ("".equals(param.replaceAll(" ", ""))) {
			return "000000000000";
		} else {
			return param;
		}
	}

}
