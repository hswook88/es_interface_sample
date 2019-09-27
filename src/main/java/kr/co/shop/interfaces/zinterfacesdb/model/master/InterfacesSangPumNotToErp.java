package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesSangPumNotToErp {

	private String maejangCd;

	private String sangPumCd;
	
	private String sangPumNm;

	private String brandNm;
	
	private java.sql.Timestamp regDate;
/*	private String sangPumFullCd;*/
}
