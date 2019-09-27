package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineProductGamGaHistroy {

	//상품번호(온라인전용)
		private String prdtNo;
		
		private int prdtPriceHistNextSeq;
		
		//ERP상품코드
		private String vndrPrdtNo;
		
		//정상가
		private int normalAmt;
		
		//판매가
		private int sellAmt;
		
		//ERP할인율
		private int erpDscntRate;
		
		//온라인 할인율
		private int onlnDscntRate;

		//임직원 할인율
		private int empDscntRate;


}
