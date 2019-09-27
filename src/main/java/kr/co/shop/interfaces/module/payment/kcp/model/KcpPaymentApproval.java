package kr.co.shop.interfaces.module.payment.kcp.model;

import kr.co.shop.interfaces.module.payment.base.model.PaymentBase;
import lombok.Data;

/**
 * @Desc : KCP 승인연동 송신 데이터
 * @FileName : KcpPaymentApproval.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Data
public class KcpPaymentApproval extends PaymentBase {

	private static final long serialVersionUID = -5006560757388107451L;

	private int amount; // 삭제예정

	/*****************
	 * 결제승인 공통
	 *****************/
	private String reqTx; // 요청의 종류를 구분하는 변수, 'pay' : 결제 요청페이지
	private String siteName; // 사이트명 : 환경설정에 설정된 값 사용
	private String siteCd; // 사이트코드 : 환경설정에 설정된 값 사용
	private String siteKey; // 사이트키 : 환경설정에 설정된 값 사용
	private String ordrIdxx; // 가맹점에서 주문정보를 관리하기 위해 자체 생성한 주문번호
	private String payMethod; // 가맹점이 이용하는 결제수단을 설정하는 변수 (사용 : 1, 사용안함 : 0), 신용카드 : 100000000000, 계좌이체 :
								// 010000000000, 가상계좌 : 001000000000, 휴대폰 : 000100000000
	private String goodName; // 상품명(30byte 이내)
	private long goodMny; // 결제 금액(숫자만 가능, 콤마 불가능)
	private String buyrName; // 주문자명
	private String buyrMail; // 주문자 이메일
	private String buyrTel1; // 주문자 전화번호 ('-' 사용 무관)
	private String buyrTel2; // 주문자 휴대전화번호 ('-' 사용 무관)
	private String currency; // 거래 화폐 단위, 원화 : WON, 달러 : USD -> 달러는 별도의 신청 필요
	private String shopUserId; // 쇼핑몰에서 관리하는 회원 ID, 휴대폰소액 결제 - 40byte, 상품권 결제 - 20byte

	/*****************
	 * 모바일 결제
	 *****************/
	private String actionResult; // 인증수단
	private String approvalKey; // 거래인증 코드(수정불가)
	private String vanCode; // 결제추가 수단
	private String retUrl; // 업체주문페이지 주소
	private String paramOpt1; // 추가파라미터
	private String paramOpt2; // 추가파라미터
	private String paramOpt3; // 추가파라미터
	private String payUrl; // 결제창 호출 주소
	private String appUrl; // 앱URL(앱을 통한 결제시 사용)

	/*****************
	 * 에스크로 설정
	 *****************/
	private String escwUsed;
	private String payMod; // 에스크로 결제처리 모드 설정, Y : 결제금액과 관계없이 에스크로 결제처리, N : 결제금액과 관계없이 일반결제 처리
	private String deliTerm; // 예상 배송 소요일(2자리로 입력 ex)5일 -> 05 , 12일 -> 12 )
	private int baskCntx; // 장바구니에 담겨있는 상품개수, 40개 이하로 해야함
	private String rcvrName; // 수취자명
	private String rcvrTel1; // 수취자 전화번호 ('-' 사용 무관)
	private String rcvrTel2; // 수취자 휴대폰번호 ('-' 사용 무관)
	private String rcvrMail; // 수취자 E-Mail
	private String rcvrZipx; // 수취자 우편번호 ('-' 사용 무관)
	private String rcvrAdd1; // 수취자 주소 ('-' 사용 무관)
	private String rcvrAdd2; // 수취자 상세주소 ('-' 사용 무관)
	private String goodInfo; // 상품정보(

	/*****************
	 * 신용카드
	 *****************/
	private int quotaopt; // [신용카드]최대 할부 개월수 (0~12, 0:일시불)
	private String kcpNoint; // [신용카드]가맹점 부담 무이자, 공백 : 관리자 설정 할부(가맹점 관리자 페이지에 설정된 무이자 정보), N : 일반할부(가맹점 관리자
								// 페이지 설정 무시), Y : 설정할부(kcp_noint_quota에 설정된 옵션으로 처리)
	private String kcpNointQuota; // [신용카드]무이자 옵션(kcp_noint)이 'Y'일 경우 무이자 설정 값을 결제 창에 설정함(카드사 별로 설정 가능함),
									// CCBC-02:03:06,CCKM-03:06,CCSS-06:09(BC 2, 3, 6개월, KB국민 3, 6개월, 삼성 6, 9개월 무이자)
	private String usedCardYn; // [신용카드]결제 요청 시 원하는 신용카드사 설정 여부(Y : 설정한 신용카드만 노출)
	private String usedCard; // [신용카드]used_card_yn 변수를 Y로 설정한 뒤 실제 사용하기 원하는 신용카드사를 설정할 수 있는 변수
	private String fixInst; // [신용카드]결제창에서 선택 할 수 있는 할부개월수 고정, ex) 03 : 3개월만 노출됨

	/*****************
	 * 가상계좌
	 *****************/
	private String wishVbankList; // [가상계좌]KCP에서 제공하는 은행목록, ex) 기업은행, 국민은행, 농협 - 03:05:11
	private int vcntExpireTerm; // [가상계좌]유효기간 설정, 계좌 발급일로부터 3일 후까지 입금 가능 - '3'(2016년 12월 1일에 발급된 계좌는 2016년 12월
								// 4일까지 입금 가능)
	private String vcntExpireTermTime; // [가상계좌]가상계좌의 유효시간을 설정하는 변수(설정하지 않은 경우 발급되는 계좌번호는 기본적으로 23시 59분 59초까지 입금가능한 계좌로
										// 설정됨)
	private String ipgmDate; // 발급된 가상계좌에 입금할 예정일을 입력하는 변수, '20130915'
	private String userBank; // KCP에서 제공하느 ㄴ은행 중 가맹점이 원하는 은행 선택, 예)기업은행, 국민은행 사용 시 -> BK03:BK04

	/*****************
	 * 휴대폰
	 *****************/
	private String hpApplyYn; // [휴대폰]원하는 통신사만 노출시킬지 여부값 설정
	private String hpCommid; // [휴대폰]원하는 통신사만 노출(SKT, KTF, LGT) - 하나의 통신사만 가능

	/*****************
	 * 포인트
	 *****************/
	private String complexPntYn; // [포인트]포인트 결제만을 사용할 경우 'N'으로 설정
	private String ptMemcorpCd; // [포인트]베네피아(SK M&C) 복지포인트 사용 시 필수

	/*****************
	 * 현금영수증
	 *****************/
	private String dispTaxYn; // [현금영수증]계좌이체, 가상계좌 이용 시 현금영수증 등록여부 창을 보여주는 변수, 자동등록을 원할 경우 'Y'로 설정 (관리자 페이지에서
								// 설정해야함[구매자 요청등록])

	/*****************
	 * 옵션변수
	 *****************/
	private String siteLogo; // 결제창 왼쪽 상단에 가맹점 사이트 로고를 띄우는 변수 (gif, jpg 최대 150 X 50 미만)
	private String engFlag; // 결제창 한글/영문 변환 변수, Y : 기본 영문으로 보임, (신용카드, 계좌이체, 가상계좌, 휴대폰 소액결제에 적용)
	private int skinIndx; // 결제창 스킨 변경 파라미터(1~11까지 설정 가능)
	private String goodExpr; // 상품 제공기간, 0 : 일반결제 상품 -> [일반결제]로 표시됨, 1:2012010120120131 제공기간이 있는 상품 ->
								// 2012.01.01 ~ 2012.01.31
	private String goodCd; // 상품코드

	/*****************
	 * 복합과세
	 *****************/
	private String taxFlag; // 복합 과세 구분, 복합과세 반드시 TG03 설정(복합과세 전용 사이트 코드로 계약한 경우만 해당됨)
	private long commTaxMny; // 과세 승인금액(공급가액), 과세 금액 = good_mny / 1.1
	private long commFreeMny; // 비과세 승인금액, 비과세 금액 = good_mny - 과세금액 - 부가가치세
	private long commVatMny; // 부가가치세, 부가가치세 = good_mny - 과세금액

	/*****************
	 * 결제창 전달
	 *****************/
	private String encInfo; // 통합결제창으로부터 전달 받는 인증결과 암호화 데이터
	private String encData; // 통합결제창으로부터 전달 받는 인증결과 암호화 데이터
	private String tranCd; // 통합결제창에서 처리하여 전달받는 상태코드 값
	private String ordrChk; // 인증완료 후 결제 창으로부터 전달 받는 주문 번호와 주문금액(pc버전만 적용)
	private String usePayMethod; // 결제고객이 선택한 결제수단
	private String cashYn; // 현금영수증 선택여부
	private String cashTrCode; // 현금영수증 선택시 식별코드, 0 : 소득공제용(개인), 1 : 지출증빙용(기업)
	private String cashIdInfo; // 현금 영수증 등록 번호

	/*****************
	 * etc
	 *****************/
	private String custIp; // 요청 IP
	private String tno; // KCP 거래 고유 번호

	/*****************
	 * 모바일 결제 인증 관련
	 *****************/
	private String resCd; // 결과코드, 0000 : 정상
	private String responseType; // 응답방식

}
