package kr.co.shop.interfaces.module.payment.kcp.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 취소연동 송신 데이터
 * @FileName : KcpPaymentCancel.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentCancel extends PaymentBase {

	private static final long serialVersionUID = -1676019490939462848L;

	private String reqTx; // 요청종류 승인(pay)/취소,매입(mod) 요청시 사용
	private String modType; // 프로세스 요청 구분 (전체취소= STSC, 부분취소= STPC)
	private String tno; // 결제승인번호
	private String modDesc; // 변경사유
	private String custIp; // 요청ip
	private String siteCd; // 사이트코드 : 환경설정에 설정된 값 사용
	private String siteKey; // 사이트키 : 환경설정에 설정된 값 사용

	/*****************
	 * 부분취소
	 *****************/
	private String modMny; // 취소요청 금액
	private String remMny; // 부분취소 이전에 남은 금액
	private String taxFlag; // [복합과세] 거래 구분 값
	private long modTaxMny; // [복합과세] 공급가 부분취소 요청금액
	private long modVatMny; // [복합과세] 부과세 부분 취소 요청금액
	private long modFreeMny; // [복합과세] 비과세 부분취소 요청금액

	/*****************
	 * 환불정보
	 *****************/
	private String modBankCode; // 환불은행코드
	private String modAccount; // 환불은행계좌
	private String modDepositor; // 환불은행계좌주
}
