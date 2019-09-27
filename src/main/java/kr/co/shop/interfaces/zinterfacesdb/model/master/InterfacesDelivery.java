package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesDelivery {

	/**
	 * 주문번호
	 */
	private String orderNum;

	/**
	 * 주문수량
	 */
	private int orderCount;

	/**
	 * 배송ID
	 */
	private String dlvyId;
}
