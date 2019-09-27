package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class StoreOrderHistory {

	/**
	 * sale_date
	 */
	private String saleDate;

	/**
	 * storeNm
	 */
	private String storeNm;

	/**
	 * deal_no
	 */
	private String dealNo;

	/**
	 * product_nm
	 */
	private String productNm;

	/**
	 * color
	 */
	private String color;

	/**
	 * size_nm
	 */
	private String sizeNm;

	/**
	 * sale_qty
	 */
	private String saleQty;

	/**
	 * consumer_price
	 */
	private String consumerPrice;

	/**
	 * charge_price
	 */
	private String chargePrice;

	/**
	 * sales_price
	 */
	private String salesPrice;

	/**
	 * deal_flag
	 */
	private String dealFlag;

}
