package kr.co.shop.interfaces.module.payment.nice.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 판매대행(선물하기) 요청 전문
 * @FileName : SaleAgenciesRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Slf4j
@Data
public class SaleAgenciesRequest extends CommonRequest {

	private static final long serialVersionUID = -161831224535268391L;
	/**
	 * 액면가
	 */
	private long amount;
	/**
	 * 구매액
	 */
	private long saleAmount;
	/**
	 * 판매형태
	 */
	private String saleKind;
	/**
	 * 상품종류구분
	 */
	private String giftCardKindSR;
	/**
	 * 사용구분
	 */
	private String exchangeCodeSR;
	/**
	 * 발급사코드
	 */
	private String issCodeSR;
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
	 * 회원번호
	 */
	private String companyUserNo;

	public SaleAgenciesRequest() {

	}

	public SaleAgenciesRequest(long amount, long saleAmount, String saleKind, String giftCardKindSR,
			String exchangeCodeSR, String issCodeSR, String recvUserCallNo, String sendMessage, String imgCode,
			String cancelOrdCode, String niceTrYn, String issCompanyCode, int issDay, String payCode,
			String companyUserNo) {
		// niceHeader
		this.setTotalSize(353);
		this.setMessageText("NICE000");
		this.setGlobalSerialNo("1234567890");
		this.setMessageType("0200");
		this.setServiceCode("A2");
		this.setMerchantId("9510473002");
		this.setVanCode("01");
		this.setIssCode("A8");
		this.setMerchantGId("");
		// serviceHeader
		this.setGiftCardKind("03");
		this.setRequestMethodCode("@");
		this.setExchangeCode("1");
		this.setSaleDate(currentDt());
		this.setSaleTime("");
		this.setStoreCd("1001");
		this.setPosNo("0000000000");
		// serviceRequest
		this.setAmount(amount);
		this.setSaleAmount(saleAmount);
		this.setSaleKind(saleKind);
		this.setGiftCardKindSR(giftCardKindSR);
		this.setExchangeCodeSR(exchangeCodeSR);
		this.setIssCodeSR(issCodeSR);
		this.setRecvUserCallNo(recvUserCallNo);
		this.setSendMessage(sendMessage);
		this.setImgCode(imgCode);
		this.setCancelOrdCode(cancelOrdCode);
		this.setNiceTrYn(niceTrYn);
		this.setIssCompanyCode(issCompanyCode);
		this.setIssDay(issDay);
		this.setPayCode(payCode);
		this.setCompanyUserNo(companyUserNo);
	}

	/**
	 * @Desc : 판매대행(선물하기) 요청 전문 body 조합
	 * @Method Name : makeSaleAgenciesRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String makeSaleAgenciesRequestBody() {
		String saleAgenciesRequestBodyStr = String.format("%131s", " ");
		byte[] saleAgenciesRequestBody = new String(saleAgenciesRequestBodyStr).getBytes();

		String bodyStr = String.format("%012d", this.amount) + String.format("%012d", this.saleAmount)
				+ String.format("%2s", this.saleKind) + String.format("%1s", this.giftCardKindSR)
				+ String.format("%1s", this.exchangeCodeSR) + String.format("%-2s", this.issCodeSR)
				+ String.format("%-12s", this.recvUserCallNo) + String.format("%-50s", this.sendMessage)
				+ String.format("%-10s", this.imgCode) + String.format("%2s", this.cancelOrdCode)
				+ String.format("%1s", this.niceTrYn) + String.format("%-4s", this.issCompanyCode)
				+ String.format("%04d", this.issDay) + String.format("%2s", this.payCode)
				+ String.format("%-16s", this.companyUserNo);

		System.arraycopy(bodyStr.getBytes(), 0, saleAgenciesRequestBody, 0, bodyStr.length());

		return new String(saleAgenciesRequestBody);
	}

	/**
	 * @Desc : 판매대행(선물하기) 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeSaleAgenciesRequestBody() + makeCommonCancelRequest(true);
	}

	/**
	 * @Desc : 판매대행(선물하기) 취소 요청 전문 전체 조합
	 * @Method Name : getCancelRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getCancelRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeSaleAgenciesRequestBody() + makeCommonCancelRequest(false);
	}

	public String currentDt() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentDt = new Date();
		String currentDateStr = mSimpleDateFormat.format(currentDt);
		log.debug("##### currentDateStr: {}", currentDateStr);

		return currentDateStr;
	}
}
