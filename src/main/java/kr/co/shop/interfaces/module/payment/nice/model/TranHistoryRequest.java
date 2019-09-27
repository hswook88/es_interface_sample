package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

/**
 * @Desc : 거래내역조회 요청전문
 * @FileName : TranHistoryRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : nalpari
 */
@Data
public class TranHistoryRequest extends CommonRequest {

	private static final long serialVersionUID = -2296849014526751743L;
	/**
	 * 회원번호
	 */
	private String companyUserNo;
	/**
	 * 선불카드번호
	 */
	private String cardNo;
	/**
	 * 거래금액
	 */
	private int amount;
	/**
	 * 조회요청페이지
	 */
	private String page;

	public TranHistoryRequest() {

	}

	public TranHistoryRequest(String companyUserNo, String cardNo, int amount, String page) {
		// niceHeader
		this.setTotalSize(251);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode("S4");
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
		this.companyUserNo = companyUserNo;
		this.cardNo = cardNo;
		this.amount = amount;
		this.page = page;
	}

	/**
	 * @Desc : 거래내역조회 body 조합
	 * @Method Name : makeTranHistoryRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : nalpari
	 * @return
	 */
	public String makeTranHistoryRequestBody() {
		String tranHistoryRequestBodyStr = String.format("%45s", " ");
		byte[] tranHistoryRequestBody = new String(tranHistoryRequestBodyStr).getBytes();

		String bodyStr = String.format("%-16s", this.companyUserNo) + String.format("%-16s", this.cardNo)
				+ String.format("%012d", this.amount) + String.format("%1s", this.page);

		System.arraycopy(bodyStr.getBytes(), 0, tranHistoryRequestBody, 0, bodyStr.length());

		return new String(tranHistoryRequestBody);
	}

	/**
	 * @Desc : 거래내역조회 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : nalpari
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeTranHistoryRequestBody() + makeCommonCancelRequest(true);
	}
}
