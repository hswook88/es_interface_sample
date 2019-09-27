package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * CJmall 주문지시상세 처리용 모델
 * 
 * @author 백인천
 *
 */
@Data
public class CJInstructionDetail {

	@XStreamOmitField
	private String ordNo; // 주문정보 - 주문번호

	// IF_04_11
	private String ordGSeq; // 주문정보 - 주문상품순번
	private String ordDSeq; // 주문정보 - 주문상세순번
	private String ordWSeq; // 주문정보 - 주문처리순번
	private String ordDtlClsNm; // 주문구분
	private String ordDtlClsCd; // 주문구분코드
	private String wbProgCd; // 진행구분
	private String transClsCd; // 출고보류여부
	private String wbCrtDt; // 지시일자
	private String cnclInsDtm; // 취소일자
	private String outwConfDt; // 출고확정일자
	private String wbProcDt; // 고객인수일자
	private String chnNm; // 채널구분
	private String recvNm; // 인수자
	private String msgSpec; // 배송참고
	private String delvplnDt; // 배송예정일
	private String itemCd; // 판매코드
	private String unitCd; // 단품코드
	private String itemName; // 판매상품명
	private String unitNm; // 단품상세
	private String outwQty; // 수량
	private String realslAmt; // 판매가(CJ몰표시금액)
	private String outwAmt; // 고객결제가(할인금액은빠진실결제가)
	private String delivClsNm; // 배송구분
	private String delivMethNm; // 배송방법
	private String delicompCd; // 택배사
	private String wbIdNo; // 운송장식별번호
	private String wbNo; // 운송장번호
	private String mixpackYn; // 합포장여부
	private String promGiftSpec; // 사은품내역
	private String cnclRsnCd; // 교환/취소사유
	private String cnclRsnSpec; // 사유상세
	private String telno; // 인수자tel
	private String cellno; // 인수자HP
	private String addr; // 주소
	private String zipno; // 우편번호
	private String printYn; // 출력여부

	// IF_04_12
	private String delivDtm; // 주문정보 - 회수집하일
	private String wbNo2; // 배송정보 - 운송장번호
	private String deliComp; // 배송정보 - 택배사
	private String autoFlag; // 배송정보 - 자동회수확정여부
	private String exceptRsnFlg; // 배송정보 - 청약예외여부
	private String recvBefCnclYn; // 배송정보 - 인수전취소여부
	private String vocNm; // 기타정보 - 반품사유
	private String claimNote; // 기타정보 - 반품사유상세

}
