package kr.co.shop.interfaces.module.payment.kcp.model;

import com.kcp.J_PP_CLI_N;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 승인연동 수신 데이터
 * @FileName : KcpPaymentApprovalReturn.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentApprovalReturn extends PaymentBase {

	private static final long serialVersionUID = -5951484161659687885L;

	/*****************
	 * 결제승인 응답 공통
	 *****************/
	private String resCd; // 결과코드, 0000 : 정상
	private String resMsg; // 결과메시지
	private String resEnMsg; // 영문 결과 메시지
	private String bSucc; // 가맹점 자체 자동취소 여부, false : 자동취소처리
	private String tno; // KCP 거래고유번호
	private String amount; // 결제금액
	private String escwYn; // 에스크로 결제여부
	private String appTime; // 신용카드, 계좌이체 : 승인시간 , 가상계좌 : 계좌발급시간
	private String couponMny; // [신용카드, 계좌이체, MR통머니] 미스터통 쿠폰할인금액

	/*****************
	 * 신용카드
	 *****************/
	private String cardCd; // [신용카드]발급사 코드
	private String cardName; // [신용카드]발급사 명
	private String cardNo; // [신용카드]카드번호, 16자리중 세번째구간 마스킹처리
	private String appNo; // [신용카드]승인번호
	private String noinf; // [신용카드]무이자여부
	private String noinfType; // [신용카드]무이자구분, 카드사 이벤트 무이자인 경우 : CARD, 상점 부담 무이자인 경우 : SHOP
	private String quota; // [신용카드]할부기간, 00 : 일시불, 03 : 3개월 할부
	private String cardMny; // [신용카드]신용카드 결제금액
	private String partcancYn; // [신용카드]부분취소 가능 유무
	private String cardBinType01; // [신용카드]카드 구분정보 (법인카드 여부), 0 : 개인, 1 : 법인
	private String cardBinType02; // [신용카드]카드 구분정보(체크카드 여부), 0 : 일반, 1 : 체크

	/*****************
	 * 신용카드+포인트
	 *****************/
	private String pntAmount; // 적립금액 or 사용금액
	/* total_amount(복합결제시 총 거래금액) = amount + pntAmount */

	/*****************
	 * 계좌이체
	 *****************/
	private String bankCode; // [계좌이체, 가상계좌]은행코드
	private String bankName; // [계좌이체, 가상계좌]은행명
	private String bkMny; // [계좌이체]실결제 계좌이체 금액

	/*****************
	 * 가상계좌
	 *****************/
	private String depositor; // [가상계좌] 입금계좌 예금주
	private String account; // [가상계좌] 가상계좌번호
	private String vaDate; // [가상계좌] 가상계좌입금마감일

	/*****************
	 * 휴대폰 결제
	 *****************/
	private String vanCd; // [휴대폰]결제사코드, VNDN : 다날, VNDW : 다우기술, VNMB : 모빌리언스
	private String vanId; // [휴대폰]실물/컨텐츠 구분, ES01 : 실물, ES02 : 컨텐츠
	private String commid; // [휴대폰]통신사코드, SKT : SK 텔레콤, KTF : KT, LGT : LG U+
	private String mobileNo; // [휴대폰] 휴대폰 번호

	/*****************
	 * 포인트 결제
	 *****************/
	private String pntAmout; // 결제 건의 포인트 결제 금액
	private String pntIssue; // 결제 포인트사 코드
	private String pntAppNo; // 결제 건의 포인트 승인번호
	private String pntAppTime; // 결제 건의 포인트 승인시각
	private String pntReceiptGubn; // 결제 건의 현금영수증 등록유무
	private String addPnt; // 결제 건의 적립/사용 포인트
	private String usePnt; // 결제 건의 가용 포인트
	private String rsvPnt; // 결제 건의 총 포인트

	/*****************
	 * 상품권 결제
	 *****************/
	private String tkVanCode; // 결제 건의 결제사 코드
	private String tkAppNo; // 결제 건의 승인번호
	private String tkAppTime; // 결제 건의 승인시간

	/*****************
	 * MR통머니 결제
	 *****************/
	private String tongmnyMny; // 결제 건의 통머니 결제 금액
	private String appTongmnyTime; // 결제 건의 승인시간
	private String tongmnyAppno; // 결제 건의 승인번호

	/*****************
	 * 현금영수증
	 *****************/
	private String cashYn; // 현금영수증 선택여부
	private String cashTrCode; // 현금영수증 선택시 식별코드, 0 : 소득공제용(개인), 1 : 지출증빙용(기업)
	private String cashIdInfo; // 현금영수증 등록번호
	private String cashAuthno; // 현금영수증 승인번호
	private String cashNo; // 현금영수증 거래번호

	/**
	 * 승인 결과를 객체에 넣는 메소드
	 * 
	 * @param c_PayPlus
	 * @return
	 */
	public static KcpPaymentApprovalReturn of(J_PP_CLI_N c_PayPlus) {
		KcpPaymentApprovalReturn reply = new KcpPaymentApprovalReturn();

		reply.setResCd(c_PayPlus.m_res_cd);
		reply.setResMsg(c_PayPlus.m_res_msg);

		reply.setTno(c_PayPlus.mf_get_res("tno"));
		reply.setAmount(c_PayPlus.mf_get_res("amount"));
		reply.setPntIssue(c_PayPlus.mf_get_res("pnt_issue"));
		reply.setCouponMny(c_PayPlus.mf_get_res("coupon_mny"));
		reply.setCardCd(c_PayPlus.mf_get_res("card_cd"));
		reply.setCardNo(c_PayPlus.mf_get_res("card_no"));
		reply.setCardName(c_PayPlus.mf_get_res("card_name"));
		reply.setAppTime(c_PayPlus.mf_get_res("app_time"));
		if (UtilsText.isNotEmpty(c_PayPlus.mf_get_res("hp_app_time"))) { // 휴대폰 결제의 경우 hp_app_time을 set
			reply.setAppTime(c_PayPlus.mf_get_res("app_time"));
		}
		reply.setAppNo(c_PayPlus.mf_get_res("app_no"));
		reply.setNoinf(c_PayPlus.mf_get_res("noinf"));
		reply.setNoinfType(c_PayPlus.mf_get_res("noinf_type"));
		reply.setQuota(c_PayPlus.mf_get_res("quota"));
		reply.setPartcancYn(c_PayPlus.mf_get_res("partcanc_yn"));
		reply.setCardBinType01(c_PayPlus.mf_get_res("card_bin_type_01"));
		reply.setCardBinType02(c_PayPlus.mf_get_res("card_bin_type_02"));
		reply.setCardMny(c_PayPlus.mf_get_res("card_mny"));
		reply.setPntAmount(c_PayPlus.mf_get_res("pnt_amount"));
		reply.setPntAppTime(c_PayPlus.mf_get_res("pnt_app_time"));
		reply.setPntAppNo(c_PayPlus.mf_get_res("pnt_app_no"));
		reply.setAddPnt(c_PayPlus.mf_get_res("add_pnt"));
		reply.setUsePnt(c_PayPlus.mf_get_res("use_pnt"));
		reply.setRsvPnt(c_PayPlus.mf_get_res("rsv_pnt"));
		reply.setBankName(c_PayPlus.mf_get_res("bank_name"));
		if (UtilsText.isNotEmpty(c_PayPlus.mf_get_res("bankname"))) { // 가상계좌 은행명의 경우 bankname을 set
			reply.setBankName(c_PayPlus.mf_get_res("bankname"));
		}
		reply.setBankCode(c_PayPlus.mf_get_res("bank_code"));
		if (UtilsText.isNotEmpty(c_PayPlus.mf_get_res("bankcode"))) { // 가상계좌 은행코드의 경우 bankcode set
			reply.setBankCode(c_PayPlus.mf_get_res("bankcode"));
		}
		reply.setBkMny(c_PayPlus.mf_get_res("bk_mny"));
		reply.setDepositor(c_PayPlus.mf_get_res("depositor"));
		reply.setAccount(c_PayPlus.mf_get_res("account"));
		reply.setVaDate(c_PayPlus.mf_get_res("va_date"));
		reply.setCommid(c_PayPlus.mf_get_res("commid"));
		reply.setMobileNo(c_PayPlus.mf_get_res("mobile_no"));
		reply.setTkAppTime(c_PayPlus.mf_get_res("tk_app_time"));
		reply.setTkVanCode(c_PayPlus.mf_get_res("tk_van_code"));
		reply.setTkAppNo(c_PayPlus.mf_get_res("tk_app_no"));
		reply.setCashAuthno(c_PayPlus.mf_get_res("cash_authno"));
		reply.setCashNo(c_PayPlus.mf_get_res("cash_no"));

		return reply;
	}

}
