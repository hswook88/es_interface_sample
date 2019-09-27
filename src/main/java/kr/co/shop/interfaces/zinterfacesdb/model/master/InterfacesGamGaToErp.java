package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesGamGaToErp {

	/*정상가*/
	private String mgAmt;

	/*할인가*/
	private String gamgaAmt;
	
	/*시작일시*/
	private String strymd;
	
	/*종료일시*/
	private String stpymd;
	
	/*매장코드*/
	private String maejangCd;
	
	/*상품코드*/
	private String sangpumCd;
	
	/**기간계 할인율*/
	private String spgb;
	
	/**임직원 할인율*/
	private String staff;
	
	/**온라인 할인율*/
	private int onlinerate;
	
	
}
