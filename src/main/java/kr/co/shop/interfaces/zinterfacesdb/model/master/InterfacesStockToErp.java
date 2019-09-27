package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

/**
 * @Desc : Erp재고정보 수신
 * @FileName : ErpStock
 * @Project : shop.interface
 * @Date : 2019. 02. 15
 * @Author : 김영진
 */

@Data
public class InterfacesStockToErp {

	private String cbcd;

	private String maejangCd;

	private String sangpumCd;
	
	private String sangPumFullCd;
	
	private String qty;

	private String inputType;
	
	private int orderCount;
	
}
