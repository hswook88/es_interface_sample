package kr.co.shop.interfaces.zinterfacesdb.model.master;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesSabangnet;
import lombok.Data;

@Data
public class InterfacesSabangnetSetItem2 extends InterfacesSabangnet {

	/** 상품명(필수) */
	private String goodsNm;

	/** 자체상품코드(필수) */
	private String companyGoodsCd;

	/** 상품상태(필수) */
	private String status;

	/** 원가(필수) */
	private String goodsCost;

	/** 판매가(필수) */
	private String goodsPrice;

	/** TAG가(소비자가)(필수) */
	private String goodsConsumerPrice;

}

