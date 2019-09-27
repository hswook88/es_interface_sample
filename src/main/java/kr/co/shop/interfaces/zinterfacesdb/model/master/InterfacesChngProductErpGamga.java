package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesChngProductErpGamga {
	
	//상품코드
	private String vndrPrdtNo;
		
	//시작일자
	private String applyStartDtm;
	
	//종료일자
	private String applyEndDtm;
	
	//판매가격
	private int sellAmt;
	
	//정상가격
	private int nomalAmt;
	
	private int erpDscntRate;
	
	private int onlnDscntRate;

	private int empDscntRate;
}
