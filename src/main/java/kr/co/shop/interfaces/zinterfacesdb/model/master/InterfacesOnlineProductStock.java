package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineProductStock {

	private int totalstockQty;
	
	private int totalOrderQty;
	
	private java.sql.Timestamp modDtm;

	private String vndrPrdtNoText;
	
	private String prdtOptnNo;
	
	private String prdtNo;

	private int orderCount;
}

