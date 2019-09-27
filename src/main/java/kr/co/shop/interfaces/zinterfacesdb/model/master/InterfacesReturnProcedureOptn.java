package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesReturnProcedureOptn {
	
	/*B코드*/
	private String cbcd;
	
	/*매장 코드*/
	private String maejangCd;
	
	/*배송 아이디*/
	private String dlvyId;
	
	/*서브키*/
	private String itemSno;
	
	/*상품 풀코드(원주문옵션)*/
	private String sangPumFullCd1;
	
	/*상품 풀코드(신주문옵션)*/
	private String sangPumFullCd2;
	
	
}
