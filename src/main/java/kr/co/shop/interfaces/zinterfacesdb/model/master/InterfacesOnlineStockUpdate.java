package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesOnlineStockUpdate {

	
	//(PD_PRODUCT_OPTION)#

		private String prdtNo;
		
		private String prdtOptnNo;
		
		private String optnName;
		
		private int totalStockQty;
		
		private int totalOrderQty;
		
		private int orderPsbltQty;
		
		private String vndrPrdtNoText;
		
		private String sellStatCode;
		
		private String soleOutRsnText;
		
		private int sortSeq;
		
		private String useYn;
		
		private String rgsterNo;
		
		private java.sql.Timestamp rgstDtm;
		
		private String moderNo;
		
		private String modDtm;

		//(PD_PRODUCT_OPTION_PRICE_HISTORY)#

		
		private String sellPriceHistSeq;
		
		private String optnAddAmt;
		
		private String applyStartDtm;
		
		private String applyEndDtm;
		
		
		//(PD_PRODUCT_OPTION_STOCK)#

	
		private String stockGbnCode;
		
		private int stockQty;
		
		private int orderCount;
		
		private java.sql.Timestamp moddtm;
		
		private String cbcd;

		

		
}
