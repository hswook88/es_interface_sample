package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOrder {

	/** 인터페이스명 */
	private String orderNum;
	
	/** B 코드 */
	private String cbcd;
	
	/** 매장코드 */
	private String maejangCd;
	
	/** 배송 아이디 */
	private String dlvyId;

}
