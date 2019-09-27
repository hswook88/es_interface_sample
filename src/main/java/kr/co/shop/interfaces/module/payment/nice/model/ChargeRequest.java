package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

/**
 * @Desc : 충전하기 전문
 * @FileName : ChargeRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class ChargeRequest extends CommonRequest {

	private static final long serialVersionUID = -8593673549461477638L;
	/**
	 * 선불카드번호
	 */
	private String cardNo;
	/**
	 * 충전금액
	 */
	private long amount;
	/**
	 * 판매형태
	 */
	private String saleKind;
	/**
	 * 상품종류구분
	 */
	private String giftKind;
	/**
	 * 결제수단
	 */
	private String payCode;
	/**
	 * 사용구분
	 */
	private String exchangeCd;

	public ChargeRequest() {

	}

	public ChargeRequest(String cardNo, long amount, String saleKind, String giftKind, String payCode,
			String exchangeCd) {
		// niceHeader
		this.setTotalSize(256);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode("A3");
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
		this.setCardNo(cardNo);
		this.setAmount(amount);
		this.setSaleKind(saleKind);
		this.setGiftKind(giftKind);
		this.setPayCode(payCode);
		this.setExchangeCd(exchangeCd);
	}

	/**
	 * @Desc : 충전하기 요청 전문 body 조합
	 * @Method Name : makeChargeRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String makeChargeRequestBody() {
		String chargeRequestBodyStr = String.format("%34s", " ");
		byte[] chargeRequestBody = new String(chargeRequestBodyStr).getBytes();

		String bodyStr = String.format("%-16s", this.cardNo) + String.format("%012d", this.amount)
				+ String.format("%-2s", this.saleKind) + String.format("%1s", this.giftKind)
				+ String.format("%-2s", this.payCode) + String.format("%1s", this.exchangeCd);

		System.arraycopy(bodyStr.getBytes(), 0, chargeRequestBody, 0, bodyStr.length());

		return new String(chargeRequestBody);
	}

	/**
	 * @Desc : 충전하기 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeChargeRequestBody() + makeCommonCancelRequest(true);
	}

	/**
	 * @Desc : 충전하기 취소 요청 전문 전체 조합
	 * @Method Name : getCancelRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getCancelRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeChargeRequestBody() + makeCommonCancelRequest(false);
	}
}
