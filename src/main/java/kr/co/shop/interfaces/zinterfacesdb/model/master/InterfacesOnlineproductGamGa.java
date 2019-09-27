package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineproductGamGa {
	
	//상품번호(온라인전용)
	private String prdtNo;
	
	//ERP상품코드
	private String vndrPrdtNo;
	
	//정상가
	private int nomalAmt;
	
	//판매가
	private int sellAmt;
	
	//할인율
	private int erpDscntRate;
	
	private int prdtPriceHistNextSeq; 
	
	
}
