package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

/**
 * @Desc : Erp가격정보 수신
 * @FileName : ErpGamGa
 * @Project : shop.interface
 * @Date : 2019. 02. 15
 * @Author : 김영진
 */

@Data
public class InterfacesGamGaViewToErp {

	private String prdtDscntPrice;
	/**
	 * 설명 : 시작시간
	 */
	private String dscntStartDt;

	/**
	 * 설명 : 종료시간
	 */
	private String dscntEndDt;

	/*
	 * 설명 : 상품코드
	 */
	private String prdtCode;

	/*
	 * 설명 : 협력업체 아이디
	 */
	private String vndrId;

}
