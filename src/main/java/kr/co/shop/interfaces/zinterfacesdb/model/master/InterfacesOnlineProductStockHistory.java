package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineProductStockHistory {

	
	//(PD_PRODUCT_OPTION_HISTORY)#
			
	private String prdtNo;
	
	private String prdtOptnNo;
	
	private int chngHistSeq;
	
	private String changeCol;
	
	private String changeName;
	
	private String bfStockQty;
	
	private String afStockQty;
	
	private String rgstNo;
	
	private java.sql.Timestamp rgstDtm;
			
}
