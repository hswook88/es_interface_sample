package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineProductDetail {

	//상품번호
	private String prdtNo;
	
	//브랜드코드
	private String brandNo;
	
	//erp상품코드
	private String vndrPrdtNoText;
	
	//상품이름
	private String prdtName;
	
	//원산지 내부테이블 코드
	private String orgPlaceCode;
	
	//수정자아이디
	private String moderNo;
	
	//스타일
	private String styleInfo;
	
	//상품타입코드
	private String prdtTypeCode;
	
	//제조사
	private String mnftrName;
	
	//색상코드 
	private String prdtDtlColor;
	
	//소재
	private String prdtMaterlText;
	
	//색상코드(tobe)
	private String prdtColor;
	
	//컬러코드
	private String prdtColorCode;
	
	
	//이력테이블 시퀀스
	private int chngHistSeq;


	
	

	
}
