package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * CJmall 주문관련 처리용 모델
 * 
 * @author 백인천
 *
 */
@Data
public class CJOrderInput {

	// if_04_01
	@XStreamAlias("tns:instructionCls")
	private String instructionCls; // 지시구분(1:출고 2:취소, IF_04_31추가 3:출고확정 4:배송완료)
	@XStreamAlias("tns:wbCrtDt")
	private String wbCrtDt; // 조회조건 - 지시일자(YYYY-MM-DD)

	// if_04_02
	@XStreamAlias("tns:autoFlag")
	private String autoFlag; // 조회조건 - 자동회수확정여부

	// if_04_32 - 순서가 변경되어도 CJ측 XML파싱에서 오류발생하므로 순서일치 시키기 위해 변수 추가
	@XStreamAlias("tns:wbCrtDt")
	private String recallWbCrtDt; // 조회조건 - 지시일자(YYYY-MM-DD)
	@XStreamAlias("tns:instructionCls")
	private String recallInstructionCls; // 지시구분(1:회수지시 3:회수집하 4:회수확정)

	// if_04_12 - 직택1 회수조회
	@XStreamAlias("tns:sschKwordCls")
	private String jiktak1SschKwordCls; // 조회조건 - 일자구분(1:회수지시일 3:회수집하일 9:접수일자)
	@XStreamAlias("tns:wbCrtDt")
	private String jiktak1WbCrtDt; // 조회조건 - 지시일자
	@XStreamAlias("tns:autoFlag")
	private String jiktak1AutoFlag; // 조회조건 - 자동회수확정여부("":전체 0:N 1:Y)

	// if_07_01 - 주문현황조회
	@XStreamAlias("tns:ordDtm")
	private String ordDtm; // 주문일자
	@XStreamAlias("tns:schnCd")
	private String schnCd; // 채널
	@XStreamAlias("tns:sitemLvl")
	private String sitemLvl; // 판매Code/단품Code
	@XStreamAlias("tns:sunitCd")
	private String sunitCd; // 상위귀속

}
