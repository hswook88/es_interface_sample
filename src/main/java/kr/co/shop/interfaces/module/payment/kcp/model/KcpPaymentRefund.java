package kr.co.shop.interfaces.module.payment.kcp.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 환불연동 송신 데이터
 * @FileName : KcpPaymentRefund.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentRefund extends PaymentBase {

	private static final long serialVersionUID = 4328228374167946142L;

	private String siteCd; // 사이트코드 : 환경설정에 설정된 값 사용
	private String siteKey; // 사이트키 : 환경설정에 설정된 값 사용
	private String custIp; // 요청ip

	private String reqTx; // 요청종류 승인(pay)/취소,매입(mod) 요청시 사용
	private String modType; // 프로세스 요청 구분 (전체환불 – STHD, 부분환불 – STPD)
	private String modCompType; // 인증타입을 설정, 계좌인증 + 환불등록 - MDCP01, (계좌+실명)인증 + 환불등록 - MDCP02
	private String modSubType; // 환불 요청 타입을 설정하는 변수(고정 값), 전체 환불 – MDSC00, 부분 환불 – MDSC03, 복합과세 부분 환불 – MDSC04
	private String tno; // 환불등록 요청건의 KCP거래번호
	private String modDesc; // 환불요청 사유
	private String modAccount; // 계좌인증 및 환불 받을 계좌번호
	private String modDepositor; // 계좌인증 및 환불 받을 계좌 예금주의 이름
	private String modBankcode; // 환불 요청하는 은행코드
	private String modSocname; // 환불 요청하는 요청자의 실명이름(계좌+실명인증 타입의 경우에만 적용)
	private String modSocno; // 환불 요청하는 요청자의 주민번호(계좌+실명인증 타입의 경우에만 적용)

	/*****************
	 * 부분환불
	 *****************/
	private long modMny; // 부분환불 요청 금액
	private long remMny; // 부분환불전 남은 금액
	private String taxFlag; // [복합과세] 복합과세 요청 구분
	private long modTaxMny; // [복합과세] 부분환불 요청 과세금액
	private long modVatMny; // [복합과세] 부분환불 요청 비과세금액
	private long modFreeMny; // [복합과세] 부분환불 요청 부가세금액
}
