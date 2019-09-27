package kr.co.shop.interfaces.module.payment.nice.model;

import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 나이스 기프트 카드 잔액조회 요청 전문
 * @FileName : BalanceRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
@Slf4j
public class BalanceRequest extends CommonRequest {

	private static final long serialVersionUID = 157501738184580081L;
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
	private boolean history = false;

	public BalanceRequest() {

	}

	public BalanceRequest(String companyUserNo, String cardNo, int amount) {
		// niceHeader
		this.setTotalSize(250);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode("S1");
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
		this.setCompanyUserNo(companyUserNo);
		this.setCardNo(cardNo);
		this.setAmount(amount);
	}

	public BalanceRequest(String companyUserNo, String cardNo, int amount, String cardImgCode) {
		// niceHeader
		this.setTotalSize(250);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode("S1");
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
		this.setCardImgCode(cardImgCode);
		// serviceRequest
		this.setCompanyUserNo(companyUserNo);
		this.setCardNo(cardNo);
		this.setAmount(amount);
	}

	/**
	 * @Desc : 본문 조합
	 * @Method Name : makeBalanceRequestBody
	 * @Date : 2019. 4. 11.
	 * @Author : YSW
	 * @return
	 */
	public String makeBalanceRequestBody() {
		String balanceRequestBodyStr = String.format("%48s", " ");
		byte[] balanceRequestBody = new String(balanceRequestBodyStr).getBytes();

		String bodyStr = String.format("%-16s", chkEmpty(this.companyUserNo))
				+ String.format("%-16s", chkEmpty(this.cardNo)) + String.format("%012d", this.amount);

		System.arraycopy(bodyStr.getBytes(), 0, balanceRequestBody, 0, bodyStr.length());

		log.debug("##### bodyStr.length: {}", bodyStr.length());
		log.debug("##### requestBody length: {}", new String(balanceRequestBody).length());

		return new String(balanceRequestBody);
	}

	/**
	 * @Desc : 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 11.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeBalanceRequestBody();
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
