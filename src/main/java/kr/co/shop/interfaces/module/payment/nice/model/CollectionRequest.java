package kr.co.shop.interfaces.module.payment.nice.model;

import org.springframework.util.StringUtils;

import lombok.Data;

/**
 * @Desc : 회수/사용 요청 전문
 * @FileName : CollectionRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class CollectionRequest extends CommonRequest {

	private static final long serialVersionUID = -6061382977091915670L;
	/**
	 * 상세서비스코드
	 */
	private String exchangeCd;
	/**
	 * TRACK II
	 */
	private String track2;
	/**
	 * 금액
	 */
	private int amountVal;
	/**
	 * 스크레치번호 또는 OTP
	 */
	private String conPin;
	/**
	 * 브랜드전용사용
	 */
	private String rcvFlag;

	public CollectionRequest() {

	}

	public CollectionRequest(String serviceCode, String exchangeCd, String track2, int amountVal, String conPin) {
		// niceHeader
		this.setTotalSize(282);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode(serviceCode);
		this.setMerchantId("9510473002");
		this.setVanCode("01");
		this.setIssCode("A8");
		this.setMerchantGId("");
		// serviceHeader
		this.setGiftCardKind("03");
		this.setRequestMethodCode("@");
		this.setExchangeCode("1");
		this.setSaleDate("");
		this.setSaleTime("");
		this.setStoreCd("1001");
		this.setPosNo("0000000000");
		// serviceRequest
		this.setExchangeCd(exchangeCd);
		this.setTrack2(track2);
		this.setAmountVal(amountVal);
		this.setConPin(conPin);
		this.setRcvFlag("");
	}

	/**
	 * @Desc : 회수/사용 요청 전문 body 조합
	 * @Method Name : makeCollectionRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String makeCollectionRequestBody() {
		String collectionRequestBodyStr = String.format("%60s", " ");
		byte[] collectionRequestBody = new String(collectionRequestBodyStr).getBytes();

		String bodyStr = String.format("%1s", this.exchangeCd) + String.format("%-40s", this.track2)
				+ String.format("%012d", this.amountVal) + String.format("%-6s", this.conPin)
				+ String.format("%1s", this.rcvFlag);

		System.arraycopy(bodyStr.getBytes(), 0, collectionRequestBody, 0, bodyStr.length());

		return new String(collectionRequestBody);
	}

	/**
	 * @Desc : 회수/사용 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeCollectionRequestBody() + makeCommonCancelRequest(true);
	}

	/**
	 * @Desc : 회수/사용 취소 요청 전문 전체 조합
	 * @Method Name : getCancelRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getCancelRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeCollectionRequestBody() + makeCommonCancelRequest(false);
	}

	public static String chkEmpty(String obj) {
		String result;
		if (StringUtils.isEmpty(obj) || "".equals(obj) || obj == null) {
			result = " ";
		} else {
			result = obj;
		}
		return result;
	}
}
