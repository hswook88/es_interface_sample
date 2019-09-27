package kr.co.shop.interfaces.module.payment.nice.model;

import kr.co.shop.interfaces.module.payment.nice.NiceGiftUtil;
import lombok.Data;

/**
 * @Desc : 상품권 잔액이전 요청 전문
 * @FileName : BalanceTransferRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class BalanceTransferRequest extends CommonRequest {

	private static final long serialVersionUID = -2515497221034766624L;
	/**
	 * 교환금액
	 */
	private Long amount;
	/**
	 * 선불카드번호(기존)
	 */
	private String cardNo;
	/**
	 * 선불카드번호(변경)
	 */
	private String toCardNo;
	/**
	 * 판매형태
	 */
	private String saleKind;
	/**
	 * 상품종류구분
	 */
	private String giftKind;
	/**
	 * 사용구분
	 */
	private String exchangeCd;
	/**
	 * 발급사코드
	 */
	private String issCompanyCd;
	/**
	 * 수취인전화번호
	 */
	private String recvUserCallNo;
	/**
	 * 전달메세지
	 */
	private String sendMessage;
	/**
	 * 배경코드
	 */
	private String imgCode;
	/**
	 * 환불대상자
	 */
	private String cancelOrdCode;
	/**
	 * NICE발송여부
	 */
	private String niceTrYn;
	/**
	 * 발행사
	 */
	private String issCompanyCode;
	/**
	 * 유효기간
	 */
	private int issDay;
	/**
	 * 결제수단
	 */
	private String payCode;
	/**
	 * 인증번호(OTP, 스크래치)
	 */
	private String pin;
	/**
	 * 회원번호
	 */
	private String companyUserNo;
	/**
	 * 기명전환(옵션)flag
	 */
	private String signYn;

	public BalanceTransferRequest() {

	}

	public BalanceTransferRequest(String serviceCode, long amount, String cardNo, String toCardNo, String saleKind,
			String giftKind, String exchangeCd, String issCompanyCd, String cancelOrdCode, String niceTrYn,
			String issCompanyCode, int issDay, String pin) {
		// niceHeader
		this.setTotalSize(380);
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
		this.setAmount(amount);
		this.setCardNo(cardNo);
		this.setToCardNo(toCardNo);
		this.setSaleKind(saleKind);
		this.setGiftKind(giftKind);
		this.setExchangeCd(exchangeCd);
		this.setIssCompanyCd(issCompanyCd);
		this.setCancelOrdCode(cancelOrdCode);
		this.setNiceTrYn(niceTrYn);
		this.setIssCompanyCode(issCompanyCode);
		this.setIssDay(issDay);
		this.setPin(pin);
	}

	/**
	 * @Desc : 요청 전문 body 조합
	 * @Method Name : makeBalanceTransferRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String makeBalanceTransferRequestBody() {
		String balanceTransferRequestBodyStr = String.format("%158s", " ");
		byte[] balanceTransferRequestBody = new String(balanceTransferRequestBodyStr).getBytes();

		String bodyStr = String.format("%012d", this.amount)
				+ String.format("%-16s", NiceGiftUtil.chkEmpty(this.cardNo))
				+ String.format("%-16s", NiceGiftUtil.chkEmpty(this.toCardNo))
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.saleKind))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.giftKind))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.exchangeCd))
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.issCompanyCd))
				+ String.format("%-12s", NiceGiftUtil.chkEmpty(this.recvUserCallNo))
				+ String.format("%-50s", NiceGiftUtil.chkEmpty(sendMessage))
				+ String.format("%-10s", NiceGiftUtil.chkEmpty(this.imgCode))
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.cancelOrdCode))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.niceTrYn))
				+ String.format("%-4s", NiceGiftUtil.chkEmpty(this.issCompanyCode)) + String.format("%04d", this.issDay)
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.payCode))
				+ String.format("%-6s", NiceGiftUtil.chkEmpty(this.pin))
				+ String.format("%-16s", NiceGiftUtil.chkEmpty(this.companyUserNo))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.signYn));

		System.arraycopy(bodyStr.getBytes(), 0, balanceTransferRequestBody, 0, bodyStr.length());

		return new String(balanceTransferRequestBody);
	}

	/**
	 * @Desc : 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeBalanceTransferRequestBody()
				+ makeCommonCancelRequest(true);
	}

	/**
	 * @Desc : 취소 요청 전문 전체 조합
	 * @Method Name : getCancelRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getCancelRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeBalanceTransferRequestBody()
				+ makeCommonCancelRequest(false);
	}
}
