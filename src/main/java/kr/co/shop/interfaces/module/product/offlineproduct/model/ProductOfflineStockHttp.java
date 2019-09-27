package kr.co.shop.interfaces.module.product.offlineproduct.model;

import lombok.Data;

@Data
public class ProductOfflineStockHttp {

	/**
	 * 재고 구분(AW: 창고 , AS : 매장)
	 */
	private String gubun;
	/**
	 * 실 재고
	 */
	private int currQty;

}
