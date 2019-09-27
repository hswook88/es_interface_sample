package kr.co.shop.interfaces.module.payment.nice.model;

import kr.co.shop.interfaces.module.payment.nice.NiceGiftUtil;
import lombok.Data;

/**
 * @Desc : 상품권 교환 요청 전문
 * @FileName : ExchangeRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Data
public class ExchangeRequest extends CommonRequest {

	private static final long serialVersionUID = 1732982501592323586L;
	/**
	 * 교환금액
	 */
	private Long amount;
	/**
	 * 선불카드번호(기존)
	 */
	private String cardNo;
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
	private String imgCd;
	/**
	 * 환불대상자
	 */
	private String cancelOrdCode;
	/**
	 * NICE 발송여부
	 */
	private String niceTrYn;
	/**
	 * 발행사
	 */
	private String issCompanyCode;
	/**
	 * 유효기간
	 */
	private Long issDay;
	/**
	 * 결제수단
	 */
	private String payCode;
	/**
	 * 인증번호 or OTP or 스크래치
	 */
	private String pin;
	/**
	 * 회원번호
	 */
	private String companyUserNo;
	/**
	 * 기명전환 (옵션) flag
	 */
	private String signYn;

	/**
	 * @Desc : 상품권 교환 요청 전문 body 조합
	 * @Method Name : makeExchangeRequestBody
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String makeExchangeRequestBody() {
		String exchangeRequestBodyStr = String.format("%142s", " ");
		byte[] exchangeRequestRequestBody = new String(exchangeRequestBodyStr).getBytes();

		String bodyStr = String.format("%012d", this.amount) + String.format("%-16s", this.cardNo)
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.saleKind))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.giftKind))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.exchangeCd))
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.issCompanyCd))
				+ String.format("%-12s", NiceGiftUtil.chkEmpty(this.recvUserCallNo))
				+ String.format("%-50s", NiceGiftUtil.chkEmpty(this.sendMessage))
				+ String.format("%-10s", NiceGiftUtil.chkEmpty(this.imgCd))
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.cancelOrdCode))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.niceTrYn))
				+ String.format("%-4s", NiceGiftUtil.chkEmpty(this.issCompanyCode)) + String.format("%04d", this.issDay)
				+ String.format("%-2s", NiceGiftUtil.chkEmpty(this.payCode))
				+ String.format("%-6s", NiceGiftUtil.chkEmpty(this.pin))
				+ String.format("%-16s", NiceGiftUtil.chkEmpty(this.companyUserNo))
				+ String.format("%1s", NiceGiftUtil.chkEmpty(this.signYn));

		System.arraycopy(bodyStr.getBytes(), 0, exchangeRequestRequestBody, 0, bodyStr.length());

		return new String(exchangeRequestRequestBody);
	}

	/**
	 * @Desc : 상품권 교환 요청 전문 전체 조합
	 * @Method Name : getRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeExchangeRequestBody() + makeCommonCancelRequest(true);
	}

	/**
	 * @Desc : 상품권 교환 취소 요청 전문 전체 조합
	 * @Method Name : getCancelRequest
	 * @Date : 2019. 4. 10.
	 * @Author : YSW
	 * @return
	 */
	public String getCancelRequest() {
		return makeNiceHeader() + makeServiceHeader() + makeExchangeRequestBody() + makeCommonCancelRequest(false);
	}
}
