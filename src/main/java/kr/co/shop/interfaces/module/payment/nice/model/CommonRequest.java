package kr.co.shop.interfaces.module.payment.nice.model;

import org.springframework.util.StringUtils;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 나이스 기프트카드 요청 전문 공통 헤더
 * @FileName : CommonRequest.java
 * @Project : shop.interfaces
 * @Date : 2019. 4. 10.
 * @Author : YSW
 */
@Slf4j
@Data
public class CommonRequest extends PaymentBase {

	private static final long serialVersionUID = -7041718367805337500L;
	/**
	 * niceHeader 메세지전체길이
	 */
	private long totalSize = 0L;
	/**
	 * niceHeader 전문 text
	 */
	private String messageText;
	/**
	 * niceHeader 거래일련번호(전문관리번호)
	 */
	private String globalSerialNo;
	/**
	 * niceHeader 메세지 TYPE
	 */
	private String messageType;
	/**
	 * niceHeader 상세서비스코드(거래구분코드)
	 */
	private String serviceCode;
	/**
	 * niceHeader CATID
	 */
	private String merchantId;
	/**
	 * niceHeader VAN 코드
	 */
	private String vanCode;
	/**
	 * niceHeader 단말기기기번호
	 */
	private String catSerialNo;
	/**
	 * niceHeader 가맹점번호
	 */
	private String affNo;
	/**
	 * niceHeader 발금사코드
	 */
	private String issCode;
	/**
	 * niceHeader 가맹점 GID
	 */
	private String merchantGId;
	/**
	 * serviceHeader 상품종류구분
	 */
	private String giftCardKind;
	/**
	 * serviceHeader 거래방법
	 */
	private String requestMethodCode;
	/**
	 * serviceHeader 사용구분
	 */
	private String exchangeCode;
	/**
	 * serviceHeader 영업일자
	 */
	private String saleDate;
	/**
	 * serviceHeader 영업시간
	 */
	private String saleTime;
	/**
	 * serviceHeader 점포코드
	 */
	private String storeCd;
	/**
	 * serviceHeader 포스번호
	 */
	private String posNo;
	/**
	 * serviceHeader 주문번호 또는 영수증번호
	 */
	private String ordNumber;
	/**
	 * serviceHeader 카드제작 이미지코드
	 */
	private String cardImgCode;
	/**
	 * serviceHeader 추가정보값
	 */
	private String companyInfo;
	/**
	 * 원거래승인번호
	 */
	private String oApprovalNo;
	/**
	 * 원거래승인일자
	 */
	private String oApprovalDate;

	public String makeNiceHeader() {
		String niceHeaderStr = String.format("%88s", " ");
		byte[] niceHeader = new String(niceHeaderStr).getBytes();

		String headerStr = String.format("%04d", this.totalSize) + String.format("%-7s", chkEmpty(this.messageText))
				+ String.format("%-12s", chkEmpty(this.globalSerialNo))
				+ String.format("%-4s", chkEmpty(this.messageType)) + String.format("%-2s", chkEmpty(this.serviceCode))
				+ String.format("%-10s", chkEmpty(this.merchantId)) + String.format("%-2s", chkEmpty(this.vanCode))
				+ String.format("%-10s", chkEmpty(this.catSerialNo)) + String.format("%-15s", chkEmpty(this.affNo))
				+ String.format("%-2s", chkEmpty(this.issCode)) + String.format("%-20s", chkEmpty(this.merchantGId));

		System.arraycopy(headerStr.getBytes(), 0, niceHeader, 0, headerStr.length());

		return new String(niceHeader);
	}

	public String makeServiceHeader() {
		String serviceHeaderStr = String.format("%118s", " ");
		byte[] serviceHeader = new String(serviceHeaderStr).getBytes();

		String headerStr = String.format("%-2s", chkEmpty(this.giftCardKind))
				+ String.format("%1s", chkEmpty(this.requestMethodCode))
				+ String.format("%1s", chkEmpty(this.exchangeCode)) + String.format("%-8s", chkEmpty(this.saleDate))
				+ String.format("%-6s", chkEmpty(this.saleTime)) + String.format("%-10s", chkEmpty(this.storeCd))
				+ String.format("%-10s", chkEmpty(this.posNo)) + String.format("%-20s", chkEmpty(this.ordNumber))
				+ String.format("%-10s", chkEmpty(this.cardImgCode))
				+ String.format("%-50s", chkEmpty(this.companyInfo));

		System.arraycopy(headerStr.getBytes(), 0, serviceHeader, 0, headerStr.length());

		return new String(serviceHeader);
	}

	public String makeCommonCancelRequest(boolean dummy) {
		String commonCancelRequestStr = String.format("%20s", " ");
		byte[] commonCancelRequestBody = new String(commonCancelRequestStr).getBytes();
		log.debug("##### commonCancelRequestBody: {}", commonCancelRequestBody);

		String bodyStr;
		if (dummy) {
			bodyStr = String.format("%-8s", " ") + String.format("%-8s", " ");
		} else {
			bodyStr = String.format("%-8s", chkEmpty(this.oApprovalNo))
					+ String.format("%-8s", chkEmpty(this.oApprovalDate));
		}
		log.debug("##### bodyStr: {}", bodyStr);
		log.debug("##### commonCancelRequestBody: {}", commonCancelRequestBody);

		System.arraycopy(bodyStr.getBytes(), 0, commonCancelRequestBody, 0, bodyStr.length());

		return new String(commonCancelRequestBody);
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
